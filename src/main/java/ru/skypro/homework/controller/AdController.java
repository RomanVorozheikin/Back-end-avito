package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.CreateAdDto;
import ru.skypro.homework.dto.ad.FullAdDto;
import ru.skypro.homework.dto.ad.ResponseWrapperAds;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.NonExistentCommentException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

/**
 * @author rvorozheikin
 */
@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;
    private final AdMapper adMapper;
    private final UserService userService;

    /**
     * Method for getting all advertisements
     * @return {@link HttpStatus#OK} with all ads in {@link ResponseWrapperAds}  instance
     * @author rvorozheikin
     */
    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adMapper.mapAdsListToResponseWrapperAds(adService.findAll()));
    }

    /**
     * Receiving advertisements from an authorized user
     * @param authentication (inject) authorized user authentication info
     * @return {@link HttpStatus#OK} with all ads of authorized user in {@link ResponseWrapperAds} instance
     * @author rvorozheikin
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getMyAds(Authentication authentication) {
        User author = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(adMapper.mapAdsListToResponseWrapperAds(author.getAds()));
    }

    /**
     * Posting an ad
     * @param authentication (inject) authorized user authentication info
     * @param image {@link MultipartFile} image ad
     * @param createAdDto characteristics ad
     * @return {@link HttpStatus#CREATED} ad created by {@link AdDto} instance
     * @author rvovrozheikin
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> postAd(Authentication authentication,
                                        @RequestPart("image") MultipartFile image,
                                        @RequestPart("properties") CreateAdDto createAdDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.createAd(authentication.getName(), image, createAdDto));
    }

    /**
     * Getting information about an ad
     * @param adId {@link Integer} Id ad
     * @return {@link HttpStatus#OK} info ad with {@link FullAdDto} instance
     * {@link HttpStatus#NOT_FOUND} if not existed
     * @author rvorozheikin
     */
    @GetMapping("/{id}")
    public ResponseEntity<FullAdDto> getInfoAd(@PathVariable("id") Integer adId) {
        Optional<Ad> targetAdOpt = adService.findById(adId);
        return targetAdOpt.map(ad -> ResponseEntity.ok(adService.createFullAdDto(ad)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Delete ad with target {@code adId}
     * @param authentication (inject) authorized user authentication info
     * @param adId variable from URL
     * @return {@link HttpStatus#OK} if target ad existed and initiator is admin or author, <br>
     * {@link HttpStatus#NOT_FOUND} if target ad not existed, <br>
     * {@link HttpStatus#FORBIDDEN} if initiator not admin and not author
     * @author rvorozeikin
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(Authentication authentication,
                                      @PathVariable("id") Integer adId) {
        Optional<Ad> targetAdOpt = adService.findById(adId);
        if (targetAdOpt.isPresent()) {
            if (isUserAdminOrAuthor(authentication.getName(), targetAdOpt)) {
                adService.deleteById(adId);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    /**
     * Edit ad with target {@code adId}
     * @param authentication (inject) authorized user authentication info
     * @param adId variable from URL
     * @param createAdDto new ad info
     * @return {@link HttpStatus#OK} with edited ad in AdDto instance if target ad existed
     * and initiator is admin or author, <br>
     * {@link HttpStatus#NOT_FOUND} if target ad not existed, <br>
     * {@link HttpStatus#FORBIDDEN} if initiator not admin and not author
     * @author rvovrozeikin
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAdInfo(
            Authentication authentication,
            @PathVariable("id") Integer adId,
            @RequestBody CreateAdDto createAdDto
    ) {
        Optional<Ad> targetAd = adService.findById(adId);

        if (targetAd.isPresent()) {
            if (isUserAdminOrAuthor(authentication.getName(), targetAd)) {
                return ResponseEntity.ok(adService.updateAdInfo(targetAd.get(), createAdDto));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Edit image of ad with target {@code adId}
     * @param authentication (inject) authorized user authentication info
     * @param adId variable from URL
     * @param image {@link MultipartFile} with new image
     * @return {@link HttpStatus#OK} with edited ad if target ad existed and initiator is admin or author, <br>
     * {@link HttpStatus#NOT_FOUND} if target ad not existed, <br>
     * {@link HttpStatus#FORBIDDEN} if initiator not admin and not author
     * @author rvovrozeikin
     */
    @PatchMapping(value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> updateAdImage(
            Authentication authentication,
            @PathVariable("id") Integer adId,
            @RequestBody MultipartFile image
    ) {
        Optional<Ad> targetAd = adService.findById(adId);
        if (targetAd.isPresent()) {
            if (isUserAdminOrAuthor(authentication.getName(), targetAd)) {
                return ResponseEntity.ok().body(adService.updateAdImage(targetAd.get(),image));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    /**
     * Check that user is admin or ad's author
     * @param email user email
     * @param targetAdOpt target ad (Optional)
     * @return {@code true} if user with {@code targetEmail} is admin or author of {@code targetAd}, <br>
     * {@code false} if not
     * @author rvovrozeikin
     */
    private boolean isUserAdminOrAuthor(String email, Optional<Ad> targetAdOpt) {
        User initiator = userService.findUserByEmail(email);
        return initiator.getRole() == Role.ADMIN
                || initiator.getEmail()
                .equals(targetAdOpt.orElseThrow(NonExistentCommentException::new).getAuthor().getEmail());
    }
}

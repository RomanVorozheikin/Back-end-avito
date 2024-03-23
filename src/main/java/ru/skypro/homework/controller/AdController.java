package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
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
    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adMapper.mapAdsListToResponseWrapperAds(adService.findAll()));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> postAd(Authentication authentication,
                                        @RequestPart("image") MultipartFile image,
                                        @RequestPart("properties") CreateAdDto createAdDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.createAd(authentication.getName(), image, createAdDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdDto> getInfoAd(@PathVariable("id") Integer adId) {
        Optional<Ad> targetAdOpt = adService.findById(adId);
        return targetAdOpt.map(ad -> ResponseEntity.ok(adService.createFullAdDto(ad)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

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

    private boolean isUserAdminOrAuthor(String name, Optional<Ad> targetAdOpt) {
        User initiator = userService.findUserByEmail(name);
        return initiator.getRole() == Role.ADMIN
                || initiator.getEmail()
                .equals(targetAdOpt.orElseThrow(NonExistentCommentException::new).getAuthor().getEmail());
    }
}

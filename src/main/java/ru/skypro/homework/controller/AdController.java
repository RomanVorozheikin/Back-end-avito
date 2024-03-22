package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateAdDto;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.AdService;

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
}

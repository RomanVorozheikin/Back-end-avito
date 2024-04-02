package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

/**
 * @author rvorozheikin
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    /**
     * Return user's avatar
     * @param fileName name of avatar file
     * @return {@code byte[]} - bytes of avatar file
     * @author rvorozheikin
     */
    @GetMapping(value = "/avatar/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getUserAvatar(@PathVariable("fileName") String fileName) {
        return imageService.getUserAvatar(fileName);
    }
    /**
     * Return ad's image
     * @param adId name of image file
     * @return {@code byte[]} - bytes of image file
     * @author rvorozheikin
     */
    @GetMapping(value = "/ad/{adId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageAd(@PathVariable("adId") Integer adId) {
        return imageService.getImageAd(adId);
    }
}

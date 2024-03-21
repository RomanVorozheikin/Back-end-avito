package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @GetMapping(value = "/avatar/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getUserAvatar(@PathVariable("fileName") String fileName) {
        return imageService.getUserAvatar(fileName);
    }
}

package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.service.ImageService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author rvorozheikin
 */
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    @Test
    public void getAvatarShouldOnlyCallGetUserAvatarMethodAndReturnBytes() {
        when(imageService.getUserAvatar(anyString())).thenReturn(new byte[0]);

        assertInstanceOf(byte[].class, imageController.getUserAvatar(" "));

        verify(imageService, only()).getUserAvatar(anyString());
    }

    @Test
    public void getAdImageShouldOnlyCallGetUserAvatarMethodAndReturnBytes() {
        when(imageService.getImageAd(anyInt())).thenReturn(new byte[0]);

        assertInstanceOf(byte[].class, imageController.getImageAd(anyInt()));

        verify(imageService, only()).getImageAd(anyInt());
    }
}
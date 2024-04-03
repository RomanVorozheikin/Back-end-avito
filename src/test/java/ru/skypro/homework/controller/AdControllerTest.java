package ru.skypro.homework.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.FullAdDto;
import ru.skypro.homework.dto.ad.ResponseWrapperAds;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdControllerTest {
    @Mock
    private AdService adService;
    @Mock
    private Authentication authentication;
    @Mock
    private UserService userService;

    @Spy
    private AdMapper adMapper;

    @InjectMocks
    private AdController adController;

    @Test
    public void getAllAdsShouldReturnResponseEntityWithResponseWrapperAdsInside() {
        Ad testAd = new Ad(1, new User(), "user", "image", 100, "title", null);
        List<Ad> ads = List.of(testAd);
        when(adService.findAll()).thenReturn(ads);

        ResponseEntity<ResponseWrapperAds> response = adController.getAllAds();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(response.getBody().getCount(),ads.size());
    }

    @Test
    public void getInfoAdStatusNotFound() {
        Integer testId = 1;

        when(adService.findById(testId)).thenReturn(Optional.empty());

        ResponseEntity<FullAdDto> response = adController.getInfoAd(testId);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void getInfoAdStatusOK() {
        Integer testId = 1;
        Ad testAd = new Ad(1, new User(), "description", "image", 100, "title", null);
        Optional<Ad> testAdOpt = Optional.of(testAd);

        when(adService.findById(testId)).thenReturn(testAdOpt);

        ResponseEntity<FullAdDto> response = adController.getInfoAd(testId);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void deleteByAdStatusForbidden() {
        User testAuthor = new User("author", "firstName", "lastName", "phone", Role.USER, "image", "12345", null, null);
        User testNotAuthor = new User("notAuthor", "firstName", "lastName", "phone", Role.USER, "image", "12345", null, null);

        Ad testAd = new Ad(10, testAuthor, "description", "image", 123, "title", null);
        Optional<Ad> adOptionalTEST = Optional.of(testAd);

        when(adService.findById(testAd.getPk())).thenReturn(adOptionalTEST);
        when(authentication.getName()).thenReturn(testNotAuthor.getEmail());
        when(userService.findUserByEmail(authentication.getName())).thenReturn(testNotAuthor);

        ResponseEntity<?> response = adController.deleteAd(authentication, testAd.getPk());

        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }

    @Test
    public void deleteByAdStatusOk() {
        User testAuthor = new User("author", "firstName", "lastName", "phone", Role.USER, "image", "12345", null, null);
        Ad testAd = new Ad(10, testAuthor, "description", "image", 123, "title", null);
        Optional<Ad> adOptionalTEST = Optional.of(testAd);

        when(adService.findById(testAd.getPk())).thenReturn(adOptionalTEST);
        when(authentication.getName()).thenReturn(testAuthor.getEmail());
        when(userService.findUserByEmail(testAuthor.getEmail())).thenReturn(testAuthor);

        ResponseEntity<?> response = adController.deleteAd(authentication, testAd.getPk());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
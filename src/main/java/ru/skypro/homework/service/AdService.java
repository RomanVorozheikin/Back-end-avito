package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author rvorozheikin
 */
@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserService userService;
    private final ImageService imageService;

    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    @Transactional
    public AdDto createAd(String email, MultipartFile file, CreateAdDto createAdDto) {
        Ad newAd = adMapper.mapCreateAdDtoToAd(createAdDto);

        newAd.setAuthor(userService.findUserByEmail(email));
        newAd = save(newAd);

        newAd.setImage(imageService.uploadImageAd(newAd.getPk(), file));
        save(newAd);
        return adMapper.mapAdToAdDto(newAd);
    }

    private Ad save(Ad ad) {
        return adRepository.save(ad);
    }
}
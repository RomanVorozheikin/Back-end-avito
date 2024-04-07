package ru.rvorozheikin.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rvorozheikin.homework.entity.Ad;
import ru.rvorozheikin.homework.mapper.AdMapper;
import ru.rvorozheikin.homework.dto.ad.AdDto;
import ru.rvorozheikin.homework.dto.ad.CreateAdDto;
import ru.rvorozheikin.homework.dto.ad.FullAdDto;
import ru.rvorozheikin.homework.entity.User;
import ru.rvorozheikin.homework.repository.AdRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    /**
     * Return all ads
     * @return {@link List< Ad >} of {@link Ad}
     * @author rvorozheikin
     */
    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    /**
     * Create new ad
     * @param email author's username (email)
     * @param file new ad's image
     * @param createAdDto new ad's data
     * @return created ad in {@link AdDto} instance
     * @author rvorozheikin
     */
    @Transactional
    public AdDto createAd(String email, MultipartFile file, CreateAdDto createAdDto) {
        Ad newAd = adMapper.mapCreateAdDtoToAd(createAdDto);

        newAd.setAuthor(userService.findUserByEmail(email));
        newAd = save(newAd);

        newAd.setImage(imageService.uploadImageAd(newAd.getPk(), file));
        save(newAd);
        return adMapper.mapAdToAdDto(newAd);
    }

    /**
     * Save ad
     * @param ad target {@link Ad}
     * @return saved {@link Ad}
     * @author rvorozheikin
     */
    private Ad save(Ad ad) {
        return adRepository.save(ad);
    }

    /**
     * Return target ad
     * @param adId id of target ad
     * @return {@link Optional<Ad>} with target {@link Ad}, <br>
     * {@link Optional#empty()} if target ad not existed
     * @author rvorozheikin
     */
    public Optional<Ad> findById(Integer adId) {
        return adRepository.findById(adId);
    }

    /**
     * Create {@link FullAdDto} instance
     * @param ad target {@link Ad}
     * @return {@link FullAdDto} instance for target {@link Ad}
     * @author rvorozheikin
     */
    public FullAdDto createFullAdDto(Ad ad) {
        User author = ad.getAuthor();
        return adMapper.mapAdAndAuthorToFullAdDto(author, ad);
    }
    /**
     * Delete target ad and it's image
     * @param adId id of target ad
     */
    public void deleteById(Integer adId) {
        imageService.deleteAdImage(adId);
        adRepository.deleteById(adId);
    }
    /**
     * Edit target ad
     * @param targetAd target {@link Ad}
     * @param newData {@link CreateAdDto} with new ad's data
     * @return edited ad in {@link AdDto} instance
     * @author rvorozheikin
     */
    public AdDto updateAdInfo(Ad targetAd, CreateAdDto newData) {
        targetAd.setTitle(newData.getTitle());
        targetAd.setPrice(newData.getPrice());
        targetAd.setDescription(newData.getDescription());
        save(targetAd);
        return adMapper.mapAdToAdDto(targetAd);
    }
    /**
     * Edit ad's image
     * @param ad target {@link Ad}
     * @param image {@link MultipartFile} with new image
     * @return {@code byte[]} - bytes of new image
     * @author rvorozheikin
     */
    @Transactional
    public byte[] updateAdImage(Ad ad, MultipartFile image) {
        imageService.uploadImageAd(ad.getPk(), image);
        return imageService.getImageAd(ad.getPk());
    }
}

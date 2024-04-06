package ru.rvorozheikin.homework.mapper;

import org.springframework.stereotype.Component;
import ru.rvorozheikin.homework.dto.ad.AdDto;
import ru.rvorozheikin.homework.dto.ad.CreateAdDto;
import ru.rvorozheikin.homework.dto.ad.FullAdDto;
import ru.rvorozheikin.homework.dto.ad.ResponseWrapperAds;
import ru.rvorozheikin.homework.entity.Ad;
import ru.rvorozheikin.homework.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rvorozheikin
 */
@Component
public class AdMapper {
    /**
     * Map {@link List} of {@link Ad} to {@link ResponseWrapperAds}
     *
     * @param allAds {@link List} of {@link Ad}
     * @return created {@link ResponseWrapperAds}
     * @author rvorozheikin
     */
    public ResponseWrapperAds mapAdsListToResponseWrapperAds(List<Ad> allAds) {
        List<AdDto> adDtoList = allAds.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return new ResponseWrapperAds(adDtoList.size(), adDtoList);
    }
    /**
     * Map {@link Ad} to {@link AdDto}
     *
     * @param ad target {@link Ad}
     * @return created {@link AdDto}
     * @author rvorozheikin
     */
    public AdDto mapEntityToDto(Ad ad) {
        return new AdDto(
                ad.getPk(),
                ad.getImage(),
                ad.getPrice(),
                ad.getTitle(),
                ad.getAuthor().getUserId()
        );
    }
    /**
     * Map {@link CreateAdDto} to {@link Ad}
     *
     * @param createAdDto target {@link CreateAdDto}
     * @return created {@link Ad}
     * @author rvorozheikin
     */
    public Ad mapCreateAdDtoToAd(CreateAdDto createAdDto) {
        return new Ad(
                new User(),
                createAdDto.getDescription(),
                "",
                createAdDto.getPrice(),
                createAdDto.getTitle(),
                new ArrayList<>()
        );
    }

    /**
     * Map {@link AdDto} to {@link Ad}
     *
     * @param ad target {@link AdDto}
     * @return created {@link Ad}
     * @author rvorozheikin
     */
    public AdDto mapAdToAdDto(Ad ad) {
        return new AdDto(
                ad.getAuthor().getUserId(),
                ad.getImage(),
                ad.getPrice(),
                ad.getTitle(),
                ad.getPk()
                );
    }

    /**
     * Map {@link Ad} and {@link User} to {@link FullAdDto}
     *
     * @param ad     target {@link Ad}
     * @param author target {@link User}
     * @return created {@link FullAdDto}
     * @author rvorozheikin
     */
    public FullAdDto mapAdAndAuthorToFullAdDto(User author, Ad ad) {
        return new FullAdDto(
                ad.getPk(),
                author.getFirstName(),
                author.getLastName(),
                ad.getDescription(),
                author.getEmail(),
                ad.getImage(),
                author.getPhone(),
                ad.getPrice(),
                ad.getTitle()
        );
    }
}

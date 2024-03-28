package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateAdDto;
import ru.skypro.homework.dto.FullAdDto;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rvorozheikin
 */
@Component
public class AdMapper {

    public ResponseWrapperAds mapAdsListToResponseWrapperAds(List<Ad> allAds) {
        List<AdDto> adDtoList = allAds.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return new ResponseWrapperAds(adDtoList.size(), adDtoList);
    }

    public AdDto mapEntityToDto(Ad ad) {
        return new AdDto(
                ad.getPk(),
                ad.getImage(),
                ad.getPrice(),
                ad.getTitle(),
                ad.getAuthor().getUserId()
        );
    }

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

    public AdDto mapAdToAdDto(Ad ad) {
        return new AdDto(
                ad.getAuthor().getUserId(),
                ad.getImage(),
                ad.getPrice(),
                ad.getTitle(),
                ad.getPk()
                );
    }

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

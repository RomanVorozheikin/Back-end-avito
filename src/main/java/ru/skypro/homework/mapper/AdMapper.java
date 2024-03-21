package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.Ad;

import java.util.List;
import java.util.stream.Collectors;

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
}

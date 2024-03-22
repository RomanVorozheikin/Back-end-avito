package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    public List<Ad> findAll() {
        return adRepository.findAll();
    }
}

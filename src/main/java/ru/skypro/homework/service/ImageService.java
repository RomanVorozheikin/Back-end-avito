package ru.skypro.homework.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author rvorozheikin
 */
@Service
public class ImageService {
    @Value("${path.to.user.photo}")
    private String pathDirAvatar;
    @Value("${path.to.ads.image}")
    private String pathDirImageAd;
    @Value("${image.postfix}")
    private String imagePostfix;

    @SneakyThrows
    public byte[] getUserAvatar(String fileName) {
        return Files.readAllBytes(Path.of(pathDirAvatar, fileName + imagePostfix));
    }

    @SneakyThrows
    public byte[] getImageAd(Integer adId) {
        return Files.readAllBytes(Path.of(pathDirImageAd, adId + imagePostfix));
    }

    public String uploadUserAvatar(String userName, MultipartFile file) {
        Path filePath = Path.of(pathDirAvatar, userName + imagePostfix);
        uploadImage(filePath, file);
        return buildURLTailToImage(pathDirAvatar, userName);
    }

    public String uploadImageAd(Integer adId, MultipartFile file) {
        Path filePath = Path.of(pathDirImageAd, adId + imagePostfix);
        uploadImage(filePath, file);
        return buildURLTailToImage(pathDirImageAd, adId.toString());
    }

    @SneakyThrows
    private void uploadImage(Path filePath, MultipartFile file) {
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        file.transferTo(filePath);
    }
    private String buildURLTailToImage(String dir, String fileName) {
        return "/" + dir + "/" + fileName;
    }
}

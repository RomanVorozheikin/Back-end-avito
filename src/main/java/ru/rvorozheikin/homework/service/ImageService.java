package ru.rvorozheikin.homework.service;

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

    /**
     * Return bytes of user's avatar
     * @param fileName name of avatar file
     * @return {@code byte[]} - bytes of avatar file
     * @author rvorozheikin
     */
    @SneakyThrows
    public byte[] getUserAvatar(String fileName) {
        return Files.readAllBytes(Path.of(pathDirAvatar, fileName + imagePostfix));
    }
    /**
     * Return bytes of ad's image
     * @param adId name of image file
     * @return {@code byte[]} - bytes of image file
     * @author rvorozheikin
     */
    @SneakyThrows
    public byte[] getImageAd(Integer adId) {
        return Files.readAllBytes(Path.of(pathDirImageAd, adId + imagePostfix));
    }

    /**
     * Upload user's avatar
     * @param userName target user's username (email)
     * @param file {@link MultipartFile} with avatar
     * @return string with URL-tail to avatar
     * @author rvorozheikin
     */
    public String uploadUserAvatar(String userName, MultipartFile file) {
        Path filePath = Path.of(pathDirAvatar, userName + imagePostfix);
        uploadImage(filePath, file);
        return buildURLTailToImage(pathDirAvatar, userName);
    }

    /**
     * Upload ad's image
     * @param adId target ad's id
     * @param file {@link MultipartFile} with image
     * @return string with URL-tail to image
     * @author rvorozheikin
     */
    public String uploadImageAd(Integer adId, MultipartFile file) {
        Path filePath = Path.of(pathDirImageAd, adId + imagePostfix);
        uploadImage(filePath, file);
        return buildURLTailToImage(pathDirImageAd, adId.toString());
    }

    /**
     * Upload image file to filesystem
     * @param filePath file's path
     * @param file {@link MultipartFile} target image file
     * @author rvorozheikin
     */
    @SneakyThrows
    private void uploadImage(Path filePath, MultipartFile file) {
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        file.transferTo(filePath);
    }
    /**
     * Build string with URL-tail to target file
     * @param dir file directory
     * @param fileName name of file
     * @return string with URL-tail to target file
     * @author rvorozheikin
     */
    private String buildURLTailToImage(String dir, String fileName) {
        return "/" + dir + "/" + fileName;
    }

    /**
     * Delete ad's image
     * @param adId target ad's id
     * @author rvorozheikin
     */
    @SneakyThrows
    public void deleteAdImage(Integer adId) {
        Files.deleteIfExists(Path.of(pathDirImageAd, adId + imagePostfix));
    }
}

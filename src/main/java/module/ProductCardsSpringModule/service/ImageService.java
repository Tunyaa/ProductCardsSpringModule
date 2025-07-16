package module.ProductCardsSpringModule.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tunyaa
 */
@Service
public class ImageService {

    public String saveImage(MultipartFile imageFile) throws IOException {
        // url куда помещать изображение
        String uploadDir = "src/main/resources/static/images/";
        // Иминование изображения
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        // Создаём путь в файловой системе
        Path uploadPath = Paths.get(uploadDir);
        // Если папки не существует, она создаётся
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Создание потока для копирования изображения по указанному url(перезаписывает если существует)
        try (InputStream inputStream = imageFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }
}

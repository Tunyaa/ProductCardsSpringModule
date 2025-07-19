package module.ProductCardsSpringModule.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import module.ProductCardsSpringModule.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tunyaa
 */
@Service
public class ImageService {

    // Копирует изображение в деррикторию
    private String saveImage(MultipartFile imageFile) throws IOException {
        // url куда помещать изображение
        String uploadDir = "src/main/resources/static/images/";
        // Иминование изображения
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        // Создаёт путь в файловой системе
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

    // Удаляет изображение
    public void deleteImage(String fileUrl) throws IOException {

        // url откуда удалять изображение "src/main/resources/static/images/" - images/
        String deletePath = "src/main/resources/static/" + fileUrl; // Где fileUrl содержит images/imgName.jpg

        // Если существует такой файл, он удаляется.        
        Path path = Paths.get(deletePath);
        Files.delete(path);
    }

    // Присваевает полю продукта url изображения, если изображение передано
    public void saveImageIfExist(MultipartFile imageFile, Product product) throws IOException {
        // Сохраняет изображение, если оно было загружено
        if (!imageFile.isEmpty()) {
            // Изображение копируется по url
            String fileName = saveImage(imageFile);
            // url сохраняется в поле продукта
            product.setImg("/images/" + fileName);
        }
    }
}

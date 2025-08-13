package module.ProductCardsSpringModule.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import module.ProductCardsSpringModule.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

/**
 *
 * @author tunyaa
 */
public class ImageServiceTest {

    public ImageServiceTest() {
    }

    @Test // Проверяет соответствие генерируемого имени изображения
    void saveImageIfExist_ProductImgNameGeneration_ShouldSaveExpectedNameFormat(@TempDir Path uploadDir) throws IOException {

        // Настраивает сервис
        ImageService imageService = new ImageService();
        // Указывает деррикторию для создания папок для сохранения файлов
        imageService.setUploadDir(uploadDir.toString() + "/");
        // Указывает деррикторию для сохранения изображений
        imageService.setImgDir("images/");
        // Создаёт тестовый продукт
        Product product = new Product();
        // Создаёт тестовый файл
        MockMultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        // Выполняется тестируемый метод
        imageService.saveImageIfExist(file, product);
        // images/1754928000388_test.jpg
        String imgUrl = product.getImg();
        // Проверяет соответствие имени изображения
        boolean actual = imgUrl.matches("images/\\d+_test\\.jpg");

        assertTrue(actual);
    }

    @Test // Проверяет соответствие пути для создания папки и сохранения изображения
    void saveImageIfExist_SaveFileInDir_ShouldSaveFileInSpecifiedDir(@TempDir Path uploadDir) throws IOException {
        String imgDir = "images/";
        // Настраивает сервис
        ImageService imageService = new ImageService();
        // Указывает деррикторию для создания папок для сохранения файлов
        imageService.setUploadDir(uploadDir.toString() + "/");
        // Указывает деррикторию для сохранения изображений
        imageService.setImgDir(imgDir);
        // Создаёт тестовый продукт
        Product product = new Product();
        // Создаёт тестовый файл
        MockMultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        // Выполняется тестируемый метод
        imageService.saveImageIfExist(file, product);
        // Получает путь
        Path path = Paths.get(uploadDir + "/" + product.getImg());
        // Проверяет наличие созданного файла по ожидаемому пути
        boolean actual = Files.exists(path);

        assertTrue(actual);
    }

    @Test // Проверяет метод если MultipartFile == null
    void saveImageIfExist_WhenFileIsNull_ShouldNotInvokeSaveMethod(@TempDir Path uploadDir) throws IOException {
        String imgDir = "images/";
        // Настраивает сервис
        ImageService imageService = new ImageService();
        // Указывает деррикторию для создания папок для сохранения файлов
        imageService.setUploadDir(uploadDir.toString() + "/");
        // Указывает деррикторию для сохранения изображений
        imageService.setImgDir(imgDir);
        // Создаёт тестовый продукт
        Product product = new Product();
        // Создаёт тестовый файл
        MockMultipartFile file = null;
        // Выполняется тестируемый метод
        imageService.saveImageIfExist(file, product);
        // Получает путь
        Path path = Paths.get(uploadDir + "/" + product.getImg());
        // Проверяет отсутствие  файла по ожидаемому пути
        boolean actual = Files.exists(path);

        assertFalse(actual);
    }

    @Test // Проверяет метод если MultipartFile.isEmpty() == true
    void saveImageIfExist_ProductImgNameGeneration_ShouldSave(@TempDir Path uploadDir) throws IOException {

        // Настраивает сервис
        ImageService imageService = new ImageService();
        // Указывает деррикторию для создания папок для сохранения файлов
        imageService.setUploadDir(uploadDir.toString() + "/");
        // Указывает деррикторию для сохранения изображений
        imageService.setImgDir("images/");
        // Создаёт тестовый продукт
        Product product = new Product();
        // Создаёт тестовый файл
        MockMultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "".getBytes()
        );
        // Выполняется тестируемый метод
        imageService.saveImageIfExist(file, product);
        // Получает путь
        Path path = Paths.get(uploadDir + "/" + product.getImg());
        // Проверяет отсутствие файла по ожидаемому пути
        boolean actual = Files.exists(path);

        assertFalse(actual);
    }

    @Test // Удаляет существующий файл
    void deleteImage_DeleteIfExist_ShouldDeleteFile(@TempDir Path uploadDir) throws IOException {
        // Настраивает сервис
        ImageService imageService = new ImageService();
        // Указывает деррикторию для создания папок для сохранения файлов
        imageService.setUploadDir(uploadDir.toString() + "/");
        // Указывает деррикторию для сохранения изображений
        imageService.setImgDir("images/");

        // Создаёт тестовый файл
        // Создаёт путь к файлу: (uploadDir)/ | images/ | testFile.jpg
        Path testFile = uploadDir.resolve(imageService.getImgDir() + "testFile.jpg");
        // Создаёт деррикторию images по пути: (uploadDir)/ | images/
        Files.createDirectories(testFile.getParent());
        // Записывает в файл массив байт
        Files.write(testFile, "Test".getBytes());

        boolean exist = true;
        // Если файл был создан, он удаляется
        if (Files.exists(testFile)) {
            imageService.deleteImage("/images/testFile.jpg");
            exist = Files.exists(testFile);
        }
        // Файл должен отсутствовать после удаления
        assertFalse(exist);
    }

    @Test // Ошибка не возникает, если аргумент метода null
    void deleteImage_DeleteIfExist(@TempDir Path uploadDir) {
        // Настраивает сервис
        ImageService imageService = new ImageService();
        assertDoesNotThrow((Executable) () -> imageService.deleteImage(null));
    }

}

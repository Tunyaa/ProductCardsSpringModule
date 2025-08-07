package module.ProductCardsSpringModule.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import module.ProductCardsSpringModule.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tunyaa
 */
public class ImageServiceTest {
    
    public ImageServiceTest() {
    }
    
     @TempDir
     Path tempDir; // Временная директория для тестов

    private ImageService createImageService() {
        ImageService service = new ImageService();
        service.setUploadDir(tempDir.toString() + "/TestUploads");
        service.setImgDir("images/");
        return service;
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
//    @Test
//void saveImage_ShouldSaveFileAndReturnFilename() throws IOException {
//    // Arrange
//    ImageService service = createImageService();
//        MockMultipartFile file = new MockMultipartFile(
//        "image", "test.jpg", "image/jpeg", "test content".getBytes()
//    );
//
//    // Act
//    String filename = service.saveImage(file);
//
//    // Assert
//    assertNotNull(filename);
//    assertTrue(Files.exists(tempDir.resolve("images/" + filename)));
//}

@Test
void deleteImage_ShouldDeleteExistingFile() throws IOException {
    // Arrange
    ImageService service = createImageService();
    Path testFile = tempDir.resolve("images/test.jpg");
    Files.createDirectories(testFile.getParent());
    Files.write(testFile, "content".getBytes());

    // Act
    service.deleteImage("images/test.jpg");

    // Assert
    assertFalse(Files.exists(testFile));
}

@Test
void deleteImage_ShouldNotThrowWhenFileNotExists() {
    // Arrange
    ImageService service = createImageService();

    // Act & Assert
    assertDoesNotThrow(() -> service.deleteImage("images/nonexistent.jpg"));
}

@Test
void saveImageIfExist_ShouldSaveImageWhenFileNotEmpty() throws IOException {
    // Arrange
    ImageService service = createImageService();
    Product product = new Product();
    MockMultipartFile file = new MockMultipartFile(
        "image", "test.jpg", "image/jpeg", "content".getBytes()
    );

    // Act
    service.saveImageIfExist(file, product);

    // Assert
    assertNotNull(product.getImg());
    assertTrue(product.getImg().startsWith("images/"));
}

@Test
void saveImageIfExist_ShouldDoNothingWhenFileEmpty() throws IOException {
    // Arrange
    ImageService service = createImageService();
    Product product = new Product();
    MockMultipartFile emptyFile = new MockMultipartFile("image", new byte[0]);

    // Act
    service.saveImageIfExist(emptyFile, product);

    // Assert
    assertNull(product.getImg());
}
//
//    /**
//     * Test of deleteImage method, of class ImageService.
//     */
//    @Test
//    public void testDeleteImage() throws Exception {
//        System.out.println("deleteImage");
//        String fileUrl = "";
//        ImageService instance = new ImageService();
//        instance.deleteImage(fileUrl);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of saveImageIfExist method, of class ImageService.
//     */
//    @Test
//    public void testSaveImageIfExist() throws Exception {
//        System.out.println("saveImageIfExist");
//        MultipartFile imageFile = null;
//        Product product = null;
//        ImageService instance = new ImageService();
//        instance.saveImageIfExist(imageFile, product);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}

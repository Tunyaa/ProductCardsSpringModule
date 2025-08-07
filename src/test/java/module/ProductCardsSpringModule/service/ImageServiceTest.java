package module.ProductCardsSpringModule.service;

import java.nio.file.Path;
import module.ProductCardsSpringModule.model.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.io.TempDir;
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
        service.setUploadDir(tempDir.toString() + "/");
        service.setImgDir("images/");
        return service;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of deleteImage method, of class ImageService.
     */
    @Test
    public void testDeleteImage() throws Exception {
        System.out.println("deleteImage");
        String fileUrl = "";
        ImageService instance = new ImageService();
        instance.deleteImage(fileUrl);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveImageIfExist method, of class ImageService.
     */
    @Test
    public void testSaveImageIfExist() throws Exception {
        System.out.println("saveImageIfExist");
        MultipartFile imageFile = null;
        Product product = null;
        ImageService instance = new ImageService();
        instance.saveImageIfExist(imageFile, product);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUploadDir method, of class ImageService.
     */
    @Test
    public void testGetUploadDir() {
        System.out.println("getUploadDir");
        ImageService instance = new ImageService();
        String expResult = "";
        String result = instance.getUploadDir();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUploadDir method, of class ImageService.
     */
    @Test
    public void testSetUploadDir() {
        System.out.println("setUploadDir");
        String uploadDir = "";
        ImageService instance = new ImageService();
        instance.setUploadDir(uploadDir);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImgDir method, of class ImageService.
     */
    @Test
    public void testGetImgDir() {
        System.out.println("getImgDir");
        ImageService instance = new ImageService();
        String expResult = "";
        String result = instance.getImgDir();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setImgDir method, of class ImageService.
     */
    @Test
    public void testSetImgDir() {
        System.out.println("setImgDir");
        String imgDir = "";
        ImageService instance = new ImageService();
        instance.setImgDir(imgDir);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

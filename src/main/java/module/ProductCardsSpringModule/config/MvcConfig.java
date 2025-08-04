package module.ProductCardsSpringModule.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author tunyaa
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

//    @Value("${app.upload.dir}")
//    private String uploadDir;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:projectuploads/images/");
    }

    @PostConstruct
    public void init() {
        System.out.println("Текущая рабочая директория: " + System.getProperty("user.dir"));
    }
}

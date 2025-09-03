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
// Позволяет браузеру получать доступ к статическим файлам напрямую(по url).
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**") // URL-шаблон для доступа к файлам.
                .addResourceLocations("file:projectuploads/images/"); // Расположение папки в системе.
    }

    @PostConstruct
    public void init() {
        System.out.println("Текущая рабочая директория: " + System.getProperty("user.dir"));
    }
}

package module.ProductCardsSpringModule.config;

import module.ProductCardsSpringModule.model.User;
import module.ProductCardsSpringModule.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Создаем администратора
        if (!userRepository.existsByUsername("Tunyaa")) {
            User admin = new User();
            admin.setUsername("Tunyaa");
            admin.setPassword(passwordEncoder.encode("15Rudariy21"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

        // Создаем обычного пользователя
        if (!userRepository.existsByUsername("Kivi")) {
            User user = new User();
            user.setUsername("Kivi");
            user.setPassword(passwordEncoder.encode("Mango"));
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }

        // Создаем обычного пользователя
        if (!userRepository.existsByUsername("Gastroterra")) {
            User manager = new User();
            manager.setUsername("Gastroterra");
            manager.setPassword(passwordEncoder.encode("Labubu"));
            manager.setRole("ROLE_MANAGER");
            userRepository.save(manager);
        }
    }
}

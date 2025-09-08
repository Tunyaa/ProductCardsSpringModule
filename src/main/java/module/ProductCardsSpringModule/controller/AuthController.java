package module.ProductCardsSpringModule.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author tunyaa
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
  
    
    
}

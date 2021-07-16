package loc.abondare.sarafan.controller;

import loc.abondare.sarafan.repo.MessageRepo;
import loc.abondare.sarafan.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/")
public class MainController {
    private final UserDetailsRepo userDetailsRepo;
    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(UserDetailsRepo userDetailsRepo, MessageRepo messageRepo) {
        this.userDetailsRepo = userDetailsRepo;
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", oAuth2User != null ? userDetailsRepo.findById(oAuth2User.getAttribute("sub")).get() : null);
        data.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}

package com.disl.startercore.controllers.auth_thymeleaf;

import com.disl.commons.configs.AppProperties;
import com.disl.startercore.entities.Secret;
import com.disl.startercore.entities.User;
import com.disl.startercore.enums.UserTokenPurpose;
import com.disl.startercore.services.SecretService;
import com.disl.startercore.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Hidden
@Controller
public class VerificationController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private SecretService secretService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "verify", produces = MediaType.TEXT_HTML_VALUE)
    public String userVerifyHtml(Model model, @RequestParam(name = "verificationToken") String verificationToken) {
        Secret secret = secretService.findByUserTokenAndUserTokenPurpose(verificationToken, UserTokenPurpose.EMAIL_VERIFICATION);
        if (secret == null) {
            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "User Token Not Found.");
            return "dispatchMessage";
        }

        User user = userService.findById(secret.getUserId());
        if (user == null) {
            model.addAttribute("message", "User verification failed. Verification token is not from server. Please follow link from mail.");
            return "token not found";
        } else {
            user.setVerified(true);
            userService.saveUser(user);
            secretService.deleteSecret(secret);

            model.addAttribute("projectName", appProperties.getName());
            model.addAttribute("message", "User verified. You may now login.");
            return "dispatchMessage";
        }
    }
}

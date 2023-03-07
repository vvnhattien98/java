package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;

    private final EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping(value = "/get-credential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    @GetMapping()
    public String getHomePage(Authentication authentication,Model model, @ModelAttribute("newFile")FileForm newFile, @ModelAttribute("newNote")NoteForm newNote,
                              @ModelAttribute("newCredential")CredentialForm newCredential){
        model.addAttribute("credentials", this.credentialService.getAllCredentialsByUserId(getUserId(authentication)));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    public Integer getUserId(Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        return user.getUserId();
    }

    @PostMapping("/add-credential")
    public String addCredential(Authentication authentication, Model model,
                                @ModelAttribute("newFile")FileForm newFile,
                                @ModelAttribute("newNote")NoteForm newNote,
                                @ModelAttribute("newCredential") CredentialForm newCredential){
        String username = authentication.getName();
        String url = newCredential.getUrl();
        String password = newCredential.getPassword();
        String credentialId = newCredential.getCredentialId();
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptionPassword = encryptionService.encryptValue(password,encodedKey);

        if(credentialId.isEmpty()){
            credentialService.addCredential(url,username,newCredential.getUsername(),encodedKey,encryptionPassword);
        }else{
            Integer creId = getCredential(Integer.parseInt(credentialId)).getCredentialId();
            credentialService.updateCredential(creId,url,encodedKey,encryptionPassword, newCredential.getUsername());
        }

        model.addAttribute("credentials", credentialService.getAllCredentialsByUserId(getUserId(authentication)));
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredential(Model model, Authentication authentication,
                                   @ModelAttribute("newNote") NoteForm newNote,
                                   @ModelAttribute("newFile") FileForm newFile,
                                   @ModelAttribute("newCredential") CredentialForm newCredential,
                                   @PathVariable Integer credentialId){

        credentialService.deleteCredential(credentialId);
        model.addAttribute("credentials", credentialService.getAllCredentialsByUserId(getUserId(authentication)));
        model.addAttribute("result", "success");

        return "result";
    }
}

package com.udacity.jwdnd.course1.cloudstorage.controller;



import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;
    private final CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, EncryptionService encryptionService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newFile") FileForm newFile,
                              @ModelAttribute("newCredential")CredentialForm credentialForm, Model model){
        Integer userId = getUserId(authentication);
        model.addAttribute("files", this.fileService.getAllFileByUserId(userId));
        model.addAttribute("notes", noteService.getNotesByUser(userId));
        model.addAttribute("credentials", credentialService.getAllCredentialsByUserId(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    public Integer getUserId(Authentication authentication){
        String userName = authentication.getName();
        User user = userService.getUserByName(userName);
        return user.getUserId();
    }

    @PostMapping
    public String addFile(Authentication authentication, Model model,
                          @ModelAttribute("newFile") FileForm newFile,
                          @ModelAttribute("newNote") NoteForm newNote,
                          @ModelAttribute("newCredential") CredentialForm newCredential) throws IOException {
        String username = authentication.getName();
        User user = userService.getUserByName(username);
        Integer userId = user.getUserId();

        MultipartFile multipartFile = newFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();

        List<File> listFilesById = fileService.getAllFileByUserId(userId);

        boolean duplicatedFile = false;
        for (File file: listFilesById) {
            if (file.getFileName().equals(fileName)) {
                duplicatedFile = true;
                break;
            }
        }
        if(!duplicatedFile){
            fileService.addFile(multipartFile,username);
            model.addAttribute("result", "success");
        }else{
            model.addAttribute("message", "You have tried to add a duplicate file.");

            model.addAttribute("result", "error");
            model.addAttribute("message", "You have tried to add a duplicate file.");
        }
        model.addAttribute("files",listFilesById);
        return "result";
    }
    @GetMapping("/delete-file/{fileId}")
    public String deleteFile(Authentication authentication, Model model,
                             @ModelAttribute("newFile") FileForm newFile,
                             @ModelAttribute("newNote") NoteForm newNote,
                             @ModelAttribute("newCredential") CredentialForm newCredential,
                             @PathVariable("fileId") Integer fileId){
        fileService.deleteFile(fileId);
        model.addAttribute("files", fileService.getAllFileByUserId(getUserId(authentication)));
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping(
            value = "/download-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )

    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFileByName(fileName).getFileData();
    }

}

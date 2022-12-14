package io.springbootlabs.app.web.in;

import io.springbootlabs.domain.usecase.FileHandling;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileHandling fileHandling;

    @GetMapping("/images")
    public String getImages(Principal principal, final Model mode) {
        mode.addAttribute("images", fileHandling.findListBy(userId(principal)));
        return "content/file/image";
    }

    @PostMapping("/image")
    @ResponseBody
    public void uploadImage(@RequestParam("image")MultipartFile multipartFile, Principal principal) {
        fileHandling.save(userId(principal), multipartFile);
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public void getImage(@PathVariable Long id) {
        fileHandling.findBy(id);
    }

    @GetMapping("/bmp")
    public String getBmp() {
        return "content/file/bmp";
    }

    @GetMapping("/mp3")
    public String getMp3() {
        return "content/file/mp3";
    }

    private Long userId(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}

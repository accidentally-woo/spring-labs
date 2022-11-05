package io.springbootlabs.domain.file.service;

import io.springbootlabs.domain.file.File;
import io.springbootlabs.domain.file.FileRepository;
import io.springbootlabs.domain.file.Kind;
import io.springbootlabs.domain.file.Type;
import io.springbootlabs.domain.usecase.FileHandling;
import io.springbootlabs.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestInmemoryFileService implements FileHandling {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Override
    public List<File> findListBy(Long userId) {
        return fileRepository.findBy(userRepository.findById(userId).get(), Type.IMAGE);
    }

    @Override
    public File findBy(Long fileId) {
        return null;
    }

    @Override
    public Long save(Long userId, MultipartFile multipartFile) { //
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Type type = Type.filterType(multipartFile); //multipartFile.part.fileItem.fieldName
        Kind kind = Kind.valueOfName(multipartFile.getContentType()); // multipartFile.part.fileItem.contentTyoe
        Long size = multipartFile.getSize();
        File file = File.createFile(userRepository.findById(userId).get(), type, fileName, kind, size);

        String fileNameEncoded = UriUtils.encode(fileName, StandardCharsets.UTF_8); // 한글, 특수 문자 등 깨질 수 있으니 인코딩
        /*
        TODO :: Github Img Upload 방법 확인 하기
         */

        return fileRepository.save(file).getId();
    }

    public void resource(HttpServletResponse httpServletResponse) {
        var imgFile = new ClassPathResource("file/10mb.jpeg");

        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            int totalByte = StreamUtils.copy(imgFile.getInputStream(), httpServletResponse.getOutputStream());

            log.info("total image bytes : {}", totalByte);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<byte[]> resourceResponseEntity() {
        var imgFile = new ClassPathResource("file/10mb.jpeg");
        byte[] bytes;
        try {
            bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    public ResponseEntity<InputStreamResource> resourceResponseEntityAndInputStreamResource() {
        var imgFile = new ClassPathResource("file/10mb.jpeg");
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

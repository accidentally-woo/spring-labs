package io.springbootlabs.domain.usecase;

import io.springbootlabs.domain.file.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileHandling {
    @Query("select f from File f where f.user.id = ?1")
    List<File> findListBy(Long userId);

    File findBy(Long fileId);

    Long save(Long userId, MultipartFile multipartFile);
}

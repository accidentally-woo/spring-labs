package io.springbootlabs.domain.file;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.MissingFormatArgumentException;

@Getter
public enum Type {
    IMAGE, // image/jpeg, image/png
    BMP, // image/bmp
    MP_3,
    TEXT;

    public static Type filterType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        assert contentType != null;
        String[] split = contentType.split("/");

        if (split.length > 1) {
            if (split[0].equals(Type.TEXT.name().toLowerCase())) {
                return Type.TEXT;
            }
            if (split[1].equals(Type.BMP.name().toLowerCase())) {
                return Type.BMP;
            }
            if (split[0].equals(Type.IMAGE.name().toLowerCase())){
                return Type.IMAGE;
            }
            return Type.MP_3;
        }

        throw new MissingFormatArgumentException(contentType);
    }
}

package admin.adminsiteserver.common.aws.util;

import admin.adminsiteserver.common.aws.exception.ConvertFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class S3Util {

    private static final String FILE_NAME_FORMAT = "%s%s";

    public String createFileName(String path) {
        return String.format(FILE_NAME_FORMAT, path, UUID.randomUUID());
    }

    public File toFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());
        } catch (IOException exception) {
            throw new ConvertFileException();
        }
        return file;
    }

    public void deleteRequestFile(File file) {
        if (file.delete()) {
            log.debug("파일 삭제 성공");
        } else {
            log.warn("파일 삭제 실패");
        }
    }
}

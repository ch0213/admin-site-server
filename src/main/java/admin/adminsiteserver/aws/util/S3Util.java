package admin.adminsiteserver.aws.util;

import admin.adminsiteserver.aws.exception.ConvertFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class S3Util {
    private static final String FILE_NAME_FORMAT = "%s%s";

    public static String createFileName(String path) {
        return String.format(FILE_NAME_FORMAT, path, UUID.randomUUID());
    }

    public static File convertToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());
        } catch (IOException exception) {
            throw new ConvertFileException();
        }
        return file;
    }

    public static void delete(File file) {
        file.delete();
    }
}

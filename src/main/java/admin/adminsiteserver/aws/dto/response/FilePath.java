package admin.adminsiteserver.aws.dto.response;

import admin.adminsiteserver.aws.exception.ConvertFileException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.lang.reflect.Constructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilePath {
    private String fileName;

    @URL
    private String fileUrl;

    public static FilePath of(String fileName, String fileUrl) {
        return new FilePath(fileName, fileUrl);
    }

    public <T> T toFilePath(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(String.class, String.class);
            return constructor.newInstance(fileName, fileUrl);
        } catch (Exception e) {
            throw new ConvertFileException();
        }
    }

    public static <T> FilePath from(T filePath) {
        try {
            String fileName = (String) filePath.getClass().getMethod("getFileName").invoke(filePath);
            String fileUrl = (String) filePath.getClass().getMethod("getFileUrl").invoke(filePath);
            return new FilePath(fileName, fileUrl);
        } catch (Exception e) {
            throw new ConvertFileException();
        }
    }
}

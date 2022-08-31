package admin.adminsiteserver.aws.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilePath {
    private String fileName;
    private String fileUrl;

    public static FilePath of(String fileName, String fileUrl) {
        return new FilePath(fileName, fileUrl);
    }

    public <T> T toFilePath(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(String.class, String.class);
            return constructor.newInstance(fileName, fileUrl);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public static <T> FilePath from(T filePath) {
        try {
            String newFileName = "";
            String newFileUrl = "";
            for (Method method : filePath.getClass().getMethods()) {
                if (method.getName().equals("getFileName")) {
                    newFileName = (String) method.invoke(filePath);
                }
                if (method.getName().equals("getFileUrl")) {
                    newFileUrl = (String) method.invoke(filePath);
                }
            }
            return new FilePath(newFileName, newFileUrl);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}

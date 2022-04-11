package admin.adminsiteserver.common.aws.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilePathDto {
    private String fileName;
    private String fileUrl;

    public static FilePathDto of(String fileName, String fileUrl) {
        return new FilePathDto(fileName, fileUrl);
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

    public static <T> FilePathDto from(T filePath) {
        try {
            String newFileName = "", newFileUrl = "";
            for (Method method : filePath.getClass().getMethods()) {
                if (method.getName().equals("getFileName")) {
                    newFileName = (String) method.invoke(filePath);
                }
                if (method.getName().equals("getFileUrl")) {
                    newFileUrl = (String) method.invoke(filePath);
                }
            }
            return new FilePathDto(newFileName, newFileUrl);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}

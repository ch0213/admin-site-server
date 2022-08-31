package admin.adminsiteserver.aws.infrastructure;

import admin.adminsiteserver.aws.dto.response.FilePath;
import admin.adminsiteserver.aws.util.S3Util;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class S3Uploader {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.cloudfront.url}")
    private String cloudfront;

    private final AmazonS3 amazonS3;

    public List<FilePath> upload(List<MultipartFile> files, String path) {
        return files.stream()
                .map(file -> upload(file, path))
                .collect(Collectors.toList());
    }

    public FilePath upload(MultipartFile multipartFile, String path) {
        File file = S3Util.convertToFile(multipartFile);
        String fileName = S3Util.createFileName(path);

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        S3Util.delete(file);

        return FilePath.of(multipartFile.getOriginalFilename(), cloudfront + fileName);
    }

    public void delete(List<FilePath> filePaths) {
        filePaths.forEach(filePath -> {
            String fileName = filePath.getFileUrl().replace(cloudfront, "");
            if (!fileName.isBlank() && amazonS3.doesObjectExist(bucketName, fileName)) {
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            }
        });
    }
}

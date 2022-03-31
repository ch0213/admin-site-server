package admin.adminsiteserver.common.aws.infrastructure;

import admin.adminsiteserver.common.dto.FilePathDto;
import admin.adminsiteserver.common.aws.util.S3Util;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.cloudfront.url}")
    private String cloudfront;

    private final AmazonS3 amazonS3;
    private final S3Util s3Util;

    public List<FilePathDto> upload(List<MultipartFile> multipartFiles, String path) {
        List<FilePathDto> filePathDtos = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            filePathDtos.add(upload(file, path));
        }
        return filePathDtos;
    }

    public FilePathDto upload(MultipartFile multipartFile, String path) {
        File file = s3Util.toFile(multipartFile);
        String fileName = s3Util.createFileName(path);

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        s3Util.deleteRequestFile(file);

        return FilePathDto.of(multipartFile.getOriginalFilename(), cloudfront + fileName);
    }

    public void delete(List<String> fileUrls) {
        for (String fileUrl : fileUrls) {
            delete(fileUrl);
        }
    }

    public void delete(String fileUrl) {
        String fileName = fileUrl.replace(cloudfront, "");
        if (amazonS3.doesObjectExist(bucketName, fileName)) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        }
    }
}

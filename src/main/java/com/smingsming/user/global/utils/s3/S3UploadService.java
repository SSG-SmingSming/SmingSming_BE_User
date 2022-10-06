package com.smingsming.user.global.utils.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;



    public String store(FileInfoDto fileInfoDto, MultipartFile multipartFile) {
        File local = new File(MultipartUtil.getBaseDir());

        if(!local.exists()) {
            local.mkdir();
        }

        File file = new File(fileInfoDto.getLocalPath());

        try {
            multipartFile.transferTo(file);

            amazonS3Client.putObject(bucket, fileInfoDto.getRemotePath(), file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(file.exists())
                file.delete();
        }
        return amazonS3Client.getUrl(bucket, fileInfoDto.getRemotePath()).toString();
    }

}

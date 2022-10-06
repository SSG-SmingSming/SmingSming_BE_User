package com.smingsming.user.global.utils.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MultipartUtil {
    private static final String BASE_DIR = System.getProperty("user.home") + "/temp";

    public static String getBaseDir() {
        return BASE_DIR;
    }

    private static String bucket;

    @Value("${cloud.aws.s3.bucket}")
    public void setBucket( String value) {
        MultipartUtil.bucket = value;
    }


    private static String region;

    @Value("${cloud.aws.region.static}")
    public void setRegion(String value) {
        MultipartUtil.region = value;
    }


    /**
     * 파일에 대한 고유의 ID 발급
     * @return 36자리의 UUID
     */
    public static String createFileUUID() {
        return UUID.randomUUID().toString();
    }


    /**
     * 로컬 저장 경로 지정
     * @param fileId
     * @param format
     * @return 
     */
    public static String createLocalPath(String fileId, String format) {
        return String.format("%s/imgs/%s.%s", BASE_DIR, fileId, format);
    }

    /**
     * 원격 저장 경로 지정
     * @param fileDiv
     * @param songId
     * @param fileId
     * @param format
     * @return
     */
    public static String createRemotePath(String fileDiv, Long songId, String fileId, String format) {
        return String.format("%s/%d/%s.%s", fileDiv, songId, fileId, format);
    }

    public static String createRemotePath(String fileDiv, String fileId, String format) {
        return String.format("%s/%s.%s", fileDiv, fileId, format);
    }

    public static String createURL(String remotePath) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, remotePath);
    }
}

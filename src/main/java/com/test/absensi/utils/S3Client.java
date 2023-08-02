package com.test.absensi.utils;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.test.absensi.config.S3Config;
import com.test.absensi.exceptions.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class S3Client {

    private final AmazonS3 amazonS3Client;

    private final S3Config s3Config;
    public String upload(File file, String namaFile) {
        try {
            if(!amazonS3Client.doesBucketExist(s3Config.getBucket())) {
                amazonS3Client.createBucket(s3Config.getBucket());
            }
            amazonS3Client.putObject(s3Config.getBucket(), "Image/"+namaFile, file);
            file.delete();
            String url = s3Config.getEndpointURL()+"/"+ s3Config.getBucket()+"/Image/"+namaFile;
            return url;
        } catch (SdkClientException e) {
            System.out.println(e.getMessage());
            throw new GlobalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}

package com.tml.otowbackend.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tml.otowbackend.core.config.OSSConfig;
import com.tml.otowbackend.core.exception.ResultCode;
import com.tml.otowbackend.core.exception.ServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/5 13:47
 */
@Component
public class OSSUtil {

    @Resource
    OSSConfig ossConfig;
    @Value("${spring.application.name}")
    private String serverName;

    public String getObjectURL(String relativePath){

        AssumeRoleResponse response = generateTempPermissionWithSTS();

        return getObjectURLwithExpireTime(relativePath,response.getCredentials().getAccessKeyId(),response.getCredentials().getAccessKeySecret(),response.getCredentials().getSecurityToken());
    }

    private AssumeRoleResponse generateTempPermissionWithSTS(){

        String endpoint = ossConfig.getStsEndPoint();
        String accessKeyId = ossConfig.getAccesskeyId();
        String accessKeySecret = ossConfig.getAccesskeySecret();
        String roleArn = ossConfig.getRoleArn();
        String roleSessionName = serverName;
        String policy = null;
        Long durationSeconds = Long.valueOf(ossConfig.getStsExpire());

        try {
            String regionId = "";
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    private String getObjectURLwithExpireTime(String path,String accessKeyId,String accessKeySecret,String token){
        String bucketName = ossConfig.getBucket();
        String endpoint = ossConfig.getEndpoint();
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret,token);

        URL signedUrl;
        try {
            Date expiration = new Date(new Date().getTime() + ossConfig.getSignedExpire() * 1000L);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, path, HttpMethod.GET);
            request.setExpiration(expiration);
            signedUrl = ossClient.generatePresignedUrl(request);
            return signedUrl.toString();
        } catch (OSSException oe) {
            throw new ServerException(ResultCode.GET_OSS_OBJECT_URL_FAIL);
        }
    }
}

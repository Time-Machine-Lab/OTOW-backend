package com.tml.otowbackend;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tml.otowbackend.core.config.OSSConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/3 22:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@WebAppConfiguration
@Slf4j
public class OSSTest {

    @Resource
    OSSConfig ossConfig;
    @Test
    public void test() {
        // STS服务接入点，例如sts.cn-hangzhou.aliyuncs.com。您可以通过公网或者VPC接入STS服务。
        String endpoint = "sts.cn-shenzhen.aliyuncs.com";
        // 从环境变量中获取步骤1生成的RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = ossConfig.getAccesskeyId();
        String accessKeySecret = ossConfig.getAccesskeySecret();
        // 从环境变量中获取步骤3生成的RAM角色的RamRoleArn。
        String roleArn = ossConfig.getRoleArn();
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "otow-server";
        // 临时访问凭证将获得角色拥有的所有权限。
        String policy = null;
        // 临时访问凭证的有效时间，单位为秒。最小值为900，最大值以当前角色设定的最大会话时间为准。当前角色最大会话时间取值范围为3600秒~43200秒，默认值为3600秒。
        // 在上传大文件或者其他较耗时的使用场景中，建议合理设置临时访问凭证的有效时间，确保在完成目标任务前无需反复调用STS服务以获取临时访问凭证。
        Long durationSeconds = 900L;
        try {
            // 发起STS请求所在的地域。建议保留默认值，默认值为空字符串（""）。
            String regionId = "";
            // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            // 添加endpoint。适用于Java SDK 3.12.0以下版本。
            // DefaultProfile.addEndpoint("",regionId, "Sts", endpoint);
            // 构造default profile。
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            // 构造client。
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            // 适用于Java SDK 3.12.0及以上版本。
            request.setSysMethod(MethodType.POST);
            // 适用于Java SDK 3.12.0以下版本。
            // request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
//            upload(response.getCredentials().getSecurityToken(),response.getCredentials().getAccessKeyId(),response.getCredentials().getAccessKeySecret());
            download(response.getCredentials().getAccessKeyId(),response.getCredentials().getAccessKeySecret(),response.getCredentials().getSecurityToken());
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
    }

    public void upload(String token,String accessKeyId,String accessKeySecret){
        // OSS访问域名。以华东1（杭州）地域为例，填写为https://oss-cn-hangzhou.aliyuncs.com。其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
// 从环境变量中获取步骤5生成的临时访问密钥AccessKey ID和AccessKey Secret，非阿里云账号AccessKey ID和AccessKey Secret。
//        String accessKeyId = ossConfig.getAccesskeyId();
//        String accessKeySecret = ossConfig.getAccesskeySecret();
// 从环境变量中获取步骤5生成的安全令牌SecurityToken。

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, token);
// 将本地文件exampletest.txt上传至examplebucket。
        PutObjectRequest putObjectRequest = new PutObjectRequest("private-welsir-work", "test.png", new File("D:\\抖音素材\\bug\\428dfc7d-d9ee-43c8-b988-94f647340451.png"));

// ObjectMetadata metadata = new ObjectMetadata();
// 上传文件时设置存储类型。
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// 上传文件时设置读写权限ACL。
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

        try {
            // 上传文件。
            ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public void download(String accessKeyId,String accessKeySecret,String token) {
        String pathName = "D:\\example.jpg";
        // 以华东1（杭州）的外网Endpoint为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";

        // 填写Bucket名称，例如examplebucket。
        String bucketName = "private-welsir-work";
        // 填写Object完整路径，例如exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "test.png";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret,token);

        URL signedUrl;
        try {
            // 指定生成的签名URL过期时间，单位为毫秒。
            Date expiration = new Date(new Date().getTime() + 5 * 1000L);

            // 生成签名URL。
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
            // 设置过期时间。
            request.setExpiration(expiration);

            // 通过HTTP GET请求生成签名URL。
            signedUrl = ossClient.generatePresignedUrl(request);
            // 打印签名URL。
            System.out.println("signed url for getObject: " + signedUrl);

            Map<String, String> headers = new HashMap<>();
            Map<String, String> userMetadata = new HashMap<>();
            // 通过签名URL下载文件，以HttpClients为例说明。
            getObjectWithHttp(signedUrl.toString(), pathName, headers, userMetadata);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getObjectWithHttp(String signedUrl, String pathName, Map<String, String> headers, Map<String, String> userMetadata) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(signedUrl);

            // 如果生成签名URL时设置了header参数，例如用户元数据，存储类型等，则调用签名URL下载文件时，也需要将这些参数发送至服务端。如果签名和发送至服务端的不一致，会报签名错误。
            for(Map.Entry header: headers.entrySet()){
                get.addHeader(header.getKey().toString(),header.getValue().toString());
            }
            for(Map.Entry meta: userMetadata.entrySet()){
                // 如果使用userMeta，sdk内部会为userMeta拼接"x-oss-meta-"前缀。当您使用其他方式生成签名URL进行下载时，userMeta也需要拼接"x-oss-meta-"前缀。
                get.addHeader("x-oss-meta-"+meta.getKey().toString(), meta.getValue().toString());
            }

            httpClient = HttpClients.createDefault();
            response = httpClient.execute(get);

            System.out.println("返回下载状态码："+response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().toString());

            saveFileToLocally(response.getEntity().getContent(), pathName);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            response.close();
            httpClient.close();
        }
    }

    public static void saveFileToLocally(InputStream inputStream, String pathName) throws IOException {
        DataInputStream in = null;
        OutputStream out = null;
        try {
            in = new DataInputStream(inputStream);
            out = new DataOutputStream(new FileOutputStream(pathName));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
        }
    }
}

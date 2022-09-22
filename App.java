package com.aws.connect;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.services.connect.*;
import software.amazon.awssdk.services.connect.model.CreateUserRequest;
import software.amazon.awssdk.services.connect.model.UserPhoneConfig;
import software.amazon.awssdk.services.connect.model.UserIdentityInfo;
import software.amazon.awssdk.regions.Region;
//引入proxy配置
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.apache.ProxyConfiguration;
import java.net.URI;

public class App 
{
    public static void main( String[] args )
    {    
         ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
         //Java SDK V2默认用aws凭证文件授权，如果想在代码里传递AK/SK，改成下面这样
         //AwsBasicCredentials credentials = AwsBasicCredentials.create(
         //     "accesskey",
         //     "securekey");
         
         //配置Proxy参数
         ProxyConfiguration.Builder proxyConfig = ProxyConfiguration.builder();
    	     proxyConfig.endpoint(URI.create("http://18.162.125.199:8118"));
    	     proxyConfig.username("");
    	     proxyConfig.password("");

    	 ApacheHttpClient.Builder httpClientBuilder = ApacheHttpClient.builder()
    	     .proxyConfiguration(proxyConfig.build());
     
         Region region = Region.US_WEST_2;
         ConnectClient connect = ConnectClient.builder()
             .region(region)
             .credentialsProvider(credentialsProvider)
             .httpClientBuilder(httpClientBuilder)
             .build();
        
         UserPhoneConfig phoneconfig = UserPhoneConfig.builder()
             .phoneType("SOFT_PHONE")
             .build();
        
         UserIdentityInfo useridentity = UserIdentityInfo.builder()
             .email("awssdk@midea.com")
             .firstName("aws")
             .lastName("sdk")
             .build();
        
         CreateUserRequest request = CreateUserRequest.builder()
             .instanceId("0fdb88c7-20c6-4a85-8a1e-068eecde0750")
             .username("awssdk")
             .password("Passw0rd")
             .phoneConfig(phoneconfig)
             .identityInfo(useridentity)
             .routingProfileId("62845221-ede4-4085-9f7c-a815be060185")
             .securityProfileIds("0ea37551-9437-460d-aab6-d4331686586a")
             .build();
        
        connect.createUser(request);
    }
}

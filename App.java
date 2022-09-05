package com.aws.connect;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.connect.*;
import software.amazon.awssdk.services.connect.model.CreateUserRequest;
import software.amazon.awssdk.services.connect.model.UserPhoneConfig;
import software.amazon.awssdk.services.connect.model.UserIdentityInfo;
import software.amazon.awssdk.regions.Region;
 

public class App 
{
    public static void main( String[] args )
    {    
    	ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.US_WEST_2;
        ConnectClient connect = ConnectClient.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
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

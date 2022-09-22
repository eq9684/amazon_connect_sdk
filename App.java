package com.aws.connect;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.services.connect.*;
import software.amazon.awssdk.services.connect.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.apache.ProxyConfiguration;
import java.net.URI;
//import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import com.aws.connect.get_secret;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class App 
{
	public static void main( String[] args ) throws ParseException
	{
		//setup Proxy
		ProxyConfiguration.Builder proxyConfig = ProxyConfiguration.builder()
				.endpoint(URI.create("http://18.162.125.199:8118"))
				.username("")
				.password("");
		
		ApacheHttpClient.Builder httpClientBuilder = ApacheHttpClient.builder()
				.proxyConfiguration(proxyConfig.build());
		
		//ClientOverrideConfiguration.Builder overrideConfig = ClientOverrideConfiguration.builder();
		JSONObject aksk = new JSONObject();
		aksk = get_secret.get_aksk();
		AwsBasicCredentials credentials = AwsBasicCredentials.create(
				aksk.get("access_key_id").toString(),
				aksk.get("secret_access_key").toString());
				
		// ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
		Region region = Region.US_WEST_2;
		ConnectClient connect = ConnectClient.builder()
				.region(region)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				//.httpClientBuilder(httpClientBuilder)
				//.overrideConfiguration(overrideConfig.build())
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

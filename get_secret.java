package com.aws.connect;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class get_secret {
	public static JSONObject get_aksk() throws ParseException {
		String secretName = "connect_credential";
		Region region = Region.of("us-west-2");

		// Create a Secrets Manager client
		SecretsManagerClient client = SecretsManagerClient.builder()
            .region(region)
            .build();

		//getValue(client, secretName);
		String aksk = getValue(client, secretName);
		
		JSONObject json = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		json = (JSONObject) jsonParser.parse(aksk);
		client.close();
		return json;
	}
    
    public static String getValue(SecretsManagerClient secretsClient,String secretName) {
    	String secret = "";
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            secret = valueResponse.secretString();
        } catch (SecretsManagerException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return secret;
    }
}

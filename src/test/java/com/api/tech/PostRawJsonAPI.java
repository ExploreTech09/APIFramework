/**
* Request Post URL
* Request Payload - Put into Postman Raw Data
* Headers will fetch by defalt
* Added (JsonPath.read) - to fetch value from json data
* 
* Dependencies
* <dependency>
			<groupId>io.rest-assured</groupId>
			<artifactId>rest-assured</artifactId>
			<version>3.0.6</version>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.jayway.jsonpath</groupId>
			<artifactId>json-path</artifactId>
			<version>2.4.0</version>
		</dependency>
* 
**/
package com.api.tech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

public class PostRawJsonAPI {

	String requestUrl = "** URL **";
	String payLoad = " ** Payload **";

	@Test
	public void main() throws IOException {
		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		writer.write(payLoad);
		writer.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuffer jsonString = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			jsonString.append(line);
		}
		
		String strReturnJSON = jsonString.toString();
		System.err.println("---> "+strReturnJSON);
		
		ArrayList<String> arrProductIds = new ArrayList<String>();
		List<String> arrProdIDS = JsonPath.read(strReturnJSON,"abcDfghi..abcDfghi..abcDfghi..*****ProductIds");
		for (int i = 0; i < arrProdIDS.size(); i++) {

			String strProdIds = arrProdIDS.get(i);
			String[] str1=strProdIds.split("#");
			String prod1 =str1[0];
			arrProductIds.add(prod1);
			System.err.println("---> "+arrProductIds.get(i));
		}
		br.close();
		connection.disconnect();
		return;
	}
}

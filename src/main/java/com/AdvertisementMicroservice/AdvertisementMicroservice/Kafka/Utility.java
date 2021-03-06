package com.AdvertisementMicroservice.AdvertisementMicroservice.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.AdvertisementMicroservice.AdvertisementMicroservice.Security.JwtTokenProvider;


@Component
public class Utility {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Value("${server.port}")
	private String port;
	
	
	public void sendInfoModelToConfig(String configURL)
	{
		    RestTemplate restTemplate = new RestTemplate();
		    MicroserviceInfo model=new MicroserviceInfo();
		    model.setMicroserviceName(MicroservicesEnum.ADVERTISEMENT);
		    model.setPort(this.port);
		    model.setToken(jwtTokenProvider.createTokenForMicroservice());
			HttpEntity<MicroserviceInfo> entity = new HttpEntity<MicroserviceInfo>(model);
			ResponseEntity<MicroserviceInfo> response = restTemplate.exchange(configURL,HttpMethod.POST,entity, MicroserviceInfo.class );
	}
}

package com.AdvertisementMicroservice.AdvertisementMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.AdvertisementMicroservice.AdvertisementMicroservice.Kafka.*;


@SpringBootApplication
public class AdvertisementMicroserviceApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext app=SpringApplication.run(AdvertisementMicroserviceApplication.class, args);
		
		try {
		ConsumerThreadService threadService = (ConsumerThreadService) app.getBean("consumerThreadService");		
		Utility utility=(Utility) app.getBean("utility");
	    utility.sendInfoModelToConfig("http://http://95.30.222.140:7082/setInfoModel");
	    Thread microserviceInfoThread =new Thread(threadService.microserviceInfoRunnable());
	    Thread subjectThread =new Thread(threadService.subjectRunnable());
	    microserviceInfoThread.start();
	    subjectThread.start();	
		}
		catch(Exception ex)
		{
			
		}
		
		
	}

}

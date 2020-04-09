package com.AdvertisementMicroservice.AdvertisementMicroservice.Controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.AdvertisementMicroservice.AdvertisementMicroservice.Entitys.*;
import com.AdvertisementMicroservice.AdvertisementMicroservice.Models.*;
import com.AdvertisementMicroservice.AdvertisementMicroservice.Services.*;



@RestController
@CrossOrigin("http://localhost:4200")
public class AdvertisementController {

	
	@Autowired
	private  SubjectService subjectService;
	
	@Autowired
	private AdvertisementService advertisementService;
		
	
	@PostMapping("filterAdvertisements")
	public ResponseEntity<List<Advertisement>> filterAdvertisements(@RequestBody AdvFilters advFilters)
	{
		List<Advertisement> advs=this.advertisementService.filterAdvertisement(advFilters);
		return new ResponseEntity<>(advs,HttpStatus.OK);
		
	}
	
	@GetMapping("allSubjects")
	public List<Subject> getAllSubjects()
	{
		return subjectService.getAllSubjects();
	}
	
	@GetMapping("allAdvertisements")
	public ResponseEntity<List<Advertisement>> getAllAdvertisements()
	{
		List<Advertisement> advs=advertisementService.findAll();
		return new ResponseEntity<>(advs,HttpStatus.OK);
		
	}
	
	
	@PostMapping("updateAdvertisementInformation")
	public ResponseEntity<?> updateAdvertisementInformation(@RequestBody AdvertisementModel model)
	{
		Advertisement adv=advertisementService.findById(model.getAdvertisementId());
		//adv.setDeadline(model.getDeadline());
		adv.setAdvertisementName(model.getAdvertisementName());
		adv.setBudget(model.getBudget());
		adv.setDescription(model.getDescription());
		adv.setSection(model.getSection());
		advertisementService.save(adv);
		
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	@GetMapping("advertisement/{id}")
	public ResponseEntity<Advertisement> getadvbyid(@PathVariable String id)
	{
		Advertisement adv=advertisementService.findById(Long.parseLong(id));
		return new ResponseEntity<>(adv,HttpStatus.OK);
	}
	
	@PostMapping("addAdvertisement")
	public ResponseEntity<Boolean> addnewAdvertisement(@RequestBody AdvertisementModel adv) throws IOException
	{
		
		adv.setDateOfPublication(LocalDateTime.now());
		Advertisement advertisement=ModelUtils.AdvertisementModelToEntity(adv);
		advertisement.setStatus(AdvertisementStatus.ACTIVE);
		advertisement=advertisementService.save(advertisement);
		advertisement.setTags(adv.getTags());
		if(adv.getAuthorRole().equals("ROLE_STUDENT"))
			advertisement.setType(AdvertisementType.ORDER);
		else
			advertisement.setType(AdvertisementType.FREELANCE);
		advertisement=advertisementService.save(advertisement);
		String[] keys=advertisement.setImageKeys(adv.getAllFiles());
		advertisementService.save(advertisement);
	
		if(keys.length!=0)
		{
			AmazonModels amazon=new AmazonModels(keys.length);
			for(int i=0;i<keys.length;i++)
			{
				amazon.allFiles[i]=new AmazonModel(keys[i],adv.getAllFiles()[i]);
			}
			 HttpEntity<AmazonModels> requestEntity =new HttpEntity<>(amazon);
			 RestTemplate restTemplate = new RestTemplate();
			 try 
			 {
			    ResponseEntity<Object> res=restTemplate.exchange("http://localhost:1234/uploadAdvertisementFiles",HttpMethod.POST,requestEntity,Object.class);
			 }
			catch(Exception ex)
			{
					
			}
		}
		return new ResponseEntity<>(true,HttpStatus.OK);
		
	}
		
}

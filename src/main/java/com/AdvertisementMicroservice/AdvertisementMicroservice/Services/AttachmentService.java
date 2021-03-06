package com.AdvertisementMicroservice.AdvertisementMicroservice.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AdvertisementMicroservice.AdvertisementMicroservice.Entitys.Attachment;
import com.AdvertisementMicroservice.AdvertisementMicroservice.Repositorys.AttachmentRepository;

@Service
@Transactional
public class AttachmentService {

	
	@Autowired
	private AttachmentRepository attachmentRep;
	
	
	public Attachment save(Attachment attachment)
	{
		return attachmentRep.save(attachment);
	}
	
	public List<Attachment> findAllByAdvertisementId(Long id)
	{
		return this.attachmentRep.findAllByAdvertisementId(id);
	}
}

package com.lti.azureservice;

import java.time.Duration;
import java.util.Base64;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.lti.dto.ImageDTO;
import com.lti.dto.ImageDTO.ImageDTOBuilder;

@Service
public class Blobservice {
	
	@Value("${azure.storage.storageconnectionstring}")
	private String storageconnectionstring;
	
	@Value("${azure.storage.containername}")
	private String blobname;
//	
	//String storageconnectionstring="AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;DefaultEndpointsProtocol=http;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;";
	
	
	public void insertFileIntoBlob(int factoryid,String productid,String filename,String file) {
             byte[] attach=file.getBytes();           //Base64.getDecoder().decode(file);
             
             BlobContainerClient container=new BlobContainerClientBuilder()
         			.connectionString(storageconnectionstring)
         			.containerName(blobname)
         			.buildClient();
		
		BlobClient blob=container.getBlobClient(factoryid+"/"+productid+"/"+filename);
		blob.upload(BinaryData.fromBytes(attach));
	}
	
	public void deleteMultipleBlob(int factid) {
		
		BlobContainerClient container=new BlobContainerClientBuilder()
				.connectionString(storageconnectionstring)
				.containerName(blobname)
				.buildClient();
		
		final ListBlobsOptions listBlobsOptions=new ListBlobsOptions().setPrefix(factid+"/");
		for(BlobItem file:container.listBlobs(listBlobsOptions,Duration.ofSeconds(60))){
			final BlobClient blobClient=container.getBlobClient(file.getName());
			blobClient.delete();
		}
	}
	
	public void deleteBlob(int factid,int id,String filename) {
		
		 BlobContainerClient container=new BlobContainerClientBuilder()
      			.connectionString(storageconnectionstring)
      			.containerName(blobname)
      			.buildClient();
		
		final BlobClient blobClient=container.getBlobClient(factid+"/"+id+"/"+filename);
		blobClient.delete();
	}
	
	public ImageDTO fetchBlob(int factid,int id,String filename) {
		
		 BlobContainerClient container=new BlobContainerClientBuilder()
      			.connectionString(storageconnectionstring)
      			.containerName(blobname)
      			.buildClient();
		
		final BlobClient blobClient=container.getBlobClient(factid+"/"+id+"/"+filename);
		byte[] attachment=blobClient.downloadContent().toBytes();
		ImageDTO idto=ImageDTO.builder()
				.file(new String(attachment))
				.build();
		return idto;
	}
}

package com.lti.dao;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.lti.azureservice.Blobservice;
import com.lti.dto.ProductDTO;

@Repository
public class ProductDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private Blobservice blobservice;
	
	
	public String createprod(ProductDTO pdto) {
		String SQL="insert into product(name,quantity,type,filename,factoryid) values(:name,:quant,:type,:filename,:factid);";
		
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("name", pdto.getName());
		sqlparms.put("quant", pdto.getQuantity());
		sqlparms.put("type", pdto.getType());
		sqlparms.put("factid", pdto.getFactoryid());
		sqlparms.put("filename",pdto.getFilename());

		KeyHolder keyHolder=new GeneratedKeyHolder();

		SqlParameterSource sqlParameterSource=new MapSqlParameterSource().addValues(sqlparms);
		
		namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
		
		blobservice.insertFileIntoBlob(pdto.getFactoryid(), String.valueOf(keyHolder.getKey()), pdto.getFilename(), pdto.getFile());
		
		return String.valueOf(keyHolder.getKey());
	}
	
	public List<ProductDTO> getallproduct(int factid){
		String SQL="select * from product where factoryid=:factid";
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("factid", factid);
		List<ProductDTO> plist=new ArrayList<>();
		namedParameterJdbcTemplate.query(SQL,sqlparms,rs ->{
			ProductDTO pdto=ProductDTO.builder()
					.id(rs.getInt("id"))
					.name(rs.getString("name"))
					.quantity(rs.getLong("quantity"))
					.factoryid(rs.getInt("factoryid"))
					.filename(rs.getString("filename"))
					.build();
			
			
			plist.add(pdto);
		});
		
		return plist;
	}
	
	public boolean updateprod(ProductDTO pdto) {
		String SQL="update product set name=:name,quantity=:quant,type=:type where id=:id";
		
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("id",pdto.getId());
		sqlparms.put("name",pdto.getName());
		sqlparms.put("quant",pdto.getQuantity());
		sqlparms.put("type",pdto.getType());
		
		int i=namedParameterJdbcTemplate.update(SQL, sqlparms);
		
		if(i==1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean deleteprod(int factid,int prodid,String filename) {
		String SQL="delete from product where id=:prodid";
		
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("prodid",prodid);
		
		blobservice.deleteBlob(factid, prodid, filename);
		
		int i=namedParameterJdbcTemplate.update(SQL, sqlparms);
		
		if(i==1) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public boolean deleteallprod(int factid) {
		String SQL="delete from product where factoryid=:factid";
		
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("factid",factid);
		
        int i=namedParameterJdbcTemplate.update(SQL, sqlparms);
        
        blobservice.deleteMultipleBlob(factid);
		
		if(i==1) {
			return true;
		}
		else {
			return false;
		}
	}

}

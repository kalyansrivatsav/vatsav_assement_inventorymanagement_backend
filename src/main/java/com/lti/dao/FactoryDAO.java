package com.lti.dao;

import java.util.ArrayList;

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

import com.lti.dto.FactoryDTO;

@Repository
public class FactoryDAO {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private ProductDAO productDAO;

	public String createfactory(FactoryDTO factoryDTO) {
		 String SQL="insert into factory(name,location) values(:name,:loc)";
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("name", factoryDTO.getName());
		sqlparms.put("loc", factoryDTO.getLocation());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource sqlParameterSource=new MapSqlParameterSource().addValues(sqlparms);
		
		int i=namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
		
        return String.valueOf(keyHolder.getKey());
	}
	
	public List<FactoryDTO> getallfactory(){
		String SQL="select id,name,location from factory";
		
		List<FactoryDTO> flist=new ArrayList<>();
		namedParameterJdbcTemplate.query(SQL, rs ->{
			FactoryDTO fdto=FactoryDTO.builder()
					.id(rs.getInt("id"))
					.location(rs.getString("location"))
					.name(rs.getString("name"))
					.build();
			
			flist.add(fdto);
		});
		
		return flist;
	}
	
	public boolean updatefact(FactoryDTO factoryDTO) {
		String SQL="update factory set name=:name,location=:loc where id=:id";
		
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("id",factoryDTO.getId());
		sqlparms.put("name", factoryDTO.getName());
		sqlparms.put("loc", factoryDTO.getLocation());
		
		int i=namedParameterJdbcTemplate.update(SQL, sqlparms);
		
		if(i==1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean deletefact(int id) {
		String SQL="delete from factory where id=:id";
		Map<String, Object> sqlparms=new HashMap<>();
		sqlparms.put("id",id);
		
		productDAO.deleteallprod(id);
		
		int i=namedParameterJdbcTemplate.update(SQL, sqlparms);
		
		if(i==1) {
			return true;
		}
		else {
			return false;
		}
	}
}

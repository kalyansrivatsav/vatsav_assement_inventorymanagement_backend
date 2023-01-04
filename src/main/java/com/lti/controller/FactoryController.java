package com.lti.controller;
import com.lti.dao.FactoryDAO;
import com.lti.dto.FactoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factory")
@CrossOrigin(origins = "*")
public class FactoryController {
	
	@Autowired
	public FactoryDAO factoryDAO;
	

	@PostMapping(value="/create")
	public String createfact(@RequestBody FactoryDTO factoryDTO) {
		return factoryDAO.createfactory(factoryDTO);
	}
	
	@GetMapping(value="/getall")
	public List<FactoryDTO> getallfact(){
		return factoryDAO.getallfactory();
	}
	
	@PostMapping(value="/update")
	public boolean updatefact(@RequestBody FactoryDTO factoryDTO) {
		return factoryDAO.updatefact(factoryDTO);
	}
	
	@DeleteMapping(value="/delete/{id}")
	public boolean deletefact(@PathVariable int id) {
		return factoryDAO.deletefact(id);
	}

}

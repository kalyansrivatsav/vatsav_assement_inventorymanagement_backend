package com.lti.controller;
import com.lti.azureservice.Blobservice;
import com.lti.dao.ProductDAO;
import com.lti.dto.ImageDTO;
import com.lti.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {
	
	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	Blobservice blobservice;
	
	@PostMapping(value="/create")
	public String createprod(@RequestBody ProductDTO productDTO) {
		return productDAO.createprod(productDTO);
	}
	
	@GetMapping(value="/getall/{factid}")
	public List<ProductDTO> getallprod(@PathVariable int factid){
		return productDAO.getallproduct(factid);
	}
	
	@PostMapping(value="/update")
	public boolean updateprod(@RequestBody ProductDTO productDTO) {
		return productDAO.updateprod(productDTO);
	}
	
	@DeleteMapping(value="/delete/{factid}/{prodid}")
	public boolean deleteprodcon(@PathVariable int factid,@PathVariable int prodid) {
		return productDAO.deleteprod(factid,prodid);
	}
	
	@GetMapping(value="/image/{factid}/{prodid}/{filename}")
	public ImageDTO getImage(@PathVariable int factid,@PathVariable int prodid,@PathVariable String filename) {
		return blobservice.fetchBlob(factid, prodid, filename);
	}

}
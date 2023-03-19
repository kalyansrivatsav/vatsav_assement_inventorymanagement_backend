package com.lti.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Builder(access = AccessLevel.PUBLIC)
@Data
public class ProductDTO {
	int id;
	String name;
	long quantity;
	String type;
	int factoryid;
	String filename;
	String file;
	int orderedQuantity;
}

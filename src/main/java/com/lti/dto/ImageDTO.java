package com.lti.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Builder(access = AccessLevel.PUBLIC)
@Data
public class ImageDTO {

	String file;
}

package com.example.demo.controller.sujan.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SujanForm2 {

	@Email(message = "{email_check}")
	@NotBlank(message = "{require_check}")
	private String email;
	@NotBlank(message = "{require_check}")
	@Size(max = 3, min = 3, message = "{length_check_number}")
	private String mobileNo1;
	@Size(max = 4, min = 4, message = "{length_check_number}")
	@NotBlank(message = "{require_check}")
	private String mobileNo2;
	@NotBlank(message = "{require_check}")
	@Size(max = 4, min = 4, message = "{length_check_number}")
	private String mobileNo3;

}

package com.example.demo.controller.sujan.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SujanDto implements Serializable {

	/**
	 * Data Transfer object
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	private String name;
	private boolean nationality;
	private String gender;
	private String dateOfYear;
	private String dateOfMonth;
	private String dateOfDay;
	private String merriageSts;
	private String email;
	private String mobileNo1;
	private String mobileNo2;
	private String mobileNo3;
	private String postNO;
	private String address1;
	private String address2;
	private String livingSituation;
	private String housingLoan;
	private String drivingLicenseLabel;
	private String drivingLicense;
	private String userId;
	private String pwd;
	private String rePwd;
}
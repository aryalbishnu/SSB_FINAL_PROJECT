package com.example.demo.controller.sujan.form;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SujanForm3 {
	@Size(max = 4, min = 4, message = "{length_check_number}")
	private String postNO;
	@Min(value = 20, message = "{min_check}")
	@Max(value = 100, message = "{max_check}")
	private String address1;
	@Min(value = 20, message = "{min_check}")
	@Max(value = 100, message = "{max_check}")
	private String address2;
	@NotBlank(message = "{select_check}")
	private String livingSituation;
	@NotBlank(message = "{select_check}")
	private String housingLoan;
	@NotBlank(message = "{select_check}")
	private String drivingLicenseLabel;
	@NotBlank(message = "{require_check}")
	@NotNull(message = "{require_check}")
	@Length(min = 10, max = 10, message = "{length_check_number}")
	private String drivingLicense;

	// gender

	public static Map<String, String> getLivingSituationList() {
		Map<String, String> livingMap = new LinkedHashMap<>();
		livingMap.put("自己・家族所有", "自己・家族所有");
		livingMap.put("社宅", "社宅");
		livingMap.put("賃貸", "賃貸");
		livingMap.put("寮・下宿", "寮・下宿");
		return livingMap;
	}

	public static Map<String, String> getHousingLoanButton() {
		Map<String, String> housingLoan = new LinkedHashMap<String, String>();
		housingLoan.put("有り", "有り");
		housingLoan.put("無し", "無し");
		return housingLoan;
	}

	public static Map<Boolean, String> getDrivingLicenseLabelButton() {
		Map<Boolean, String> drivingLicense = new LinkedHashMap<Boolean, String>();
		drivingLicense.put(true, "有り");
		drivingLicense.put(false, "無し");
		return drivingLicense;
	}

}

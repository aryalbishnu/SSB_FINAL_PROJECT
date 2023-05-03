package com.example.demo.controller.sujan.form;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SujanForm1 {
	
	@NotBlank(message = "{require_check}")
	@NotNull(message = "{require_check}")
	@Length(min= 4 ,max = 100, message = "{length_check}")
	private String name;
	
	@NotNull(message = "{select_check}")
	private String gender;

	private boolean nationality;
	@NotBlank(message = "{select_check}")
	private String merriageSts;
	@NotBlank(message = "{select_check}")
	private String dateOfYear;
	@NotBlank(message = "{select_check}")
	private String dateOfMonth;
	@NotBlank(message = "{select_check}")
	private String dateOfDay;


	// gender

	public static Map<String, String> getGenderItem() {
		Map<String, String> sexMap = new LinkedHashMap<>();
		sexMap.put("男性", "男性");
		sexMap.put("女性", "女性");
		return sexMap;
	}

	public static Map<String, String> getMerriageItems() {
		Map<String, String> merriageSts = new LinkedHashMap<String, String>();
		merriageSts.put("結婚", "結婚");
		merriageSts.put("独身", "独身");
		merriageSts.put("離婚", "離婚");
		return merriageSts;
	}

	public static Map<String, String> getYearItems() {
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		LocalDate year = LocalDate.now();
		int currentYear = year.getYear() - 20;
		for (int i = 1985; i <= currentYear; i++) {
			yearMap.put(String.valueOf(i), String.valueOf(i));
		}
		return yearMap;
	}

	public static Map<String, String> getMonthItems() {
		Map<String, String> monthMap = new LinkedHashMap<String, String>();
		for (int i = 1; i <= 12; i++) {
			monthMap.put(String.valueOf(i), String.valueOf(i));
		}
		return monthMap;
	}

	// day

	public static Map<String, String> getDayItems() {
		Map<String, String> dayMap = new LinkedHashMap<String, String>();
		for (int i = 1; i <= 30; i++) {
			dayMap.put(String.valueOf(i), String.valueOf(i));
		}
		return dayMap;
	}

}

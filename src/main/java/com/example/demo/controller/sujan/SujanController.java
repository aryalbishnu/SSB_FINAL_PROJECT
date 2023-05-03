package com.example.demo.controller.sujan;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.controller.sujan.dto.SujanDto;
import com.example.demo.controller.sujan.form.SujanForm1;
import com.example.demo.controller.sujan.form.SujanForm2;
import com.example.demo.controller.sujan.form.SujanForm3;
import com.example.demo.controller.sujan.form.SujanForm4;
import com.example.demo.controller.sujan.service.SujanService;

@Controller
@SessionAttributes(value = "sujanDto", types = SujanDto.class)
public class SujanController {
@Autowired
ModelMapper modelMapper = new ModelMapper();
	// first page
	@RequestMapping("/sujan/chooseCard")

	public String welcomePage(SujanDto sujanDto) {
		sujanDto.setName("");
		sujanDto.setGender("");
		sujanDto.setNationality(false);
		sujanDto.setMerriageSts("");
		sujanDto.setDateOfYear("");
		sujanDto.setDateOfMonth("");
		sujanDto.setDateOfDay("");
		sujanDto.setEmail("");
		sujanDto.setMobileNo1("");
		sujanDto.setMobileNo2("");
		sujanDto.setMobileNo3("");
		sujanDto.setPostNO("");
		sujanDto.setAddress1("");
		sujanDto.setAddress2("");
		sujanDto.setLivingSituation("");
		sujanDto.setHousingLoan("");
		sujanDto.setDrivingLicenseLabel("");
		sujanDto.setDrivingLicense("");
		return "sujan/welcome_page";
	}

	// first page return
	@RequestMapping(value = "form1", params = "back", method = RequestMethod.POST)
	public String welcomePageBack(SujanDto sujanDto) {
		sujanDto.setName("");
		sujanDto.setGender("");
		sujanDto.setNationality(false);
		sujanDto.setMerriageSts("");
		sujanDto.setDateOfYear("");
		sujanDto.setDateOfMonth("");
		sujanDto.setDateOfDay("");
		sujanDto.setEmail("");
		sujanDto.setMobileNo1("");
		sujanDto.setMobileNo2("");
		sujanDto.setMobileNo3("");
		sujanDto.setPostNO("");
		sujanDto.setAddress1("");
		sujanDto.setAddress2("");
		sujanDto.setLivingSituation("");
		sujanDto.setHousingLoan("");
		sujanDto.setDrivingLicenseLabel("");
		sujanDto.setDrivingLicense("");
		return "sujan/welcome_page";
	}

	// entry page1
	@RequestMapping(value = "welcome_form", method = RequestMethod.GET)
	public String personalInfo1(@ModelAttribute SujanForm1 sujanForm1,
			@ModelAttribute SujanDto SujanDto, Model model) {
		model.addAttribute("genderItem", SujanForm1.getGenderItem());
		model.addAttribute("merriageStsList", SujanForm1.getMerriageItems());
		model.addAttribute("dateOfYearList", SujanForm1.getYearItems());
		model.addAttribute("dateOfMonthList", SujanForm1.getMonthItems());
		model.addAttribute("dateOfDayList", SujanForm1.getDayItems());

		return "sujan/personalInfoForm1";
	}

	// page1 back
	@RequestMapping(value = "form2", params = "back", method = RequestMethod.POST)
	public String personalInfo2Back(@ModelAttribute SujanForm1 sujanForm1,
			@ModelAttribute SujanDto SujanDto,Model model) {
		model.addAttribute("genderItem", SujanForm1.getGenderItem());
		model.addAttribute("merriageStsList", SujanForm1.getMerriageItems());
		model.addAttribute("dateOfYearList", SujanForm1.getYearItems());
		model.addAttribute("dateOfMonthList", SujanForm1.getMonthItems());
		model.addAttribute("dateOfDayList", SujanForm1.getDayItems());
		modelMapper.map(SujanDto, sujanForm1);
		return "sujan/personalInfoForm1";
	}

	// page2
	@RequestMapping(value = "form1", params = "next", method = RequestMethod.POST)
	public String personalInfo2(@ModelAttribute SujanForm2 sujanForm2,
			@ModelAttribute SujanDto SujanDto,
			@Validated SujanForm1 sujanForm1, BindingResult error,Model model) {
		if(error.hasErrors()){
			return personalInfo1(sujanForm1,SujanDto,model);
        }
		modelMapper.map(sujanForm1, SujanDto);
		return "sujan/personalInfoForm2";
	}

	// page2 back
	@RequestMapping(value = "form3", params = "back", method = RequestMethod.POST)
	public String personalInfo3Back(@ModelAttribute SujanForm2 sujanForm2,
			@ModelAttribute SujanDto SujanDto,Model model) {
		modelMapper.map(SujanDto, sujanForm2);
		return "sujan/personalInfoForm2";
	}

	// entry page3
	@RequestMapping(value = "form2", params = "next", method = RequestMethod.POST)
	public String personalInfo3(@ModelAttribute SujanForm3 sujanForm3,@ModelAttribute SujanDto SujanDto,
			@Validated SujanForm2 sujanForm2, BindingResult error,
			 Model model) {
		
		if(error.hasErrors()){
			personalInfo2(sujanForm2, SujanDto, null, error, model);
			return "sujan/personalInfoForm2";
        }
		
		model.addAttribute("livingSituationList", SujanForm3.getLivingSituationList());
		model.addAttribute("housingLoanButton", SujanForm3.getHousingLoanButton());
		model.addAttribute("drivingLicenseLabelButton", SujanForm3.getDrivingLicenseLabelButton());
		modelMapper.map(sujanForm2, SujanDto);
		return "sujan/personalInfoForm3";
	}

	// page3 back
	@RequestMapping(value = "form4", params = "back", method = RequestMethod.POST)
	public String personalInfo4Back(@ModelAttribute SujanForm3 sujanForm3,
			@ModelAttribute SujanDto SujanDto,Model model) {
		model.addAttribute("livingSituationList", SujanForm3.getLivingSituationList());
		model.addAttribute("housingLoanButton", SujanForm3.getHousingLoanButton());
		model.addAttribute("drivingLicenseLabelButton", SujanForm3.getDrivingLicenseLabelButton());
		modelMapper.map(SujanDto, sujanForm3);
		return "sujan/personalInfoForm3";
	}

	// entry page4
	@RequestMapping(value = "form3", params = "next", method = RequestMethod.POST)
	public String personalInfo4(@ModelAttribute SujanForm4 sujanForm4,@ModelAttribute SujanDto SujanDto,
			@Validated SujanForm3 sujanForm3,BindingResult error,
			 Model model) {
		if(error.hasErrors()){
			personalInfo3(sujanForm3, SujanDto, null, error, model);
			return "sujan/personalInfoForm3";
        }
		modelMapper.map(sujanForm3, SujanDto);
		return "sujan/personalInfoForm4";
	}

	// check page
	@RequestMapping(value = "form4", params = "next", method = RequestMethod.POST)
	public String personalInfo5(@ModelAttribute SujanDto sujanDto,
			@ModelAttribute SujanForm4 sujanForm4, Model model) {
		modelMapper.map(sujanForm4, sujanDto);
		model.addAttribute("sujanDto", sujanDto);
		return "sujan/confirmationScreen";
	}

	// check page back
	@RequestMapping(value = "sendData", params = "back", method = RequestMethod.POST)
	public String DbInsertback(@ModelAttribute SujanForm4 sujanForm4,
			@ModelAttribute SujanDto SujanDto, Model model) {
		modelMapper.map(SujanDto, sujanForm4);

		return "sujan/personalInfoForm4";
	}
	// insert do
	/**
	 * ユーザー情報 Service
	 */
	@Autowired
	SujanService sujanService;

	@RequestMapping(value = "sendData", params = "send", method = RequestMethod.POST)
	public String DbInsert(@ModelAttribute SujanDto sujanDto, HttpSession session, SessionStatus status, Model model) {

		// ユーザー情報の登録
		sujanService.insertData(sujanDto);

		status.setComplete();
		session.removeAttribute("sujanDto");
		model.addAttribute("message", "お申し込みが完了しました。");
		return "sujan/thankYou";
	}

}

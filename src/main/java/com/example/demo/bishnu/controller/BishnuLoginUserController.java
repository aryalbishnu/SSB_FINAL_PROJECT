package com.example.demo.bishnu.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bishnu.dto.UpdateAdminDto;
import com.example.demo.bishnu.dto.UpdateNormalDto;
import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.entity.CardEntity;
import com.example.demo.bishnu.helper.Message;
import com.example.demo.bishnu.message.CommonMessage;
import com.example.demo.bishnu.model.LoginForm1;
import com.example.demo.bishnu.model.LoginForm3;
import com.example.demo.bishnu.model.RePassword;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.repo.CardEntityRepo;


@Controller
@RequestMapping("/bishnu/user/")
public class BishnuLoginUserController {

  
  @Autowired
  private ModelMapper modelMapper;
  
  @Autowired
  private BishnuRepository bishnuRepository;
  
  @Autowired
  private CardEntityRepo cardEntityRepo;
  
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
 
//common message throw 
  CommonMessage commonMessage= new CommonMessage();
  
  //common data
  @ModelAttribute
  public void addCommonData(Model model, Principal principal) {
    String userName = principal.getName();

    //get the user using userName(Email)
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("user", user);
  }
  
  //spring securty after open first page
  @GetMapping("/dologin")
  public String userLoginby(Model model) {
    model.addAttribute("title", "User_Home");
      return "bishnu/normal/register";

  }
  
  //View page open only admin
  @GetMapping("/viewControl")
  public String viewControlBy(Model model) {
    model.addAttribute("title", "adminControl_page");
    return "bishnu/admin/adminControlPage";
    
  }
  
  //pagination and show contact list only admin from view
  @GetMapping("/show_contact/{page}")
  public String showContacct(@PathVariable("page") Integer page, Model model) {
    model.addAttribute("title", "Show_Contact");
    
   
   Pageable pageable= PageRequest.of(page, 5);
   Page<BishnuEntity>contacts=this.bishnuRepository.findAll(pageable);
   
   model.addAttribute("contacts", contacts);
   model.addAttribute("currentPage", page);
   model.addAttribute("totalPage", contacts.getTotalPages());
    
    return "bishnu/admin/view_contact";
  }
  
  //delete only by admin 
  @GetMapping("/delete/{id}")
  public String deleteby(@PathVariable("id") int id, Model model,  HttpSession session, Principal principal) {
 BishnuEntity bishnuEntity = this.bishnuRepository.findById(id).get();
 try{
CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
this.cardEntityRepo.deleteById(cardEntity.getCardId());
 }catch (Exception e) {
e.printStackTrace();
}
    this.bishnuRepository.deleteById(id);
  
    session.setAttribute("message", new Message("Succesfull delete account!!", "success"));
    return "redirect:/bishnu/user/show_contact/0";
  }
  
  //update only by admin click update button from view_contact
  @GetMapping("/update/{id}")
  public String updateOpen(@PathVariable("id") int id, Model model, BishnuEntity bishnuEntity) {
    model.addAttribute("title", "Update_Page");
   BishnuEntity dataOut = this.bishnuRepository.findById(id).get();
   model.addAttribute("livingCondition", LoginForm3.getLivingCondition());
   model.addAttribute("gender", LoginForm1.getByGender());
   model.addAttribute("dateofyear", LoginForm1.yearMap());
   model.addAttribute("dateofmonth", LoginForm1.getMonth());
   model.addAttribute("dateofday", LoginForm1.getDay());
   model.addAttribute("marriedStatus", LoginForm1.getByMarriedStatus());
   model.addAttribute("mortageLoan", LoginForm3.getByMortgageLoan());
   model.addAttribute("role", LoginForm3.getByRole());
   model.addAttribute("license", LoginForm3.gerByLicense());
   model.addAttribute("country", LoginForm1.getByCountryName());
   model.addAttribute("data", dataOut);
  

    return "bishnu/admin/update_admin";
    
  }
  
  //update success by update_admin page submit button return back to view_contact page
  @RequestMapping(value = "/updateSuccess", params = "next", method = RequestMethod.POST)
  public String updateSuccessByAdmin(Model model, UpdateAdminDto updateAdminDto, HttpSession session,
      @RequestParam("profileImage") MultipartFile file) {
   
     try {
      BishnuEntity oldFile = this.bishnuRepository.getUserByUserName(updateAdminDto.getEmail());
      
      if(!file.isEmpty()){
        String file3  = oldFile.getImage();
       
        if(!file3.equals("default.jpg")){
          File deleteFile=   new ClassPathResource("static/img/bishnu").getFile();
          File file1=new File(deleteFile, oldFile.getImage());
          file1.delete();
        }
        
     
  //update new file
        File saveFile=   new ClassPathResource("static/img/bishnu").getFile();
        
        
        UUID uuid = UUID.randomUUID();
        StringBuffer sb = new StringBuffer();
        sb.append(uuid.toString());
        sb.append(file.getOriginalFilename()); 
        String fileName = sb.toString();
        Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+fileName);
        Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
        updateAdminDto.setImage(fileName);
        
      }else {
        updateAdminDto.setImage(oldFile.getImage());
        }
    
    
    BishnuEntity user=this.bishnuRepository.getUserByUserName(updateAdminDto.getEmail());
    
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String now = date.format(format);
   // updateAdminDto.setUpdateTime(now);
     this.modelMapper.map(updateAdminDto, user);

    user.setUpdateTime(now);
    /*
    user.setImage(updateAdminDto.getImage());
    user.setFirstName(updateAdminDto.getFirstName());
    user.setLastName(updateAdminDto.getLastName());
    user.setKataFirstName(updateAdminDto.getKataFirstName());
    user.setKataLastName(updateAdminDto.getKataLastName());
    user.setGender(updateAdminDto.getGender());
    user.setCountryStatus(updateAdminDto.getCountryStatus());
    user.setMarriedStatus(updateAdminDto.getMarriedStatus());
    user.setDateofyear(updateAdminDto.getDateofyear());
    user.setDateofmonth(updateAdminDto.getDateofmonth());
    user.setDateofday(updateAdminDto.getDateofday());
    user.setEmail(updateAdminDto.getEmail());
    user.setMobile1(updateAdminDto.getMobile1());
    user.setMobile2(updateAdminDto.getMobile2());
    user.setMobile3(updateAdminDto.getMobile3());
    user.setTel1(updateAdminDto.getTel1());
    user.setTel2(updateAdminDto.getTel2());
    user.setTel3(updateAdminDto.getTel3());
    user.setZipCode(updateAdminDto.getZipCode());
    user.setAddress1(updateAdminDto.getAddress1());
    user.setAddress2(updateAdminDto.getAddress2());
    user.setAddress3(updateAdminDto.getAddress3());
    user.setLivingSituation(updateAdminDto.getLivingSituation());
    user.setDrivingLicense(updateAdminDto.getDrivingLicense());
    user.setLicenseNumber(updateAdminDto.getLicenseNumber());
    user.setMortageLoan(updateAdminDto.getMortageLoan());
    user.setRole(updateAdminDto.getRole());
*/
    //database update
  this.bishnuRepository.save(user);

    session.setAttribute("message", new Message("Succesfull Update account!!", "success"));
     }catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/bishnu/user/show_contact/0";
  }
  
  //update_admin page back button return back to view_contact page
  @RequestMapping(value = "/updateSuccess", params = "back", method = RequestMethod.POST)
  public String returnByViewContact(Model model) {
   
    return "redirect:/bishnu/user/show_contact/0";
  }
  

  
  //open of profile page click of profile
  @GetMapping("/profile")
  public String openProfile( Model model, Principal principal) {
    model.addAttribute("title", "profile_page");
    String userName = principal.getName();

    //get the user using userName(Email)
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
   model.addAttribute("user", user);
   
    return "bishnu/normal/profile";
  }
  
  //open profile update page click 修正 button of profile page
  @PostMapping("/profile_update")
  public String profileUpdate( Model model, Principal principal) {
    model.addAttribute("title", "profile_update page");
    
    model.addAttribute("living", LoginForm3.getLivingCondition());
    model.addAttribute("genders", LoginForm1.getByGender());
    model.addAttribute("dateofyear", LoginForm1.yearMap());
    model.addAttribute("dateofmonth", LoginForm1.getMonth());
    model.addAttribute("dateofday", LoginForm1.getDay());
    model.addAttribute("marriedStatus", LoginForm1.getByMarriedStatus());
    model.addAttribute("mortageLoan", LoginForm3.getByMortgageLoan());
    model.addAttribute("role", LoginForm3.getByRole());
    model.addAttribute("license", LoginForm3.gerByLicense());
    model.addAttribute("country", LoginForm1.getByCountryName());
    String userName = principal.getName();

    //get the user using userName(Email)
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("data", user);
  
    
    return "bishnu/normal/profile_update";
  }
  
  //open profile page click back button from profile update page
  @RequestMapping(value = "/profile/updateBy", params = "back", method = RequestMethod.POST)
  public String updateBack(Model model) {
    model.addAttribute("title", "profile_page");
    return "bishnu/normal/profile";
  }
  
  //open profile page click next button from profile update page
  @RequestMapping(value = "/profile/updateBy", params = "next", method = RequestMethod.POST)
  public String updateSuccess(Model model, HttpSession session, Principal principal,
      UpdateNormalDto updateNormalDto, @RequestParam("profileImage") MultipartFile file) {
    
    try {
      BishnuEntity oldFile = this.bishnuRepository.findById(updateNormalDto.getId()).get(); 
      
      if(!file.isEmpty()) {
        String file3  = oldFile.getImage();
        
        if(!file3.equals("default.jpg")){
          File deleteFile=   new ClassPathResource("static/img/bishnu").getFile();
          File file1=new File(deleteFile, oldFile.getImage());
          file1.delete();
        }
        
        //update new photo
        File saveFile=   new ClassPathResource("static/img/bishnu").getFile();
        UUID uuid = UUID.randomUUID();
        StringBuffer sb = new StringBuffer();
        sb.append(uuid.toString());
        sb.append(file.getOriginalFilename()); 
        String fileName = sb.toString();
        Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+fileName);
        Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
        updateNormalDto.setImage(fileName);
      }else {
        updateNormalDto.setImage(oldFile.getImage());
      }
    model.addAttribute("title", "profile_page");
    String userName = principal.getName();
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("data", user);
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String now = date.format(format);
    
    
    this.modelMapper.map(updateNormalDto, user);
    user.setUpdateTime(now);  
    /*
    user.setUpdateTime(now);
    user.setImage(updateNormalDto.getImage());
    user.setFirstName(updateNormalDto.getFirstName());
    user.setLastName(updateNormalDto.getLastName());
    user.setKataFirstName(updateNormalDto.getKataFirstName());
    user.setKataLastName(updateNormalDto.getKataLastName());
    user.setGender(updateNormalDto.getGender());
    user.setCountryStatus(updateNormalDto.getCountryStatus());
    user.setMarriedStatus(updateNormalDto.getMarriedStatus());
    user.setDateofyear(updateNormalDto.getDateofyear());
    user.setDateofmonth(updateNormalDto.getDateofmonth());
    user.setDateofday(updateNormalDto.getDateofday());
    user.setEmail(updateNormalDto.getEmail());
    user.setMobile1(updateNormalDto.getMobile1());
    user.setMobile2(updateNormalDto.getMobile2());
    user.setMobile3(updateNormalDto.getMobile3());
    user.setTel1(updateNormalDto.getTel1());
    user.setTel2(updateNormalDto.getTel2());
    user.setTel3(updateNormalDto.getTel3());
    user.setZipCode(updateNormalDto.getZipCode());
    user.setAddress1(updateNormalDto.getAddress1());
    user.setAddress2(updateNormalDto.getAddress2());
    user.setAddress3(updateNormalDto.getAddress3());
    user.setLivingSituation(updateNormalDto.getLivingSituation());
    user.setDrivingLicense(updateNormalDto.getDrivingLicense());
    user.setLicenseNumber(updateNormalDto.getLicenseNumber());
    user.setMortageLoan(updateNormalDto.getMortageLoan());
*/
    //update database
  this.bishnuRepository.save(user);
    session.setAttribute("message", new Message("Succesfull Update account!!", "success"));
    }catch (Exception e) {
      e.printStackTrace();
    }
    return "bishnu/normal/profile";
    }
  
  //open password change page click of setting
  @GetMapping("/setting")
  public String openSetting(Model model, Principal principal) {
    String userName = principal.getName();
    BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
     model.addAttribute("user", user);
    model.addAttribute("title", "setting page");
    return "bishnu/normal/setting";
  }
  
  //update password change page click of submit from setting page
  @PostMapping("/passwordUpdate")
  public String passwordUpdate(@Valid  RePassword rePassword,BindingResult result, Model model, Principal principal, HttpSession session) {
    model.addAttribute("title", "setting page");
    if (result.hasErrors()) {
      model.addAttribute("title", "setting page");
      session.setAttribute("message", new Message("Please enter a 6 digit above password !!", "danger"));
      return "bishnu/normal/setting";
   }

    String userName = principal.getName();
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
   if(this.passwordEncoder.matches(rePassword.getOldPassword(), user.getPassword())) {
    if(rePassword.getNewPassword().equals(rePassword.getReNewPassword())) {
      user.setPassword(this.passwordEncoder.encode(rePassword.getNewPassword()));
      bishnuRepository.save(user);
      session.setAttribute("message", new Message("Succesfull Password Update Your  account!!", "success"));  
      }
    session.setAttribute("message", new Message("Please enter a same password  !!!", "danger"));
    }else {

    session.setAttribute("message", new Message("Failed Password Update Your  account!!", "danger"));
    }

    return "bishnu/normal/setting";
  }
  
  
  
  }



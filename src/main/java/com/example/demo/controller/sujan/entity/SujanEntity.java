package com.example.demo.controller.sujan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "entry_table")
public class SujanEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 名前
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 国籍
	 */
	@Column(name = "nationality")
	private boolean nationality;
	/**
	 * 性別
	 */
	@Column(name = "gender")
	private String gender;
	/**
	 * 年
	 */
	@Column(name = "date_Of_Year")
	private String dateOfYear;
	/**
	 * 月
	 */
	@Column(name = "date_Of_Month")
	private String dateOfMonth;
	/**
	 * 日
	 */
	@Column(name = "date_Of_Day")
	private String dateOfDay;
	/**
	 * 結婚
	 */
	@Column(name = "merriage")
	private String MerriageSts;
	/**
	 * メールアドレス
	 */
	@Column(name = "email")
	private String email;
	/**
	 * 電話１
	 */
	@Column(name = "mobile_No1")
	private String mobileNo1;
	/**
	 * 電話２
	 */
	@Column(name = "mobile_No2")
	private String mobileNo2;
	/**
	 * 電話３
	 */
	@Column(name = "mobile_No3")

	private String mobileNo3;
	/**
	 * 郵便番号
	 */
	@Column(name = "post_NO")
	private String postNO;
	/**
	 * 住所１
	 */
	@Column(name = "address1")
	private String address1;
	/**
	 * 住所２
	 */
	@Column(name = "address2")
	private String address2;
	/**
	 * 居住状況
	 */
	@Column(name = "living_Situation")
	private String livingSituation;
	/**
	 * 自宅ローン
	 */
	@Column(name = "housing_Loan")
	private String housingLoan;
	/**
	 * 免許
	 */
	@Column(name = "driving_License_Label")
	private String drivingLicenseLabel;
	/**
	 * 免許番号
	 */
	@Column(name = "driving_License")
	private String drivingLicense;
	/**
	 * ログインID
	 */
	@Column(name = "user_Id")
	private String userId;
	/**
	 * パスワード
	 */
	@Column(name = "pwd")
	private String pwd;
	/**
	 * 再パスワード
	 */
	@Column(name = "re_Pwd")
	private String rePwd;

	/**
	 * 更新日時
	 */
	@Column(name = "update_date")
	private Date updateDate;
	/**
	 * 登録日時
	 */
	@Column(name = "create_date")
	private Date createDate;
	/**
	 * 削除日時
	 */
	@Column(name = "delete_date")
	private Date deleteDate;


}

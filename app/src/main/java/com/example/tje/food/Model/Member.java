package com.example.tje.food.Model;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable {





	private String member_id;
	private String member_password;
	private String member_name;
	private String member_nickname;
	private String member_tel;

	private int member_address_id;

	private String member_email;
	private String member_birthday;
	private String member_gender;
	private int member_type =0;
	private String member_photo;
	private Date member_date;

	private Member_address member_address;

	public Member_address getMember_address() {
		return member_address;
	}

	public void setMember_address(Member_address member_address) {
		this.member_address = member_address;
	}

	public void setMember_address_id(int member_address_id) {
		this.member_address_id = member_address_id;
	}

	public Member() {

	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_password() {
		return member_password;
	}

	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_tel() {
		return member_tel;
	}

	public void setMember_tel(String member_tel) {
		this.member_tel = member_tel;
	}



	public int getMember_address_id() {
		return member_address_id;
	}

	public String getMember_email() {
		return member_email;
	}

	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}


	public String getMember_birthday() {
		return member_birthday;
	}

	public void setMember_birthday(String member_birthday) {
		this.member_birthday = member_birthday;
	}

	public String getMember_gender() {
		return member_gender;
	}

	public void setMember_gender(String member_gender) {
		this.member_gender = member_gender;
	}

	public int getMember_type() {
		return member_type;
	}

	public void setMember_type(int member_type) {
		this.member_type = member_type;
	}

	public String getMember_photo() {
		return member_photo;
	}

	public void setMember_photo(String member_photo) {
		this.member_photo = member_photo;
	}

	public Date getMember_date() {
		return member_date;
	}

	public void setMember_date(Date member_date) {
		this.member_date = member_date;
	}


}

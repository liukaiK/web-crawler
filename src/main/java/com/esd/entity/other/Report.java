package com.esd.entity.other;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 举报
 * 
 * @author liukai
 * 
 */
@Document
public class Report {
	private String id;
	private String name = "";
	private String gender = "";
	private String phone = "";
	private String adress = "";
	private String password = "";
	private String affirmpassword = "";
	private String designation = "";
	private String city = "";
	private String local = "";
	private String number = "";
	private String goods = "";
	private String goods_name = "";
	private String look_into = "";
	private String award = "";
	private String secrecy = "";
	private String communication = "";
	private String investigate = "";
	private String happen_time = "";
	private String content = "";
	private String date; // 表单提交时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAffirmpassword() {
		return affirmpassword;
	}

	public void setAffirmpassword(String affirmpassword) {
		this.affirmpassword = affirmpassword;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getLook_into() {
		return look_into;
	}

	public void setLook_into(String look_into) {
		this.look_into = look_into;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getSecrecy() {
		return secrecy;
	}

	public void setSecrecy(String secrecy) {
		this.secrecy = secrecy;
	}

	public String getCommunication() {
		return communication;
	}

	public void setCommunication(String communication) {
		this.communication = communication;
	}

	public String getInvestigate() {
		return investigate;
	}

	public void setInvestigate(String investigate) {
		this.investigate = investigate;
	}

	public String getHappen_time() {
		return happen_time;
	}

	public void setHappen_time(String happen_time) {
		this.happen_time = happen_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

package com.esd.entity.other;


/**
 * 我要投诉实体类
 * 
 * @author liukai
 * 
 */
public class Complain {
	private String conTarget;
	private String conUnit;
	private String conType;
	private String passengerType;
	private String passengerName;
	private String zhengjianid;
	private String shenfenId;
	private String flightNo; // 航班号
	private String year; //乘机日期年份
	private String month; //乘机日期月份
	private String day; //乘机日期日期
	private String passengerTel; // 联系电话
	
	public String getConTarget() {
		return conTarget;
	}
	public void setConTarget(String conTarget) {
		this.conTarget = conTarget;
	}
	public String getConUnit() {
		return conUnit;
	}
	public void setConUnit(String conUnit) {
		this.conUnit = conUnit;
	}
	public String getConType() {
		return conType;
	}
	public void setConType(String conType) {
		this.conType = conType;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getZhengjianid() {
		return zhengjianid;
	}
	public void setZhengjianid(String zhengjianid) {
		this.zhengjianid = zhengjianid;
	}
	public String getShenfenId() {
		return shenfenId;
	}
	public void setShenfenId(String shenfenId) {
		this.shenfenId = shenfenId;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getPassengerTel() {
		return passengerTel;
	}
	public void setPassengerTel(String passengerTel) {
		this.passengerTel = passengerTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepPort() {
		return depPort;
	}
	public void setDepPort(String depPort) {
		this.depPort = depPort;
	}
	public String getArrPort() {
		return arrPort;
	}
	public void setArrPort(String arrPort) {
		this.arrPort = arrPort;
	}
	public String getCptContent() {
		return cptContent;
	}
	public void setCptContent(String cptContent) {
		this.cptContent = cptContent;
	}
	private String email; // 邮箱
	private String depPort; // 出发航站
	private String arrPort; // 到达航站
	private String cptContent;// 正文



}

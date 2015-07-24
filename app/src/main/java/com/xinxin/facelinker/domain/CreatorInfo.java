package com.xinxin.facelinker.domain;

public class CreatorInfo {

	public CreatorInfo(String name, String address, String qq) {
		super();
		this.name = name;
		this.address = address;
		this.qq = qq;
	}
	public CreatorInfo() {
		// TODO Auto-generated constructor stub
	}
	String name;
	String address;
	String qq;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	@Override
	public String toString() {
		return "CreatorInfo [name=" + name + ", address=" + address + ", qq="
				+ qq + "]";
	}
	

}

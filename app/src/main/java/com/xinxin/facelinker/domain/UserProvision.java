package com.xinxin.facelinker.domain;

public class UserProvision {

	public UserProvision() {
		// TODO Auto-generated constructor stub
	}
	String provison;
	int provisonVersion;
	@Override
	public String toString() {
		return "UserProvision [provison=" + provison + ", provisonVersion="
				+ provisonVersion + "]";
	}
	public String getProvison() {
		return provison;
	}
	public void setProvison(String provison) {
		this.provison = provison;
	}
	public int getProvisonVersion() {
		return provisonVersion;
	}
	public void setProvisonVersion(int provisonVersion) {
		this.provisonVersion = provisonVersion;
	}
	public UserProvision(String provison, int provisonVersion) {
		super();
		this.provison = provison;
		this.provisonVersion = provisonVersion;
	}


}

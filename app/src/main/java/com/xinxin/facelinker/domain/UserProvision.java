package com.xinxin.facelinker.domain;

public class UserProvision extends EntityBase{

	public UserProvision() {
		// TODO Auto-generated constructor stub
	}
	int id;
	String provison;
	int provisonVersion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "UserProvision{" +
				"id=" + id +
				", provison='" + provison + '\'' +
				", provisonVersion=" + provisonVersion +
				'}';
	}
}

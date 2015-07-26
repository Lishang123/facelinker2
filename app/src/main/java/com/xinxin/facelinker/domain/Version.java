package com.xinxin.facelinker.domain;

public class Version extends EntityBase{
	int id;
	int version;
	String versionName;
	String url;
	String describe;

	public Version() {

	}

	public Version (int version, String versionName, String url, String describe) {

		this.version = version;
		this.versionName = versionName;
		this.url = url;
		this.describe = describe;
	}

	@Override
	public String toString() {
		return "Version{" +
				"id=" + id +
				", version=" + version +
				", versionName='" + versionName + '\'' +
				", url='" + url + '\'' +
				", describe='" + describe + '\'' +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}

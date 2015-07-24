package com.xinxin.facelinker.domain;

import java.util.Date;

public class Feedback {
	String userId;
	Date timeDate;
	String feedMessage;
	

	@Override
	public String toString() {
		return "Feedback [userId=" + userId + ", timeDate=" + timeDate
				+ ", feedMessage=" + feedMessage + "]";
	}


	public Feedback(String userId, Date timeDate, String feedMessage) {
		super();
		this.userId = userId;
		this.timeDate = timeDate;
		this.feedMessage = feedMessage;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Date getTimeDate() {
		return timeDate;
	}


	public void setTimeDate(Date timeDate) {
		this.timeDate = timeDate;
	}


	public String getFeedMessage() {
		return feedMessage;
	}


	public void setFeedMessage(String feedMessage) {
		this.feedMessage = feedMessage;
	}


	public Feedback() {
		// TODO Auto-generated constructor stub
	}

}

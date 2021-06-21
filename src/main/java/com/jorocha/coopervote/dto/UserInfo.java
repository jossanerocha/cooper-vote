package com.jorocha.coopervote.dto;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isAbleToVote() {
		return this.getStatus().equalsIgnoreCase("ABLE_TO_VOTE");
	}
	
	
}

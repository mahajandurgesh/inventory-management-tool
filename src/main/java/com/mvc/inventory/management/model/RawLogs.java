package com.mvc.inventory.management.model;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class RawLogs {
	@Id
	private int logId;
	private int id;
	private String name;
	private int quantity;
	private String status;
	private String issuer;
	private String timeStamp;
	
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public RawLogs(int logId, int id, String name, int quantity, String status, String issuer, String timeStamp) {
		super();
		this.logId = logId;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.status = status;
		this.issuer = issuer;
		this.timeStamp = timeStamp;
	}
	public RawLogs() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "RawLogs [logId=" + logId + ", id=" + id + ", name=" + name + ", quantity=" + quantity + ", issuer="
				+ issuer + ", timeStamp=" + timeStamp + "]";
	}
	
	
}

package com.noahark.calcedit.db;

public class HspObject {

	private String objectCode;
	private String objectName;
	private String objectParent;
	private String objectType;
	private String custom1;
	private String custom2;
	private String custom3;
	private String custom4;
	
	
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObjectParent() {
		return objectParent;
	}
	public void setObjectParent(String objectParent) {
		this.objectParent = objectParent;
	}
	
	
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public HspObject(String objectCode, String objectName, String objectParent, String objectType) {
		super();
		this.objectCode = objectCode;
		this.objectName = objectName;
		this.objectParent = objectParent;
		this.objectType = objectType;
	}
	
	
	
}

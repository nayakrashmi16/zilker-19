package io.ztech.expensesapp.beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Expense {
	int eId;
	int uId;
	String expensePayer;
	public String getExpensePayer() {
		return expensePayer;
	}

	public void setExpensePayer(String expensePayer) {
		this.expensePayer = expensePayer;
	}

	int categoryId;
	int typeId;
	String description;
	float amount;
	String category;
	String type;
	String createdAt;
	String updatedAt;
	
	

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt.toGMTString();
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt.toGMTString();
	}

	
	public String getUpdatedAt() {
		return updatedAt;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int geteId() {
		return eId;
	}

	public void seteId(int eId) {
		this.eId = eId;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	// date

}

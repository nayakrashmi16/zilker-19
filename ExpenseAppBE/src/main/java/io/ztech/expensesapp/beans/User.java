package io.ztech.expensesapp.beans;

import java.util.ArrayList;

public class User {
	int uId;
	String userName;
	String emailId;
	String password;
	float expenseLimit;
	ArrayList<Expense> expenses = new ArrayList<Expense>();
	ArrayList<Group> groups = new ArrayList<Group>();

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getExpenseLimit() {
		return expenseLimit;
	}

	public void setExpenseLimit(float expenseLimit) {
		this.expenseLimit = expenseLimit;
	}

	public ArrayList<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(ArrayList<Expense> expenses) {
		this.expenses = expenses;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

}

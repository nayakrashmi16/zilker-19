package io.ztech.expensesapp.beans;

import java.util.ArrayList;

public class Group {
	int gId;
	String groupName;
	ArrayList<GroupPayment> groupPayments;
	ArrayList<User> users;

	public Group() {
		groupPayments = new ArrayList<>();
		users = new ArrayList<>();
	}

	public ArrayList<GroupPayment> getGroupPayments() {
		return groupPayments;
	}

	public void setGroupPayments(ArrayList<GroupPayment> groupPayments) {
		this.groupPayments = groupPayments;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}

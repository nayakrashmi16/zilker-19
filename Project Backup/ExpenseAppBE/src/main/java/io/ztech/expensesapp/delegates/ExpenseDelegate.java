package io.ztech.expensesapp.delegates;

import java.util.ArrayList;

import io.ztech.expensesapp.beans.Expense;
import io.ztech.expensesapp.beans.Group;
import io.ztech.expensesapp.beans.GroupPayment;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.constants.DisplayConstants;
import io.ztech.expensesapp.exceptions.CouldNotAddMembersException;
import io.ztech.expensesapp.exceptions.LoginFailedException;
import io.ztech.expensesapp.exceptions.UsernameAlreadyExistsException;
import io.ztech.expensesapp.dao.ExpenseDAO;

public class ExpenseDelegate {
	ExpenseDAO expenseDao;

	public ExpenseDelegate() {
		expenseDao = new ExpenseDAO();
	}

	public void signUp(User user) throws UsernameAlreadyExistsException {
		if (expenseDao.isExistingUserName(user.getUserName())) {
			throw new UsernameAlreadyExistsException(DisplayConstants.USERNAME_EXISTS);
		}
		expenseDao.signUp(user);

	}

	public User logIn(User user) throws LoginFailedException {
		User activeUser = expenseDao.logIn(user);
		if (activeUser == null) {
			throw new LoginFailedException(DisplayConstants.INVALID_USERNAME_PASSWORD);
		}
		return activeUser;
	}

	public void addNewExpense(Expense expense) {
		if (expense instanceof GroupPayment)
			expenseDao.addGroupExpense((GroupPayment) expense);
		else
			expenseDao.addNewExpense(expense);

	}

	public User showAllExpense(User activeUser, int categoryId) {

		User user = expenseDao.showAllExpenses(activeUser, categoryId);
		return user;
	}

	public User viewGroups(int id) {
		User user = expenseDao.viewGroups(id);
		return user;
	}

	public int createGroups(Group group) throws CouldNotAddMembersException {
		try {
			return expenseDao.createGroups(group);
		} catch (CouldNotAddMembersException e) {
			throw new CouldNotAddMembersException(e.getMessage());
		}
	}

	public void addExpenseMembers(GroupPayment groupPayment) {
		expenseDao.addExpenseMembers(groupPayment);
	}

	public Group viewGroupExpenses(int gId) {
		Group group = expenseDao.viewGroupExpenses(gId);
		return group;
	}

	public void editExpenseLimit(User activeUser) {
		expenseDao.editExpenseLimit(activeUser);
	}

	public GroupPayment viewBalances(Group activeGroup) {
		GroupPayment groupPayment = expenseDao.viewBalances(activeGroup);
		return groupPayment;
	}
	
	public ArrayList<User> searchUser(String userInput) {
		ArrayList<User> users = expenseDao.searchUser(userInput);
		return users;
	}
}

package io.ztech.expensesapp.services;

import io.ztech.expensesapp.beans.Expense;
import io.ztech.expensesapp.beans.Group;
import io.ztech.expensesapp.beans.GroupPayment;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.exceptions.CouldNotAddMembersException;
import io.ztech.expensesapp.exceptions.LoginFailedException;
import io.ztech.expensesapp.exceptions.UsernameAlreadyExistsException;
import io.ztech.expensesapp.dao.ExpenseDAO;
import io.ztech.expensesapp.delegates.ExpenseDelegate;

public class ExpenseService {
	ExpenseDelegate expenseDelegate;

	public ExpenseService() {
		expenseDelegate = new ExpenseDelegate();
	}

	public void signUp(User user) throws UsernameAlreadyExistsException {
		try {
			expenseDelegate.signUp(user);
		} catch (UsernameAlreadyExistsException e) {
			throw new UsernameAlreadyExistsException(e.getMessage());
		}
	}

	public User logIn(User user) throws LoginFailedException {
		User activeUser;
		try {
			activeUser = expenseDelegate.logIn(user);
		} catch (LoginFailedException e) {
			throw new LoginFailedException(e.getMessage());

		}
		return activeUser;
	}

	public void addNewExpense(Expense expense) {
		expenseDelegate.addNewExpense(expense);
	}

	public User showAllExpense(User activeUser, int categoryId) {
		User user = expenseDelegate.showAllExpense(activeUser, categoryId);
		return user;
	}

	public User viewGroups(User activeUser) {
		User user = expenseDelegate.viewGroups(activeUser);
		return user;
	}

	public void createGroups(User activeUser) throws CouldNotAddMembersException {
		try {
			expenseDelegate.createGroups(activeUser);
		} catch (CouldNotAddMembersException e) {
			throw new CouldNotAddMembersException(e.getMessage());
		}
	}

	public void addExpenseMembers(GroupPayment groupPayment) {
		expenseDelegate.addExpenseMembers(groupPayment);
	}

	public Group viewGroupExpenses(Group activeGroup) {
		Group group = expenseDelegate.viewGroupExpenses(activeGroup);
		return group;
	}

	public GroupPayment viewBalances(Group activeGroup) {
		GroupPayment groupPayment = expenseDelegate.viewBalances(activeGroup);
		return groupPayment;
	}

	public void editExpenseLimit(User activeUser) {
		expenseDelegate.editExpenseLimit(activeUser);
	}
}

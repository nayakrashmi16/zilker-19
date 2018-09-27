package io.ztech.expensesapp.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ztech.expensesapp.beans.Expense;
import io.ztech.expensesapp.beans.Group;
import io.ztech.expensesapp.beans.GroupPayment;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.constants.DisplayConstants;
import io.ztech.expensesapp.delegates.ExpenseDelegate;
import io.ztech.expensesapp.exceptions.CouldNotAddMembersException;
import io.ztech.expensesapp.exceptions.LoginFailedException;
import io.ztech.expensesapp.exceptions.UsernameAlreadyExistsException;

@RestController
@RequestMapping("/ExpenseAppBE")
public class ExpenseController {

	@PostMapping("/signUp")
	public void signUp(@RequestBody User user) throws UsernameAlreadyExistsException {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		System.out.println("Username:"+user.getUserName()+" Password:"+user.getPassword()+"Email:"+user.getEmailId());
		expenseDelegate.signUp(user);
	}
	
	@PostMapping("/login")
	public User logIn(@RequestBody User user) throws LoginFailedException {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		System.out.println("Username:"+user.getUserName()+" Password:"+user.getPassword());
		User activeUser =  expenseDelegate.logIn(user);
		if(activeUser == null) {
			throw new LoginFailedException(DisplayConstants.INVALID_USERNAME_PASSWORD);
		}
		return activeUser;
	}
	
	@PostMapping("/addExpense")
	public void addExpense(@RequestBody Expense expense) {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		expenseDelegate.addNewExpense(expense);
}
	
	@PostMapping("/viewExpenses/{id}")
	public User showAllExpense(@RequestBody User activeUser, @PathVariable int id) {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		User user = expenseDelegate.showAllExpense(activeUser, id);
		return user;
	}
	
	@GetMapping("/viewGroups/{id}")
	public User viewGroups(@PathVariable int id) {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		User user = expenseDelegate.viewGroups(id);
	    return user;
	}
	
	@GetMapping("/viewGroupExpenses/{id}")
	public Group viewGroupExpenses(@PathVariable int id) {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		Group group = expenseDelegate.viewGroupExpenses(id);
		System.out.println("Group Members:"+group.getUsers());
		return group;
	}
	
	@PostMapping("/createGroup") 
	public Integer createGroups(@RequestBody Group group) throws CouldNotAddMembersException {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		System.out.println("GroupDetails Service:"+group);
		Integer gId = expenseDelegate.createGroups(group);
		return gId;
	}
	
	@GetMapping("/searchUser/{userInput}")
	public ArrayList<User> searchUser(@PathVariable String userInput) {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		ArrayList<User> users = expenseDelegate.searchUser(userInput);
		return users;
	}
	
	@PostMapping("/addGroupExpense")
	public void addGroupExpense(@RequestBody GroupPayment groupPayment) {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		expenseDelegate.addNewExpense(groupPayment);
	}
	
	@PostMapping("/addExpenseMembers")
	public void addExpenseMembers(@RequestBody GroupPayment groupPayment) {
		ExpenseDelegate expenseDelegate = new ExpenseDelegate();
		expenseDelegate.addExpenseMembers(groupPayment);
	}
	
	
	
	
}

package io.ztech.expensesapp.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.ztech.expensesapp.beans.Expense;
import io.ztech.expensesapp.beans.Group;
import io.ztech.expensesapp.beans.GroupPayment;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.constants.DisplayConstants;
import io.ztech.expensesapp.exceptions.CouldNotAddMembersException;
import io.ztech.expensesapp.exceptions.LoginFailedException;
import io.ztech.expensesapp.exceptions.UsernameAlreadyExistsException;

public class ExpenseService {
	ArrayList<String> list;

	public ExpenseService() {
	}

	public void signUp(User user) throws UsernameAlreadyExistsException {
		String output = "";
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/signUp");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String userGson = new Gson().toJson(user);
			out.write(userGson.getBytes());
			out.flush();
			out.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server-Signup:");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User logIn(User user) throws LoginFailedException {
		User activeUser = null;
		String output = "";
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/login");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String userGson = new Gson().toJson(user);
			out.write(userGson.getBytes());
			out.flush();
			out.close();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server-logIn:");
			while ((output = br.readLine()) != null) {
				sb.append(output);

			}
			activeUser = new Gson().fromJson(sb.toString(), User.class);
			if (activeUser == null) {
				throw new LoginFailedException(DisplayConstants.INVALID_USERNAME_PASSWORD);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return activeUser;
	}

	public void addNewExpense(Expense expense) {
		System.out.println("In add expense Service:"+expense.getClass());
		String output = "";
		try {
			URL url;
			if(expense instanceof GroupPayment)
				url = new URL("http://localhost:8090/ExpenseAppBE/addGroupExpense");
			else
				url = new URL("http://localhost:8090/ExpenseAppBE/addExpense");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String expenseGson = new Gson().toJson(expense);
			out.write(expenseGson.getBytes());
			out.flush();
			out.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server-addNewExpense:");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public User showAllExpense(User activeUser, int categoryId) {
		User user = null;
		String output = "";
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/viewExpenses/" + categoryId);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String userGson = new Gson().toJson(activeUser);
			out.write(userGson.getBytes());
			out.flush();
			out.close();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server-showAllExpense:");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			user = new Gson().fromJson(sb.toString(), User.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return user;
	}

	public User viewGroups(int id) {
		User user = null;
		String output = "";
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/viewGroups/" + id);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server-viewGroups:");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			user = new Gson().fromJson(sb.toString(), User.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return user;
	}

	public int createGroups(Group group) throws CouldNotAddMembersException {
		System.out.println("In create groups Service!");
		int gId = 0;
		String output = "";
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/createGroup");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String groupGson = new Gson().toJson(group);
			System.out.println("groupGson:"+groupGson);
			out.write(groupGson.getBytes());
			out.flush();
			out.close();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server-createGroup:");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			gId = new Gson().fromJson(sb.toString(), Integer.class);

		} catch (MalformedURLException e) {
			//e.printStackTrace();
		}

		catch (IOException e) {
			//e.printStackTrace();
		}
		return gId;

	}

	public void addExpenseMembers(GroupPayment groupPayment) {
		String output = "";
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/addExpenseMembers");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String groupPaymentGson = new Gson().toJson(groupPayment);
			out.write(groupPaymentGson.getBytes());
			out.flush();
			out.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server-addExpenseMembers:");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Group viewGroupExpenses(int gId) {
		System.out.println("in view group service");
		Group group = null;
		String output = "";
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/viewGroupExpenses/" + gId);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server at View Group Expenses:");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			group = new Gson().fromJson(sb.toString(), Group.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return group;
	}

//	public GroupPayment viewBalances(Group activeGroup) {
//		GroupPayment groupPayment = expenseDelegate.viewBalances(activeGroup);
//		return groupPayment;
//	}

//	public void editExpenseLimit(User activeUser) {
//		expenseDelegate.editExpenseLimit(activeUser);
//	}

	public ArrayList<User> searchUser(String userInput) {
		String output = "";
		ArrayList<User> users = null;
		try {
			URL url = new URL("http://localhost:8090/ExpenseAppBE/searchUser/" + userInput);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Output from Server at View Group Expenses:");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			Type listType = new TypeToken<List<User>>() {
			}.getType();
			users = new Gson().fromJson(sb.toString(), listType);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

}

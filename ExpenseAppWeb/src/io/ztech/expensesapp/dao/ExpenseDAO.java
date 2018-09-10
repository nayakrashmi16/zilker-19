package io.ztech.expensesapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import io.ztech.expensesapp.beans.Expense;
import io.ztech.expensesapp.beans.ExpenseMember;
import io.ztech.expensesapp.beans.Group;
import io.ztech.expensesapp.beans.GroupPayment;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.constants.DisplayConstants;
import io.ztech.expensesapp.constants.QueryConstants;
import io.ztech.expensesapp.dbutils.DBManager;
import io.ztech.expensesapp.exceptions.CouldNotAddMembersException;

public class ExpenseDAO {
	Connection connection;
	PreparedStatement prepareStatement;
	ResultSet resultSet;
	DBManager dbManager;

	public ExpenseDAO() {
		dbManager = new DBManager();
	}

	public void signUp(User user) {
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.INSERT_INTO_USERS);
			prepareStatement.setString(1, user.getUserName());
			prepareStatement.setString(2, user.getEmailId());
			prepareStatement.setString(3, user.getPassword());
			prepareStatement.setFloat(4, user.getExpenseLimit());
			prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("Here in DAO");
			e.printStackTrace();
		}
		finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
		}
	}

	public boolean isExistingUserName(String userName) {
		boolean isPresent = false;
		try {

			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.CHECK_EXISTING_USERNAME);
			prepareStatement.setString(1, userName);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getInt(1) > 0)
					isPresent = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
			return isPresent;
		}

	}

	public User logIn(User user) {
		User activeUser = null;
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.VALIDATE_USER);
			prepareStatement.setString(1, user.getUserName());
			prepareStatement.setString(2, user.getUserName());
			prepareStatement.setString(3, user.getPassword());
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				activeUser = new User();
				activeUser.setUserName(resultSet.getString(1));
				activeUser.setEmailId(resultSet.getString(2));
				activeUser.setExpenseLimit(resultSet.getFloat(3));
				activeUser.setuId(resultSet.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
			return activeUser;
		}
	}

	public void addNewExpense(Expense expense) {
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.INSERT_INTO_EXPENSES);
			prepareStatement.setInt(1, expense.getuId());
			prepareStatement.setInt(2, expense.getCategoryId());
			prepareStatement.setInt(3, expense.getTypeId());
			prepareStatement.setString(4, expense.getDescription());
			prepareStatement.setFloat(5, expense.getAmount());
			prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
		}
	}

	public User showAllExpenses(User activeUser, int categoryId) {
		User user = new User();
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.SELECT_GROUP_EXPENSES);
			prepareStatement.setInt(1, activeUser.getuId());
			prepareStatement.setInt(2, categoryId);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				GroupPayment expense = new GroupPayment();
				expense.setTypeId(resultSet.getInt(1));
				expense.setDescription(resultSet.getString(2));
				expense.setAmount(resultSet.getFloat(3));
				expense.setPaymentId(resultSet.getInt(4));
				expense.setCreatedAt(resultSet.getTimestamp(5));
				expense.setUpdatedAt(resultSet.getTimestamp(6));
				expenses.add(expense);
				
			}
			prepareStatement = connection.prepareStatement(QueryConstants.SELECT_ALL_EXPENSES);
			prepareStatement.setInt(1, activeUser.getuId());
			prepareStatement.setInt(2, categoryId);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Expense expense = new Expense();
				expense.setTypeId(resultSet.getInt(1));
				expense.setDescription(resultSet.getString(2));
				expense.setAmount(resultSet.getFloat(3));
				expense.seteId(resultSet.getInt(4));
				expense.setCreatedAt(resultSet.getTimestamp(5));
				expense.setUpdatedAt(resultSet.getTimestamp(6));
				expenses.add(expense);
			}

			user.setExpenses(expenses);
			for (Expense expense : user.getExpenses()) {
				prepareStatement = connection.prepareStatement(QueryConstants.SELECT_CATEGORY_AND_TYPE);
				prepareStatement.setInt(1, categoryId);
				prepareStatement.setInt(2, expense.getTypeId());
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					expense.setCategory(resultSet.getString(1));
					expense.setType(resultSet.getString(2));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
			return user;
		}
	}

	public User viewGroups(User activeUser) {
		User user = new User();
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.SELECT_GROUPS);
			prepareStatement.setInt(1, activeUser.getuId());
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Group group = new Group();
				group.setgId(resultSet.getInt(1));
				group.setGroupName(resultSet.getString(2));
				user.getGroups().add(group);
			}
			for (Group group : user.getGroups()) {
				prepareStatement = connection.prepareStatement(QueryConstants.SELECT_GROUP_MEMBERS);
				prepareStatement.setInt(1, group.getgId());
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					User member = new User();
					member.setuId(resultSet.getInt(1));
					member.setUserName(resultSet.getString(2));
					group.getUsers().add(member);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
			return user;
		}
	}

	public void createGroups(User activeUser) throws CouldNotAddMembersException {
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.INSERT_INTO_GROUPS);
			prepareStatement.setString(1, activeUser.getGroups().get(activeUser.getGroups().size() - 1).getGroupName());
			prepareStatement.execute();
			prepareStatement = connection.prepareStatement(QueryConstants.SELECT_RECENT_GID);
			resultSet = prepareStatement.executeQuery();
			int recentGid = 1;
			while (resultSet.next()) {
				recentGid = resultSet.getInt(1);
			}
			for (User user : activeUser.getGroups().get(activeUser.getGroups().size() - 1).getUsers()) {
				prepareStatement = connection.prepareStatement(QueryConstants.INSERT_INTO_GROUP_MEMBERS);
				prepareStatement.setInt(1, recentGid);
				prepareStatement.setString(2, user.getUserName());
				System.out.println("Inserted:" + prepareStatement.executeUpdate());

			}

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new CouldNotAddMembersException(DisplayConstants.USERNAME_INVALID_NOT_ADDED);
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
		}
	}

	public void addGroupExpense(GroupPayment groupExpense) {
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.INSERT_INTO_GROUP_EXPENSES);
			prepareStatement.setInt(1, groupExpense.getgId());
			prepareStatement.setInt(2, groupExpense.getuId());
			prepareStatement.setFloat(3, groupExpense.getAmount());
			prepareStatement.setInt(4, groupExpense.getCategoryId());
			prepareStatement.setInt(5, groupExpense.getTypeId());
			prepareStatement.setString(6, groupExpense.getDescription());
			prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);

		}
	}

	public void addExpenseMembers(GroupPayment groupPayment) {
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.INSERT_INTO_EXPENSE_MEMBERS);
			for (ExpenseMember expenseMember : groupPayment.getExpenseMembers()) {
				prepareStatement.setString(1, expenseMember.getUserName());
				prepareStatement.setFloat(2, expenseMember.getTotalAmount());
				prepareStatement.setBoolean(4, expenseMember.isPending());
				prepareStatement.setFloat(3, expenseMember.getAmountPaid());
				prepareStatement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace(); // TODO: handle exception
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);

		}
	}

	public Group viewGroupExpenses(Group activeGroup) {
		Group group = new Group();
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.SELECT_ALL_GROUP_EXPENSES);
			prepareStatement.setInt(1, activeGroup.getgId());
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("1");
				GroupPayment groupPayment = new GroupPayment();
				groupPayment.setPaymentId(resultSet.getInt(1));
				groupPayment.setuId(resultSet.getInt(2));
				groupPayment.setAmount(resultSet.getFloat(3));
				groupPayment.setDescription(resultSet.getString(4));
				groupPayment.setUpdatedAt(resultSet.getTimestamp(5));
				group.getGroupPayments().add(groupPayment);
			}
			for (GroupPayment groupPayment : group.getGroupPayments()) {
				prepareStatement = connection.prepareStatement(QueryConstants.SELECT_ALL_EXPENSE_MEMBERS);
				prepareStatement.setInt(1, activeGroup.getgId());
				prepareStatement.setInt(2, groupPayment.getPaymentId());
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					ExpenseMember member = new ExpenseMember();
					member.setuId(resultSet.getInt(1));
					member.setUserName(resultSet.getString(2));
					member.setAmountPaid(resultSet.getFloat(3));
					member.setPending(resultSet.getBoolean(4));
					groupPayment.getExpenseMembers().add(member);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
			return group;
		}

	}

	public void editExpenseLimit(User activeUser) {
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.UPDATE_EXPENSE_LIMIT);
			prepareStatement.setInt(2, activeUser.getuId());
			prepareStatement.setFloat(1, activeUser.getExpenseLimit());
			prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
		}
	}

	public GroupPayment viewBalances(Group activeGroup) {
		GroupPayment groupPayments = new GroupPayment();
		try {
			connection = dbManager.getConnection();
			prepareStatement = connection.prepareStatement(QueryConstants.FIND_GROUP_BALANCES);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				ExpenseMember member = new ExpenseMember();
				member.setuId(resultSet.getInt(1));
				member.setTotalAmount(resultSet.getFloat(2));
				groupPayments.getExpenseMembers().add(member);
			}
			for (ExpenseMember members : groupPayments.getExpenseMembers()) {
				prepareStatement = connection.prepareStatement(QueryConstants.SELECT_USERNAME);
				prepareStatement.setInt(1, members.getuId());
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					members.setUserName(resultSet.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.closeConnection(resultSet, prepareStatement, connection);
			return groupPayments;
		}

	}
}

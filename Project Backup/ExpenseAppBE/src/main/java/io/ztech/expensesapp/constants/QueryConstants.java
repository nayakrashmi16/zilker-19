package io.ztech.expensesapp.constants;

public class QueryConstants {
	public static final String INSERT_INTO_USERS = "insert into Users(user_name,email_id,password,expense_limit) values(?,?,md5(?),?)";
	public static final String CHECK_EXISTING_USERNAME = "select count(*) from Users where user_name = ?";
	public static final String VALIDATE_USER = "select user_name, email_id, expense_limit,u_id from Users where ( user_name = ? or email_id = ? )and password = md5(?)";
	public static final String INSERT_INTO_EXPENSES = "insert into Expenses(u_id,category_id,type_id,description,amount) values((select u_id from Users where user_name = ?),?,?,?,?)";
	public static final String SELECT_ALL_EXPENSES = "select type_id,description,amount,e_id,Expenses.created_at,Expenses.updated_at from Expenses where u_id = ? and category_id = ?";
	public static final String SELECT_GROUP_EXPENSES = "select type_id,description,amount,payment_id,Group_Expenses.created_at,Group_Expenses.updated_at from Group_Expenses where u_id = ? and category_id = ?";
	public static final String INSERT_INTO_GROUPS = "insert into Groups(group_name) values(?)";
	public static final String SELECT_RECENT_GID = "select g_id from Groups order by g_id desc limit 1";
	public static final String INSERT_INTO_GROUP_MEMBERS = "insert into Group_Members(g_id,u_id) values(?,(select u_id from Users where user_name = ?))";
	public static final String SELECT_CATEGORY_AND_TYPE = "select category_type, type from Category_Types,Expense_Types where category_id = ? and type_id = ?";
	public static final String SELECT_TYPE = "select type from Expense_Types where type_id = ?";
	public static final String SELECT_GROUPS = "select Groups.g_id, group_name from Groups join Group_Members on Groups.g_id = Group_Members.g_id where u_id = ?";
	public static final String SELECT_GROUP_MEMBERS = "select Group_Members.u_id,user_name from Group_Members join Users on Group_Members.u_id = Users.u_id where g_id = ?";
	public static final String INSERT_INTO_GROUP_EXPENSES = "insert into Group_Expenses(g_id,u_id,amount,type_id,description) values(?,(select u_id from Users where user_name = ?),?,?,?)";
	public static final String INSERT_INTO_EXPENSE_MEMBERS = "insert into Expense_Members(u_id,totalAmount,amountPaid,pending,payment_id) values((select u_id from Users where user_name = ?),?,?,?,(select payment_id from Group_Expenses order by payment_id desc limit 1))";
	public static final String SELECT_ALL_GROUP_EXPENSES = "select payment_id,u_id as id,amount,description,created_at,type_id,(select user_name from Users where u_id = id) as user_name from Group_Expenses where g_id = ?";
	public static final String SELECT_ALL_EXPENSE_MEMBERS = "select Expense_Members.u_id,  user_name, amountPaid,pending,totalAmount from Group_Expenses join Expense_Members on Group_Expenses.payment_id = Expense_Members.payment_id join Users on Users.u_id = Expense_Members.u_id where g_id = ? and Expense_Members.payment_id = ?"; 
	public static final String UPDATE_EXPENSE_LIMIT = "update Users set expense_limit = ? where u_id = ?";
	public static final String FIND_GROUP_BALANCES = "select u_id,sum(totalAmount)-sum(amountPaid) from Expense_Members group by(Expense_Members.u_id)";
	public static final String SELECT_USERNAME = "select user_name from Users where u_id = ?";
	public static final String SEARCH_USER = "select user_name, email_id from Users where user_name like ? or email_id like ?";
}

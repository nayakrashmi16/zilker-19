package io.ztech.expensesapp.constants;

public class DisplayConstants {
	public static final String STARTUP_MENU = "=============\n"
											+ " PENNY WISE  \n"
											+ "=============\n"
											+ "1.Sign Up 	\n"
											+ "2.Log In 	\n"
											+ "Enter Choice:";
	public static final String ENTER_VALID_CHOICE = "Enter valid choice!";
	public static final String INVALID_INPUT = "Invalid input!";
	public static final String ENTER_USERNAME = "Enter username:";
	public static final String ENTER_EMAILID = "Enter your email-id : ";
	public static final String ENTER_NEW_PASSWORD = "Enter a password [Minimum of 6 characters] : ";
	public static final String CONFIRM_PASSWORD = "Confirm password : ";
	public static final String ADD_EXPENSE_LIMIT = "Set your expense limit : ";
	public static final String ENTER_USERNAME_EMAIL = "Enter username or Registered email id:";
	public static final String ENTER_PASSWORD = "Enter password:";
	public static final String PASSWORD_ERROR = "Passwords don't match or password strength insufficient! Please re-enter details!";
	public static final String INVALID_EMAILID = "Enter valid email id!";
	public static final String USERNAME_EXISTS = "Username already exists!";
	public static final String INVALID_USERNAME_PASSWORD = "Incorrect username or password!";
	public static final String MAIN_MENU = "=====================	\n"
										 + "       MAIN MENU   		\n"
										 + "=====================	\n"
										 + "1.My expenses 			\n"
										 + "2.My Groups 	      	\n"
										 + "3.Edit Expense Limit	\n"
										 + "4.Log Out 				\n"
										 + "Enter your choice:";
	public static final String ENTER_AMOUNT = "Enter Amount:";
	public static final String ENTER_DESCRIPTION = "Enter a description (Max 100 characters) :";
	public static final String CATEGORY_TYPES = "===============\n"
											  + "   CATEGORIES  \n"
											  + "===============\n"
											  + "1.Utilities 	\n"
											  + "2.Travel 		\n"
											  + "3.Education 	\n"
											  + "Enter choice:";
	public static final String EXPENSE_TYPES = "================\n"
											 + "  EXPENSE TYPE  \n"
	                                    	 + "================\n"
	                                    	 + "1.Food     		\n"
	                                    	 + "2.Water 		\n"
	                                    	 + "3.Fuel 			\n"
	                                    	 + "4.Clothing 		\n"
	                                    	 + "5.Other  		\n"
	                                    	 + "Enter choice:";
	public static final String MY_EXPENSE_OPTIONS = "================== \n"
			                                      + "    MY EXPENSES    \n"
			                                      + "================== \n"
			                                      + "1.Add new expense  \n"
			                                      + "2.Show All expenses\n"
			                                      + "3.Go back          \n"
			                                      + "Enter choice:";
	public static final String GROUP_MENU = "================\n"
										  + "    MY GROUPS   \n"
										  + "================\n"
										  + "1.View Groups   \n"
										  + "2.Create Group  \n"
										  + "3.Go Back       \n"
										  + "Enter choice:";
	public static final String ENTER_GROUP_NAME = "Enter group name: ";
	public static final String ENTER_NO_OF_MEMBERS = "Enter no.of members: ";
	public static final String SUCCESSFUL_SIGNUP = "User signed up successfully!";
	public static final String SUCCESSFUL_LOGIN = "User Logged in successfully!";
	public static final String EXPENSE_ADDED = "Expense added successfully!";
	public static final String ENTER_GID = "Enter group ID to enter group, enter 0 to go back: ";
	public static final String NOT_INVOLVED = "You were not involved in this expense.";
	public static final String EXPENSE_PAID_BY = "Expense paid by : ";
	public static final String AMOUNT = "Amount          : ";
	public static final String DESCRIPTION = "Description     : ";
	public static final String LAST_UPDATED_AT = "Last Updated at : ";
	public static final String YOU_LENT = "You lent        : ";
	public static final String YOU_BORROWED = "You borrowed    : ";
	public static final String STATUS = "Status          : ";
	public static final String NOT_SETTLED = "Not Settled";
	public static final String SETTLED = "Settled";
	public static final String PAYMENT_MADE_BY = "Payment made by (Enter username) :";
	public static final String ENTER_EXISTING_USERNAME = "Enter username of existing member!";
	public static final String NO_OF_PEOPLE_TO_SPLIT = "Enter no.of people to split between (excluding payer):";
	public static final String ENTER_APART_FROM_PAYER = "Enter member apart from payer!";
	public static final String SPLIT_EQUAL_UNEQUAL = "Enter 1 to split unequally, anything else to split equally:";
	public static final String ENTER_AMOUNT_FOR = "Enter amount for  : ";
	public static final String 	AMOUNT_DOESNT_TALLY = "Amount does not tally, please check your math!";
	public static final String GROUP_CREATED = "Group created!";
	public static final String NEW_EXPENSE_LIMIT = "Enter new expense limit: ";
	public static final String EXPENSE_LIMIT_UPDATED = "Expenses limit updated!";
	public static final String ENTER_100_CHARACTERS = "Enter upto 100 characters!";
	public static final String GROUP_EXPENSE_HEADING = "Group Expenditures\n" + "================== ";
	public static final String EXPENSE_HEADING = "AMOUNT      DESCRIPTION           CATEGORY    TYPE        CREATED AT                      UPDATED AT";
	public static final String LINE = "----------------------------------------------------------------------------------------------------";
	public static final String TOTAL_GROUP_EXPENSE = "Total Group Expenditure: ";
	public static final String PERSONAL_EXPENSE_HEADING = "Personal Expenditures\n" + "=====================";
	public static final String TOTAL_PERSONAL_EXPENSE = "Total Personal Expenditure: ";
	public static final String TOTAL_EXPENSE = "Total Expenditure: ";
	public static final String YOU_HAVE_REACHED = "You have reached ";
	public static final String PERCENT_OF_LIMIT = "% of your total expense limit!";
	public static final String GETS_BACK = " gets back ";
	public static final String OWES = " owes ";
	public static final String ENTER_GROUP_MEMBERS = "Enter usernames of members (excluding you):";
	public static final String GROUP_HEADING = "G_ID  GROUP NAME";
	public static final String GROUP_OPTIONS = "====================			   \n GROUP \n"
			+ "====================			   \n" + "1.Add Expense 					   \n"
			+ "2.View Expenses 				   \n" + "3.View Balances                    \n"
			+ "4.View Members                     \n" + "5.Go Back                          \n"
			+ "Enter choice:";
	public static final String GROUP_MEMBER_HEADING = "U_ID  USERNAME";
	public static final String USERNAME_INVALID_NOT_ADDED = "Username(s) you have entered are invalid! Some members might not have been added!";
	private DisplayConstants() {

	}
}

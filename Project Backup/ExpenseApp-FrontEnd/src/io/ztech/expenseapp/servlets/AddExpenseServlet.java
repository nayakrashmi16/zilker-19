package io.ztech.expenseapp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import io.ztech.expensesapp.beans.Expense;
import io.ztech.expensesapp.services.ExpenseService;

/**
 * Servlet implementation class AddExpenseServlet
 */
@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ExpenseService expenseService;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddExpenseServlet() {
//		super();
		expenseService = new ExpenseService();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession currentSession = request.getSession(false);
		if(currentSession.getAttribute("uid") == null) {
			request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		System.out.println("in expense servlet-chan");
		String message = "Successfully added expense!";
		try {
			HttpSession currentSession = request.getSession(false);
			if(currentSession.getAttribute("uid") == null) {
				request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
			}
			 StringBuilder buffer = new StringBuilder();
			    BufferedReader reader = request.getReader();
			    String line;
			    while ((line = reader.readLine()) != null) {
			        buffer.append(line);
			    }
			    String data = buffer.toString();
			    System.out.println("Data:"+data);
			JSONObject expenseJSON = new JSONObject(data);
			String description = (String)expenseJSON.get("description");
			String amount = (String)expenseJSON.get("amount");
			String category = (String)expenseJSON.get("category");
			String expenseType = (String)expenseJSON.get("expenseType");
			String username = (String)currentSession.getAttribute("username");
			Expense expense = new Expense();
			expense.setAmount(Float.parseFloat(amount));
			expense.setCategoryId(Integer.parseInt(category));
			expense.setTypeId(Integer.parseInt(expenseType));
			expense.setDescription(description);
			expense.setExpensePayer(username);
			expenseService.addNewExpense(expense);
			System.out.println("In add expense servlet");
		} 
		catch (Exception e) {
			message = "Error in adding expense!";
			e.printStackTrace();
		}
		response.getWriter().write(message);
		

	}

}

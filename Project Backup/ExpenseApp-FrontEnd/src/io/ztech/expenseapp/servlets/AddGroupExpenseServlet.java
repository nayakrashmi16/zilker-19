package io.ztech.expenseapp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import io.ztech.expensesapp.beans.ExpenseMember;
import io.ztech.expensesapp.beans.GroupPayment;
import io.ztech.expensesapp.services.ExpenseService;

/**
 * Servlet implementation class AddGroupExpenseServlet
 */
@WebServlet("/AddGroupExpenseServlet")
public class AddGroupExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddGroupExpenseServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ExpenseService expenseService = new ExpenseService();
		HttpSession currentSession = request.getSession(false);
		if (currentSession == null) {
			request.setAttribute("message", "Please login to continue!");
			request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);

		}
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		try {
			GroupPayment groupPayment = new GroupPayment();
			JSONObject groupExpenseJson = new JSONObject(data);
			String description = groupExpenseJson.getString("description");
			Float amount = Float.parseFloat(groupExpenseJson.getString("amount"));
			Integer expenseType = Integer.parseInt(groupExpenseJson.getString("expenseType"));
			String expensePayer = groupExpenseJson.getString("expensePayer");
			Integer gId = Integer.parseInt(groupExpenseJson.getString("groupId"));
			JSONObject splitDetails = (JSONObject)groupExpenseJson.getJSONObject("splitDetails");
			ArrayList<ExpenseMember> expenseMembers = new ArrayList<ExpenseMember>();
			Iterator keys = splitDetails.keys();
			while(keys.hasNext()) {
				ExpenseMember expenseMember = new ExpenseMember();
				String key = (String) keys.next();
				expenseMember.setUserName(key);
				Float splitAmount = Float.parseFloat(splitDetails.getString(key));
				expenseMember.setTotalAmount(splitAmount);
				if(expenseMember.getUserName().compareTo(expensePayer) == 0) {
					expenseMember.setAmountPaid(splitAmount);
				}
				else {
					expenseMember.setAmountPaid(0);
				}
				expenseMembers.add(expenseMember);
			}
			groupPayment.setAmount(amount);
			groupPayment.setDescription(description);
			groupPayment.setgId(gId);
			groupPayment.setTypeId(expenseType);
			groupPayment.setExpensePayer(expensePayer);
			groupPayment.setExpenseMembers(expenseMembers);
			expenseService.addNewExpense(groupPayment);
			expenseService.addExpenseMembers(groupPayment);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

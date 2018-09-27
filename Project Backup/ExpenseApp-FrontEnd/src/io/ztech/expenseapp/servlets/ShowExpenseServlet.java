package io.ztech.expenseapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.ztech.expensesapp.beans.Expense;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.services.ExpenseService;

/**
 * Servlet implementation class ShowExpenseServlet
 */
@WebServlet("/ShowExpenseServlet")
public class ShowExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ExpenseService expenseService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowExpenseServlet() {
		super();
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
		if (currentSession == null) {
			request.setAttribute("message","Please login to continue");
			request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
		} 
		else {
			User activeUser = new User();
			int uId = (Integer) request.getSession(false).getAttribute("uid");
			activeUser.setuId(uId);
			int categoryId = Integer.parseInt(request.getParameter("category"));
			User user = expenseService.showAllExpense(activeUser, categoryId);
			for (Expense expense : user.getExpenses()) {
				System.out.println(expense.getAmount() + " " + expense.getDescription() + " " + expense.getType());
			}
			request.setAttribute("expenseList", user.getExpenses());
			request.getRequestDispatcher("/pages/showexpense.jsp").forward(request, response);
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
		HttpSession currentSession = request.getSession(false);
		if (currentSession == null) {
			request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
		}

	}

}

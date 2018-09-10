package io.ztech.expenseapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.exceptions.LoginFailedException;
import io.ztech.expensesapp.services.ExpenseService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ExpenseService expenseService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In do post");
		ArrayList responses = new ArrayList();
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username.equals("") || password.equals("")) {
			responses.add("Field cannot be blank!");
		}
		User user = new User();
		user.setUserName(username);
		user.setPassword(password);
		HttpSession session = request.getSession();
		try {
			User activeUser = expenseService.logIn(user);
			session.setAttribute("username", activeUser.getUserName());
			session.setAttribute("uid", activeUser.getuId());
			request.getRequestDispatcher("/pages/myexpense.jsp").forward(request,response);
		} catch (LoginFailedException e) {
			responses.add(e.getMessage());
//			printWriter.append(responses.toString());
			request.setAttribute("message",e.getMessage());
			request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
		}
	}

}

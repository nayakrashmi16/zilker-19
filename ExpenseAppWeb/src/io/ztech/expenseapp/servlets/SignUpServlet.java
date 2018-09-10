package io.ztech.expenseapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.constants.RegexConstants;
import io.ztech.expensesapp.delegates.ExpenseDelegate;
import io.ztech.expensesapp.exceptions.UsernameAlreadyExistsException;
import io.ztech.expensesapp.services.ExpenseService;
import io.ztech.expensesapp.services.Validator;

/**
 * Servlet implementation class FormValidation
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ExpenseService expenseService;
	Validator validator;

	/**
	 * Default constructor.
	 */

	public SignUpServlet() {
		expenseService = new ExpenseService();
		validator = new Validator();
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
		System.out.println(expenseService);
		User user = new User();
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		ArrayList responses = new ArrayList();
		String username = request.getParameter("username");
		String email = request.getParameter("email-id");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm-password");
		if (username == null || username.equals(""))
			responses.add("Please enter username!");
		if (email == null || email.equals(""))
			responses.add("Plese enter email!");
		else {
			if (!validator.isValidated(email, RegexConstants.EMAIL_REGEX))
				responses.add("Enter valid email!");
		}
		if (password == null || password.equals("") || confirmPassword == null || confirmPassword.equals(""))
			responses.add("Please enter a password!");
		else {
			if (password.compareTo(confirmPassword) != 0)
				responses.add("Password dont match!");
		}
		if(responses.size() != 0) {
			request.setAttribute("message", responses.toString());
			request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
		}
		user.setUserName(username);
		user.setEmailId(email);
		user.setPassword(password);
		try {
			expenseService.signUp(user);
			responses.add("Successfully signed up!");
			request.setAttribute("message", "Successfully signed up, Login to continue!");
		} catch (UsernameAlreadyExistsException e) {
			request.setAttribute("message", "Username already exists");
			responses.add("Username already exists");
		} catch (Exception e) {
			e.printStackTrace();
		}
		printWriter.append(responses.toString());
		request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
		
	}
}

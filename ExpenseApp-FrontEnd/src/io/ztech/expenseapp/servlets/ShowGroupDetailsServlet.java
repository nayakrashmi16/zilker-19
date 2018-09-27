package io.ztech.expenseapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.ztech.expensesapp.beans.Group;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.services.ExpenseService;

/**
 * Servlet implementation class ShowGroupDetailsServlet
 */
@WebServlet("/ShowGroupDetailsServlet")
public class ShowGroupDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowGroupDetailsServlet() {
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
		HttpSession currentSession = request.getSession(false);
		if (currentSession == null) {
			request.setAttribute("message", "Please login to continue!");
			request.getRequestDispatcher("/pages/landingpage.jsp").forward(request, response);
		} else {
			ExpenseService expenseService = new ExpenseService();
			int gId = Integer.parseInt((String) request.getParameter("gId"));
			User activeUser = new User();
			Integer uId = (Integer) currentSession.getAttribute("uid");
			String userName = (String) currentSession.getAttribute("username");
			activeUser.setuId(uId);
			activeUser.setUserName(userName);
			System.out.println("ExpenseService Object:"+expenseService);
			Group activeGroup = expenseService.viewGroupExpenses(gId);
			request.setAttribute("groupDetails", activeGroup);
			System.out.println("GroupMembers:"+activeGroup.getUsers());
			request.getRequestDispatcher("/pages/groupDetails.jsp").forward(request, response);
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

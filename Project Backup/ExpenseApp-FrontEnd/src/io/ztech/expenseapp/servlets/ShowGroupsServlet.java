package io.ztech.expenseapp.servlets;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class ShowGroupsServlet
 */
@WebServlet("/ShowGroupsServlet")
public class ShowGroupsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ExpenseService expenseService = new ExpenseService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowGroupsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession currentSession = request.getSession(false);
		User activeUser = new User();
		if(currentSession == null) {
			response.getWriter().write("Please login in continue!");
			request.getRequestDispatcher("/pages/landingpages.jsp").forward(request, response);
		}
		else {
			int id = (Integer)currentSession.getAttribute("uid");
			activeUser = expenseService.viewGroups(id);
			request.setAttribute("groups", activeUser.getGroups());
			request.getRequestDispatcher("/pages/groups.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
}

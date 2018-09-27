package io.ztech.expenseapp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import com.google.gson.Gson;

import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.services.ExpenseService;

/**
 * Servlet implementation class GetUserSuggestionServlet
 */
@WebServlet("/GetUserSuggestionServlet")
public class GetUserSuggestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserSuggestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*doGet(request, response);*/
		System.out.println("In UserSuggestion Do Post");
		ExpenseService expenseService = new ExpenseService();
		HttpSession currentSession = request.getSession(false);
		String activeUserName = (String)currentSession.getAttribute("username");
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    String userInput = buffer.toString();
	    userInput = userInput;
	    ArrayList<User> users = expenseService.searchUser(userInput);
	    Map<String, String> userMap = new LinkedHashMap<>();
	    for(User user : users) {
	    	if(user.getUserName().compareTo(activeUserName) != 0)
	    		userMap.put(user.getUserName(), user.getEmailId());
	    }
	    String usersJSON = new Gson().toJson(userMap);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(usersJSON);
	    System.out.println("JSON:"+usersJSON);
	    System.out.println("Leaving UserSuggestion Do Post");
	}

}

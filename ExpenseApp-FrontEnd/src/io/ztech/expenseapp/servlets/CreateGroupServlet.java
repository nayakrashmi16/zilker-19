package io.ztech.expenseapp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import io.ztech.expensesapp.beans.Group;
import io.ztech.expensesapp.beans.User;
import io.ztech.expensesapp.services.ExpenseService;

import org.json.JSONArray;
import org.json.JSONArray.*;

import com.google.gson.Gson;

/**
 * Servlet implementation class CreateGroupServlet
 */
@WebServlet("/CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateGroupServlet() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		ExpenseService expenseService = new ExpenseService();
		System.out.println("In Create Group Do Post");
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String groupDetails = buffer.toString();
		try {
			Group group = new Group();
			JSONObject groupDetailsJson = new JSONObject(groupDetails);
			String groupName = groupDetailsJson.getString("groupName");
			if(groupName == null || groupName.compareTo("") == 0) {
				request.setAttribute("message", "Group Name cannot be blank!");
				request.getRequestDispatcher("/pages/groups.jsp").forward(request, response);
			}
			group.setGroupName(groupName);
			JSONArray groupMembersJson= groupDetailsJson.getJSONArray("groupMembersList");
			ArrayList<User> groupMembers = group.getUsers();
			User user = new User();
			user.setUserName((String)request.getSession(false).getAttribute("username"));
			groupMembers.add(user);
			for(int i = 0; i < groupMembersJson.length(); i++) {
				User member = new User();
				member.setUserName(groupMembersJson.getString(i));
				groupMembers.add(member);
			}
			System.out.println("reached till here");
			Integer gId = expenseService.createGroups(group);
			JSONObject gIdJson = new JSONObject();
			gIdJson.put("gId", gId);
			System.out.println("JSON String:"+gIdJson.toString());
			response.getWriter().print(gIdJson.toString());
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}

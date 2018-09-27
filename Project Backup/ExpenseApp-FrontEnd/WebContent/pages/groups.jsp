<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="io.ztech.expensesapp.beans.Group"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/grid.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/myexpense.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/groups.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/fontawesome-free-5.2.0-web/css/all.css">
<link href="https://fonts.googleapis.com/css?family=Ubuntu+Condensed"
	rel="stylesheet">
<title>My Groups</title>
</head>
<body>
	<div class="header">
		<div class="header__app-heading col-sm-12 col-md-12 col-lg-6">
			<p>PennyWise</p>
		</div>
		<div class="header__app-tagline col-sm-12 col-md-12 col-lg-6">
			<!-- <p>Manage your expenses</p> -->
			<p>
				Welcome,
				<%=session.getAttribute("username") + "  "%>|
			</p>
			<a class="logout-button" href="<%= request.getContextPath() %>/LogoutServlet">Logout</a>
		</div>
		<div class="header__navbar col-sm-12 col-md-12 col-lg-12">
			<div
				class="header__sidebar-outer-container col-sm-6 col-md-6 col-lg-6">
				<div class="sidebar-button-container">
					<i class="fas fa-bars sidebar-button"></i>
				</div>
				<ul class="sidebar-options">
					<div class="sidebar-options-container">
						<li class="sidebar-option"><a class="nav-link"
							href="<%= request.getContextPath() %>/pages/myexpense.jsp">My Expenses</a></li>
						<li class="sidebar-option"><a class="nav-link"
							href="<%= request.getContextPath() %>/ShowGroupsServlet">Group Expenses</a></li>
						<li class="sidebar-option"><a class="nav-link" href="#">Statistics</a>
						</li>
					</div>
				</ul>
			</div>
			<div class="navbar__nav-options col-sm-6 col-md-6 col-lg-6">
				<ul class="nav-options__nav-options-list">
					<li class="nav-options-list__nav-option nav-option-active"><img
						src="<%= request.getContextPath() %>/images/rupee.png" alt="coin"
						class="nav-option__icon"> <a class="nav-link-active"
						href="<%= request.getContextPath() %>/pages/myexpense.jsp">My Expenses</a></li>
					<li class="nav-options-list__nav-option"><img
						src="<%= request.getContextPath() %>/images/group.png" alt="group"
						class="nav-option__icon"> <a class="nav-link"
						href="<%= request.getContextPath() %>/ShowGroupsServlet">Group Expenses</a></li>
					<li class="nav-options-list__nav-option"><img
						src="<%= request.getContextPath() %>/images/statistic.png" alt="statistics"
						class="nav-option__icon"> <a class="nav-link" href="#">Statistics</a>
					</li>
				</ul>
			</div>
			<div class="navbar__search-option col-sm-6 col-md-4 col-lg-2">
				<input type="text" name="search-param" placeholder="Search.."
					class="search-option__input">
				<div class="search-option__icon" onclick="displaySearchInputField()">
					<i class="fas fa-search search-btn"></i>
				</div>
			</div>
		</div>
	</div>
	<div class="expense-container">
		<%
			for (Group group : (ArrayList<Group>) request.getAttribute("groups")) {
				out.print("<div class='expense-container__expense-card-container col-sm-12 col-md-6 col-lg-4'>"
						+ "<a class='nav-link' href='"+request.getContextPath()+"/ShowGroupDetailsServlet?gId="+group.getgId()+"'>" + "<div class='expense-card-container__expense-card'>"
						+ "<img src='"+request.getContextPath()+"/images/group-icon.png' alt='group-icon' class='category-icon'></img>"
						+ "<p class='expense-card__category-name'>" + group.getGroupName() + "</p>" + "</div>" + "</a>"
						+ "</div>");
			}
		%>
		<div
			class="expense-container__expense-card-container col-sm-12 col-md-6 col-lg-4">
			<button type="button" class="add-btn" onclick="showModal()">
				<i class="fas fa-plus plus"></i>Create New Group
			</button>
		</div>

	</div>
	<div id="createGroupModal" class="modal-container">
		<div class="modal-content">
			<h2 class="modal-content__heading">Create New Group</h2>
			<span class="close" onclick="closeModal()"><i
				class="far fa-times-circle"></i></span>
			<form class="form" name="new-group-form" autocomplete="off" on>
				<div class="group-details-container col-sm-6 col-md-6 col-lg-6">
					<input type="text" name="group-name"
						placeholder="Enter a group name.." class="input">
					<div class="add-person-button-container">
						<p class="add-person-button-container__add-person-msg">Add
							person to group</p>
						<button type="button"
							class="add-person-button-container__add-person-button"
							onclick="displaySearchInputField()">
							<i class="fas fa-plus plus"></i>
						</button>
					</div>
					<input type="text" name="username"
						placeholder="Enter username of person.." class="search-input">
				</div>
				<div class="group-members-container col-sm-6 col-md-6 col-lg-6">
					<h3 class="group-members-container__heading">Group Members</h3>
					<div class="group-members-container__group-member-card-container">
					</div>
				</div>
				<button type="button" class="submit-btn" onclick='createGroup()'>Create
					Group</button>
			</form>
		</div>

	</div>

	<script type="text/javascript" src="<%= request.getContextPath() %>/js/groups.js"></script>
</body>
</html>
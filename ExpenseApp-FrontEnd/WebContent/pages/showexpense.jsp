<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="io.ztech.expensesapp.beans.Expense"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/grid.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/myexpense.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/showexpense.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/fontawesome-free-5.2.0-web/css/all.css">
<link href="https://fonts.googleapis.com/css?family=Ubuntu+Condensed"
	rel="stylesheet">
<title>Show Expense</title>
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
						<li class="sidebar-option"><a class="nav-link" href="<%= request.getContextPath() %>/ShowGroupsServlet">Group
								Expenses</a></li>
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
						class="nav-option__icon"> <a class="nav-link" href="<%= request.getContextPath() %>/ShowGroupsServlet">Group
							Expenses</a></li>
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
	<div class="page-content">
		<%
			ArrayList<Expense> expenses = (ArrayList<Expense>) request.getAttribute("expenseList");
			if(expenses.isEmpty()) {
				out.print("<div class='no-expense-message-container'>"
						 	+"<img alt='pig' src='"+request.getContextPath()+"/images/pig.png' class='no-expense-message-container__image'>"
							+"<p class='no-expense-message-container__message'>Yay! You do not have any expenses!</p>"
							+"<p class='no-expense-message-container__message'>Click on the Plus (+) to add a new expense!</p>"
						 +"</div>");
			}
			for (Expense expense : expenses) {
				out.print("<div class='expense-list-container col-sm-12 col-md-12 col-lg-12'>"
						+ "<div class='expense-list-container__expense-preview-card' onclick='showExpenseDetails(event)'>"
						+ "<div class='expense-preview-card__date-container col-sm-1 col-md-1 col-lg-1'>" + "<p>"
						+ expense.getCreatedAt().substring(0,11)+ "</p>" + "</div>"
						+ "<div class='expense-preview-card__icon-container col-sm-1 col-md-1 col-lg-1'>"
						+ "<img src='"+request.getContextPath()+"/images/" + expense.getType()
						+ ".png' class='icon-container__icon'></i>" + "</div>"
						+ "<div class='expense-preview-card__expense-info-container col-sm-7 col-md-7 col-lg-7'>"
						+ "<p>" + expense.getDescription() + "</p>" + "</div>"
						+ "<div class='expense-preview-card__balance-container col-sm-3 col-md-3 col-lg-3'>"
						+ "<p>&#x20b9; " + expense.getAmount() + "</p>" + "</div>" + "</div>" + "</div>");
			}
		%>
		
		<div class="add-expense-btn" onclick='showModal(window.location.href)'>
			<i class="fas fa-plus plus"></i>
		</div>
	</div>
	<div id="addExpenseModal" class="modal-container">
		<div class="modal-content">
			<span class="close" onclick="closeModal()"><i
				class="far fa-times-circle"></i></span>
			<h2 class="modal-content__heading">Add New Expense</h2>
			<form name="new-expense-form" class="form">
				<input type="hidden" name="category"></input>
				<div
					class="new-expense-form__expense-type-dropdown-container col-sm-3 col-md-3 col-lg-3">
					<div
						class="expense-type-dropdown-container__expense-type-icon-container">
						<img src="<%= request.getContextPath() %>/images/food.png" alt="expense-type"
							class="expense-type-icon-container__icon">
					</div>
					<select name="expense-type-dropdown" id="food"
						class="expense-type-dropdown-container__dropdown">
						<option class="dropdown__option" value="1" id="food"
							onclick="changeIcon(id)">Food</option>
						<option class="dropdown__option" value="9" id="water"
							onclick="changeIcon(id)">Water</option>
						<option class="dropdown__option" value="3" id="taxi"
							onclick="changeIcon(id)">Taxi</option>
						<option class="dropdown__option" value="4" id="hotel"
							onclick="changeIcon(id)">Hotel</option>
						<option class="dropdown__option" value="8" id="bill"
							onclick="changeIcon(id)">Bill</option>
					</select>
				</div>
				<div
					class="new-expense-form__expense-details col-sm-8 col-md-8 col-lg-8">
					<input type="text" name="expense-description"
						placeholder="Enter a description.." class="input"> <input
						type="text" name="expense-amount" placeholder="Enter an amount.."
						class="input">
				</div>
				<div
					class="new-expense-form__submit-button col-sm-12 col-md-12 col-lg-12">
					<button type="button" class="submit-btn">Add Expense</button>
				</div>
			</form>
		</div>

	</div>

	<script src="<%= request.getContextPath() %>/js/showexpense.js"></script>
</body>

</html>
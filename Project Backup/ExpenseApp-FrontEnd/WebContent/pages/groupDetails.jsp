<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="io.ztech.expensesapp.beans.Group"%>
<%@ page import="io.ztech.expensesapp.beans.GroupPayment"%>
<%@ page import="io.ztech.expensesapp.beans.User"%>
<%@ page import="io.ztech.expensesapp.beans.ExpenseMember"%>
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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/showexpense.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/groupDetails.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/fontawesome-free-5.2.0-web/css/all.css">
<link href="https://fonts.googleapis.com/css?family=Ubuntu+Condensed"
	rel="stylesheet">
<body>
	<%! float balance; %>
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
	<div class="page-content">
		<%
			Group activeGroup = (Group) request.getAttribute("groupDetails");
			if (activeGroup != null) {
				ArrayList<GroupPayment> groupPayments = activeGroup.getGroupPayments();
				if (groupPayments.isEmpty()) {
					out.print("<div class='no-expense-message-container'>"
							+ "<img alt='pig' src='"+request.getContextPath()+"/images/pig.png' class='no-expense-message-container__image'>"
							+ "<p class='no-expense-message-container__message'>Yay! You do not have any expenses!</p>"
							+ "</div>");
				}
				for (GroupPayment groupPayment : groupPayments) {
					out.print("<div class='expense-list-container col-sm-12 col-md-12 col-lg-12'>"
								+ "<div class='expense-list-container__expense-preview-card' onclick='showExpenseDetails(event)'>"
									+ "<div class='expense-preview-card__date-container col-sm-1 col-md-1 col-lg-1'>" 
										+ "<p>"+ groupPayment.getCreatedAt().substring(0, 11) + "</p>" 
									+ "</div>"
									+ "<div class='expense-preview-card__icon-container col-sm-1 col-md-1 col-lg-1'>"
										+ "<img src='"+request.getContextPath()+"/images/" + groupPayment.getType()+ ".png' class='icon-container__icon'></i>" 
									+ "</div>"
									+ "<div class='expense-preview-card__expense-info-container col-sm-7 col-md-7 col-lg-7'>"
										+ "<p class='expense-info-container__expense-description'>" + groupPayment.getDescription() +"</p>"
										+ "<p class='expense-info-container__expense-payer'> "+ (groupPayment.getExpensePayer().compareTo((String)session.getAttribute("username")) == 0 ? "You ": groupPayment.getExpensePayer()) + " paid &#x20b9;"+ groupPayment.getAmount() + "</p>" 
									+ "</div>"
									+ "<div class='expense-preview-card__balance-container col-sm-3 col-md-3 col-lg-3'>");
					boolean involved = false;
					String message;
					Integer activeUid = (Integer)session.getAttribute("uid");
					for(ExpenseMember expenseMember : groupPayment.getExpenseMembers()) {
						if(activeUid == expenseMember.getuId()) {
							involved = true;
							if(activeUid == groupPayment.getuId()) {
								out.print("<p class='balance-container__money-lent'>" + "You Lent &#x20b9;"+ (groupPayment.getAmount() - expenseMember.getTotalAmount()) + "</p>" + "</div>" + "</div>");
							}
							else {
								out.print("<p class='balance-container__money-borrowed'>" + "You borrowed &#x20b9;"+ expenseMember.getTotalAmount() + "</p>" + "</div>" + "</div>");
							}
						}
					}
					if(!involved) {
						out.print("<p class='balance-container__not-involved'>You were not involved</p>" + "</div>" + "</div>");
					}
					out.print("<div class='expense-list-container__expense-details-card'><div class='expense-details-card__expense-members-container col-sm-12 col-md-12 col-lg-12'>");
					for(ExpenseMember expenseMember : groupPayment.getExpenseMembers()) {
						out.print("<div class='expense-member-card' onclick='flipCard(event)'><div class=expense-members-container__expense-member col-sm-12 col-md-12 col-lg-12'>"
				                    	+"<div class='expense-member__display-picture'>"
				                    		+"<img class='display-picture__picture' src='"+request.getContextPath()+"/images/dp.png'>"
				                    	+"</div>"
				                    	+"<div class='expense-member__balance-details'>");
										 if(expenseMember.getUserName().compareTo(groupPayment.getExpensePayer())==0) {
												out.print("<p>"+groupPayment.getExpensePayer()+" paid &#x20b9;"+groupPayment.getAmount()+" and owes &#x20b9;"+expenseMember.getTotalAmount()+"</p></div>");
										}
										else {
												out.print("<p>"+expenseMember.getUserName()+" owes &#x20b9;"+expenseMember.getTotalAmount()+" and has settled &#x20b9; "+expenseMember.getAmountPaid()+"</p></div>");
										}
						
						out.print("</div>"
								 +"<div class='settlement-details-container col-sm-12 col-md-12 col-lg-12'>"
									+"<p>"+expenseMember.getUserName()+((balance = expenseMember.getTotalAmount()-expenseMember.getAmountPaid()) == 0 ? " is all settled up!" : " has a balance of &#x20b9; "+balance )+"</p>"
									+"<button type='button' class='settle-btn' onclick='showModal(\"settleExpenseModal\"); getDetails(\""+expenseMember.getUserName()+"\",\""+(expenseMember.getTotalAmount()-expenseMember.getAmountPaid())+"\",\""+groupPayment.getExpensePayer()+"\")'>Settle</button>" 
								+"</div>"
						+"</div>");
				    }
					out.print("</div></div></div>");
				}
			}
		%> 
		<div class="add-expense-btn" onclick='showModal("addExpenseModal")'>
			<i class="fas fa-plus plus"></i>
		</div>
	</div>
	<div id="addExpenseModal" class="modal-container">
		<div class="modal-content">
			<span class="close" onclick='closeModal("addExpenseModal")'><i
				class="far fa-times-circle"></i></span>
			<h2 class="modal-content__heading">Add New Expense</h2>
			<form name="new-expense-form" class="form">
				<input type="hidden" name="category"></input>
				<div
					class="new-expense-form__expense-type-dropdown-container col-sm-2 col-md-2 col-lg-2">
					<div
						class="expense-type-dropdown-container__expense-type-icon-container">
						<img src="<%= request.getContextPath() %>/images/food.png" alt="expense-type"
							class="expense-type-icon-container__icon">
					</div>
					<select name="expense-type-dropdown"
						id="food"
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
					class="new-expense-form__expense-details col-sm-5 col-md-5 col-lg-5">
					<input type="text" name="expense-description"
						placeholder="Enter a description.." class="input"> <input
						type="text" name="expense-amount" placeholder="Enter an amount.."
						class="input">
					<div
						class="payment-options-container col-sm-12 col-lg-12 col-md-12">
						<p class="payment-options-container-text">Paid By:</p>
						<select name="group-expense-payer-dropdown"
							class="payment-options-container-dropdown">
							<%
								ArrayList<User> groupmembers = activeGroup.getUsers();
								if (groupmembers != null) {
									for (User groupMember : groupmembers) {
										out.print("<option value='" + groupMember.getUserName() + "'>" + groupMember.getUserName()
												+ "</option>");
									}
								}
							%>
						</select>
						<p class="payment-options-container-text">Split Amount:</p>
						<select name="split-method-dropdown"
							class="payment-options-container-dropdown"
							onchange="toggleDisplay(value)">
							<option value='equally'>Equally</option>
							<option value='unequally'>Unequally</option>
						</select>
					</div>
				</div>
				<div
					class="new-expense-form__split-expense-members col-sm-3 col-md-3 col-lg-3">
					<h3 class="modal-content__heading">Split Expense with</h3>
					<%
						for (User user : activeGroup.getUsers()) {
							out.print(
									"<div class='split-member-details-container col-sm-12 col-md-12 col-lg-12'><input onclick='toggleSplitAmountField(this,value)' class='split-member-details-container-checkbox' type='checkbox' name='"
											+ user.getUserName() + "' value='" + user.getUserName()
											+ "'><p class='split-member-details-container-text'>" + user.getUserName()
											+ "</p></div>");
							out.print("<input placeholder='Enter Amount..' type='text' class='split-member-amount " + user.getUserName() + "'>");
						}
					%>
				</div>
				<div
					class="new-expense-form__submit-button col-sm-12 col-md-12 col-lg-12">
					<button type="button" class="submit-btn" onclick="formValidate()">Add
						Expense</button>
				</div>
			</form>
		</div>

	</div>
	<div id="settleExpenseModal" class="settleExpenseModal modal-container">
		<div class="modal-content">
			<span class="close" onclick='closeModal("settleExpenseModal")'>
				<i class="far fa-times-circle"></i>
			</span>
			<h2 class="modal-content__heading">Settle Expense</h2>
			<div class="settlers-details"></div>
			<form class="form" name="settleExpense">
				<div>&#x20b9;<input type="text"></div>
			</form>
		</div>
	</div>
	<script src="<%= request.getContextPath() %>/js/groupDetails.js"></script>	
	<% 
		out.print("<script>getActiveUser('"+session.getAttribute("username")+"','"+session.getAttribute("uid")+"')</script>");
	%>
	
</body>
</html>
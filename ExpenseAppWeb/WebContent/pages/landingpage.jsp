<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="../../ExpenseAppWeb/css/landingpage.css">
<link rel="stylesheet" href="../../ExpenseAppWeb/css/grid.css">
<link href="https://fonts.googleapis.com/css?family=Ubuntu+Condensed"
	rel="stylesheet">
<link rel="stylesheet"
	href="./../ExpenseAppWeb/fontawesome-free-5.2.0-web/css/all.css">
<title>Landing Page</title>
</head>

<body>
	<div class="app-heading-card-container col-sm-8 col-md-8 col-lg-8">
		<h1 class="app-heading-card-container__app-name">PennyWise</h1>
		<pre class="app-heading-card-container__app-tagline">Manage &#183 Split &#183 Analyse</pre>
	</div>
	<div class="login-signup-card-container col-sm-4 col-md-4 col-lg-4">
		<div class="login-signup-container">
			<div class="login-container">
				<div class="heading-container">
					<img alt="login" src="../../ExpenseAppWeb/images/login.png">
					<h2>Login</h2>
				</div>
				<form name="login-form" onsubmit="return loginFormValidate();"
					action="../../ExpenseAppWeb/LoginServlet" method="post">
					<div class="form-container">
						<input type="text" name="username"
							placeholder="Enter your username.." class="input"> <input
							type="password" name="password"
							placeholder="Enter your password.." class="input"> <input
							type="submit" name="submit" value="Login" class="submit-button">
					</div>
				</form>
				<div class="login-circle-button" onclick="loginOnclick()">
					<img alt="arrow-up" src="../../ExpenseAppWeb/images/arrow-up.png">
				</div>
			</div>
			<div class="signup-container">
				<div class="heading-container">
					<img alt="signup" src="../../ExpenseAppWeb/images/signup.png">
					<h2>Signup</h2>
				</div>
				<form name="signup-form" onsubmit="return signupFormValidate();"
					action="../../ExpenseAppWeb/SignUpServlet" method="post">
					<div class="form-container">
						<input type="text" name="username"
							placeholder="Enter a username.." class="input"> <input
							type="email" name="email-id" placeholder="Enter your email-id.."
							class="input"> <input type="password" name="password"
							placeholder="Enter a password.." class="input"> <input
							type="password" name="confirm-password"
							placeholder="Confirm your password.." class="input"> <input
							type="submit" name="submit" value="Signup" class="submit-button">
					</div>
				</form>
				<div class="signup-circle-button" onclick="signupOnclick()">
					<img alt="arrow-up" src="../../ExpenseAppWeb/images/arrow-up.png">
				</div>
			</div>
		</div>
		<%--         <p><%= request.getAttribute("message") %></p>
 --%>
	</div>
	<div class="app-features-card-container col-sm-12 col-md-12 col-lg-12">
		<div
			class="app-features-card-container__scroll-msg-container col-sm-12 col-md-12 col-lg-12"
			onclick="moveDown();">
			<p class="scroll-msg-container__message">Click here to scroll
				down!</p>
			<div class="scroll-msg-container__arrow">
				<img alt="scroll-arrow"
					src="../../ExpenseAppWeb/images/scroll-arrow.png">
			</div>
		</div>
		<div
			class="app-features-card-container__feature-card col-sm-4 col-md-4 col-lg-4">
			<img alt="manage-expense"
				src="../../ExpenseAppWeb/images/coin-wallet.png">
			<div class="feature-card__feature-info">Expenses not tallying
				up? Manage all your expenses, add custom categories and organise by
				Expense Types.</div>
		</div>
		<div
			class="app-features-card-container__feature-card col-sm-4 col-md-4 col-lg-4">
			<img alt="split-bill" src="../../ExpenseAppWeb/images/split-bill.png">
			<div class="feature-card__feature-info">Going out for lunch
				with friends? Split the bill without any hassle!</div>
		</div>
		<div
			class="app-features-card-container__feature-card col-sm-4 col-md-4 col-lg-4">
			<img alt="analyse" src="../../ExpenseAppWeb/images/analyse.png">
			<div class="feature-card__feature-info">Feel you're spending
				too much, and want to cut costs? Get insights and stats on all your
				expenditures!</div>
		</div>

	</div>

	<script src="../../ExpenseAppWeb/js/landingpage.js"></script>
</body>

</html>
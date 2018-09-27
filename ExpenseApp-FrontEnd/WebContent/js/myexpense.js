function displaySearchInputField() {
    console.log("aa");
    var searchInputField = document.getElementsByClassName("search-option__input")[0];
    searchInputField.classList.toggle("search-option__input-visible");
}

function showModal(category) {
	console.log(category);
	var modal = document.getElementById("addExpenseModal");
	modal.style.display = "flex";
	var categoryInput = document.forms["new-expense-form"]["category"];
	categoryInput.setAttribute("value",category);
}

function closeModal() {
	var modal = document.getElementById("addExpenseModal");
	modal.style.display = "none";
}

function changeIcon(id) {
	console.log("in change icon");
    var img = document.getElementsByClassName("expense-type-icon-container__icon")[0];
    var dropdown = document.getElementsByName("expense-type-dropdown")[0];
    dropdown.setAttribute("id", id);
    img.setAttribute("src","/ExpenseApp-FrontEnd/images/"+id+".png");
}

submitBtn = document.getElementsByClassName("submit-btn")[0];
submitBtn.onclick = function() {
	var expense = {};
	if(formValidate(expense)) {
		addExpense(expense);
		closeModal();
	}
}

function addExpense(expense) {
	console.log("Called");
	var expenseJSON = JSON.stringify(expense);
	fetch("/ExpenseApp-FrontEnd/AddExpenseServlet",{
		method: "POST",
		headers: {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
 		},
 		body: expenseJSON
	}).then(function(response){
		alert("Success");
	}).catch(function(error) {
		alert("Error!");
	});
}



function formValidate(expense) {
	var description = document.forms["new-expense-form"]["expense-description"].value;
	if(description == null || description == "") {
		alert("Please enter a description!");
		return false;
	}
	expense.description = description;
	var amount = document.forms["new-expense-form"]["expense-amount"].value;
	if(amount == null || amount == "") {
		alert("Please enter an amount!");
		return false;
	}
	if(!/[0-9]/.test(amount)) {
		alert("Please enter a valid amount!");
		return false;
	}
	expense.amount = amount;
	var category = document.forms["new-expense-form"]["category"].value;
	if(!/[0-9]/.test(category)) {
		alert("Please choose valid category!");
		return false;
	}
	expense.category = category;
	var expenseType = document.forms["new-expense-form"]["expense-type-dropdown"].value;
	if(!/^[0-9.]+$/.test(expenseType)) {
		alert("Please choose valid expense type!");
		return false;
	}
	expense.expenseType = expenseType;
	return true;
	
}

function showButtonText(event) {
	var button = event.target;
	window.setTimeout(function(){
		button.getElementsByClassName("circular-btn__text")[0].style.display = "block";
	}, 250);
}

function hideButtonText(event) {
	var button = event.target;
	button.getElementsByClassName("circular-btn__text")[0].style.display = "none";
}


function displaySearchInputField() {
    var searchInputField = document.getElementsByClassName("search-option__input")[0];
    searchInputField.classList.toggle("search-option__input-visible");
}

function showExpenseDetails(event) {
    var previewCards = document.getElementsByClassName("expense-list-container__expense-preview-card");
    for (var i = 0; i < previewCards.length; i++) {
        if (event.target != previewCards[i])
            previewCards[i].classList.remove("expense-list-container__expense-preview-card-reveal")
    }
    event.target.classList.toggle("expense-list-container__expense-preview-card-reveal");
}

function showModal(url) {
	var url = window.location;
	var category = url.search.split("category=")[1];
	console.log("Cat:" + category);
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
	var img = document.getElementsByClassName("expense-type-icon-container__icon")[0];
	document.forms["new-expense-form"]["expense-type-dropdown"].id = id;
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
		console.log(expense.expenseTypeName);
		var strArray=['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		var today = new Date();
		var date = today.getDate()+" "+strArray[today.getMonth()]+" "+today.getFullYear();
		var noExpenseDiv = document.getElementsByClassName("no-expense-message-container")[0];
		if(noExpenseDiv != undefined) {
			noExpenseDiv.parentNode.removeChild(noExpenseDiv);
		}
		var pageContentDiv = document.getElementsByClassName("page-content")[0];
		pageContentDiv.innerHTML += `<div class='expense-list-container col-sm-12 col-md-12 col-lg-12'>
							<div class='expense-list-container__expense-preview-card' onclick='showExpenseDetails(event)'>
								<div class='expense-preview-card__date-container col-sm-1 col-md-1 col-lg-1'>
									<p>${date}</p>
								</div>
								<div class='expense-preview-card__icon-container col-sm-1 col-md-1 col-lg-1'>
									<img src='/ExpenseAppWeb/images/${expense.expenseTypeName}.png' class='icon-container__icon'></i>
								</div>
								<div class='expense-preview-card__expense-info-container col-sm-7 col-md-7 col-lg-7'>
									<p>`+expense.description+`</p>
								</div>
								<div class='expense-preview-card__balance-container col-sm-3 col-md-3 col-lg-3'>
									<p>&#x20b9;`+expense.amount+`</p>
								</div>
							</div>
						</div>` ;
	}).catch(function(error) {
		console.log(error);
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
	var expenseTypeName = document.forms["new-expense-form"]["expense-type-dropdown"].id;
	if(!/[0-9]/.test(expenseType)) {
		alert("Please choose valid expense type!");
		return false;
	}
	expense.expenseType = expenseType;
	expense.expenseTypeName = expenseTypeName;
	return true;
	
}

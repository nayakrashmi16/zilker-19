/**
 * 
 */

var activeUserName;
var activeUserId;

function getActiveUser(username, uid) {
	activeUserName = username;
	activeUserId = uid;
}
function showModal(id) {
	var modal = document.getElementById(id);
	modal.style.display = "flex";
}

function closeModal(id) {
	var modal = document.getElementById(id);
	modal.style.display = "none";
}

function changeIcon(id) {
	console.log("in change icon "+id);
    var img = document.getElementsByClassName("expense-type-icon-container__icon")[0];
    var dropdown = document.getElementsByName("expense-type-dropdown")[0];
    dropdown.setAttribute("id", id);
    img.setAttribute("src","/ExpenseAppWeb/images/"+id+".png");
}

function toggleDisplay(value) {
	if(value == 'equally') {
		hideAllSplitAmountFields();
	}
	else {
		showAllSplitAmountFields();
	}
}
	
function showAllSplitAmountFields() {
	var splitAmountInputFields = document.getElementsByClassName("split-member-amount");
	for(var i = 0; i < splitAmountInputFields.length; i++) {
		var checkbox = document.getElementsByName(splitAmountInputFields[i].classList[1]);
		if(checkbox[0].checked) {
			splitAmountInputFields[i].style.display = "block";
		}
	}
}

function hideAllSplitAmountFields() {
	var splitAmountInputFields = document.getElementsByClassName("split-member-amount");
	for(var i = 0; i < splitAmountInputFields.length; i++) {
		splitAmountInputFields[i].style.display = "none";
	};
}

function toggleSplitAmountField(target,value) {
	var splitMethod = document.getElementsByName("split-method-dropdown");
	var splitMember = document.querySelector(".split-member-amount."+value);
	if(splitMethod[0].value == 'unequally') {
		if(target.checked) {
			splitMember.style.display = "block";
		}
		else {
			splitMember.style.display = "none";
		}
			
	} 
}

submitBtn = document.getElementsByClassName("submit-btn")[0];
submitBtn.onclick = function() {
	var groupExpense = {};
	if(formValidate(groupExpense)) {
		addGroupExpense(groupExpense);
		closeModal();
	}
}

function addGroupExpense(groupExpense) {
	var groupExpenseJson = JSON.stringify(groupExpense);
	fetch("/ExpenseApp-FrontEnd/AddGroupExpenseServlet",{
		method: "POST",
		headers: {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
 		},
 		body: groupExpenseJson
	}).then(function(response){
		alert("Success");
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
												<img src='/ExpenseAppWeb/images/${groupExpense.expenseTypeName}.png' class='icon-container__icon'></i>
											</div>
											<div class='expense-preview-card__expense-info-container col-sm-7 col-md-7 col-lg-7'>
												<p class='expense-info-container__expense-description'>${groupExpense.description}</p>
												<p class='expense-info-container__expense-payer'>`+ (groupExpense.expensePayer == activeUserName ? `You` : groupExpense.expensePayer) +` paid &#x20b9; ${groupExpense.amount}</p>
											</div>
											<div class='expense-preview-card__balance-container col-sm-3 col-md-3 col-lg-3'>
												<p class='balance-container__money-lent'>You `+
													(groupExpense.expensePayer == activeUserName ? `Lent &#x20b9; `+(groupExpense.amount - groupExpense.splitDetails[activeUserName]) : `Borrowed &#x20b9; `+groupExpense.splitDetails[activeUserName] ) +`</p>
											</div>
										</div>
									</div>`
													
										
	}).catch(function(error) {
		alert(error);
	});
}

function formValidate(groupExpense) {
	
	var form = document.forms["new-expense-form"];
	
	var description = form["expense-description"].value;
	if(description === null || description === "") {
		alert("Description field cannot be blank!");
		return false;
	}
	groupExpense.description = description;
	
	var amount = form["expense-amount"].value;
	if(amount === null || description === "") {
		alert("Amount field cannot be blank!");
		return false;
	}
	if(!/^[0-9.]+$/.test(amount)) {
		alert("Enter valid amount!");
		return false;
	}
	groupExpense.amount = amount;
	
	var expenseType= form["expense-type-dropdown"].value;
	if(expenseType === null || expenseType === "") {
		alert("Please choose a expense type!");
		return false;
	}
	if(!/^[0-9]+$/.test(expenseType)) {
		alert("Please choose valid expense type!");
		return false;
	}
	groupExpense.expenseType = expenseType;
	
	var expenseTypeName = document.forms["new-expense-form"]["expense-type-dropdown"].id;
	groupExpense.expenseTypeName = expenseTypeName;
	
	var expensePayer = form["group-expense-payer-dropdown"].value;
	if(expensePayer === null || expensePayer === "") {
		alert("Please choose an expense payer!");
		return false;
	}
	groupExpense.expensePayer = expensePayer;
	
	var splitMethod = form["split-method-dropdown"].value;
	if(splitMethod === null || splitMethod === "") {
		alert("Please choose a split method!");
		return false;
	}
	groupExpense.splitMethod = splitMethod;
	
	var splitMembersInput = document.getElementsByClassName("split-member-details-container-checkbox");
	
	var splitMembers = [];
	for(var i = 0; i < splitMembersInput.length; i++) {
		if(splitMembersInput[i].checked) { 
			splitMembers.push(splitMembersInput[i].name);
		}
	}
	if(splitMembers.length == 0) {
		alert("Please choose atleast one split member!");
		return false;
	}
	
	var splitDetails = {};
	if(splitMethod === 'unequally') {
		var totalAmount = 0;
		for(var i = 0; i < splitMembers.length; i++) {
			var amount = document.querySelector(".split-member-amount."+splitMembers[i]).value;
			if(amount === null || amount === "") {
				alert("Amount field cannot be blank!");
				return false;
			}
			totalAmount += Number(amount);
			splitDetails[splitMembers[i]] = Number(amount);
		}
		console.log("Total Amount:"+totalAmount);
		if(totalAmount != groupExpense.amount) {
			alert("Amount doesnt tally!");
			return false;
		}
	}
	
	else {
		for(var i = 0; i < splitMembers.length; i++) {
			splitDetails[splitMembers[i]] = Number(amount)/(splitMembers.length);
		}
	}
	groupExpense.splitDetails = splitDetails;
	
	var groupId = location.search.split("gId=")[1];
	groupExpense.groupId = groupId;
	
	console.log("JSON String:"+JSON.stringify(groupExpense));
	return true;
}

function showExpenseDetails(event) {
	var parent = event.target.closest(".expense-list-container__expense-preview-card");
    var previewCards = document.getElementsByClassName("expense-list-container__expense-preview-card");
    for (var i = 0; i < previewCards.length; i++) {
        if (parent != previewCards[i])
            previewCards[i].classList.remove("expense-list-container__expense-preview-card-reveal")
    }
    parent.classList.toggle("expense-list-container__expense-preview-card-reveal");
}

function flipCard(event) { 
	var card = event.target.closest(".expense-member-card");
	var settleUpBtn = card.getElementsByClassName("settle-btn")[0]; 
	if(event.target != settleUpBtn)
		card.classList.toggle("flipped");
	
	
}

function getDetails(expenseMember, amount, expensePayer) {
	console.log("get details called!");
	var settlersHeading = document.querySelector(".settleExpenseModal .modal-content .settlers-details");
	settlersHeading.innerHTML = `${expenseMember} pays ${expensePayer}`;

}
console.log("Changes2");
function showModal(category) {
	console.log(category);
	var modal = document.getElementById("createGroupModal");
	modal.style.display = "flex";
}

function displaySearchInputField() {
    var searchInputField = document.getElementsByClassName("search-input")[0];
    searchInputField.classList.toggle("search-input-visible");
}

function closeModal() {
	var modal = document.getElementById("createGroupModal");
	modal.style.display = "none";
}

var debounceTimeout = null;
var userInputField = document.forms["new-group-form"]["username"];
userInputField.addEventListener("keydown", function(event){
	clearTimeout(debounceTimeout);
	debounceTimeout = setTimeout(autoComplete, 800);
});


addedGroupMembers = [];
function autoComplete() {
	var value = userInputField.value;
	closeAllLists();
	if (!value) {
		return false;
	}
	fetch("/ExpenseApp-FrontEnd/GetUserSuggestionServlet", {
		method: "POST",
		headers: {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		body: value
	}).then(function(response){
		return response.json();
	}).then(function(data){
		autocompleteList = document.createElement("div");
		autocompleteList.setAttribute("id", "autocomplete-list");
		autocompleteList.setAttribute("class", "autocomplete-items");
		userInputField.parentNode.appendChild(autocompleteList);
		var resultsExist = false;
		for(var key in data) {
			resultsExist = true;
			if(!addedGroupMembers.includes(key)) {
			autocompleteSuggestion = document.createElement("div");
			autocompleteSuggestion.innerHTML = "<strong>" + key + "</strong> - "+ data[key];
			autocompleteSuggestion.innerHTML += "<input class='autocomplete-suggestion' type='hidden' value='" + key + "'>";
			autocompleteSuggestion.onclick = function(event) {
				var clickedElement = event.target;
				var parentDiv = clickedElement.closest("div");
				var hiddenInputField = parentDiv.getElementsByClassName("autocomplete-suggestion");
				var suggestion = hiddenInputField[0].value;
				addGroupMemberToList(suggestion);
				addedGroupMembers.push(suggestion);
			}
			autocompleteList.appendChild(autocompleteSuggestion); }
		}
		if(!resultsExist) {
			autocompleteSuggestion = document.createElement("div");
			autocompleteSuggestion.innerHTML = "Sorry, user does not exist!";
			autocompleteList.appendChild(autocompleteSuggestion);
		}
	}).catch(function(error) {
		console.log(error);
	});
}

function closeAllLists() {
	 var list = document.getElementsByClassName("autocomplete-items")[0];
	 if(list) {
		 list.parentNode.removeChild(list);
	 }
}

document.addEventListener("click", function() {
	closeAllLists();
});

function addGroupMemberToList(user) {
	if(user) {
		var groupMemberCardContainer = document.getElementsByClassName("group-members-container__group-member-card-container")[0];
		groupMemberCardContainer.innerHTML += `<div
													class="group-member-card-container__group-member-card">
													<input class="group-member" type="hidden" value="${user}"> 
													<p class="group-member-card__text">${user}</p>
													<i class="fas fa-times group-member-card__text remove" onclick="removeMemberFromGroup(event)"></i>
											  </div>`;
	}
}

function removeMemberFromGroup(event) {
	console.log("In remove")
	var remove = event.target;
	var element = remove.parentNode;
	addedGroupMembers.pop(element.parentNode.getElementsByTagName("input").value);
	element.parentNode.removeChild(element);
}

function createGroup() {
	var groupJSON = {};
	//	groupJSON.groupMembersList.push(activeUserName);
	if(formValidate(groupJSON)) {
		data = JSON.stringify(groupJSON);
		console.log("Data:"+data);
		fetch("/ExpenseApp-FrontEnd/CreateGroupServlet", {
			method: "POST",
			headers: {
				"Accept" : "application/json",
				"Content-Type" : "application/json"
			},
			body: data
		}).then(function(response){
			return response.json();
		}).then(function(data) {
			closeModal();			
			console.log("Success");
			var expenseContainer = document.getElementsByClassName("expense-container")[0];
			var lastChildExpenseCardContainer = expenseContainer.lastElementChild;
			var expenseCardContainer = document.createElement("div");
			expenseCardContainer.setAttribute("class","expense-container__expense-card-container col-sm-12 col-md-6 col-lg-4");
			expenseCardContainer.innerHTML = `<a class="nav-link" href="/ExpenseAppWeb/ShowGroupDetailsServlet?gId=${data.gId}">
                <div class="expense-card-container__expense-card">
                    <img src="../../ExpenseAppWeb/images/group-icon.png" alt="group-icon" class="category-icon"></img>
                    <p class="expense-card__category-name">${groupJSON.groupName}</p>
                </div>
            </a>`;
			expenseContainer.insertBefore(expenseCardContainer,lastChildExpenseCardContainer);
		}).catch(function(error) {
			console.log(error);
		});
	}
}

function formValidate(groupJSON) {
	var form = document.forms["new-group-form"];
	var groupName = form["group-name"].value;
	if(groupName === null || groupName === "") {
		alert("GroupName field cannot be blank");
		return false;
	}
	groupJSON.groupName = groupName;
	groupJSON.groupMembersList = [];
	var groupMembers = document.getElementsByClassName("group-member");
	for(var i = 0; i < groupMembers.length; i++) {
		groupJSON.groupMembersList.push(groupMembers[i].value);
	}
	return true;
}


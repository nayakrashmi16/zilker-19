var signupContainer = document.querySelector(".signup-container");
var loginCircleButton = document.querySelector(".login-circle-button");
var signupCircleButton = document.querySelector(".signup-circle-button");

function signupOnclick() {
    signupContainer.classList.add("reveal");
    loginCircleButton.classList.add("moveup");
    setTimeout(function() {
    	loginCircleButton.style.display = "flex";
    	signupCircleButton.style.display = "none";
    },600);
}

function loginOnclick() {
	signupContainer.classList.remove("reveal");
    loginCircleButton.classList.remove("moveup");
    setTimeout(function() {
    	loginCircleButton.style.display = "none";
    	signupCircleButton.style.display = "flex";
    },600);
}

function signupFormValidate() {
	var form = document.forms["signup-form"];
    var username = form["username"].value;
    if (username == null || username == "") {
        alert("Please enter a username!");
        return false;
    }

    var emailid = form["email-id"].value;
    if (!/^[a-zA-Z0-9_+&*-]+(?:\." + "[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-z" + "A-Z]{2,7}$/.test(emailid) || emailid == null || emailid == "") {
        alert("Please enter a valid email-id!");
        return false;
    }

    var password = form["password"].value;
    var confirmPassword = form["confirm-password"].value;
    if (password == null || password == "" || confirmPassword == null || confirmPassword == "") {
        alert("Please enter a password!");
        return false;
    }
    if (password != confirmPassword) {
        alert("Passwords dont match!");
        return false;
    }
    return true;
}

function loginFormValidate() {
	var form = document.forms["login-form"];
	var username = form["username"].value;
	if(username == "" || username == null) {
		alert("Username field cannot be blank!");
		return false;
	} 
	
	var password = form["password"].value;
	if(password == "" || password == null) {
		alert("Password field cannot be blank");
		return false;
	}
	return true;
}

function moveDown() {
	window.scrollTo(0,document.body.scrollHeight);
}
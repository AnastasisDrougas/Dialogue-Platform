document.getElementById("passwordForm").addEventListener("submit", function(event) {
	
	const username = document.getElementById("username").value.trim();
    const oldPass = document.getElementById("oldPassword").value;
    const newPass = document.getElementById("newPassword").value;
    const conf1 = document.getElementById("confirmPassword1").value;
    const conf2 = document.getElementById("confirmPassword2").value;
	const errorDiv = document.getElementById("output-div-1");
	
	//Clear previous error messages.
	errorDiv.innerText = "";
	
	//Check if the fields are empty.
	if (!username || !oldPass || !newPass || !conf1 || !conf2) {
	        event.preventDefault(); 
	        errorDiv.innerText = "All fields are mandatory!";
	        return;
	}
	
	//New password must be identical in all three fields 
    if (newPass !== conf1 || newPass !== conf2) {
        event.preventDefault(); 
        errorDiv.innerText = "The three new password fields must match exactly!";
        return;
    }
	
	//New password must be different from the existing one 
    if (newPass === oldPass) {
        event.preventDefault();
        errorDiv.innerText = "New password must be different from your current one!";
        return;
    }
	
	//Length must exceed 6 characters 
    if (newPass.length <= 6) {
        event.preventDefault();
        errorDiv.innerText = "New password must be longer than 6 characters!";
        return;
    }
		
	//Must consist of letters AND digits, we will use regex.
    const hasLetter = /[a-zA-Z]/.test(newPass);
    const hasDigit = /[0-9]/.test(newPass);
	
	if (!hasLetter || !hasDigit) {
        event.preventDefault();
        errorDiv.innerText = "New password must contain both letters and digits!";
        return;
    }
});
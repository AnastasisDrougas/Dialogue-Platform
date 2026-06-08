document.getElementById("passwordForm").addEventListener("submit", function(event) {
	
	const username = document.getElementById("username").value.trim();
	const pass = document.getElementById("password").value;	
	const errorDiv = document.getElementById("output-div-1");
		
	//Clear previous error messages.
	errorDiv.innerText = "";
	
	//Check if the fields are empty.
	if (!username || !pass ) {
	        event.preventDefault(); 
	        errorDiv.innerText = "All fields are mandatory!";
	        return;
	}
});
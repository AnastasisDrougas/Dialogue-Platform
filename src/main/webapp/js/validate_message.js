document.getElementById("messageForm").addEventListener("submit", function(event) {
	const topicSelect = document.getElementById("topic");
    const messageField = document.getElementById("messageText");
    const errorDiv = document.getElementById("forum-error-div");
	
	const messageValue = messageField.value.trim();
	const topicValue = topicSelect.value;
	
	if (!topicValue || topicValue === "") {
	        event.preventDefault(); 
	        errorDiv.innerText = "Please select a valid discussion topic!";
	        return;
	    }
	    
    if (!messageValue) {
        event.preventDefault(); 
        errorDiv.innerText = "Message field cannot be blank!";
        return;
    }


});
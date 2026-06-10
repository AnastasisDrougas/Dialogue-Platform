document.addEventListener("DOMContentLoaded", function() {

    let topicForm = document.querySelector("#topicForm");

    topicForm.addEventListener("submit", function(e) {

        e.preventDefault();

        let title = document.querySelector("#topicTitle").value;
        let description = document.querySelector("#topicDesc").value;

        let message = document.querySelector("#message");

        fetch("/Dialogue-Platform/TopicsServlet", {
            method: "POST",
			headers: {
		        "Content-Type": "application/json"
		    },
            body: JSON.stringify({
                title: title,
                description: description
            })
        })
        .then(response => {
            if(response.ok) {
                return response.json();
            } else {
                throw new Error("Unexpected problem: " + response.status);
            }
        })
        .then(json_data => {

            document.getElementById("topicForm").reset();

            message.innerHTML = json_data.message;

        })
        .catch(error => {
            console.log("Fetching problem: " + error);
        });

    });

});
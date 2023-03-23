function sendMessage(rr) {
	// variables required to send message
	var address = "http://radikaldesign.co.uk/sandbox/shoutbox/postmessage.php";
	var username = getValue("username");
	var sessionID = getValue("session-id");
	var message = getValue("message");
	
	if (username!= "" && sessionID!= "" && message!= ""){
		// JS data object
		var data = {
			message_text: message,
			session_id: sessionID,
			username: username
		}
		// convert JS object to JSON string
		var json = JSON.stringify(data);
		
		ajaxPost(address, json, function(request) {
			handleResponse(request, rr);
		});
	} else {
		htmlInsert(rr, "Error all fields required!")
	}
}

function handleResponse(req, resultRegion){
	htmlInsert(resultRegion, req.responseText);
}
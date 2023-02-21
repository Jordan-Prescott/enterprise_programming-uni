/**
 * 
 */

function getRequestObject() {
  if (window.XMLHttpRequest) {
    return(new XMLHttpRequest());
  } else if (window.ActiveXObject) { 
    return(new ActiveXObject("Microsoft.XMLHTTP"));
  } else {
    return(null); 
  }
}

//Make an HTTP request to the given address. 
//Display result in an alert box.

function ajaxAlert() {
	console.log("HERE")
	var request = getRequestObject();
	request.onreadystatechange = function() { showResponseAlert(request)}; 
	request.open("GET", "testing.txt", true);
	request.send(null);
}

//Make a request and display randon number

function getRandomNumber(address) {
	var request = getRequestObject();
	request.onreadystatechange = function() { showResponseAlert(request)}; 
	request.open("GET", address, true);
	request.send(null);
}

function getNextTarget(address) {
	var request = getRequestObject();
	request.onreadystatechange = function() { showResponseAlert(request)}; 
	request.open("GET", address, true);
	request.send(null);
}

//Put response text in alert box.

function showResponseAlert(request) {
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    alert(request.responseText);
  }
}

// Exercise 5
function getNextTargetDetails(address, resultRegion) {
	var request = getRequestObject();
	request.onreadystatechange = function() { showResponseText(request, resultRegion)}; 
	request.open("GET", address, true);
	request.send(null);
}

function showResponseText(request, resultRegion) {
	if((request.readyState == 4) && (request.status == 200)) {
		htmlInsert(resultRegion, request.responseText);
	}
}

function htmlInsert(id, htmlData) {
	document.getElementById(id).innerHTML = htmlData;
}

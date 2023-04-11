// Get the browser-specific request object, either for
// Firefox, Safari, Opera, Mozilla, Netscape, or IE 7 (top entry);
// or for Internet Explorer 5 and 6 (bottom entry). 

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

function ajaxAlert(address) {
var request = getRequestObject();
request.onreadystatechange = 
 function() { showResponseAlert(request); };
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


// Make an HTTP request to the given address. 
// Display result in the HTML element that has given ID.

function ajaxResult(address, resultRegion) {
  var request = getRequestObject();
  request.onreadystatechange = 
    function() { handleJoke(request, 
                                  resultRegion); };
  request.open("GET", address, true);
  request.send(null);
}

//Put response text in the HTML element that has given ID.

function showResponseText(request, resultRegion) {
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    htmlInsert(resultRegion, request.responseText);
  }
}


function handleJoke(request, resultRegion) {
  if ((request.readyState == 4) && (request.status == 200)) {
	
	var joke = JSON.parse(request.responseText);
	var setup = joke[0].setup;
	var pl = joke[0].punchline;
	
    htmlInsert(resultRegion, makeJokeCard(setup, pl));
  }
}


function makeJokeCard(s, p){
	
	var card = '<div class="card"> '+ s +'<br> <div class="pl"><em> '+ p +'<em></div> </div>';
	return card;
	
}

// Make an HTTP request to the given address. 
// Display result in the HTML element that has given ID.
// Use POST. 

function ajaxResultPost(address, data, resultRegion) {
  var request = getRequestObject();
  request.onreadystatechange = 
    function() { showResponseText(request, 
                                  resultRegion); };
  request.open("POST", address, true);
  request.setRequestHeader("Content-Type", 
                           "application/x-www-form-urlencoded");
  request.send(data);
}

// Insert the html data into the element that has the specified id.

function htmlInsert(id, htmlData) {
  document.getElementById(id).innerHTML += htmlData;
}

// Return escaped value of textfield that has given id.
// The builtin "escape" function converts < to &lt;, etc.

function getValue(id) {
  return(escape(document.getElementById(id).value));
}
/**
 * webFeatures
 * The webFeatures contains all additional features of a web application. 
 * It includes functions and code that enhance the functionality and 
 * user experience of the web application beyond the basic features.
 * 
 * @author jordanprescott
 * @version 1.0
 * 
 */

/**
 * notify
 * 
 * The notify function sets a notification in the navigation bar for the user 
 * about an action they have performed. It retrieves a message from localStorage, 
 * creates a button with the message, adds the button and clear the message, and 
 * appends the button to the navigation bar. This function provides visual 
 * feedback for the user and can be used for various actions.
 *  
 */
function notify() {

	// get notification set in ajax methods
	message = getNotification();
	
	if (message) { // if not null - used when page is first loaded
		
		// nav bar where notifcation is inserted
		var notify = $('#inbox')
	
		// replace inbox with current notification
		notify.replaceWith('<div id="inbox" class="alert alert-dismissible alert-info">' +
			'<button type="button" class="btn-close" data-bs-dismiss="alert" onclick="clearNotification()"></button>' + 
			message + '</div>');
	}

}

/**
 * setNotification
 * 
 * This function is used to store a notifcation message in the localStorage.
 * The reason is on moving to other pages the messages would be lost otherwise
 * this way when a page loads it can look if there any notifications and store
 * in inbox for user.
 * 
 */
function setNotification(message) {
	localStorage.setItem("notification", message);
}

/**
 * getNotification
 * 
 * This is used to retrieve the notification stored in localStorange to be 
 * displayed to the user.
 * 
 */
function getNotification() {
	return localStorage.getItem("notification");
}

/**
 * clearNotification
 * 
 * clearNotification is a function that clears the message stored in localStorage 
 * and replaces the notification div with an empty li element. This resets the 
 * notification display, ready for a new notification. It's a useful function to 
 * provide a clean interface for the user.
 * 
 */
function clearNotification() {
	
	var notify = $('#inbox')
	
	// replaces current notfication with nothing 
	notify.replaceWith('<li id="inbox"></li>');
	
	// clear notification stored in local storage
	setNotification("");
}

/**
 * liveSearch
 * 
 * The code that enables live searching in a web application allows users 
 * to search for specific data within a table and see the results update 
 * live as they type their search query. 
 *
 */
$(document).ready(function() {
    $('#searchString').on('keyup', function() {
        var query = $(this).val().toLowerCase();
        $('#filmTable tbody tr').each(function() {
            var text = $(this).text().toLowerCase();
            if(text.indexOf(query) !== -1) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });
});

/**
 * randomFilm
 * 
 * This function clears the table and randomly generates a film for the user.
 * This is if the user is looking for a film to watch and cant decide this 
 * fucntion will give a random film.
 * 
 */
function randomFilm() {

	// empty table ready for new entries
	body.empty();
	
	// get random film
	const randomNumber = Math.floor(Math.random() * allFilms.length);
	const film = allFilms[randomNumber];
	
	// display random film
	body.append(getRow(film));
	
}






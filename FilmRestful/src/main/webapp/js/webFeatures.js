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
		
		var notify = $('#inbox')
	
		// replace inbox with current notification
		notify.replaceWith('<div id="inbox" class="alert alert-dismissible alert-info">' +
			'<button type="button" class="btn-close" data-bs-dismiss="alert" onclick="clearNotification()"></button>' + 
			message + '</div>');
	}

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








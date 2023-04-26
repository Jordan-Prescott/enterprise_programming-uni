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








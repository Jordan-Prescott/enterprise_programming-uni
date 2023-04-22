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
 * TODO: refactor 
 */
function notify(message) {

	var notify = $('#inbox')

	notify.replaceWith('<div id="inbox" class="alert alert-dismissible alert-info">' +
		'<button type="button" class="btn-close" data-bs-dismiss="alert" onclick="clearNotification()"></button>' + 
		message + '</div>');

	notify.fadeIn().delay(2000).fadeOut();
	
	return notify
}


/**
 * clearNotification
 * 
 * TODO: refactor 
 */
function clearNotification() {
	var notify = $('#inbox')
	notify.replaceWith('<li id="inbox"></li>');
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


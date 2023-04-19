/**
 * webFeatures
 * 
 * 
 */

/**
 * notify
 * 
 * 
 */
function notify(message) {

	var notify = $('#inbox')

	notify.replaceWith('<div id="inbox" class="alert alert-dismissible alert-info">' +
		'<button type="button" class="btn-close" data-bs-dismiss="alert" onclick="clearNotification()"></button>' + 
		message + '</div>');

}


/**
 * clearNotification
 * 
 * 
 */
function clearNotification() {
	var notify = $('#inbox')

	notify.replaceWith('<li id="inbox"></li>');
}
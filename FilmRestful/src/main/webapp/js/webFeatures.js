/**
 * 
 */
function notify(message) {

	var notify = $('#notifcation')

	notify.replaceWith($('<div class="alert alert-dismissible alert-info">' +
		'<button type="button" class="btn-close" data-bs-dismiss="alert"></button>' + 
		message + '</div>'));

}
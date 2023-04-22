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

	return notify
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


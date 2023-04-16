/**
 * 
 */
function loadPage() {
  $.ajax({
  url: "http://localhost:8080/FilmRestful/FilmsAPI",
  dataType: "json",
  success: function(data) {
    // Process the data that is returned
    console.log(data);
  },
  error: function(jqXHR, textStatus, errorThrown) {
    // Handle any errors that occur during the request
    console.error("Error: " + textStatus, errorThrown);
  }
});
}
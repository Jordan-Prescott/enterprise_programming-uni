// Show the time in the city whose name is given
// by the textfield whose id is "city-1". Use GET.

function showTimeInCity(inputField, resultRegion) {
  var baseAddress = "show-time-in-city";
  var data = "city=" + getValue(inputField);
  var address = baseAddress + "?" + data;
  ajaxResult(address, resultRegion);
}

// Show the time in the city whose name is given
// by the textfield whose id is "city-2". Use POST.

function showTimeInCityPost(inputField, resultRegion) {
  var address = "show-time-in-city";
  var data = "city=" + getValue(inputField);
  ajaxResultPost(address, data, resultRegion);
}
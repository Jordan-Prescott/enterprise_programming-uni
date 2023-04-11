function getJoke(rr){
	
	ajaxResult("https://official-joke-api.appspot.com/jokes/programming/random", rr);
}


document.addEventListener("DOMContentLoaded", function(){
    getJoke('joke');
});
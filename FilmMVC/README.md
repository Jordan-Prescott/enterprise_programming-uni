#FilmMVC
##Description
The FilmMVC program follows the Model-View-Controller (MVC) design pattern. The default page is the home page, which gives users a view to interact with a Films database. 
This program provides full CRUD (Create, Read, Update, Delete) functionality for the Films database, allowing users to manage the data in a user-friendly way. 
The Model component is responsible for managing the data, while the View component provides the interface for users to interact with the database. The Controller component 
handles the communication between the Model and View components and processes user requests.

##Instructions
To run this program import it into your eclipse environment when you have done so please ensure you have Tomcat server 9 installed. Right click on the root folder 'FilmMVC' > Run As > Run on Server. This should run the program on your machine at http://localhost:8080/FilmMVC
if when you load this program and no film entries appear please select the 'Film DB' or append /home to the end of url and this will load the entries. 

##Usage
#####Adding A Film.
To add a film select the 'Add Film' button at the side of 'Film DB' in the top left hand corner. This will then take you to a separate page to add details of the new film. Note to add a film you will need to fill in all sections before you can submit the film and the year field will only let you enter numbers and a complete year higher than 1888(The first movie filmed according to Google). Once you have added all fields choose 'Add Film' and this will submit your film to the database and redirect you to the home page where you can then search for your film. 

#####Searching.
You can search the whole database for title, director, year, stars, rating, and genre buy using the search bar in the top right hand corner. If the value is found in any of these sections it will return true. For example searching 'Robert De Niro' will show films he has directed as well as starred in but if you would like all the films that are 5 stars in rating searching '*****' will only return this rating of film. 

#####Update A Film.
To update a film click the 'Update' button found at the side of each film entry in the table. This will take you to a separate page showing the films details of the one you selected in a form. You can then adjust any entry and then click the 'Update Film.' button which will update your film. Please note that each field is required before you can submit the film.

#####Returning Home.
If you have clicked the 'Add Film' or 'Update' button from the /home screen you will be directed to another page. If you do not want to carry out that operation click the 'Film DB' in the top left and this will return you to the /home view.

#####Deleting A Film.
To delete a film click the 'Delete' button found at the side of each film entry in the table. This will then delete that film and reload the /home page where that film will no longer be shown as it was deleted from the database.

##Documentation
For documentation on the application code itself please find generated java docs in the doc folder.
#FilmRestful
##Description
The web application is designed to interact with a database using a RESTful API. It presents an HTML webpage to the user and uses Jquery to dynamically fetch data from the API based on the user's request. The application allows users to browse and search through the data in the database and display it in a user-friendly format on the webpage. Users can use different search parameters to filter the data and get more specific results. 


##Instructions
To run the program please add the project to your eclipse enviroment and run this on your local machine and some documentation for this API has this saved to work on local machines for your convenience. Once you have the program in your eclipse environment please right click the root folder 'FilmRestful' select 'Run As' and then select 'Run On Server'. Note, you will need Tomcat Server 9 installed and then in the next screen choose this server to run on.
a 
REST API:
The Film API is a RESTful API that provides functionality to manage film entries in a database. The API supports four main HTTP methods:

GET: This method is used to retrieve film entries from the database. The API provides various GET requests to retrieve all film entries, a subset or a single film using search criteria.

POST: This method is used to create a new film entry in the database. The request body should include the film entry details like title, year, director, stars, and review.

PUT: This method is used to update an existing film entry in the database. The request body should include the ID of the film entry to be updated along with the modified fields like title, year, director, stars, and review.

DELETE: This method is used to delete a specific film entry from the database based on the ID of the film entry.
The API also supports various data formats such as JSON, XML, and TEXT based on the format specified by the client in the Accept or Content-Type header of the request. The Film API is designed to provide a simple and efficient way to manage film entries in a database.

NOTE: In all instances the REST API defaults to JSON as its widely used and then tries XML and then TEXT. For example if the user is to run a GET request or all films and does not specify the format the result returned will be JSON.

A Postman collection has been saved with further documentation on each request in this project. You can import this collection directly into postman which also has all requests set up for testing. Please find this collection at FilmRestful > doc > Film API.postman_collection.json.
Alternatively, you can run the web application and locate the 'API' page, once you are here there is a Postman button that will let you import a Film API collection directly into a specified environment. Either way when you have this in postman please right click the collection
and then select 'View Documentation' this will give you all the details you need along with example requests.

Web App:
This application also has a front end UI where you can interact with the database in a graphical way. To run this application it is the same instructions above and once this is launched in the browser you will be presented with the web app GUI.

##Usage

REST API:
To interact directly with the Restful API the easiest way is to use Postman to send request and integrate the data. For convenience a Postman collection has been saved in the project doc folder which also serves as complete documentation for interacting with the API, please import this file into your postman enviroment. Once imported you will see the new Film API collection, righ-click on this and then selecet View Documentation this will show all calls with examples and details of each request. Please note you may need to open each request first before the documentaion populates I believe this is an issue with postman. 

Web App:
For details on using the front end web application please select the 'Help' button on the main page. This help page will detail each how to navigate the web application as well as each feature for you to make the most of the application. 

##Documentation
For documentation on the application code itself please find generated java docs in the doc folder.
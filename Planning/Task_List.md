# Planter's Guide Task List

## 1. Materials
- Obtain a plant that can be used throughout the development process (Chris)
- Research/Obtain the most useful sunlight & moisture sensor and their respective cables for the raspberry pi (Kevin)
- Draft up a model for our automated watering mechanism (Kevin)
- Obtain all required parts to make our automated watering mechanism (Raymond)

## 2. SQL Database
- Research the best method to store our SQL database -- either on the raspberry pi on through a web service (Raymond)
- Create a SQL database that stores various plant species' names, recommended moisture level, and recommended sunlight level (Raymond)
- Decide the best route to go when given a name currently NOT in the database. Find the most similar name or reject entirely? Implement accordingly. (Raymond)

## 3. Data Transfer
-  Research the best way to transfer data from our raspberry pi to the android app (Kevin)

- (1) Develop/Research a method to send user inputted plant species to the raspberry pi (Chris)
- (2) Develop/Research a method for sending a select statement to the SQL database (Raymond)
- (3) Develop/Research a method to have output of select statement in SQL sent back to the raspberry pi (Raymond)
- (4) Develop/Research a method to have the select statement on the raspberry pi sent to the android app (Chris)

- Implement a method that will send a signal to the raspberry pi to let it know if it should automatically water the plant or not (Kevin)

## 4. App
- Develop the skeleton of the application. Get a functioning app that will open and has all of the screens we will need to work with. (Chris)
- Develop the application's components that will be used to send data to the raspberry pi (automation check box / text box to enter plant species) (Chris)
- Develop notifications for the app to alert the user when the plant needs more or less water/sunlight. (Chris)
- Create an appeasing UI for the application (Chris)

## 5. Automated Watering Mechanism
- Create machine that will automatically water the plant if moisture level gets too low and automatic watering is on (Kevin)
- Ensure that automatic watering does not over water -- make the machine choose when to water based on the recommended moisture level and the value in the moisture sensor (Kevin)
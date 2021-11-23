# Team *B.E.G*

## Team Members
Chris Butts (CS)
- E-mail: buttscm@mail.uc.edu

Kevin Eaton (CS)
* E-mail: eatonko@mail.uc.edu

Raymond Gee (CS)
* E-mail: geern@mail.uc.edu

Advised by Dr. Badri Vellambi

<br>

## Planter's Guide

### Project Focus
* Utizing sensors & other hardware to gather data and automate processes
* Database management
* Image Processing

### Current Plan
  Planters Guide is an Android application that can be utilized to monitor a plant’s sunlight and moisture levels. The app will display the plant’s current moisture and sunlight levels, as well as notifying the user if either of these levels are appropriate for the plant’s species.
  The application will communicate with a Raspberry Pi to get the plant’s sunlight and moisture levels via two sensors that will be placed into the plant’s soil. The Raspberry Pi will also be communicating with a remote SQL database which will store a variety of different plant species’ appropriate water and sunlight thresholds. The Raspberry Pi will be sending data from both the SQL database and the sensors to the app in real time.
  The application will also allow the user to turn on automatic watering if they are unable to reliably water their plant. The automatic watering will be done by a mechanism we develop that will communicate with the Raspberry Pi; if the water level gets too low below the water threshold, the machine will water the plant until it is comfortably above the threshold.

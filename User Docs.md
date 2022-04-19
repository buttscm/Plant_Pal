# Initial Setup

## Setting up the Hardware

1. Assuming the Raspberry Pi already has the GPIO and sensors plugged in, please place the moisture sensor into the soil of your plant, and the sunlight sensor either into the soil or closeby the plant. This way, the Raspberry Pi will be able to read the plant's current moisture and sunlight levels.

2. The automatic watering device should be already hooked up to the Raspberry Pi. After assuring that the water tank is adequately filled with water, make sure the output tube for the water is properly positioned above the plant. This way the automatic watering device will release its water into the soil.

For a more detailed set up guide please see the following set-up guide: [Pi Setup](https://github.com/buttscm/Plant_Pal/blob/main/pi/Pi%20Setup.docx)
## Setting up the Software

1. Assuming the app has been downloaded, you will be prompted with a screen that looks something like this

![alt text](https://github.com/buttscm/Plant_Pal/blob/main/Formal%20Documents/App%20Images/Plant_Pal_Home.png "Home Screen")

You will know that you've properly set up the sensors properly if you see values under the sunlight and moisture blocks of the app. However, this data will not be able to do much without knowing what kind of plant you have.

2. Press the "Change Species" button and you will be redirected to a page where you will be shown all of the plants currently in our database.

![alt text](https://github.com/buttscm/Plant_Pal/blob/main/Formal%20Documents/App%20Images/Plant_Pal_Lookup.png "Lookup Screen")

You can use the search bar at the top of the screen to look up your particular plant. Once you find your plant, you can click on it and it will redirect you back to the home screen. The homescreen will now look much more like the first screenshot seen above, with your plant's name, and thresholds populating the 3 fields below the buttons.

3. Turn on automatic watering

On the home screen, if you click the 3-dots in the top right you can go to the settings screen. From here, you can turn on automatic watering to allow the device to start automatically watering your plant.

![alt text](https://github.com/buttscm/Plant_Pal/blob/main/Formal%20Documents/App%20Images/Plant_Pal_Settings.png "Settings")

The app can now let you know if your sunlight or moisture levels are at proper levels. If the moisture level is not at the proper level, the automatic watering device will turn on and water the plant in small bursts, assuring that it does not over water the plant. Now you can occasionally check on your plant while you are out assuring that your plant is being properly cared for and watered with the device!

# Common Questions

## "What if I don't know my plant's species?"
- If you don't know your plant's species, you can utilize the settings menu. As shown above, the settings menu has options for manually setting a moisture and sunlight threshold. If you do not know your plant, you can use the preset radio buttons located in the settings to select the settings you think would best fit your plant.

## "What if I have specific conditions I want my plant to be in?"
- Similar to the question above, you can manually enter your moisture and sunlight thresholds in the text fields to assure that you are meeting the conditions your plant requires.

## "What if my plant Isn't in the database?"
- First off, apologies for your plant not being in our database! To circumvent this, if there is a plant similar to the one you own with similar watering conditions that you are aware of you could choose that plant. If not, you can also simply manually enter your own thresholds in the settings menu.

## "What if my plant is dying while using the app?"
- If your plant is dying under certain moisture and sunlight levels, please stop using the app. Your plant's health is much more important than the correctness of our database. If you still wish to use the app you can manually adjust the moisture and sunlight levels, and it may also be helpful to turn off automatic watering if you have that setting on. 
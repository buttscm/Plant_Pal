# Main function for running Plant Project
# Kevin Eaton


import busio
import digitalio
import board
import adafruit_mcp3xxx.mcp3008 as MCP
import time
import RPi.GPIO as GPIO
from gpiozero import OutputDevice
from adafruit_mcp3xxx.analog_in import AnalogIn
import mysql.connector

import setup
import db



def main():
    
    #Sunlight Sensor
    sunlight = setup.Sensor("sunlight")
    setup.config(sunlight)
    
    print("\n\n")
    
    #Moisture sensor
    moisture = setup.Sensor("moisture")
    setup.config(moisture)
    
    print("\n\n")
    
    #Resevoir Sensor
    resevoir = setup.Sensor("resevoir")
    setup.config(resevoir)
    
    print("\n\n")
    
    #Pump
    pump = setup.Relay(16, False)
    
    #SQL Variables MAKE THESE COME FROM CONFIG
    with open(r".config", "r") as config:
        lines = config.read()
        lines_split = lines.split("\n")
        hostname = lines_split[0]
        username = lines_split[1]
        password = lines_split[2]
        database = lines_split[3]
        port = lines_split[4]
    
    myConnection = mysql.connector.connect(host = hostname, user = username, passwd=password, db=database, port=port)
    
    #User Settings
    settings = db.UserSettings()
    
    ###LOOP VARIABLES###
    minute_count = 0
    currMoisture = 0
    currSunlight = 0
    currResevoir = "Full"
    
    
    ###MAIN LOOP###
    while True:
        time.sleep(5)
        minute_count += 1
        
        #get variables
        moisture.get_reading()
        sunlight.get_reading()
        resevoir.get_reading()
        
        currMoisture = moisture.val
        currSunlight = sunlight.val
        currResevoir = setup.updateResevoir(resevoir.val)
        
        ##EVERY 5 SECONDS
            #grab user settings, check if a fetch now request has been made and send a log if so
        
        settings.updateSettings(db.settingSelect(myConnection))
        if(settings.do_fetch == 1):
            
            db.logInsert(myConnection,  [currResevoir, "temp light state", currSunlight, currMoisture])
            print("LOG SENT:\n" + 'Moisture: ' + str(currMoisture) + '\t\tSunlight: ' + str(currSunlight))
            db.settingUpdate(myConnection)
            #reset minute_count
            minute_count = 0
        
        ##EVERY MINUTE
            #send a log
            #count to 20 from 5 second sleep
        if(minute_count >= 2):
            
            db.logInsert(myConnection,  [currResevoir, "temp light state", currSunlight, currMoisture])
            print("LOG SENT:\n" + 'Moisture: ' + str(currMoisture) + '\t\tSunlight: ' + str(currSunlight) + "\n")
            db.logSelect(myConnection)
            #reset minute_count
            minute_count = 0
        
        #Wait 5 seconds, do it again
        if(currMoisture < settings.moisture_threshold):
            #Turn the watering on for 5 seconds
            pump.on()
            time.sleep(5)
            pump.off()
            #update minute count
            minute_count += 1

if(__name__ == "__main__"):
    main()

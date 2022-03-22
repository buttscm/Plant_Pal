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

import setup

def main():
    
    #Sunlight Sensor
    sunlight = setup.Sensor("sunlight")
    #setup.config(sunlight)
    
    #Moisture sensor
    moisture = setup.Sensor("moisture")
    setup.config(moisture)
    
    #Pump
    pump = setup.Relay(16, False)
    
    #Loop that takes constant inputs
    while True:
        time.sleep(3)
        moisture.get_reading()
        print(moisture.val)
        
        if(moisture.val < moisture.threshold):
            pump.on()
            time.sleep(3)
            pump.off()
        

if(__name__ == "__main__"):
    main()

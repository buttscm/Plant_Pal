# setup classes and functions for the main file
# Kevin Eaton

import busio
import digitalio
import board
import adafruit_mcp3xxx.mcp3008 as MCP
import time
import RPi.GPIO as GPIO
from gpiozero import OutputDevice
from adafruit_mcp3xxx.analog_in import AnalogIn

class Relay(OutputDevice):
    def __init__(self, pin, active_high):
        super(Relay, self).__init__(pin, active_high)

def GetSensor(sensorName):
    #Setup for SPI analog to digital converter
    spi = busio.SPI(clock=board.SCK, MISO=board.MISO, MOSI=board.MOSI)
    cs = digitalio.DigitalInOut(board.D5)
    mcp = MCP.MCP3008(spi, cs)
    #P0 first channel, P1 next, so on
    
    if(sensorName == "sunlight"):
        return AnalogIn(mcp, MCP.P0)
    elif(sensorName == "moisture"):
        return AnalogIn(mcp, MCP.P1)
    else:
        return AnalogIn(mcp, MCP.P2)

class Sensor:
    def __init__(self, sensorName):
        self.min = 0
        self.max = 0
        self.val = 0
        self.name = sensorName
        self.sensor = GetSensor(sensorName)

    def get_reading(self):
        #Get reading as a percentage of max and min
        #divide by the min reading - max reading
        #1 - answer gets percentage from of sunlight on plant
        
        self.val = round(1 - (self.sensor.value - self.max)/(self.min - self.max), 2)

def config(sensor):
    print("Configuring " + sensor.name + " Sensor. \n")
    
    #Config the sunlight sensor
    #To config: first cover, get 50 readings, average those out, and make that
    #an approximate baseline
    # do for both max and min
    
    if(sensor.name == "sunlight"):
        input("Please place cover the sunlight sensor. Use your finger or something to fully obscure.\nPress enter when ready.\n")

    else:
        input("Please make sure sensor is completely dry. \nPress enter when ready.\n")
        
    total = 0
    for i in range(500):
        total += sensor.sensor.value
        
    sensor.min = int(total/500)
    
    if(sensor.name == 'sunlight'):
        input("\nPlease use a light source directly on the sunlight sensor.\nPress when ready.\n")
    else:
        input("Please submerge sensor in water fully up until electronic components.\nPress enter when ready.\n")        
    
    total = 0
    for i in range(500):
        total += sensor.sensor.value
        
    sensor.max = int(total/500)
    
    print("Completed " + sensor.name + " Sensor Config!\n\n")
    
    #IMPORTANT: max and min will be opposite, max and min just mean max and min light/moisture valus
    
def updateResevoir(val):
    
    if(val > 0.2):
        return ("Full")
    elif(val > 0.05):
        return("Warning")
    else:
        return("Empty")
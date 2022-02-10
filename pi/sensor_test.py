# Testing the sensors functionality
# Kevin Eaton
# This will be used to test the functionality of our hardware sensors
# that will go into the plant.

import RPi.GPIO as GPIO
import time


def TestSunlight():
    GPIO.setmode(GPIO.BCM)
    
    #setup the GPIO for input on the pin i have it plugged into
    GPIO.setup(21, GPIO.IN)
    
    print("Sunlight test")

    #constantly read, see what the output is
    #works, but we want analog output
    while True:
        print(float(GPIO.input(21)))

def TestPump():
    print("test pump")
def main():    
    TestPump()

if(__name__ == "__main__"):
    main()
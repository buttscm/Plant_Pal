# Testing the sensors functionality
# Kevin Eaton
# This will be used to test the functionality of our hardware sensors
# that will go into the plant.

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
    
def TestAnalogInputs():
    #Setup for SPI analog to digital converter
    spi = busio.SPI(clock=board.SCK, MISO=board.MISO, MOSI=board.MOSI)
    cs = digitalio.DigitalInOut(board.D5)
    mcp = MCP.MCP3008(spi, cs)
    #P0 first channel, P1 next, so on
    sunlight = AnalogIn(mcp, MCP.P0)
    moisture = AnalogIn(mcp, MCP.P1)
    
    #Setup GPIO
    #GPIO.setmode(GPIO.BCM)
    #GPIO.setup(DataPin,  GPIO.OUT)
    
    #Infinite loop read voltage
    #Good way to check if there are readings in
    print("Testing Sunlight and Moisture Sensor values (ctrl c to stop)")
    time.sleep(3)
    
    for i in range(10):
        #print('Raw Sunlight ADC Value (P0): ', sunlight.value)
        print('Sunlight Voltage: ' + str(round(sunlight.voltage, 2)) + 'V\n')
        #print('Raw Moisture ADC Value (P1): ', moisture.value)
        print('Moisture Voltage: ' + str(round(moisture.voltage, 2)) + 'V\n\n')
        #if channel.voltage > 2.0:
            #GPIO.output(DataPin, True)
        #else:
            #GPIO.output(DataPin, False)
        
        time.sleep(1)

def TestPump(DataPin):
    #testing the pump, runs on and off
    #GPIO.setmode(GPIO.BCM)
    #GPIO.setup(DataPin, GPIO.OUT)
    RELAY = Relay(16, False)
    
    print('Testing Pump.')
    time.sleep(3)
    
    print('Pump On!')
    RELAY.on()
    time.sleep(3)
    print('Pump Off!')
    RELAY.off()
    time.sleep(3)

def main():    
    TestAnalogInputs()
    print('\n\n\n')
    
    TestPump(16)
    
    print('Test Concluded. If anything did not work properly, please consult wiring manual.')
    

if(__name__ == "__main__"):
    main()

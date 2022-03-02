# Webscraper to get database and save data into a csv
# Kevin Eaton

# Webscraping using selenium and BeautifulSoup
# This could be faster, like running a scrape page for every letter at the same time
# and combining, but thats a lot of work
# and this only needs to be run once for us to have the data and save it (its only about 1Mb of data)

import re, csv
from selenium import webdriver
from webdriver_manager.firefox import GeckoDriverManager
from bs4 import BeautifulSoup
from string import ascii_uppercase

def WebScraper():
    try:
        #load up browser, headless
        fireFoxOptions = webdriver.FirefoxOptions()
        fireFoxOptions.headless = True
        browser = webdriver.Firefox(executable_path=GeckoDriverManager().install(), options=fireFoxOptions)

        plants = [['Latin Name', 'Common Name', 'Habit', 'Height', 'Hardiness', 'Growth', 'Soil', 'Shade', 'Moisture', 'Edible', 'Medicinal', 'Other']]
        # from a to z uppercase, go through the database
        for c in ascii_uppercase:
            #get first page of latin name source
            browser.get('https://pfaf.org/user/DatabaseSearhResult.aspx?CName=' + c + '%')

            ScrapePage(browser, plants)
            print('Finished with plants that start with ' + c + ".")


        print('Finished Fetching Data!')
        print('Cleaning data...')
        
        plants = CleanData(plants)
        with open("./plant-table.csv", 'w', newline='') as f:
            writer = csv.writer(f)
            writer.writerows(plants)
        
        print("Successfully Completed! xD Joe Biden lol ")
        
    finally:
        try:
            browser.close()
        except:
            pass

def ScrapePage(browser, plants):
    # grab browser source again
    # help from https://www.titanwolf.org/Network/q/841bb30f-6e56-41cf-8434-939d84f25256/y

    #go from 0 to 100, 
    # it will error out at some point and move on in the try-except loop
    for i in range(100):
        #Parse out the html from the data into a BS object
        soup = BeautifulSoup(browser.page_source, 'html.parser')

        #Grab table with ID we want
        table = soup.find(lambda tag: tag.name == 'table' and tag.has_attr('id') and tag['id'] == "ContentPlaceHolder1_gvresults")
        rows = table.findAll(lambda tag: tag.name == 'tr')

        #text out of each row and add it to a python list
        for row in rows:
            new_row = []
            for cell in row("td"):
                #remove \n and \t from cell.text, append to new_row
                new_row.append(re.sub(r'[\t\n\r\xa0]', '', cell.text))
            #append this new row to plants
            plants.append(new_row)
        
        #last row is going to be the links to press that move you to another page, delete that row
        plants.pop()

        try: 
            # goes purposely too far, 
            # if it goes too far it gets caught by an exception and continues to the 
            # next letter in the alphabet
            # click on link to next page and parse again
            browser.find_element_by_xpath("//td/a[text()=" + str(i) + "]").click()

            # FYI: This is depriciated, but it works for now. 
            # In the future it may not work but for our purposes, 
            # it only needs to work once
        except:
            pass

def CleanData(plants):
    newplants = []
    #remove duplicates, make sure the propper data is in there first
    for row in plants:
        if row not in newplants and row != [] and len(row) > 7:
            newplants.append(row)
    
    for row in newplants:
        new_four = []
        for element in row[4]: #this was a pain in the ass, its formatted so weird from the db
            if element != ' ': #if its not a space, dont add it
                new_four.append(element)
        row[4] = ''.join(new_four) #join the list together to be one string
    
    return newplants

if(__name__ == "__main__"):
    WebScraper()
    

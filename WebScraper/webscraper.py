# Webscraper to get database and save data into a csv

import requests, re
from bs4 import BeautifulSoup

# Making a GET request
r = requests.get('https://pfaf.org/user/DatabaseSearhResult.aspx?LatinName=A%25')

#Parse out the html from the data into a BS object
soup = BeautifulSoup(r.content, 'html.parser')

#Grab table with ID we want
table = soup.find(lambda tag: tag.name == 'table' and tag.has_attr('id') and tag['id'] == "ContentPlaceHolder1_gvresults")
rows = table.findAll(lambda tag: tag.name == 'tr')

#text out of each row and add it to a python list
plants = []
for row in rows:
    new_row = []
    for cell in row("td"):
        #remove \n and \t from cell.text, append to new_row
        new_row.append(re.sub(r'[\t\n\r\xa0]', '', cell.text))
    
    #append this new row to plants
    plants.append(new_row)


#print(plants)

res = requests.post('https://pfaf.org/user/DatabaseSearhResult.aspx?LatinName=A%25', data={})
__doPostBack('ctl00$ContentPlaceHolder1$gvresults','Page$2')

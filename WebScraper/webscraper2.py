#Webscraping using selenium instead of BeautifulSoup
# Issues with interactions between javascript on page and scraping

from selenium import webdriver

driver = webdriver.Firefox('./geckodriver.exe')
driver.get("https://dev.to")
 
driver.find_element_by_id("nav-search").send_keys("Selenium")

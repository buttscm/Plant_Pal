#Data Obtained from DB
# Kevin Eaton

class UserSettings:
    def __init__(self):
        self.plant = 0
        self.moisture_threshold = 0.5
        self.sunlight_threshold = 0.5
        self.auto_water = 1
        self.do_fetch = 0
        
    
    def updateSettings(self, settingsArr):
        self.plant = settingsArr[1]
        self.moisture_threshold = settingsArr[2]
        self.sunlight_threshold = settingsArr[3]
        self.auto_water = settingsArr[4]
        self.do_fetch = settingsArr[5]     


def logInsert (conn, toSend):
    cur = conn.cursor()
    
    sql = "INSERT INTO log (WaterResevoirState, SunlightStatus, CurrentSunlight, CurrentWater) VALUES (%s, %s, %s, %s)"
    #vals = ('Deez nuts', 'Good', 0.5, 0.5)
    cur.execute(sql, toSend)
    conn.commit()
    #for name in cur.fetchall():
        #print(name)
def logSelect(conn):
    cur = conn.cursor()
    
    sql = "SELECT * FROM log ORDER BY time DESC LIMIT 5"
    cur.execute(sql)
    
    for record in cur.fetchall():
        print(record)

def settingSelect(conn):
    cur = conn.cursor()
    
    sql = "SELECT * FROM usersetting LIMIT 1"
    cur.execute(sql)
    
    
    return(cur.fetchall()[0])

def settingUpdate(conn):
    cur = conn.cursor()
    sql = "UPDATE usersetting SET ForceLog = 0 WHERE 1"
    
    cur.execute(sql)
    conn.commit()
    
def settingTest(conn):
    cur = conn.cursor()
    sql = "UPDATE usersetting SET ForceLog = 1 WHERE 1"
    
    cur.execute(sql)
    conn.commit()

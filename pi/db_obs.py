#Data Obtained from DB
# Kevin Eaton

class UserSettings:
    def __init__(self, settingsArr):
        self.plant = settingsArr[1]
        self.moisture_threshold = settingsArr[2]
        self.sunlight_threshold = settingsArr[3]
        self.auto_water = settingsArr[4]
        


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
    
    sql = "SELECT * FROM log"
    cur.execute(sql)
    
    for record in cur.fetchall():
        print(record)
def settingSelect(conn):
    cur = conn.cursor()
    
    sql = "SELECT * FROM usersetting LIMIT 1"
    cur.execute(sql)
    
    for record in cur.fetchall():
        plant = record[1]
        moisture_threshold = record[2]
        sunlight_threshold = record[3]
        auto_water = record[4]
        print(record)

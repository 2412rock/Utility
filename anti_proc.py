import win32gui
import time
import datetime
import win32api

#CHECK EVERY MINUTE
w=win32gui
save_file = open("User_activity.txt", "a")
#Find a way to classify productive time
productiveMinutes = 0
procastinationMinutes = 0
gamingMinutes = 0
movieMinutes = 0
runningTime = 0
whateverCount = 0
checkEvery = 60
#startTime = datetime.now()

def gettActiveWindow():
    #returns window Text
    return w.GetWindowText (w.GetForegroundWindow())

def periodCheck():
    global runningTime
    running = True
    #print("Started recording at %s " % (startTime))
    while(running):
        runningTime += 1
        #get the first 3 chars from text
        if runningTime % 30 == 0:
            saveStats()
        activeWindow = gettActiveWindow()
        activeWindow = activeWindow.upper()
        print(activeWindow)
        if not systemIsIdle() and not movieIsPlaying(activeWindow):
            if isBrowser(activeWindow):
                checkChromeTabs(activeWindow)
            else:
                checkSoftware(activeWindow)
        time.sleep(checkEvery)
    #write stats in a txt file
def saveStats():
     save_file.write("Date:%s | productiveMinutes: %d | procastinationMinutes: %d | gamingMinutes:%d | movieMinutes:%d\r\n" % (time.ctime(), productiveMinutes, procastinationMinutes, gamingMinutes, movieMinutes))

def movieIsPlaying(currentWindow):
    #check if Movies app is running or YT video is playing
    if "MOVIE" in currentWindow:
        #print("MOVIE IS PLAYING")
        return True
    elif "YOUTUBE" in currentWindow:
        #print("YT IS PLAYING")
        return True
    else:
        return False

def isBrowser(currentWindow):
    if "CHROME" in currentWindow:
        return True
    else:
        return False

def systemIsIdle(): 
    LastInput =  win32api.GetTickCount() - win32api.GetLastInputInfo()
    #print("LastInput %s " % (LastInput))
    if LastInput > 540000:
        print("System is idle")
        return True
    else:
        return False

def checkChromeTabs(currentWindow):
    global procastinationMinutes
    if "FACEBOOK" in currentWindow:
        procastinationMinutes += 1
        print("Ai belit poola")
    elif "YOUTUBE" in currentWindow:
        if "ALGORITHM" in currentWindow:
            print("Sa zicem ca esti productiv")
        else:
            print("Iara freci pula")
    
def checkSoftware(currentWindow):
    global productiveMinutes, gamingMinutes, movieMinutes
    if "VISUAL STUDIO" in currentWindow:
        productiveMinutes += 1
    elif "ECLIPSE" in currentWindow:
        productiveMinutes += 1
    elif "HEARTHSTONE" in currentWindow:
        gamingMinutes += 1
    elif "GRAND THEFT" in currentWindow:
        gamingMinutes += 1
    elif "MOVIES" in currentWindow:
        movieMinutes += 1
    
periodCheck()


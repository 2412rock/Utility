import win32gui
import time
import datetime
import win32api


w=win32gui
save_file = open("User_activity.txt", "a") #used for saving the stats
productiveSeconds = 0
procastinationSeconds = 0
gamingSeconds = 0
watchingVideoSeconds = 0
runningTime = 0
checkEvery = 5 

def gettActiveWindow():
    #returns window Text
    return w.GetWindowText (w.GetForegroundWindow())

def periodCheck():
    global runningTime
    running = True
    #print("Started recording at %s " % (startTime))
    #check every 10 seconds => 
    #if app is productive => increase productiveSeconds
    while(running):
        runningTime += checkEvery
        #get the first 3 chars from text
        if runningTime % 60 == 0:
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
    print("---Stats saved----")
    save_file.write("Date:%s | productiveSeconds: %d | procastinationSeconds: %d | gamingSeconds:%d | movieSeconds:%d\r\n" % (time.ctime(), productiveSeconds, procastinationSeconds, gamingSeconds, watchingVideoSeconds))

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
    #3000 <=> 1 second
    if LastInput > 540000:
        print("System is idle")
        return True
    else:
        return False

def checkChromeTabs(currentWindow):
    global procastinationSeconds
    if "FACEBOOK" in currentWindow:
        procastinationSeconds += checkEvery
    elif "YOUTUBE" in currentWindow:
        #add browser exceptions here
        if "ALGORITHM" in currentWindow:
            pass
        else:
            watchingVideoSeconds += checkEvery
    
def checkSoftware(currentWindow):
    global productiveSeconds, gamingSeconds, movieSeconds
    if "VISUAL STUDIO" in currentWindow:
        productiveSeconds += checkEvery
    elif "ECLIPSE" in currentWindow:
        productiveSeconds += checkEvery
    elif "HEARTHSTONE" in currentWindow:
        gamingSeconds += checkEvery
    elif "GRAND THEFT" in currentWindow:
        gamingSeconds += checkEvery
    elif "MOVIES" in currentWindow:
        watchingVideoSeconds += checkEvery
    
periodCheck()


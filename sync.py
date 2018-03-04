import os
import shutil
import time

initLocation = "C:/Users/r0ck/Documents"
dest = "C:/Users/r0ck/Desktop/dest"

def copyFiles(input,sendTo):
    src_files = os.listdir(input)
    for file_name in src_files:
        full_file_name = os.path.join(input, file_name)
        if (os.path.isfile(full_file_name)):
            shutil.copy(full_file_name, sendTo)
        if(os.path.isdir(full_file_name)):
            nextLocation = input + "/" + file_name
            endingURL = sendTo + "/" + file_name
            #create a directory with the endingURL
            os.mkdir(endingURL)
            print("ENDING URL: " + endingURL)
            #add folders that should be emitted
            if(endingURL != "C:/Users/r0ck/Desktop/dest/My Music" and endingURL != "C:/Users/r0ck/Desktop/dest/My Pictures" and endingURL != "C:/Users/r0ck/Desktop/dest/My Videos"):
                copyFiles(nextLocation,endingURL)
        

copyFiles(initLocation,temp)

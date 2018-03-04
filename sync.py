#2412rock
#copies Files and Folders from one Location to another
import os
import shutil
import time


initLocation = "C:/Users/adi/Documents"
dest = "D:/Cloud/OneDrive/Documents_backup/"
temp = "D:/Cloud/OneDrive/Documents_backup/"

def wasLastModified(fileInDocuments,fileInBackup):
    fileInDocumentsTime = os.path.getmtime(fileInDocuments)
    fileInBackupTime =  os.path.getatime(fileInBackup)
    if(fileInDocumentsTime > fileInBackupTime):
        return True
    elif os.path.getmtime(fileInDocuments) < os.path.getatime(fileInBackup):
        return False

def copyFiles(input,sendTo):
    try:
        src_files = os.listdir(input)
        for file_name in src_files:
            full_file_name = os.path.join(input, file_name)
            target = sendTo + "/" + file_name
            #print("Checking if target exists: " + target)
            if(not os.path.exists(target) and os.path.isfile(full_file_name)):
                print("Copying from: " + full_file_name + " To: " + target)
                shutil.copy(full_file_name, target)
            if (os.path.isfile(full_file_name) and wasLastModified(full_file_name,sendTo)):
                print("File: " + full_file_name + " needs to be updated")
                print("Copying from: " + full_file_name + " To: " + target)
                os.remove(target)
                shutil.copy(full_file_name, target)
            if(not os.path.exists(target) and os.path.isdir(full_file_name)):
                print("Create dir at: " + target)
                os.mkdir(target)
            if(os.path.isdir(full_file_name)):
                nextLocation = input + "/" + file_name
                #folders that you want to emit
                if(target != "D:/Cloud/OneDrive/Documents_backup/My Music" and target != "D:/Cloud/OneDrive/Documents_backup/My Pictures" and target != "D:/Cloud/OneDrive/Documents_backup/My Videos"):
                    copyFiles(nextLocation,target)
    except(WindowsError, IOError):
        pass
        

copyFiles(initLocation,temp)
print("Sync done..exiting")
time.sleep(5)





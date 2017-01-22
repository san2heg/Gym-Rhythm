
lyrics = [""]
str_songLength = ""
songLength = 0

def readLyrics():
	fName = "lyrics.txt"
	file = open(fName, 'r')
	global lyrics
	global str_songLength
	lyrics = file.readlines()
	lyrics = [x.strip() for x in lyrics]
	str_songLength = lyrics[0]
	del lyrics[0]

def printLyrics():
	global lyrics
	global songLength
	for x in lyrics:
		print(x)
	print("Songlength: %s" % getMinuteCountFromSeconds(songLength))

def readSongLength():
	global str_songLength
	global songLength
	tempTime = str_songLength
	min = int(tempTime[:tempTime.index(":")])
	sec = int(tempTime[(tempTime.index(":")+1):])
	songLength = (min*60) + sec

def getIndexesOfHooks():
	global lyrics
	global songLength
	lineIndexList = []
	index = 0
	for x in lyrics:
		if x.rstrip() == "[Hook]":
			print("hookfound")
			lineIndexList.append(index)
		index+=1
	return lineIndexList

def calculateTimes(indexes):
	global lyrics
	global songLength
	tempList = []
	secondsPerLine = float(songLength) / len(lyrics)
	print("secondsPerLine = %f" % secondsPerLine)
	for x in indexes:
		tempList.append((int)(secondsPerLine * x))
	return tempList

def getMinuteCountFromSeconds(s):
	global songLength
	global lyrics
	leftoverSeconds = s % 60
	minutes = (int)(s/60)
	string = "" + repr(leftoverSeconds)
	if leftoverSeconds < 10:
		string = "" + "0" + repr(leftoverSeconds)
	return repr(minutes) + ":" + string

readLyrics()
readSongLength()
printLyrics()
timeList = calculateTimes(getIndexesOfHooks())
print
print("Hook occurs at: ")
for x in timeList:
	print(getMinuteCountFromSeconds(x-7))






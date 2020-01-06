textDict=dict()
data=open('text1.txt','r')
for string in data:
    string = string.split()
    for word in string:
        if word.lower() not in textDict:
            textDict[word.lower()] = 1
        else:
            textDict[word.lower()]+=1
data.close()
data=open('textDict.txt','w')
data.write(str(textDict))
data.close()
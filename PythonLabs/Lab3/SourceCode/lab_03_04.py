'''
 Словари
'''
d1 = {
 "day": 18,
 "month": 6,
 "year": 1983
}
d2 = dict(bananas=3,apples=5,oranges=2,bag="basket")
d3 = dict([("street","Kronverksky pr."), ("house",49)])
d4 = dict.fromkeys(["1","2"], 3)
print("Dict d1 = ", d1)
print("Dict d2 by dict()= ", d2)
print("Dict d3 by dict([])= ", d3)
print("Dict d4 by fromkeys = ", d4)
print("\n")
startDict = {'ready':3, 'set':2,'go':1}
startDict1 = dict(
    ready=3,
    set=2,
    go =1
)
startDict2 = dict()
startDict2['ready']=3
startDict2['set']=2
startDict2['go']=1
print(startDict,startDict1,startDict2,sep ='\n')
a=int(input())
dict1=dict.fromkeys(['key1','key2'], a)
print(dict1)
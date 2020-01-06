'''
 Операции cо словарями
'''
d2 = {'a':1, 'b':2, 'c':3, 'bag':4}
d5 = d2.copy() # создание копии словаря
print("Dict d5 copying d2 = ", d5)
# получение значения по ключу
print("Get dict value by key d5['bag']: ", d5["bag"])
print("Get dict value by key d5.get('bag'): ",d5.get('bag'))
print("Get dict keys d5.keys(): ", d5.keys()) #список ключей
print("Get dict values d5.values(): ", d5.values()) #список значений
print("\n")
myInfo = {'surname':'Chapkey', 'name':'Alexander', 'middlename': 'Urievich1', 'day':29, 'mouth':3,'year':2001, 'university':'ITMO'}
for i in myInfo.keys():
    print('MyInfo[',i,'] = ', myInfo[i])

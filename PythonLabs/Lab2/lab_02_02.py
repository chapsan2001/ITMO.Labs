'''
 Циклы
'''
# while
print("Numbers < 10 (while):")
i = 0
while (i<10):
 print(i, end=" ") # print in one line
 i += 1
print("\n")
# for
print("Numbers < 10 (for):")
for i in range(0,10):
 print(i, end=" ")
else:
 print("\nThe next number is 10\n")
# break
sum = 0
for i in range(0,100):
 if i > 10:
  print("\nWe reached the end, final sum: ",sum)
  break
 sum += i
# continue
i = 0
while i<=15:
 if i % 3 == 0:
  i += 1
 print(i, end=" ")
 i += 1
print("\n")
# pass
print("Let's print numbers again!")
for i in range(0,10):
 pass
 print(i, end=" ")
print("\n\n")
for i in range(0,500):
    if (i%7==0) and (i%14!=0): print(i)
    if i==300: break
else: print("All numbers were printed!")
i=1
'''
while i<=500:
    if (i%7==0) and (i%14!=0):
        i+=1
        print(i)
    if i==300: break
else: print("All numbers were printed!")
'''
i=1
mas = [[1, 0, 0, 0], [0, 2, 0, 0], [0, 0, 3, 0], [0, 0, 0, 4]]
for i in range(4):
    for j in range(4):
        print(mas[i][j], end=" ")
    print()
i=0
j=0
while j<4:
    while i<4:
        print(mas[i][j], end=" ")
        i+=1
    print()
    j+=1
    i=0
a = int(float.fromhex(input()))
if a<0:
    c = str(bin(128+abs(128+a)))
    c = c[2:]
    print(c)
else:
    c = str(bin(128+abs(128+a)))
    c = c[3:]
    print(c)


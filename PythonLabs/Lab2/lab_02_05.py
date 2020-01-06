def generate(n, now, en, j):
    if now != "":en[j] -= 1
    if n == len(now):
        print(now)
    else:
        for i in range(len(en)):
            if en[i] != 0:

                generate(n, now+chr(i), en.copy(), i)
                continue


str = input()
letters = [0]*256
for i in raw_str:
    letters[ord(i)] += 1
generate(len(str), "", letters, 0)

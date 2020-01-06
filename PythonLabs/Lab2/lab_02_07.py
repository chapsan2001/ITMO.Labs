def twelweToDec(val):
    res = 0
    for i in range(len(val)):
        if val[len(val) - i - 1] == 'A' or val[len(val) - i - 1] == 'a':
            res += 10 * 12 ** i
        elif val[len(val) - i - 1] == 'B' or val[len(val) - i - 1] == 'b':
            res += 11 * 12 ** i
        else:
            res += int(val[len(val) - i - 1]) * 12 ** i
    return res


def decTo14(val):
    res = ""
    if val % 14 < 9:
        res = str(val % 14) + res
    elif val % 14 == 10:
        res = 'A' + res
    elif val % 14 == 11:
        res = 'B' + res
    elif val % 14 == 12:
        res = 'C' + res
    else:
        res = 'D' + res
    return res


print(decTo14(twelweToDec(input())))

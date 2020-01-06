import sys
kLZ = kH = 0
d = dict()
def encodeHuffman(fileIn, fileOut, d): # d - словарь, который определяется в глобальном пространстве имен, чтобы передать его потом в encode
    global kH
    file1 = open(fileIn, 'r')
    string = file1.read().strip()
    kH = len(string)
    file1.close()
    s = set(string)
    line = []
    for i in s:
        freq = string.count(i)
        line.append((i, freq))
    new_line = list()
    while line[1:]:
        line = sorted(line, key=lambda x: x[1], reverse=True)
        left = line.pop()
        right = line.pop() #
        new_line.extend([left, right])
        line.append((left[0] + right[0], left[1] + right[1]))
    k = len(s)
    def coding(letter, code, ls):
        if not new_line:
            return '0'
        while ls:
            left = ls[-1]
            right = ls[-2]
            if letter in left[0]:
                code += '0'
                var = left
            elif letter in right[0]:
                code += '1'
                var = right
            else:
                return code
            ls_new = []
            for i in ls:
                if i[0] in var[0]:
                    ls_new.append(i)
            ls_new.remove(var)
            ls = ls_new
        return code
    for letter in s:
        code = ''
        ls = list(new_line)
        d[letter] = coding(letter, code, ls)
    codes = ''
    for i in string:
        codes += d[i]
    kH = kH/ sys.getsizeof(codes)
    f2 = open(fileOut, 'w')
    f2.write(codes)
    f2.close()
def decodeHuffman(fileIn, fileOut, d):
    dic = {}
    for i in d:
        dic[d[i]] = i
    f1 = open(fileIn, 'r')
    s = f1.read().strip()
    f1.close()
    i = 0
    seq = ''
    f2 = open(fileOut, 'w')
    while len(s) != 0:
        seq += s[i]
        if seq in dic:
            f2.write(dic[seq])
            s = s[1:]
            seq = ''
        else:
            s = s[1:]
    f2.close()
    f3 = open(fileOut, 'r')
    s1 = f3.read().strip()
    f4 = open('dataH.txt', 'r')
    s2 = f4.read().strip()
    if s1 == s2:
        return True
    else:
        return False
def encodeLZ(fileIn, fileOut):
    global kLZ
    file1 = open(fileIn, 'r')
    data = file1.read().strip()
    kLZ = len(data)
    file1.close()
    file2 = open(fileOut, 'r+')
    seq = ['', data[0]]
    warn = [] # список индексов, которые нужно пропускать, т.к. эти элементы уже были задействованы
    file2.write('0'+ data[0])
    for i in range(1, len(data)):
        if i in warn:
            continue
        if data[i] in seq:
            cur = data[i]
            for j in range(i + 1, len(data)):
                prev = cur
                cur += data[j]
                if cur not in seq:
                    warn.append(j)
                    seq.append(cur)
                    file2.write(str(seq.index(prev)) + cur[len(cur) - 1])
                    break
                else:
                    prev = cur
                    warn.append(j)
        else:
            seq.append(data[i])
    file2.close()
    test = open(fileOut, 'r')
    kLZ = kLZ/len(test.read().strip())
def decodeLZ(fileIn, fileOut):
    file1 = open(fileIn, 'r')
    data = file1.read().strip()
    file1.close()
    file2 = open(fileOut, 'w')
    code = ['']
    numbers = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0']
    for i in range(1, len(data), 2):
        subst = code[int(data[i - 1])]
        code.append(subst + data[i])
    for i in data:
        if i in numbers:
            file2.write(code[int(i)])
        else:
            file2.write(i)
    file2.close()
    beg = open(fileOut, 'r')
    fin = open('dataLZ.txt', 'r')
    if beg.read().strip() == fin.read().strip():
        return True
    else:
        return False
encodeHuffman('dataH.txt', 'encodedH.txt', d)
print(decodeHuffman('encodedH.txt', 'decodedH.txt', d))
encodeLZ('dataLZ.txt', 'encodedLZ.txt')
print(decodeLZ('encodedLZ.txt', 'decocedLZ.txt'))
print('Коэффициент сжатия кодом Хаффмана:', kH)
print('Коэффициент сжатия кодом Лемпеля-Зива: ', kLZ)
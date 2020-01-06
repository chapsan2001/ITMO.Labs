import math
class HammingEncoder:
    def __init__(self, dataBits):
        self.dataBits = dataBits
        self.encoded = ''
    def encode(self, string):
        controlBits = int(math.log(self.dataBits, 2))
        encoded = []
        controlindex = []
        accesslist = []
        stoplist = []
        for i in range(controlBits + self.dataBits):
            encoded.append('')
        for i in range(controlBits + 1):
            encoded[2 ** i] = 0
        for i in range(controlBits + 1):
            controlindex.append(2 ** i - 1)
        encoded.pop(0)
        encoded.append('')
        encoded.append('')
        for i in range(len(string)):
            for j in range(controlBits + self.dataBits + 1):
                if encoded[j] == '':
                    encoded[j] = string[i]
                    break
        for i in controlindex:
            res = 0
            k = 0
            turn = i + 1
            for j in range(i, len(encoded), turn):
                if k % 2 == 0:
                    accesslist.append(j)
                if k % 2 == 1:
                    stoplist.append(j)
                k += 1
            new_access = set(accesslist)
            for j in range(len(stoplist)):
                for t in range(accesslist[j] + 1, stoplist[j]):
                    new_access.add(t)
            if len(accesslist) > len(stoplist):
                for p in range(accesslist[len(accesslist) - 1], len(encoded)):
                    new_access.add(p)
            for j in new_access:
                res += int(encoded[j])
            if res % 2 == 1:
                encoded[i] = 1
            res = 0
            new_access.clear()
            k = 0
            accesslist.clear()
            stoplist.clear()
        for i in encoded:
            self.encoded += str(i)
    def decode(self, string):
        controlBits = int(math.log(self.dataBits, 2))
        controlindex = []
        new_control = []
        new_string = ''
        res = 0
        for i in range(controlBits + 1):
            controlindex.append(string[(2 ** i) - 1])
        for i in range(len(string)):
            if i not in controlindex:
                new_string += string[i]
        self.encode(new_string)
        answer = self.encoded
        for i in range(controlBits):
            if answer[2 ** i - 1] != string[2 ** i - 1]:
                new_control.append(int(i))
        for i in new_control:
            res += i
        if len(new_string) != 0:
            print("Ошибка в бите номер ", res + 1)
        else:
            print("Ошибок нет")
test = HammingEncoder(16)
test.encode('1010111001101010')
print(test.encoded)
test.decode('001001011110011001010')
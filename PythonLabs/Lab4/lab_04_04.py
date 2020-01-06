class Encoder:
    def encode(self, string):
        d = dict()
        ans = LZEncoder()
        ans.encode(string)
        print("Encoded in LZ:", ans.encoded)
        ans1 = HuffmanEncoder()
        ans1.encode(string, d)
        print ("Encoded in Haffman:",ans1.encoded )
    def decode(self, string, d = {}):
        ans = LZEncoder()
        ans.decode(string)
        print("Decoded LZ:", ans.decoded)
        ans1 = HuffmanEncoder()
        ans1.decode(string, d)
        print ("Decoded in Haffman:", ans1.decoded)
class LZEncoder(Encoder):
    def __init__(self):
        self.compressionCoef = 0
        self.encoded = ''
        self.decoded = ''
    def encode(self, data):
        seq = ['', data[0]]
        warn = []  # список индексов, которые нужно пропускать, т.к. эти элементы уже были задействованы
        self.encoded = '0' + data[0]
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
                        self.encoded += str(seq.index(prev)) + cur[len(cur) - 1]
                        break
                    else:
                        prev = cur
                        warn.append(j)
            else:
                seq.append(data[i])
    def decode(self, data):
        code = ['']
        numbers = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0']
        for i in range(1, len(data), 2):
            subst = code[int(data[i - 1])]
            code.append(subst + data[i])
        for i in data:
            if i in numbers:
                self.decoded += code[int(i)]
            else:
                self.decoded += i
    def __setCompressionCoef(self):
        self.compressionCoef = len(self.decoded)/len(self.encoded)
    def getCompressionCoef(self):
        self.__setCompressionCoef()
        return self.compressionCoef
class HuffmanEncoder(Encoder):
    def __init__(self):
        self.compressionCoef = 0
        self.encoded = ''
        self.decoded = ''
    def encode(self, string, d):
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
        s = set(string)
        line = []
        for i in s:
            freq = string.count(i)
            line.append((i, freq))
        new_line = list()
        while line[1:]:
            line = sorted(line, key=lambda x: x[1], reverse=True)
            left = line.pop()
            right = line.pop()
            new_line.extend([left, right])
            line.append((left[0] + right[0], left[1] + right[1]))
        k = len(s)
        for letter in s:
            code = ''
            ls = list(new_line)
            d[letter] = coding(letter, code, ls)
        for i in string:
            self.encoded += d[i]
        self.d = d
    def decode(self, s, d):
        dic = {}
        for i in d:
            dic[d[i]] = i
        i = 0
        seq = ''
        while len(s) != 0:
            seq += s[i]
            if seq in dic:
                self.decoded += dic[seq]
                s = s[1:]
                seq = ''
            else:
                s = s[1:]
    def __setCompresionCoef(self):
        self.compressionCoef = len(self.decoded)/len(self.encoded)
    def getCompressionCoef(self):
        self.__setCompresionCoef()
        return self.compressionCoef
d = dict()
test = Encoder()
test.encode('aaaaabaacaabd')
test1 = HuffmanEncoder()
test1.encode('aaaaabaacaabd', d)
test.decode(test1.encoded, test1.d)
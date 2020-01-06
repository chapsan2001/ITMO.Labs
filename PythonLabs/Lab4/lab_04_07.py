class Row:
    count = 0
    def __init__(self, value, collection):
        self.id = Row.count
        Row.count += 1
        self.collection = collection
        self.value = value
class Table:
    def __init__(self, rowsNum):
        self.rowsNum = rowsNum
        self.rows = []
    def addRow(self, row):
        self.rows.append(row)
    def setRow(self, row):
        idList = [i.id for i in self.rows]
        if row.id not in idList:
            print('Error\n')
        else:
            for i in self.rows:
                if i.id == row.id:
                    self.rows.remove(i)
                    self.rows.append(row)
    def getRow(self, rowId):
        for row in self.rows:
            if row.id == rowId:
                return row
    def display(self, variablesNum):
        print('id ', end='')
        for i in range(1, variablesNum + 1):
            print('x' + str(i) + ' ', end='')
        print('f(x1', end='')
        for i in range(2, variablesNum + 1):
            print(', x' + str(i), end='')
        print(')')
        for row in self.rows:
            if len(str(row.id)) == 1:
                print(' ' + str(row.id), end=' ')
            else:
                print(row.id, end=' ')
            print(row.collection, '|', row.value)
        print()
class LogicFunction:
    def __init__(self, variablesNum, table):
        self.variablesNum = variablesNum
        self.table = table
    def getExpression(self):
        true_rows = [row.id for row in self.table.rows if
                     row.value == 1]
        new_table = Table(len(true_rows))
        d = {}
        for row in self.table.rows:
            if row.value == 1:
                new_table.addRow(row)
                d[tuple(row.collection)] = []
        def canCombine(table):
            for i in range(len(table.rows) - 1):
                for j in range(i + 1, len(table.rows)):
                    res = rowCompare(table.rows[i], table.rows[j])
                    if res >= -1:
                        return True
            return False
        def rowCompare(row1, row2):
            k = 0
            ind = -1
            for b in range(len(row1.collection)):
                if row1.collection[b] != row2.collection[b]:
                    k += 1
                    ind = b
            if k == 0:
                return -1
            elif k == 1 and row1.collection[ind] != '*' and row2.collection[ind] != '*':
                return ind
            else:
                return -2
        def rowCombine(table):
            table_copy = Table(len(table.rows))
            for a in range(len(table.rows) - 1):
                for j in range(a + 1, len(table.rows)):
                    dif_ind = rowCompare(table.rows[a], table.rows[j])
                    if dif_ind >= 0:
                        col = table.rows[a].collection.copy()
                        changing_term = Row(1, col)
                        changing_term.collection[dif_ind] = '*'
                        table_copy.rows.append(changing_term)
                        table.rows[a].id = -1
                        table.rows[j].id = -1
                    elif dif_ind == -1:
                        table_copy.rows.append(table.rows[a])
                        table.rows[a].id = -1
                        table.rows[j].id = -1
            for i1 in table.rows:
                if i1.id != -1:
                    table_copy.rows.append(i1)
            return table_copy
        def cover(row1, tup):
            count = 0
            for number in range(len(row1.collection)):
                if row1.collection[number] == tup[number] or row1.collection[number] == '*':
                    count += 1
            if count == len(row1.collection):
                return True
            else:
                return False
        while canCombine(new_table):
            new_table = rowCombine(new_table)
        for string in d:
            for row in new_table.rows:
                if cover(row, string):
                    d[string].append(row)
        cores = []
        for string in d.keys():
            if len(d[string]) == 1:
                if d[string][0] not in cores:
                    cores.append(d[string][0])
                    new_table.rows.remove(d[string][0])
                d[string] = []
        for i4 in cores:
            for j4 in d:
                if i4 in d[j4]:
                    d[j4] = []
        d_copy = {}
        for i3 in d.keys():
            if len(d[i3]) != 0:
                d_copy[i3] = d[i3]
        f = []
        f += cores
        while d_copy:
            term_to_add = 0
            strings = []
            strings_to_delete = []
            for row in new_table.rows:
                k = 0
                mk = 0
                for i in d_copy:
                    if row in d_copy[i]:
                        k += 1
                        strings.append(i)
                if k > mk:
                    mk = k
                    strings_to_delete = strings
                    term_to_add = row
            f.append(term_to_add)
            new_table.rows.remove(term_to_add)
            for s in strings_to_delete:
                if s in d_copy:
                    d_copy.pop(s)
        print("Соращеная формула: ", end='')
        for i in range(len(f)):
            for j in range(len(f[i].collection)):
                if f[i].collection[j] == 0:
                    print('~', end='')
                if f[i].collection[j] != '*':
                    print('x' + str(j + 1), end='')
            if i != len(f) - 1:
                print(' + ', end='')
        print()
    def getTable(self):
        return self.table
    def printTable(self):
        self.table.display(self.variablesNum)
def main():
    var_num = 4
    value_list1 = [0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1]
    value_list2 = [0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1]
    functions = [value_list1, value_list2]
    fun_table = Table(16)
    print("Выберете, какую функцию минимизировать (1 или 2) или введите другую функцию (3)")
    choose = int(input())
    if choose == 3:
        print("Введите количество переменных")
        var_num = int(input())
        print("Введите", pow(2, var_num), "значений функции через пробел")
        value_list3 = [int(i) for i in input().split()]
        functions.append(value_list3)
    for i in range(pow(2, var_num)):
        collection_str = (('0' * var_num + str(bin(i)).replace('0b', ''))[-1: -var_num - 1: -1])[-1:-var_num - 1:-1]
        collection = [int(j) for j in collection_str]
        fun_table.addRow(Row(functions[choose - 1][i], collection))
    fun = LogicFunction(var_num, fun_table)
    fun.printTable()
    fun.getExpression()
if __name__ == '__main__':
    main()
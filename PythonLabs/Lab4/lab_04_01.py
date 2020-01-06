import time

class Ticket:
    def __init__(self, date, name, deadline):
        self.createDate = date
        self.owner = name
        self.deadline = deadline

    def __del__(self):
        print('Delete ticket: ', time.asctime(self.createDate))

    def display(self):
        print('Ticket: ')
        print('createDate: ', time.asctime(self.createDate))
        print('owner: ', self.owner)
        print('deadline: ', time.asctime(self.deadline))


ticket1 = Ticket(time.localtime(), 'Ivan Ivanov', time.strptime('17.12.2019','%d.%m.%Y'))

ticket1.display()
print('Owner: ',ticket1.owner)
print("Owner(getattr): ", getattr(ticket1,"owner"))
print("hasattr: ", hasattr(ticket1,"owner")) #проверка наличия атрибута
setattr(ticket1,"owner","Alexei Petrov")
print("Owner(setattr): ", ticket1.owner)
delattr(ticket1,"owner") # удаление значения атрибута
#print("delattr: ", ticket1.owner) - атрибут owner был удален
del ticket1 # удаление объекта
# print(ticket1) - ошибка: объект был удален
print(time.asctime(time.localtime()))
obj = time.strptime('17.07.2017 10:53:00','%d.%m.%Y %H:%M:%S')
print (obj)
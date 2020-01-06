class Person:
    def __init__(self, firstname, lastname, age):
        self.firstname = firstname
        self.lastname = lastname
        self.age = age
    def display(self):
        print("Firstname: ", self.firstname)
        print("Lastname: ", self.lastname)
        print("Age: ", self.age)
class Student(Person):
    id = 0
    def __init__(self, firstname, lastname, age,  record):
        Person.__init__(self, firstname, lastname, age)
        Student.id+=1
        self.studentID = Student.id
        self.recordBook = record
    def display(self):
        Person.display(self)
        print("ID: ", self.studentID)
        print("Marks: ", *self.recordBook)
        print()
class Professor(Person):
    id = 0
    def __init__(self, firstname, lastname, age, degree):
        Person.__init__(self, firstname, lastname, age)
        Professor.id+=1
        self.professorID = Professor.id
        self.degree = degree
    def display(self):
        Person.display(self)
        print("Professor ID: ", self.professorID)
        print("Degree: ", self.degree)
        print()
student1 = Student('Name', 'Surname', 5, [5,5,5])
student2 = Student('AnotherName', 'AnotherSurname', 7, [2,3,4,5])
student3 = Student('OneMoreName', 'OneMoreSurname', 10, [4,2,1,5,5,3,2,1])
student1.display()
student2.display()
student3.display()
professor1 = Professor('OriginaName', 'OriginalSurname', 50, 'THE BEST')
professor2 = Professor('NotOriginalName', 'NotOriginalSurname', 51, 'BETTER THAN FIRST')
professor3 = Professor('StrangeName', 'StrangeSurname', 40, 'USUAL DEGREE')
professor1.display()
professor2.display()
professor3.display()
import os
import sys
from sqlalchemy import Column, ForeignKey, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import create_engine

Base = declarative_base()


class Teacher(Base):
    __tablename__ = 'teacher'
    username = Column(String(250), nullable=False)
    password = Column(String(250), nullable=False)
    id = Column(Integer, primary_key=True)
    name = Column(String(250), nullable=False)
    department = Column(String(250), nullable=False)

    @property
    def serialize(self):
        return {
            'username': self.username,
            'password': self.password,
            'id': self.id,
            'name': self.name,
            'department': self.department,

        }


class Classes(Base):
    __tablename__ = 'classes'

    sno = Column(Integer, primary_key=True)
    branch = Column(String(80), nullable=False)
    section = Column(String(80))
    Semester = Column(Integer, nullable=False)
    teacher_id1 = Column(Integer, ForeignKey('teacher.id'))
    teacher_id2 = Column(Integer, ForeignKey('teacher.id'))
    teacher_id3 = Column(Integer, ForeignKey('teacher.id'))
    teacher_id4 = Column(Integer, ForeignKey('teacher.id'))
    teacher_id5 = Column(Integer, ForeignKey('teacher.id'))
    teacher1 = relationship("Teacher", foreign_keys=[teacher_id1])
    teacher2 = relationship("Teacher", foreign_keys=[teacher_id2])
    teacher3 = relationship("Teacher", foreign_keys=[teacher_id3])
    teacher4 = relationship("Teacher", foreign_keys=[teacher_id4])
    teacher5 = relationship("Teacher", foreign_keys=[teacher_id5])

    @property
    def serialize(self):
        return {
            'sno': self.sno,
            'branch': self.branch,
            'section':self.section,
            'semester': self.Semester,
            'teacher_id1': self.teacher_id1,
            'teacher_id2': self.teacher_id2,
            'teacher_id3': self.teacher_id3,
            'teacher_id4': self.teacher_id4,
            'teacher_id5': self.teacher_id5,

        }


class Student(Base):
    __tablename__ = 'student'

    name = Column(String(80), nullable=False)
    id = Column(Integer, primary_key=True)
    course = Column(String(250))
    class_id = Column(Integer, ForeignKey('classes.sno'))
    cls_attendend = Column(Integer,default=0)
    total_classes = Column(Integer,default=0)
    jan = Column(String(31))
    feb = Column(String(29))
    march = Column(String(31))
    april = Column(String(30))
    may = Column(String(31))
    june = Column(String(30))
    july = Column(String(31))
    aug = Column(String(31))
    sept = Column(String(30))
    oct = Column(String(31))
    nov = Column(String(30))
    dec = Column(String(31))
    classes = relationship(Classes)

    @property
    def serialize(self):
        return {
            'name': self.name,
            'course': self.course,
            'id': self.id,
            'class_id': self.class_id,
            'jan': self.jan,
            'feb': self.feb,
            'march': self.march,
            'april': self.april,
            'may': self.may,
            'june': self.june,
            'july': self.july,
            'aug': self.aug,
            'sept': self.sept,
            'oct': self.oct,
            'nov': self.nov,
            'dec': self.dec,
            'cls_attended': self.cls_attendend,
            'total_classes' : self.total_classes,
        }


engine = create_engine('sqlite:///attendancee.db')

Base.metadata.create_all(engine)

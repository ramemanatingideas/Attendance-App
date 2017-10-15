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
    teacher = relationship(Teacher)



class Student(Base):
    __tablename__ = 'student'

    name = Column(String(80), nullable=False)
    id = Column(Integer, primary_key=True)
    course = Column(String(250))
    class_id = Column(Integer, ForeignKey('classes.sno'))
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


engine = create_engine('sqlite:///attendancee.db')

Base.metadata.create_all(engine)



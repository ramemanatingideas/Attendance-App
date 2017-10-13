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


class Student(Base):
    __tablename__ = 'student'

    name = Column(String(80), nullable=False)
    id = Column(Integer, primary_key=True)
    course = Column(String(250))
    class_id = Column(Integer, ForeignKey('teacher.id'))
    teacher = relationship(Teacher)

class Classes(Base):
     __tablename__ = 'classes'
     class = Column(String(80))
     branch = Column(String(80), nullable=False)
     section = Column(String(80))
     year = Column(Integer,primary_key=True)
     teacher_id = Column(Integer, ForeignKey('teacher.id'))
     teachers = relationship(Teacher)


engine = create_engine('sqlite:///attendance.db')


Base.metadata.create_all(engine)
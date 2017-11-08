import os
from flask import Flask, render_template, request, redirect, url_for, jsonify, flash
from sqlalchemy import create_engine, or_
from sqlalchemy.orm import sessionmaker
from project import Base, Teacher, Classes, Student

app = Flask(__name__)

engine = create_engine('sqlite:///attendancee.db')
Base.metadata.bind = engine

DBSession = sessionmaker(bind=engine)
s = DBSession()


@app.route('/')
@app.route('/login', methods=["POST"])
def login():
    data = request.json
    query = s.query(Teacher).filter_by(username=data['username']).first()

    if query:
        result = s.query(Teacher).filter_by(password=data['password']).first()
        if (result):
            id1 = result.id
            return jsonify({"value": id1})
        else:

            return jsonify({"value": 0}, {"name": query.name}, {"department": query.department})
    else:
        return jsonify({"value": 0})


@app.route('/classlist/<int:tid>')
def listclass(tid):
    classs1 = []
    classs1.append(s.query(Classes).filter_by(teacher_id1=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id2=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id3=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id4=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id5=tid).all())

    return jsonify(cls=[i.serialize for j in classs1 for i in j])


@app.route('/studentlist/<int:cid>')
def studlist(cid):
    student = s.query(Student).filter_by(class_id=cid).all()
    return jsonify(std=[i.serialize for i in student])


@app.route("/attendance", methods=["POST"])
def update():
    data = request.json
    students = s.query(Student).filter_by(class_id=data['cid']).all()

    d = data['stu']
    l=0
    for j in d:
        i=students[l]
        i.total_classes = i.total_classes + 1
        k = i.id
        print str(k)
        if (int(j[str(i.id)])!=None and int(j[str(i.id)])==1):
            i.cls_attendend = i.cls_attendend + 1
        i.percentage = ((i.cls_attendend) * 100) / (i.total_classes)
        l+=1
    s.commit()
    st = s.query(Student).filter_by(class_id=data['cid']).all()
    return jsonify(s1=[i.serialize for i in st])


@app.route('/percentage/<int:percentages>')
def percentage(percentages):
    lists = s.query(Student).filter_by(percentage < percentages).all()
    return jsonify(st=[i.serialize for i in lists])


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0', port=2000)

import os
from flask import Flask, render_template, request, redirect, url_for, jsonify, flash
from sqlalchemy import create_engine,or_
from sqlalchemy.orm import sessionmaker
from project import Base, Teacher, Classes, Student


app = Flask(__name__)

engine = create_engine('sqlite:///attendancee.db' )
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
            if(result):
                id1=result.id
                return jsonify({"value":id1})
            else:

                return jsonify({"value":0})
        else:
            return jsonify({"value":0})

@app.route('/classlist/<int:tid>')
def listclass(tid):
    classs1=[]
    classs1.append(s.query(Classes).filter_by(teacher_id1=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id2=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id3=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id4=tid).all())
    classs1.append(s.query(Classes).filter_by(teacher_id5=tid).all())

    return jsonify(cls=[i.serialize for j in classs1 for i in j])
@app.route('/studentlist/<int:cid>')
def studlist(cid):
    student=s.query(Student).filter_by(class_id=cid).all()
    return jsonify(std=[i.serialize for i in student])

@app.route("/attendance/<int:sid>/<int:response>")
def update(sid,response):
    student=s.query(Student).filter_by(id=sid).first()
    student.total_classes=student.total_classes+1
    if(response):
          student.cls_attendend=student.cls_attendend+1
          s.add(student)
          s.commit
    return jsonify(student.serialize)


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0', port=2000)

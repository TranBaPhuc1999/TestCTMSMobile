package com.dmt.tranbaphuc1999.ctms;

public class Show_Score {
    private String __teacher, __subject;
    private int __tcNumber;
    private float __totalScore, __diligenceScore, __finalExaminationScore, __testScore;

    public String get__teacher() {
        return __teacher;
    }

    public void set__teacher(String __teacher) {
        this.__teacher = __teacher;
    }

    public String get__subject() {
        return __subject;
    }

    public void set__subject(String __subject) {
        this.__subject = __subject;
    }

    public int get__tcNumber() {
        return __tcNumber;
    }

    public void set__tcNumber(int __tcNumber) {
        this.__tcNumber = __tcNumber;
    }

    public float get__totalScore() {
        return __totalScore;
    }

    public void set__totalScore(float __totalScore) {
        this.__totalScore = __totalScore;
    }

    public float get__diligenceScore() {
        return __diligenceScore;
    }

    public void set__diligenceScore(float __diligenceScore) {
        this.__diligenceScore = __diligenceScore;
    }

    public float get__finalExaminationScore() {
        return __finalExaminationScore;
    }

    public void set__finalExaminationScore(float __finalExaminationScore) {
        this.__finalExaminationScore = __finalExaminationScore;
    }

    public float get__testScore() {
        return __testScore;
    }

    public void set__testScore(float __testScore) {
        this.__testScore = __testScore;
    }

    public Show_Score(String __subject,String __teacher,  int __tcNumber, float __diligenceScore, float __testScore, float __finalExaminationScore) {
        this.__teacher = __teacher;
        this.__subject = __subject;
        this.__tcNumber = __tcNumber;
        this.__diligenceScore = __diligenceScore;
        this.__finalExaminationScore = __finalExaminationScore;
        this.__testScore = __testScore;
        __totalScore = (float) (this.__diligenceScore*0.1+this.__testScore*0.2+this.__finalExaminationScore*0.7);
        __totalScore = (float) (Math.round(__totalScore*100.0)/100.0);
    }
}

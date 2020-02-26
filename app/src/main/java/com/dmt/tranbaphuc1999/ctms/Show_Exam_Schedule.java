package com.dmt.tranbaphuc1999.ctms;


public class Show_Exam_Schedule {
    private String __dateExam, __timeExam, __roomExam, __subjectExam, __codeExam;

    public Show_Exam_Schedule(String __dateExam, String __timeExam, String __roomExam, String __subjectExam, String __codeExam) {
        this.__dateExam = __dateExam;
        this.__timeExam = __timeExam;
        this.__roomExam = __roomExam;
        this.__subjectExam = __subjectExam;
        this.__codeExam = __codeExam;
    }

    public String get__dateExam() {
        return __dateExam;
    }

    public void set__dayExam(String __dateExam) {
        this.__dateExam = __dateExam;
    }

    public String get__timeExam() {
        return __timeExam;
    }

    public void set__timeExam(String __timeExam) {
        this.__timeExam = __timeExam;
    }

    public String get__roomExam() {
        return __roomExam;
    }

    public void set__roomExam(String __roomExam) {
        this.__roomExam = __roomExam;
    }

    public String get__subjectExam() {
        return __subjectExam;
    }

    public void set__subjectExam(String __subjectExam) {
        this.__subjectExam = __subjectExam;
    }

    public String get__codeExam() {
        return __codeExam;
    }

    public void set__codeExam(String __codeExam) {
        this.__codeExam = __codeExam;
    }
}

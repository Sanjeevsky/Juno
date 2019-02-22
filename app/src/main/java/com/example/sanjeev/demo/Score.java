package com.example.sanjeev.demo;

public class Score {

    public String time,date,result;

    public Score() {
    }

    public Score(String time, String date, String result) {
        this.time = time;
        this.date = date;
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

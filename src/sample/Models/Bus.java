package sample.Models;

import java.time.LocalDate;

public class Bus {
    private String number;
    private String date;
    private String time;
    //Назначение
    private String destination;
    private String beginStation;
    private String platform;
    private String endStation;
    private String cost;
    private String mark;
    private String takeTime;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getLocalDate() {
        if (date.contains("-")) {
            String[] temp = date.split("-");
            return LocalDate.of(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
        }
        else{
            String[] temp = date.split("[.]");
            return LocalDate.of(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
        }
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBeginStation() {
        return beginStation;
    }

    public void setBeginStation(String beginStation) {
        this.beginStation = beginStation;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }
}

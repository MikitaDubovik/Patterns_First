package sample.Models;

import java.time.LocalDate;

public class Train {
    private String number;
    private String beginDate;
    private String endDate;
    private String beginTime;
    private String endTime;
    //Назначение
    private String destination;
    private String beginStation;
    private String platform;
    private String endStation;
    private String type;
    private String cost;

    public LocalDate getStartLocalDate() {
        if (beginDate.contains(".")) {
            String[] temp = beginDate.split("[.]");
            return LocalDate.of(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
        }
        else {
            String[] temp = beginDate.split("-");
            return LocalDate.of(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
        }
    }

    public LocalDate getEndLocalDate() {
        if (endDate.contains(".")) {
            String[] temp = endDate.split("[.]");
            return LocalDate.of(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
        }
        else {
            String[] temp = endDate.split("-");
            return LocalDate.of(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0]));
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}

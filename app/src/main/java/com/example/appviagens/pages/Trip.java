package com.example.appviagens.pages;

public class Trip {
    public static Trip instance;
    private String name_driver;
    private String date_trip;
    private String hour_trip;
    private String passagers;
    private String origin;
    private String destiny;
    private String plate_car;
    private String observation;
    private String km_inc;
    private String km_fim;
    private int oil;
    private int Sttepe;
    private int Water;
    private int documents;
    private int Tire;

    private Trip(){}

    public static Trip getInstance(){
        if(instance == null){
            instance = new Trip();
        }
        return instance;
    }

    public String getName_driver() {
        return name_driver;
    }

    public void setName_driver(String name_driver) {
        this.name_driver = name_driver;
    }

    public String getDate_trip() {
        return date_trip;
    }

    public void setDate_trip(String date_trip) {
        this.date_trip = date_trip;
    }

    public String getHour_trip() {
        return hour_trip;
    }

    public void setHour_trip(String hour_trip) {
        this.hour_trip = hour_trip;
    }

    public String getPassagers() {
        return passagers;
    }

    public void setPassagers(String passagers) {
        this.passagers = passagers;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getPlate_car() {
        return plate_car;
    }

    public void setPlate_car(String plate_car) {
        this.plate_car = plate_car;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getKm_inc() {
        return km_inc;
    }

    public void setKm_inc(String km_inc) {
        this.km_inc = km_inc;
    }

    public String getKm_fim() {
        return km_fim;
    }

    public void setKm_fim(String km_fim) {
        this.km_fim = km_fim;
    }

    public int getOil() {
        return oil;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public int getSttepe() {
        return Sttepe;
    }

    public void setSttepe(int sttepe) {
        Sttepe = sttepe;
    }

    public int getWater() {
        return Water;
    }

    public void setWater(int water) {
        Water = water;
    }

    public int getDocuments() {
        return documents;
    }

    public void setDocuments(int documents) {
        this.documents = documents;
    }

    public int getTire() {
        return Tire;
    }

    public void setTire(int tire) {
        Tire = tire;
    }
}

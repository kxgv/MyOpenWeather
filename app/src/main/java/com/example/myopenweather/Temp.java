package com.example.myopenweather;

public class Temp {

    private String data;
    private String tempe;
    private String imatge;
    private String humidity;

    public Temp(String data, String tempe, String imatge,String humid) {
        this.data = data;
        this.tempe = tempe;
        this.imatge = imatge;
        this.humidity=humid;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getData() {
        return data;
    }

    public String getTempe() {
        return tempe;
    }

    public String getImatge() {
        return imatge;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTempe(String tempe) {
        this.tempe = tempe;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }
}
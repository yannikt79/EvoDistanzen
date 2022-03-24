package ch.kbw;


import javafx.scene.control.Label;

public class Cell extends Label {


    int wert;
    String stringwert;
    public Cell(){
        super();
    }

    public Cell(int wert){
        this.wert = wert;
    }

    public Cell(String stringwert){
        this.stringwert=stringwert;
    }

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        this.wert = wert;
    }

}

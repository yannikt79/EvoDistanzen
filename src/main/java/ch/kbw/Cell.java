package ch.kbw;


import javafx.scene.control.Label;

public class Cell extends Label {


    int wert;
    public Cell(){
        super();
    }

    public Cell(int wert){
        this.wert = wert;
    }

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        this.wert = wert;
    }

}

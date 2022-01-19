package ch.kbw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Controller {

    String x="ATGC";
    String y="CATA";

    public int[][] abc;



    Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    AnchorPane anchor;

    @FXML
    Pane panee;

    @FXML
    TextField field1;

    @FXML
    TextField field2;

    @FXML
    Button startbutton;

    GridPane grid = new GridPane();
    Cell[][] cells;


    @FXML
    protected void initialize(){

        panee.getChildren().add(grid);

        getit();
        getabc(abc);
        reset();



    }
    @FXML
    private void start(ActionEvent f){

        if (field1.getText().isEmpty()&& field2.getText().isEmpty()){
           field1.setText("Leer");
           field2.setText("Leer");
        } else if(field2.getText().isEmpty()){
            field2.setText("Leer");
        } else if (field1.getText().isEmpty()){
            field1.setText("Leer");
        }else if(field1.getText().equals(field2.getText())){
            System.out.println("Die DNA-Sequenzen sind identisch");
        }else if(field1.getText().equals("Leer")||field2.getText().equals("Leer")){

        }else if(field1.getText().equals("Leer")&&field2.getText().equals("Leer")){

        }
        else{
            reset();
            x = field1.getText();
            y = field2.getText();

            getit();
            getabc(abc);
        }


    }

    public String subStrong(String x, int pos1, int pos2){
        return x.substring(pos1, pos2);
    }

    public void getabc(int[][] abc){

        cells = new Cell[x.length()+1][y.length()+1];
        grid.setPrefSize((x.length()+1)*25,(y.length()+1)*25);
        grid.setAlignment(Pos.CENTER);
        for (int i=0;i<x.length()+1;i++){
            for (int j=0;j<y.length()+1;j++){


                cells[i][j]= new Cell(abc[i][j]);
                cells[i][j].setPrefHeight(25);
                cells[i][j].setPrefWidth(25);
                cells[i][j].setMaxHeight(25);
                cells[i][j].setMaxWidth(25);
                cells[i][j].setMinHeight(25);
                cells[i][j].setMinWidth(25);
                cells[i][j].setAlignment(Pos.CENTER);
                cells[i][j].setText("");
                cells[i][j].setText(Integer.toString(abc[i][j]));

                grid.add(cells[i][j],i,j,1,1);


            }

        }
        System.out.println("Fertig");
    }

    public void getit(){

        abc = new int[x.length()+1][y.length()+1];
        for (int i=0;i<x.length()+1;i++){
            for (int j=0;j<y.length()+1;j++){
                abc[i][j]= getcost(i,j);
            }
        }

    }

    public int getcost(int i, int j){

        if (i==0&&j==0){
            return abc[i][j]=0;
        } else if(i==0){
            return einfuegen(i,j);
        } else if(j==0){
            return streichen(i,j);
        } else{

            if(subStrong(x,i-1,i).equals(subStrong(y,j-1,j))){
                return abc[i-1][j-1];
            }

            int[] costs = {streichen(i,j),einfuegen(i,j),ersetzten(i,j)};
            int[] costs2 = {streichen(i,j),einfuegen(i,j),ersetzten(i,j)};

            if(getSmallest(costs)==costs2[0]){
                return costs2[0];
            }else if(getSmallest(costs)==costs2[1]){
                return costs2[1];
            }else if(getSmallest(costs)==costs2[2]){
                return costs2[2];
            }


        }
        System.out.println("DEBUG 1000");
        return 0;

    }

    public int streichen(int i, int j){

        return abc[i-1][j]+2;

    }

    public int einfuegen(int i, int j){

        return abc[i][j-1]+2;

    }

    public int ersetzten(int i, int j){

        return abc[i-1][j-1]+3;

    }

    public int getSmallest(int[] a){
        int temp;
        for (int i = 0; i < a.length; i++)
        {
            for (int j = i + 1; j < a.length; j++)
            {
                if (a[i] > a[j])
                {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a[0];
    }

    public void reset(){
        for (int i=0;i<x.length()+1;i++) {
            for (int j = 0; j < y.length() + 1; j++) {
                cells[i][j].setText("");


            }

        }

    }
}

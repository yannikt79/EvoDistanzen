package ch.kbw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Controller {

    String x="";
    String y="";

    public int[][] kosten;

    public int[][] kostenStriche;



    Model model;

    /***
     *
     * @param model
     */
    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    AnchorPane anchor;

    @FXML
    Pane panee;

    @FXML
    Pane ypanee;

    @FXML
    Pane xpanee;

    @FXML
    TextField field1;

    @FXML
    TextField field2;

    @FXML
    Button startbutton;

    GridPane grid = new GridPane();
    GridPane ygrid = new GridPane();
    GridPane xgrid = new GridPane();
    Cell[][] cells;
    Cell[][] ycells;
    Cell[][] xcells;


    /***
     *
     */
    @FXML
    protected void initialize(){

        panee.getChildren().add(grid);
        ypanee.getChildren().add(ygrid);
        xpanee.getChildren().add(xgrid);

        getit();
        getkosten();
        getsidebars();
        reset();



    }

    /***
     *
     * @param f
     * Button-Event -> Wenn der Start Button gedrückt wird, wird diese Methode aufgerufen.
     */
    @FXML
    private void start(ActionEvent f){

        //Überprüfen, ob eines der beiden oder beide keinen Wert enthält.
        if (field1.getText().isEmpty()&& field2.getText().isEmpty()){
           field1.setText("Leer");
           field2.setText("Leer");
        } else if(field2.getText().isEmpty()){
            field2.setText("Leer");
        } else if (field1.getText().isEmpty()){
            field1.setText("Leer");
        }else
            //Überprüft, ob die beiden Wert gleich sind.
            if(field1.getText().equals(field2.getText())){
            System.out.println("Die DNA-Sequenzen sind identisch");
        }else if(field1.getText().equals("Leer")||field2.getText().equals("Leer")){

        }else if(field1.getText().equals("Leer")&&field2.getText().equals("Leer")){

        }
            //Wenn keiner der obigen Fälle eintrifft, dann beginnt er damit, die Kosten zu ermitteln.
        else{
            reset();

            //Übergeben der eingegebenen DNA-Sequenzen an die Strings x und y
            x = field1.getText();
            y = field2.getText();

            getit();
            getkosten();
            getsidebars();
        }


    }

    public void getsidebars(){

        ycells = new Cell[y.length()*2+1][2];
        ygrid.setPrefSize((y.length()*2+1)*25,50);
        ygrid.setAlignment(Pos.CENTER);
        int p=0;
        for(int i=1;i<y.length()*2+1;i++){



            ycells[i][0]= new Cell(y.substring(p,p+1));
            ycells[i][0].setPrefHeight(25);
            ycells[i][0].setPrefWidth(25);
            ycells[i][0].setMaxHeight(25);
            ycells[i][0].setMaxWidth(25);
            ycells[i][0].setMinHeight(25);
            ycells[i][0].setMinWidth(25);
            ycells[i][0].setAlignment(Pos.CENTER);
            ycells[i][0].setText("");
            ycells[i][0].setText(y.substring(p,p+1));

            ygrid.add(ycells[i][0],i,0,1,1);

            p++;
            i++;

        }

        int p2=0;
        for(int i=1;i<y.length()*2+1;i++){
            if(p2==0){
                i=0;
            }


            ycells[i][1]= new Cell(Integer.toString(p2));
            ycells[i][1].setPrefHeight(25);
            ycells[i][1].setPrefWidth(25);
            ycells[i][1].setMaxHeight(25);
            ycells[i][1].setMaxWidth(25);
            ycells[i][1].setMinHeight(25);
            ycells[i][1].setMinWidth(25);
            ycells[i][1].setAlignment(Pos.CENTER);
            ycells[i][1].setText("");
            ycells[i][1].setText(Integer.toString(p2));

            ygrid.add(ycells[i][1],i,1,1,1);
            p2++;
            i++;
        }

        xcells = new Cell[2][x.length()*2+1];
        xgrid.setPrefSize(50,(x.length()*2+1)*25);
        xgrid.setAlignment(Pos.CENTER);
        int p3=0;
        for(int i=1;i<x.length()*2+1;i++){



            xcells[0][i]= new Cell(x.substring(p3,p3+1));
            xcells[0][i].setPrefHeight(25);
            xcells[0][i].setPrefWidth(25);
            xcells[0][i].setMaxHeight(25);
            xcells[0][i].setMaxWidth(25);
            xcells[0][i].setMinHeight(25);
            xcells[0][i].setMinWidth(25);
            xcells[0][i].setAlignment(Pos.CENTER);
            xcells[0][i].setText("");
            xcells[0][i].setText(x.substring(p3,p3+1));

            xgrid.add(xcells[0][i],0,i,1,1);
            p3++;
            i++;

        }

        int p4=0;
        for(int i=1;i<x.length()*2+1;i++){
            if(p4==0){
                i=0;
            }
            xcells[1][i]= new Cell(Integer.toString(p4));
            xcells[1][i].setPrefHeight(25);
            xcells[1][i].setPrefWidth(25);
            xcells[1][i].setMaxHeight(25);
            xcells[1][i].setMaxWidth(25);
            xcells[1][i].setMinHeight(25);
            xcells[1][i].setMinWidth(25);
            xcells[1][i].setAlignment(Pos.CENTER);
            xcells[1][i].setText("");
            xcells[1][i].setText(Integer.toString(p4));

            xgrid.add(xcells[1][i],1,i,1,1);
            p4++;
            i++;
        }
    }



    public void getkosten(){

        cells = new Cell[x.length()*2+2][y.length()*2+2];
        grid.setPrefSize((x.length()*2+2)*25,(y.length()*2+2)*25);
        grid.setAlignment(Pos.CENTER);
        int p1=0;
        int p2=0;
        for (int i=1;i<x.length()*2+1;i++){
            if(p1==0){
                i=0;
            }
            for (int j=1;j<y.length()*2+1;j++){
                if(p2==0){
                    j=0;
                }
               cells[i][j]= new Cell(kosten[p1][p2]);
               cells[i][j].setPrefHeight(25);
               cells[i][j].setPrefWidth(25);
               cells[i][j].setMaxHeight(25);
               cells[i][j].setMaxWidth(25);
               cells[i][j].setMinHeight(25);
               cells[i][j].setMinWidth(25);
               cells[i][j].setAlignment(Pos.CENTER);
               cells[i][j].setText("");
               cells[i][j].setText(Integer.toString(kosten[p1][p2]));
                grid.add(cells[i][j],i,j,1,1);


                p2++;
                j++;
            }
            p2=0;
            p1++;
            i++;
        }
        System.out.println("Done -- 257");
    }

    public void getkostenStriche(int pos1, int pos2){


        System.out.println("Done1 -- 291");
    }

    //Erstellt einen Zweidimensionalen Array, mit den Kosten für jede Mutation
    public void getit(){


        kosten = new int[x.length()+1][y.length()+1];
        for (int i=0;i<x.length()+1;i++){
            for (int j=0;j<y.length()+1;j++){
                kosten[i][j]= getcost(i,j);
            }
        }

    }


    //Ermittelt die Kosten für jede Mutation
    public int getcost(int i, int j){

        //Wenn i = 0 und j = 0 ist, gibt es logischerweise keine Kosten
        if (i==0&&j==0){
            return kosten[i][j]=0;
        } else
            //Wenn i = 0 ist, dann ist nur einfügen möglich
            if(i==0){
            return einfuegen(i,j);
        } else
            //Wenn j = 0 ist, dann ist nur streichen möglich
            if(j==0){
            return streichen(i,j);
        } else{

                //Wenn die Zeichen gleich sind, dann gibt es logischerweise keine weitern Kosten, als die bisherigen
            if(x.substring(i-1,i).equals(y.substring(j-1,j))){
                return kosten[i-1][j-1];
            }

            int[] costs = {streichen(i,j),einfuegen(i,j),ersetzten(i,j)};
            int[] costs2 = {streichen(i,j),einfuegen(i,j),ersetzten(i,j)};

            Arrays.sort(costs);

            //Überprüft, welche der drei Möglichkeiten die geringsten Kosten hat
            if(costs[0]==costs2[0]){
                return costs2[0];
            }else if(costs[0]==costs2[1]){
                return costs2[1];
            }else if(costs[0]==costs2[2]){
                return costs2[2];
            }


        }
            //Wenn dieser Debug in der Konsole erscheint, dann ist etwas nicht korrekt gelaufen.
        System.out.println("Something went wrong at ("+i+", "+j+")");
        return 0;

    }

    public int streichen(int i, int j){

        return kosten[i-1][j]+2;

    }

    public int einfuegen(int i, int j){

        return kosten[i][j-1]+2;

    }

    public int ersetzten(int i, int j){

        return kosten[i-1][j-1]+3;

    }


    //Funktion um den günstigsten der drei Wert zu ermitteln
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

    //Setzt einfach den Inhalt des GridPane zurück
    public void reset(){

        int p1=0;
        int p2=0;
        for (int i=1;i<x.length()*2+1;i++) {
            if(p1==0){
                i=0;
            }
            for (int j = 1; j < y.length()*2+1; j++) {
                if(p2==0){
                    j=0;
                }
               cells[i][j].setText("");
                j++;
                p2++;
            }
            i++;
            p1++;
            p2=0;
        }

        for(int i=1;i<x.length()*2+1;i++) {
            xcells[0][i].setText("");
            i++;
        }

        int p3=0;
        for(int i=1;i<x.length()*2+1;i++){
            if(p3==0){
                i=0;
            }
            xcells[1][i].setText("");
            i++;
            p3++;
        }

        for(int i=1;i<y.length()*2+1;i++) {
            ycells[i][0].setText("");
            i++;
        }

        int p4=0;
        for(int i=1;i<y.length()*2+1;i++) {
            if(p4==0){
                i=0;
            }
            ycells[i][1].setText("");
            i++;
            p4++;
        }
    }
}

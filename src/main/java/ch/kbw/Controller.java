package ch.kbw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.util.Arrays;

/***
 *
 * @author S. Sturzenegger
 * @author Y. Tobler
 * @version 1.0.1 - First full version finished in March 2022
 */
public class Controller {

    String x="";
    String y="";

    public String[][] kostenArray;



    Model model;

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


    //Input Field 1 for DNA-Sequenz a = x
    @FXML
    TextField field1;

    //Input Field 2 for DNA-Sequenz b = y
    @FXML
    TextField field2;

    @FXML
    Button startbutton;

    //Field for Errormessages
    @FXML
    Label errMsgField;

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


        getsidebars();
        displaykosten();
        reset();



    }

   //Diese Methode ist an den Startbutton gebindet.
    @FXML
    private void start(ActionEvent f){

        //Überprüfen, ob eines der beiden oder beide keinen Wert enthält.
        if (field1.getText().isEmpty()&& field2.getText().isEmpty()){
            reset();
            errMsgField.setText("Bitte geben Sie zwei DNA-Sequenz an.");
        } else if(field2.getText().isEmpty()){
            reset();
            errMsgField.setText("Bitte geben Sie zwei DNA-Sequenz an.");
        } else if (field1.getText().isEmpty()){
            reset();
            errMsgField.setText("Bitte geben Sie zwei DNA-Sequenz an.");
        }else
            //Überprüft, ob die beiden Wert gleich sind.
            if(field1.getText().equals(field2.getText())){
                reset();
                errMsgField.setText("Bitte geben Sie zwei unterschiedliche DNA-Sequenzen an.");
        }
            //Wenn keiner der obigen Fälle eintrifft, dann beginnt er damit, die Kosten zu ermitteln.
        else{
            //Zurücksetzten aller eingetragenen Werte
            reset();

            //Übergeben der eingegebenen DNA-Sequenzen an die Strings x und y
            x = field1.getText();
            y = field2.getText();


            getsidebars();
            fillKostenArray();
            displaykosten();
        }


    }

    public void getsidebars(){

        ycells = new Cell[y.length()*2+1][2];
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

    public void displaykosten(){

        cells = new Cell[y.length()*2+1][x.length()*2+1];
        grid.setAlignment(Pos.CENTER);
        int p1=0;
        int p2=0;
        for (int i=1;i<y.length()*2+1;i++){
            if(p1==0){
                i=0;
            }
            int k1= i;
            for (int j=1;j<x.length()*2+1;j++){
                if(p2==0){
                    j=0;
                }
                int k2=j;
                cells[i][j]= new Cell(kostenArray[k2][k1]);
                cells[i][j].setPrefHeight(25);
                cells[i][j].setPrefWidth(25);
                cells[i][j].setMaxHeight(25);
                cells[i][j].setMaxWidth(25);
                cells[i][j].setMinHeight(25);
                cells[i][j].setMinWidth(25);
                cells[i][j].setAlignment(Pos.CENTER);
                cells[i][j].setText("");
                cells[i][j].setText(kostenArray[k2][k1]);
                grid.add(cells[i][j],i,j,1,1);


                p2++;
            }
            p2=0;
            p1++;
        }
        p1=0;
    }
    
    public void fillKostenArray(){
        kostenArray = new String[x.length()*2+1][y.length()*2+1];
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
                
                kostenArray[i][j] = getcost(i,j,p1,p2);

                p2++;
                j++;
            }
            p2=0;
            p1++;
            i++;
        }
    }

    //Ermittelt die Kosten für jede Mutation und die dazugehörigen Striche
    public String getcost(int i, int j, int p1, int p2){

        //Wenn i = 0 und j = 0 ist, gibt es logischerweise keine Kosten
        if (i==0&&j==0){
            return kostenArray[i][j]="0";
        } else
            //Wenn i = 0 ist, dann ist nur einfügen möglich
            if(i==0){
                kostenArray[i][j-1]="-";
                return einfuegen(i,j);
            } else
                //Wenn j = 0 ist, dann ist nur streichen möglich
                if(j==0){
                    kostenArray[i-1][j]="|";
                    return streichen(i,j);
                } else{

                    //Wenn die Zeichen gleich sind, dann gibt es logischerweise keine weiteren Kosten, als die bisherigen
                    if(x.substring(p1-1,p1).equals(y.substring(p2-1,p2))){
                        kostenArray[i-1][j-1] = "\\";
                        return kostenArray[i-2][j-2];

                    }

                    int[] costs = {Integer.parseInt(streichen(i,j)),Integer.parseInt(einfuegen(i,j)),Integer.parseInt(ersetzten(i,j))};
                    int[] costs2 = {Integer.parseInt(streichen(i,j)),Integer.parseInt(einfuegen(i,j)),Integer.parseInt(ersetzten(i,j))};

                    Arrays.sort(costs);

                    //Überprüft, welche der drei Möglichkeiten die geringsten Kosten hat und ob andere gleichviel kosten
                    if(costs[0]==costs2[0]){
                        kostenArray[i-1][j]="|";
                        if(costs[0]==costs2[1]){
                            kostenArray[i][j-1]="-";
                        }
                        if (costs[0]==costs2[2]){
                            kostenArray[i-1][j-1]="\\";
                        }
                        return Integer.toString(costs2[0]);
                    }else if(costs[0]==costs2[1]){
                        if (costs[0]==costs2[2]){
                            kostenArray[i-1][j-1]="\\";
                        }
                        kostenArray[i][j-1]="-";
                        return Integer.toString(costs2[1]);
                    }else if(costs[0]==costs2[2]){
                        kostenArray[i-1][j-1]="\\";
                        return Integer.toString(costs2[2]);
                    }


                }
        //Wenn dieser Debug in der Konsole erscheint, dann ist etwas nicht korrekt gelaufen.
        System.out.println("Something went wrong at ("+i+", "+j+")");
        return null;

    }

    //Erste der drei Mutations-Möglichkeiten
    public String streichen(int i, int j){
        int p = Integer.parseInt(kostenArray[i-2][j])+2;

        return Integer.toString(p);

    }

    //Zweite der drei Mutations-Möglichkeiten
    public String einfuegen(int i, int j){
        int p = Integer.parseInt(kostenArray[i][j-2])+2;

        return Integer.toString(p);

    }

    //Dritte der drei Mutations-Möglichkeiten
    public String ersetzten(int i, int j){
        int p = Integer.parseInt(kostenArray[i-2][j-2])+3;

        return Integer.toString(p);

    }


    //Setzt alles auf Anfang zurück
    public void reset(){

        //Der Inhalt des errMsgField Labels wird auf "" gesetzt
        errMsgField.setText("");


        //Alle Werte im grossen GridPane (kostenGrid) werden auf "" gesetzt
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
               cells[j][i].setText("");
                p2++;
            }
            p1++;
            p2=0;
        }


        //Die Inhalte der Sidebars werden auf "" gesetzt
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

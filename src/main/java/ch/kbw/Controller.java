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

/*
 *
 * @author S. Sturzenegger
 * @author Y. Tobler
 * @version 1.0.1 - First full version finished in March 2022
 */
public class Controller {

    String x="";
    String y="";

    public String[][] costArray;



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


    //InputField 1 for DNA sequence a = x
    @FXML
    TextField field1;

    //InputField 2 for DNA sequence b = y
    @FXML
    TextField field2;

    @FXML
    Button startbutton;

    //Field for errormessages
    @FXML
    Label errMsgField;

    GridPane grid = new GridPane();
    GridPane ygrid = new GridPane();
    GridPane xgrid = new GridPane();
    Cell[][] cells;
    Cell[][] ycells;
    Cell[][] xcells;


    @FXML
    protected void initialize(){

        panee.getChildren().add(grid);
        ypanee.getChildren().add(ygrid);
        xpanee.getChildren().add(xgrid);


        getsidebars();
        displayCosts();
        reset();




    }

   //This method is binded to the startButton.
    @FXML
    private void start(ActionEvent f){

        //Resets all already entered values
        reset();


        //Checks, if both fields are empty
        if (field1.getText().isEmpty()&& field2.getText().isEmpty()){
            setErrorMessage("Invalid Input! Please only enter: \nA(Adenin), C(Cytosin), G(Guanin) or T(Thymin) in capital letters!\nBoth fields must contain at least one of those letters!");
        } else
            //Checks, if one of the two fields is empty
            if(field2.getText().isEmpty() || field1.getText().isEmpty()){
                setErrorMessage("Invalid Input! Please only enter: \nA(Adenin), C(Cytosin), G(Guanin) or T(Thymin) in capital letters!\nBoth fields must contain at least one of those letters!");

            } else
            //Checks, if the content of the two fields is the same
            if(field1.getText().equals(field2.getText())){
                setErrorMessage("Invalid Input! Please only enter: \nA(Adenin), C(Cytosin), G(Guanin) or T(Thymin) in capital letters!\nContent of both fields can't be the same!");


            } else

            if (correctString()){
                setErrorMessage("Invalid Input! Please only enter: \nA(Adenin), C(Cytosin), G(Guanin) or T(Thymin) in capital letters!");
            }
            //If none of the above cases apply, then the determination of the costs begins
        else{

            //Transferring the entered DNA sequences to the strings x and y
            x = field1.getText();
            y = field2.getText();


            getsidebars();
            fillCostArray();
            displayCosts();
            solveMaze();
            printMaze();
        }


    }

    public boolean correctString(){
        String[] xStringSplit = field1.getText().split("");
        String[] yStringSplit = field2.getText().split("");

        for (int i=0; i < xStringSplit.length;i++){
            if (!xStringSplit[i].contains("A")&& !xStringSplit[i].contains("C")&& !xStringSplit[i].contains("G")&& !xStringSplit[i].contains("T")){
                return true;
            }
        }

        for (int i=0; i < yStringSplit.length;i++){
            if (!yStringSplit[i].contains("A")&& !yStringSplit[i].contains("C")&& !yStringSplit[i].contains("G")&& !yStringSplit[i].contains("T")){
                return true;
            }
        }
        return false;
    }

    public void setErrorMessage(String content){
        StringBuilder msg = new StringBuilder();
        msg.append(content);
        errMsgField.setText(msg.toString());
        errMsgField.setMinHeight(25.0*content.split("\n").length);
    }
    //This method fills in the panes xpanee and ypanee with the descriptions
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

    //fills the content of the costArray into the cells Array and puts every cells content to a grid cell
    public void displayCosts(){

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
                cells[i][j]= new Cell(costArray[k2][k1]);
                cells[i][j].setPrefHeight(25);
                cells[i][j].setPrefWidth(25);
                cells[i][j].setMaxHeight(25);
                cells[i][j].setMaxWidth(25);
                cells[i][j].setMinHeight(25);
                cells[i][j].setMinWidth(25);
                cells[i][j].setAlignment(Pos.CENTER);
                cells[i][j].setText("");
                cells[i][j].setText(costArray[k2][k1]);
                grid.add(cells[i][j],i,j,1,1);


                p2++;
            }
            p2=0;
            p1++;
        }
        p1=0;
    }

    //This method fills the, with the getcost method calculated, costs and the specific lines
    public void fillCostArray(){
        costArray = new String[x.length()*2+1][y.length()*2+1];
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
                
                costArray[i][j] = getcost(i,j,p1,p2);

                p2++;
                j++;
            }
            p2=0;
            p1++;
            i++;
        }
    }

    //Calculates the costs for every mutation and the related lines
    public String getcost(int i, int j, int p1, int p2){

        //If i = 0 and j = 0, logicaly there are no costs for that mutation.
        if (i==0&&j==0){
            return costArray[i][j]="0";
        } else
            //If i = 0, only the mutation paste is possible
            if(i==0){
                costArray[i][j-1]="-";
                return einfuegen(i,j);
            } else
                //If j = 0, only the mutation strike is possible
                if(j==0){
                    costArray[i-1][j]="|";
                    return streichen(i,j);
                } else{

                    //If the character is even, logicaly there are no more costs, except the current
                    if(x.substring(p1-1,p1).equals(y.substring(p2-1,p2))){
                        costArray[i-1][j-1] = "\\";
                        return costArray[i-2][j-2];

                    }

                    int[] costs = {Integer.parseInt(streichen(i,j)),Integer.parseInt(einfuegen(i,j)),Integer.parseInt(ersetzten(i,j))};
                    int[] costs2 = {Integer.parseInt(streichen(i,j)),Integer.parseInt(einfuegen(i,j)),Integer.parseInt(ersetzten(i,j))};

                    Arrays.sort(costs);

                    //Checks, which one of the three possible mutations has the lowest costs or if there is more than one mutation with the lowest costs
                    if(costs[0]==costs2[0]){
                        costArray[i-1][j]="|";
                        if(costs[0]==costs2[1]){
                            costArray[i][j-1]="-";
                        }
                        if (costs[0]==costs2[2]){
                            costArray[i-1][j-1]="\\";
                        }
                        return Integer.toString(costs2[0]);
                    }else if(costs[0]==costs2[1]){
                        if (costs[0]==costs2[2]){
                            costArray[i-1][j-1]="\\";
                        }
                        costArray[i][j-1]="-";
                        return Integer.toString(costs2[1]);
                    }else if(costs[0]==costs2[2]){
                        costArray[i-1][j-1]="\\";
                        return Integer.toString(costs2[2]);
                    }


                }
        //If this Message gets printed in the console, something must have gone wrong. Because logicaly, this string can't get printed
        System.out.println("Something went wrong at ("+i+", "+j+")");
        return null;

    }

    //The first one of the three possible mutations
    public String streichen(int i, int j){
        int p = Integer.parseInt(costArray[i-2][j])+2;

        return Integer.toString(p);

    }

    //The second one of the three possible mutations
    public String einfuegen(int i, int j){
        int p = Integer.parseInt(costArray[i][j-2])+2;

        return Integer.toString(p);

    }

    //The third one of the three possible mutations
    public String ersetzten(int i, int j){
        int p = Integer.parseInt(costArray[i-2][j-2])+3;

        return Integer.toString(p);

    }


    //Resets everything
    public void reset(){

        //The content of the errMsgField label is set to "".
        errMsgField.setText("");


        //Every value of the big GridPane (grid) is set to "".
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


        //The contents of the sidebars is set to "".
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








    int a = x.length()*2+1;
    int b = y.length()*2+1;
    boolean[][] maze;
    boolean[][] wasHere;
    boolean[][] correctPath;
    int startX=0,starty=0;
    int endX=x.length(),endY=y.length();

    public void solveMaze(){
        maze = new boolean[x.length()*2+1][y.length()*2+1];
        wasHere = new boolean[x.length()*2+1][y.length()*2+1];
        correctPath = new boolean[x.length()*2+1][y.length()*2+1];

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
                if (costArray[i][j]==null || costArray[i][j]==""|| costArray[i][j]==" "){
                    maze[i][j]=true;
                } else{
                    maze[i][j]=false;
                }
                p2++;
            }
            p1++;
            p2=0;
        }


        for (int row=0;row<maze.length;row++){
            for (int col=0;col<maze[row].length;col++){
                wasHere[row][col]=false;
                correctPath[row][col]=false;
            }
        }
        boolean b=recursiveSolve(startX, starty);




    }

    public boolean recursiveSolve(int x, int y){
        if (x==endX&&y==endY) return true;

        if (maze[x][y] || wasHere[x][y]) return false;

        wasHere[x][y]=true;

        if (x!=0){
            if (recursiveSolve(x-1,y)){
                correctPath[x][y]=true;
                return true;
            }
        }
        if (x!=y-1){
            if (recursiveSolve(x+1,y)){
                correctPath[x][y] = true;
                return true;
            }
        }
        if (y!=0){
            if (recursiveSolve(x, y-1)){
                correctPath[x][y]=true;
                return true;
            }
        }
        if (y!=x-1){
            if (recursiveSolve(x,y+1)){
                correctPath[x][y]=true;
                return true;
            }
        }
        return false;
    }


    public void printMaze(){

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
                if (maze[i][j]){
                    System.out.print("X");
                } else{
                    System.out.print("Y");
                }
                System.out.print("\t");
                p2++;
            }
            System.out.print("\n");
            p1++;
            p2=0;
        }

    }


}

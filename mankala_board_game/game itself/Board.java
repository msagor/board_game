
package mancala_kalah;


public class Board {
    
    int p1_kalah;
    int p2_kalah;
    int pit_size;
    int pits[];
    
    
    public Board(){}
    
    
    public Board(int x, int y){
        
        //initialize the board at the initial state
        p1_kalah = 0;
        p2_kalah = 0;
        pit_size=x*2;
        pits= new int[pit_size];
        for(int i =0;i<pits.length;i++){
            pits[i] = y;
        }
    
    }
    
    
    public int get_p1_kalah(){
        return p1_kalah;
    }
    
    public int get_p2_kalah(){
        return p2_kalah;
    }
    
    public void increase_p1_kalah(){
        p1_kalah= p1_kalah + 1;
    }
    
     public void increase_p1_kalah(int x){
        p1_kalah= p1_kalah + x;
    }
    
    
    public void increase_p2_kalah(){
        p2_kalah= p2_kalah + 1;
    }
    
   
    public void increase_p2_kalah(int x){
        p2_kalah= p2_kalah + x;
    }
    
    
    
}

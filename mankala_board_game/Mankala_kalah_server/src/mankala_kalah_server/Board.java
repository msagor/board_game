
package mankala_kalah_server;


public class Board implements Cloneable{
    
    int utility;
    int board_validity;
    int p1_kalah;
    int p2_kalah;
    int pit_size;
    int pits[];
    
    
    public Board(){}
    
    public Board(int x){
        
    }
    
    
    public Board(int x, int y){
        
        //initialize the board at the initial state
        utility =0;
        board_validity =1; // 1= valid board //0 = invalid board
        p1_kalah = 0;
        p2_kalah = 0;
        pit_size=x*2;
        pits= new int[pit_size];
        for(int i =0;i<pits.length;i++){
            pits[i] = y;
        }
        
        //ai testing board states here...delete after use
        
    
    }
    
    public Board(Board b){
        pits = new int[b.pits.length];
        pit_size = pits.length;
        p1_kalah = b.get_p1_kalah();
        p2_kalah = b.get_p2_kalah();
        utility = b.get_utility();
        board_validity = b.get_validity();
        
        for(int i =0;i<b.pits.length;i++){
            pits[i] = b.pits[i];
        }
    }
    
    
    
    public Board clonee(Board b){
        Board b1 = new Board(b);
        return b1;
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
    
    public void set_validity(int num){
        board_validity = num;
    }
    
    public int get_validity(){
        return board_validity;
    }
    
    public void set_utility(int num){
        utility = num;
    }
    
    public int get_utility(){
        return utility;
    }
    
    
    
    public void p1_print_board(Board b){
        String x ="";
        System.out.print("  ");
        for(int i =b.pits.length-1;i>=b.pits.length/2;i--){
            x = x+ "  "+b.pits[i]+"  ";
        }
        System.out.println(x);
        System.out.print("  ");
        System.out.println();
        
        
        System.out.print(b.get_p2_kalah());
        for(int i =0;i<x.length();i++){
            System.out.print(" ");
        }
        System.out.print("  "+b.get_p1_kalah());
        System.out.println();
       
        
        
        System.out.println();
        System.out.print("  ");
        for(int i =0;i<b.pits.length/2;i++){
            System.out.print("  "+b.pits[i]+"  ");
        }
        System.out.print("  ");
        System.out.println();
        
    }
    
    public void p2_print_board(Board b){
        String x ="";
        System.out.print("  ");
        for(int i =b.pits.length/2-1;i>=0;i--){
            x = x+ "  "+b.pits[i]+"  ";
        }
        System.out.println(x);
        System.out.print("  ");
        System.out.println();
        
        
        System.out.print(b.get_p1_kalah());
        for(int i =0;i<x.length();i++){
            System.out.print(" ");
        }
        System.out.print("  "+b.get_p2_kalah());
        System.out.println();
       
        
        
        System.out.println();
        System.out.print("  ");
        for(int i =b.pits.length/2;i<b.pits.length;i++){
            System.out.print("  "+b.pits[i]+"  ");
        }
        System.out.print("  ");
        System.out.println();
    }
    
    
    public void clear(){
        for(int i =0;i<pits.length;i++){
            pits[i] = 0;
        }
        p1_kalah =0;
        p2_kalah = 0;
    }
    
    
    protected Object clone() throws CloneNotSupportedException { //here
            return super.clone();
        }
    
    
    
    
}

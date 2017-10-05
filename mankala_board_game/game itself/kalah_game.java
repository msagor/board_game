
package mancala_kalah;
import static java.lang.Math.abs;
import java.util.Scanner;
import java.util.*;



public class kalah_game {
    public static void main(String[] args) {
        System.out.println("Welcome!!!");
        //board must be (even, any) form;
        Board b =new Board(6,4);
        start(b);
        //print_board(b);
    }
    
    
    
    
    
    //game starts from here
    public static void start(Board b){
        print_board(b);
        p1_moves(b);  //awyays starts with p1
    }
    
    
    
    
    
    
    
    //calling this function makes changes in pit[] for p1
    public static void p1_moves(Board b){
        int pit_num = input(1, b);
        make_a_move(b, 1, pit_num);
    }
    
    
    
    
    
    //calling this function makes changes in pit[] for p2 
    public static void p2_moves(Board b){
        int pit_num = input(2, b);
        make_a_move(b, 2, pit_num);
    }
    
    
    
    
    //calling this functions gets player input to make a move
    // player = 1; brings changes to the player1 side
    //player = 2; brings changes to the player 2 side
    public static void make_a_move(Board b, int player, int pit_num){
        Move move = new Move();
        Checks check = new Checks();
        if(player == 1){
            int second_move = check.check_for_second_move_kalah(b, 1, pit_num); 
            int reg_kalah = check.check_for_regular_kalah(b, 1, pit_num);
            //System.out.println(second_move+"  " + reg_kalah); //delete 
            System.out.println();
            System.out.println();
            System.out.println();
            
            //second_move
            if( reg_kalah == 0 && second_move==1){
                move.make_second_move(b, player, pit_num);
                if(check.check_for_out_of_seed(b)!=0){
                    move.make_finishing_move(b);
                    winner(b);
                }
                print_board(b);  
                System.out.println("Youve earned another move.");
                p1_moves(b);  
                
            }
            
            //reg_kalah
            else if(second_move ==0 && reg_kalah == 1 ){
                move.make_regular_kalah(b, player, pit_num);
                if(check.check_for_out_of_seed(b)!=0){
                    move.make_finishing_move(b);
                    winner(b);
                }
                print_board(b); 
                p2_moves(b);
            }
            
            
            //reg_move
            else if(second_move==0 && reg_kalah ==0){
                move.make_reg_move(b, player, pit_num);
                if(check.check_for_out_of_seed(b)!=0){
                    move.make_finishing_move(b);
                    winner(b);
                }
                print_board(b); 
                p2_moves(b);  
            }
            
        }
        
        else if(player == 2){
            int second_move =check.check_for_second_move_kalah(b, 2, pit_num); 
            int reg_kalah = check.check_for_regular_kalah(b, 2, pit_num);
            //System.out.println(second_move+"  "+reg_kalah); //delete 
            System.out.println();
            System.out.println();
            System.out.println();
        
        
            //second move
            if(reg_kalah == 0 && second_move==1){
                move.make_second_move(b, player, pit_num);
                if(check.check_for_out_of_seed(b)!=0){
                    move.make_finishing_move(b);
                    winner(b);
                }
                print_board(b);
                System.out.println("Youve earned another move.");
                p2_moves(b);
            }
            
            //reg_kalah
            else if(second_move ==0 && reg_kalah ==1){
                move.make_regular_kalah(b, player, pit_num);
                if(check.check_for_out_of_seed(b)!=0){
                    move.make_finishing_move(b);
                    winner(b);
                }
                print_board(b); 
                p1_moves(b); 
            } 
            
            //reg move
            else if(second_move ==0 && reg_kalah ==0){
                move.make_reg_move(b, player, pit_num);
                if(check.check_for_out_of_seed(b)!=0){
                    move.make_finishing_move(b);
                    winner(b);
                }
                print_board(b);
                p1_moves(b);
            }
        }
    }
    
    
    public static void print_board(Board b){
        System.out.println("     "+b.pits[11]+"  "+b.pits[10]+"  "+b.pits[9]+"  "+b.pits[8]+"  "+b.pits[7]+"  "+b.pits[6]+"     ");
        System.out.println("  "+b.get_p2_kalah()+"  "+"                "+"  "+b.get_p1_kalah()+"  ");
        System.out.println("     "+b.pits[0]+"  "+b.pits[1]+"  "+b.pits[2]+"  "+b.pits[3]+"  "+b.pits[4]+"  "+b.pits[5]+"     ");
        
    }
    
    public static int input(int player,Board b){
        Scanner console = new Scanner( System.in );
        System.out.print("Input move for player"+ player +": ");
        int input = console.nextInt();
        if(b.pits[input]==0){
            System.out.println("Invalid move. The pit is empty.Try again.");
            input(1,b);
        }
        if(player ==1){
            if(input<0 || input >=b.pits.length/2){
                System.out.println("Invalid pit selection. Try again.");
                input(1,b);    
            }
        }
        if(player ==2 && (input<b.pits.length/2 || input>=b.pits.length)){
            System.out.println("Invalid pit selection. Try again.");
            input(2,b); 
        }
        return input;
    }
    
    public static void winner(Board b){
        if(b.get_p1_kalah()>b.get_p2_kalah()){
            System.out.println("Player 1 wins!!!");
        }
        
        else if(b.get_p1_kalah()<b.get_p2_kalah()){
            System.out.println("Player 2 wins!!!");
        }
        
        else{
            System.out.println("Its a draw!!!");
        }
        System.exit(0);
    }
    
   
    
}


//They dont wanna care about us


package mankala_kalah_server;

import java.util.Random;
import java.util.Scanner;


public class kalah_game {
    
    
    public kalah_game(Board b, String random_dist){
        if(random_dist.equals("R")  ){
            System.out.println("inside random dist");
            random_distribution(b);
        }
        
    }
    
    
      //calling this function makes changes in pit[] for p1
    public int p1_moves(Board b, int pit_num){
        return make_a_move(b, 1, pit_num);
    }
    
    
    
    
    
    //calling this function makes changes in pit[] for p2 
    public int p2_moves(Board b, int pit_num){
        return make_a_move(b, 2, pit_num);
    }
    
    
    
    
    //calling this functions gets player input to make a move
    // player = 1; brings changes to the player1 side
    //player = 2; brings changes to the player 2 side
    public int make_a_move(Board b, int player, int pit_num){
        Move move = new Move();
        Checks check = new Checks();
        if(player == 1){
            int second_move = check.check_for_second_move_kalah(b, 1, pit_num); 
            int reg_kalah = check.check_for_regular_kalah(b, 1, pit_num);
            
            //second_move
            if( reg_kalah == 0 && second_move==1){
                move.make_second_move(b, player, pit_num);
                return 1;
            }
            
            //reg_kalah
            else if(second_move ==0 && reg_kalah == 1 ){
                move.make_regular_kalah(b, player, pit_num);
                return 0;
            }
            
            
            //reg_move
            else if(second_move==0 && reg_kalah ==0){
                move.make_reg_move(b, player, pit_num);
                return 0;
            }
        }
        
        
        else if(player == 2){
            int second_move =check.check_for_second_move_kalah(b, 2, pit_num); 
            int reg_kalah = check.check_for_regular_kalah(b, 2, pit_num);
            
            //second move
            if(reg_kalah == 0 && second_move==1){
                move.make_second_move(b, player, pit_num);
                return 1;
            }
            
            //reg_kalah
            else if(second_move ==0 && reg_kalah ==1){
                move.make_regular_kalah(b, player, pit_num);
                return 0;
            } 
            
            //reg move
            else if(second_move ==0 && reg_kalah ==0){
                move.make_reg_move(b, player, pit_num);
                return 0;
            }
        }
        
        return 0;
    }
    
    
    public void print_board(Board b){
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
    
    
    public void random_distribution(Board b){
        Random ran = new Random();
        int total_pit_length = b.pits.length;
        int player_side_length = total_pit_length /2;
        int total_seeds=0;
        
        //compying out the pits
        for(int i =0;i<total_pit_length;i++){
            total_seeds = total_seeds + b.pits[i];
        }
        
        //emptying out the pits
        for(int i =0;i<total_pit_length;i++){
            b.pits[i]=0;
        } 

        
        
        //initializing the pits by at least one seed
        for(int i =0;i<total_pit_length;i++){
            b.pits[i]=1;
            total_seeds = total_seeds -1 ;
        }
        
        
        int total_seeds_half = total_seeds/2;
        
        //random dist on one side
        for(int i =0;i<player_side_length;i++){
             int x =0;
             x = ran.nextInt(total_seeds_half) + 0;
             b.pits[i] = b.pits[i]+ x;
             total_seeds_half = total_seeds_half - x;
             
        }
        
        
        
        //compying one side at another
        for(int i =player_side_length;i<total_pit_length;i++){
            b.pits[i]=b.pits[i-player_side_length];
        }
        
        
    }
    
    public void board_flip(Board b){
        for(int i =0;i<b.pits.length/2;i++){
            int temp = b.pits[i];
            b.pits[i] = 0;
            b.pits[i] = b.pits[i+(b.pits.length/2)];
            b.pits[i+(b.pits.length/2)] = temp;
        }
    }
   
    
}


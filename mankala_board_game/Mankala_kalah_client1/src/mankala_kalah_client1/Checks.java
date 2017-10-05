/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mankala_kalah_client1;

public class Checks {
    
    public Checks(){}
    
    
    
    
    //identifies if making this move will give a second_move
    //return = 0 means no second move
    //retun = 1 means second move
    public int check_for_second_move_kalah(Board b, int player, int pit_num){
        if(player == 1){
            int total_pit_length = b.pits.length;
            int length_of_player_side = b.pits.length/2;
            int modd = b.pits[pit_num] % total_pit_length;
            int divv = b.pits[pit_num] / total_pit_length;
            int remaining = modd-divv;
            if(remaining == (length_of_player_side - pit_num)){
                return 1;
            }
            
            else{
                return 0;
            }
        }
        
        else if(player == 2){
            int total_pit_length = b.pits.length;
            int length_of_player_side = b.pits.length/2;
            int modd = b.pits[pit_num] % total_pit_length;
            int divv = b.pits[pit_num] / total_pit_length;
            int remaining = modd-divv;
            if(remaining == total_pit_length - pit_num ){
                return 1;
            }
            
            else{
                
                return 0; 
            }   
        }
        
        else{
            return 0;
        }
    }
    
    
    //check if the move can make a regular kalah without gainging a second move
    //retun: 1 =yes, 0 = no
    public int check_for_regular_kalah(Board b, int player, int pit_num){
        int length_of_player_side = b.pits.length/2;
        int length_of_pits = b.pits.length;
        if(player==1){
            if(check_for_second_move_kalah(b, 1, pit_num) == 0 && (b.pits[pit_num] > (length_of_player_side - pit_num))){
                return 1;
            }
            
            else{
                return 0;
            }
        }
        
        else if(player==2){
            if(check_for_second_move_kalah(b, 2, pit_num) == 0 && (b.pits[pit_num] > (length_of_pits - pit_num))){
                return 1;
            }
            
            else{
                return 0;
            }
        }
        
        else{
            return 0;
        }
    }
    
    
    //check if this move qualifies for a a overkill
    //return : 1= yes, 0 = no
    public int check_for_overkill(Board b, int player, int pit_num, int finishing_pit){
        int length_of_player_side = b.pits.length/2;
        int length_of_pits = b.pits.length;
        
        if(player == 1){
            if(finishing_pit >= length_of_player_side){
                return 0; //finishing pit at oppo side
            }
        }
        
        if(player == 2){
            if(finishing_pit >= length_of_pits || finishing_pit < length_of_player_side){
                return 0; //finishing pit at oppo side
            }
        }
        
        if(b.pits[finishing_pit]!=1){
            return 0; //finishing pit was not empty
        }
            
        if(b.pits[length_of_pits-1-finishing_pit]!=0){
            return 1; //overkill approved
        }
            
        else if(b.pits[length_of_pits-1-finishing_pit]==0){
            return 0; //oppo pit is empty
        }
        
        else{
            return 0;
        }
    }
    
    //check if the game is over. 
    //returns: 0 = no one is out of seed, 1 = p1 out of seed, 2 = p2 out of seed. 3 = both out of seeds
    public int check_for_out_of_seed(Board b){
       int length_of_player_side = b.pits.length/2;
       int counter1=0;
       int counter2=0;
       
       for(int i =0; i<length_of_player_side ;i++){
           if(b.pits[i]==0){
               counter1++;
             
           }
        }
       for(int i = length_of_player_side; i<b.pits.length;i++ ){
           if(b.pits[i]==0){
               counter2++;
           }
       }
       
       if(counter1!=length_of_player_side && counter2 ==length_of_player_side){
           return 2; //p2 out of seed 
       }
       
       else if(counter1==length_of_player_side && counter2 !=length_of_player_side){
           return 1; //p1 out of seed
       }
       
       else if(counter1==length_of_player_side && counter2==length_of_player_side){
           return 3; //both player out of seed
       }
       
       else {
           return 0; //no one is out of seed
       }
    }
    
}


package mancala_kalah;


public class Move {
 
    public Move(){}
    
    
    public void simulation_move(Board b, int player, int pit_num){
        int total_pit_length = b.pits.length;
        int length_of_player_side = b.pits.length/2;
        if(player ==1 || player == 2){
            int seeds = b.pits[pit_num];
            b.pits[pit_num]=0;
            int finishing_pit = pit_num + seeds;
            for(int i = pit_num+1; i<=finishing_pit;i++){
                b.pits[i] = b.pits[i] + 1;
            }
        }
    }
    
    public void make_second_move(Board b, int player, int pit_num){
        int total_pit_length = b.pits.length;
        int length_of_player_side = b.pits.length/2;
        int divv = b.pits[pit_num] / total_pit_length;
        int modd = b.pits[pit_num] % total_pit_length;
        int seeds_remaining = b.pits[pit_num] - (divv+1);
        int div = seeds_remaining / total_pit_length;
        int mod = seeds_remaining % total_pit_length;
        b.pits[pit_num]=0;
        for(int i =0; i<total_pit_length; i++){ 
            b.pits[i] = b.pits[i] + div;
        }
        
        if(player==1){
            b.increase_p1_kalah(divv+1);
            for(int i = pit_num +1; i<length_of_player_side; i++){
                b.pits[i] = b.pits[i] + 1;
            }
        }
        
        else if(player ==2){
            b.increase_p2_kalah(divv+1);
            for(int i = pit_num +1; i<total_pit_length; i++){
                b.pits[i] = b.pits[i] + 1;
            }
        }
    }
    
    public void make_regular_kalah(Board b, int player, int pit_num){
        Checks check = new Checks();
        int total_pit_length = b.pits.length;
        int length_of_player_side = b.pits.length/2;
        int finishing_pit=0;
        int seeds = b.pits[pit_num];
        int temp_seeds = b.pits[pit_num];
        b.pits[pit_num] = 0;
        if(player ==1){
            for(int i = pit_num; i <(temp_seeds+pit_num); i++){
                if(seeds>0){
                    if((i % total_pit_length) == length_of_player_side-1 && seeds >0){
                        b.increase_p1_kalah();
                        seeds--;
                    }
                    if(seeds>0){
                        b.pits[(i+1)% total_pit_length]++;
                        seeds--;
                        finishing_pit = (i+1)% total_pit_length;
                    }
                }
            }
        }
        
        if(player ==2){
            for(int i = pit_num; i <(temp_seeds+pit_num); i++){
                if(seeds>0){
                    if((i % total_pit_length) == total_pit_length-1 && seeds >0){
                        b.increase_p2_kalah();
                        seeds--;
                    }
                    if(seeds>0){
                        b.pits[(i+1)% total_pit_length]++;
                        seeds--;
                        finishing_pit = (i+1)% total_pit_length;
                    }
                }
            }
        }
        
        //check for overkill
        int overkill=0;
        if(player ==1){
            overkill =check.check_for_overkill(b, 1, pit_num,finishing_pit);
        }
        if(player ==2){
            overkill =check.check_for_overkill(b, 2, pit_num,finishing_pit);
        }
        if(overkill ==1){
            int all = b.pits[total_pit_length-1-finishing_pit] + b.pits[finishing_pit];
            b.pits[total_pit_length-1-finishing_pit] =0;
            b.pits[finishing_pit] = 0;
            if(player ==1){
                b.increase_p1_kalah(all);
            }
            if(player ==2){
                b.increase_p2_kalah(all);
            }
        }
    }
    
    public void make_reg_move(Board b, int player, int pit_num){
        Checks check = new Checks();
        int total_pit_length = b.pits.length;
        int length_of_player_side = b.pits.length/2;
        if(player ==1 || player == 2){
            int seeds = b.pits[pit_num];
            b.pits[pit_num]=0;
            int finishing_pit = pit_num + seeds;
            for(int i = pit_num+1; i<=finishing_pit;i++){
                b.pits[i] = b.pits[i] + 1;
            }
        
            //check for overkill
            int overkill=0;
            if(player ==1){
                overkill =check.check_for_overkill(b, 1, pit_num,finishing_pit);
            }
            if(player ==2){
                overkill =check.check_for_overkill(b, 2, pit_num,finishing_pit);
            }
            if(overkill ==1){
                int all = b.pits[total_pit_length-1-finishing_pit] + b.pits[finishing_pit];
                b.pits[total_pit_length-1-finishing_pit] =0;
                b.pits[finishing_pit] = 0;
                if(player ==1){
                    b.increase_p1_kalah(all);
                }
                if(player ==2){
                    b.increase_p2_kalah(all);
                }
            }
        }
    }
    
    
    
    
    //this move is made only when one of the player do not have any seeds, 
    //so the game concludes by capturing all the remainign seeds.
    public void make_finishing_move(Board b){
        int total_pit_length = b.pits.length;
        int length_of_player_side = b.pits.length/2;
        
        int p1=0;
        for(int i = 0;i<length_of_player_side;i++){
            p1 = p1+ b.pits[i];
            b.pits[i] = 0;
        }
        b.increase_p1_kalah(p1);
        
        int p2=0;
        for(int i =length_of_player_side; i<total_pit_length; i++){
            p2 = p2+b.pits[i];
            b.pits[i] = 0;
        }
        b.increase_p2_kalah(p2);
    }
    
    
    
    
}


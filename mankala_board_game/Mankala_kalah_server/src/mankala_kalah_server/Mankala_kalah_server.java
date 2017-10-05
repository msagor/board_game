
package mankala_kalah_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import static java.lang.System.out;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;



public class Mankala_kalah_server {

    public static void main(String[] args) throws IOException {
    
        
        //server says Welcome on its own screen after start
        System.out.println("Welcome!!!"); 
        System.out.print("The server just started.");  
        System.out.println();   
        
        //server asks user to pick setting
        Scanner ssc = new Scanner(System.in);
        System.out.print("Input the number of pits: ");
        String pits = ssc.nextLine();
        System.out.print("Input the number of seeds: ");
        String seeds = ssc.nextLine();
        System.out.print("Input the time limit in miliseconds: ");
        String time = ssc.nextLine();
        System.out.print("select if the client1 will be first player or the second player: ");
        String client1_first_or_second_player = ssc.nextLine();
        System.out.print("Select if the game has regular or random distribution:  ");
        String random_dist = ssc.nextLine();
        System.out.println();
        System.out.print("Is it a Singleplayer or Multiplayer game: ");
        String game_type = ssc.nextLine();
        System.out.println();
        
        
        /*
        String pits = "6";
        String seeds = "1";
        String time = "500000000";
        String client1_first_or_second_player = "S";
        String random_dist = "S";
        String game_type = "M";
        */
        
        String info_1="";
        String info_2="";
        
        if(client1_first_or_second_player.equals("F")){  //client1 as first one to move
            info_1 = "INFO "+ pits+" "+ seeds+" "+ time+ " "+ client1_first_or_second_player +" "+ random_dist;
            info_2 = "INFO "+ pits+" "+ seeds+" "+ time+ " "+ "S"+ " "+ random_dist;
            }
        
        else if(client1_first_or_second_player.equals("S")){ //client1 as second one to move
            info_1 = "INFO "+ pits+" "+ seeds+" "+ time+ " "+ client1_first_or_second_player + " "+ random_dist;
            info_2 = "INFO "+ pits+" "+ seeds+" "+ time+ " "+ "F" +" "+ random_dist;
            
        }
        
        
        //server waits until client1 is connected
        System.out.println();
        System.out.println("The server is waiting for a client to connect.");
        ServerSocket listener1 = new ServerSocket(1342);
        Socket c1 =listener1.accept();
        System.out.println("Server received Client1 connection!!");
        PrintStream p1 = new PrintStream(c1.getOutputStream());
        p1.println("WELCOME");
        
        //if its singleplayer AKA client1 against AI
        if(game_type.equals("S")){
            //client1 vs ai
            System.out.println("AI has not been implemented here ...see the other project.");
            System.exit(0);
            
            
        }
        
        //if its  multiplayer AKA client1 vs client2
        else if(game_type.equals("M")){
            //client1 vs client2
            System.out.println("The server is waiting for another client to connect.");
            ServerSocket listener2 = new ServerSocket(1343);
            Socket c2 =listener2.accept();
            System.out.println("Server received Client2 connection!!");
            PrintStream p2 = new PrintStream(c2.getOutputStream());
            p2.println("WELCOME");
            System.out.println();
            System.out.println("The game is about to begin");
            
            multiplayer(info_1,info_2,game_type, listener1,listener2,c1,c2);
            
        }
    }
    
    
    public static void multiplayer(String info_1, String info_2,String game_type, ServerSocket listener1, ServerSocket listener2, Socket c1, Socket c2) throws IOException{
        
        PrintStream p1 = new PrintStream(c1.getOutputStream());
        PrintStream p2 = new PrintStream(c2.getOutputStream());
        
        Scanner sc1 = new Scanner(c1.getInputStream());
        Scanner sc2 = new Scanner(c2.getInputStream());
        
        //pits
        int pits = Character.getNumericValue(info_1.charAt(5));
        
        //seeds
        String seeds_str="";
        for(int i =7;i<info_1.length();i++){
            String temp="";
            temp=temp+info_1.charAt(i);
            if(temp.compareTo(" ")!=0){
                seeds_str =seeds_str+temp;
            }
            if(temp.compareTo(" ")==0){
                break;
            }
        }
        int seeds = Integer.parseInt(seeds_str);
        
        //time
         String time_str="";
        for(int i =(6+seeds_str.length()+2);i<info_1.length();i++){
            String temp="";
            temp=temp+info_1.charAt(i);
            if(temp.compareTo(" ")!=0){
                time_str =time_str+temp;
            }
            if(temp.compareTo(" ")==0){
                break;
            }
        }
        int time = Integer.parseInt(time_str);
        
        
        //client1_first_or_second_player
        String client1_first_or_second_player="";
        client1_first_or_second_player= client1_first_or_second_player+info_1.charAt(6+seeds_str.length()+1+time_str.length()+2);
        
        
        //random_dist
        String random_dist="";
        random_dist  = random_dist+ info_1.charAt(6+1+seeds_str.length()+1+time_str.length()+3);
        
        
        
        //server creates its own instance of the game/board
        Board board = new Board(pits,seeds);
        kalah_game game = new kalah_game(board, random_dist);
        
        
        
        
        
        //if ranfom_dist == R then info_1 and info_2 also changed  in INTO H S TTTT F R 6 6 6 6 6 6
        if(random_dist.equals("R")){
           for(int i =0;i<board.pits.length/2;i++){
                int temp =board.pits[i];
                String tmp = Integer.toString(temp);
                info_1 = info_1+" "+tmp;
            }
        
            for(int i = board.pits.length/2;i<board.pits.length;i++){
                int temp = board.pits[i];
                String tmp = Integer.toString(temp);
                info_2 =info_2+" "+tmp;
            } 
        }
        
        
        
        //server sends the INFO to the clients and receives Ready
        System.out.println("Server sending INFO to the client1");
         p1.println(info_1);
        String ready1 = sc1.nextLine();
        if(ready1.equals("READY")){
            System.out.println("Client1 is ready");
        }
        System.out.println();
        
        System.out.println("Server sending INFO to the client2");
        p2.println(info_2);
        String ready2 = sc2.nextLine();
        if(ready2.equals("READY")){
            System.out.println("Client2 is ready");
        }
        System.out.println();
        
        //server sends BEGIN to the clients
        System.out.println("Server sending begin to both clients");
        System.out.println();
        p1.println("BEGIN");
        p2.println("BEGIN");
        
        //the game is about to begin for realll.....
        
        //server calls client1 to make the first move
        if(client1_first_or_second_player.equals("F")){
            c1_call(game, board,time,listener1,listener2,c1,c2);
        }
        
        //server calls client2 to make the first move
        else if(client1_first_or_second_player.equals("S")){
            c2_call(game, board,time,listener1,listener2,c1,c2);
        }
        
    }
    
    
    
    
    public static void c1_call(kalah_game game, Board board,int time,ServerSocket listener1, ServerSocket listener2, Socket c1, Socket c2) throws IOException{
        PrintStream p1 = new PrintStream(c1.getOutputStream());
        PrintStream p2 = new PrintStream(c2.getOutputStream());
        
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(c1.getInputStream());
        Scanner sc2 = new Scanner(c2.getInputStream());
        Checks check = new Checks();
        Move move = new Move();
        
        
        
        //server waits for client response = MOVE, OK or P
        Timer timer = new Timer();
                timer.schedule( new TimerTask(){
                public void run() {
                    out.println(" Time out. No input within time from client1!");
                     p1.println("TIME");
                     p2.println("WINNER");
                     System.out.println("Game over!! server shuts down");
                     //System.exit(0);
                }
                }, time );
        
        board.p1_print_board(board);  
        System.out.println();
        System.out.println("Server waiting for client1 to respond");
        String res = sc1.nextLine();
        if(res.equals("P") || res.equals("OK") || 
                res.charAt(0)=='1' || res.charAt(0)=='2' || res.charAt(0)=='3' || 
                res.charAt(0)=='4' || res.charAt(0)=='5' || res.charAt(0)=='6' ||
                res.charAt(0)=='7' || res.charAt(0)=='8' || res.charAt(0)=='9' ){
            timer.cancel();
        }
        
        
        
        //client1 sends a pie move
        //server - updates its board/sends client2 P/sends client1 OK to move again /server waits to receive
        if(res.equals("P")){
            
            System.out.println("Server receives client1 pie move request.");
            System.out.println();
            game.board_flip(board);  //flip the board
            System.out.println();
            p1.println("OK");
            p2.println("P");
            c1_call(game, board,time, listener1,listener2,c1,c2);
        }
        
        //client1 sends OK
        //server - waits to receive
        if(res.equals("OK")){
            c1_call(game, board,time, listener1,listener2,c1,c2);
        }
        
        //client1 sends moves
        //server - updates its board / sends ok to client1 /calls client2 function by sending those update moves
        else{
            
            
            //converting the moves from string into int
            Vector<Integer> c1_moves  = new Vector<Integer>();
            String[] num_arr = res.split(" ");
            for (int i = 0; i < num_arr.length; i++) {
                int num_int = Integer.parseInt(num_arr[i]);
                c1_moves.add(num_int);
            }
            
            //server prints the moves
            System.out.println();
            for(int i =0;i<c1_moves.size();i++){
                System.out.println("Server receives client1 moves.");
                System.out.print(c1_moves.elementAt(i));
                System.out.println();
            }
            
            //check if any move was illegal
            for(int i =0;i<c1_moves.size();i++){
                if(c1_moves.elementAt(i)<0 || c1_moves.elementAt(i)>=board.pits.length/2){
                    p1.println("ILLEGAL");
                    p2.println("WINNER");
                    System.out.println("Game over!! server shuts down");
                    //System.exit(0);
                }
                
                if(board.pits[c1_moves.elementAt(i)]==0){
                    p1.println("ILLEGAL");
                    p2.println("WINNER");
                    System.out.println("Game over!! server shuts down");
                    //System.exit(0);
                }
            }
        
            //server updates its board and checks in every move if the game is over
            for(int i =0;i<c1_moves.size();i++){
                int ret = game.make_a_move(board,1,c1_moves.elementAt(i));
                int game_over = check.check_for_out_of_seed(board); //check for game over
                if(game_over ==1 || game_over ==2 || game_over == 3){  //p1 out of seed
                    move.make_finishing_move(board);
                    winner(p1,p2,board);
                }
                
            }
            
            //server sends OK to client1 saying it received the moves
            p1.println("OK");
            
            //server sends the moves to the client2
            p2.println(res);
            c2_call(game, board,time,listener1,listener2,c1,c2);
        }
    }
    
    public static void c2_call(kalah_game game, Board board,int time,ServerSocket listener1, ServerSocket listener2, Socket c1, Socket c2)throws IOException{
        PrintStream p1 = new PrintStream(c1.getOutputStream());
        PrintStream p2 = new PrintStream(c2.getOutputStream());
        
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(c1.getInputStream());
        Scanner sc2 = new Scanner(c2.getInputStream());
        Checks check = new Checks();
        Move move = new Move();
        
        
        
        //server waits for client response = MOVE, OK or P
        Timer timer = new Timer();
                timer.schedule( new TimerTask(){
                public void run() {
                    out.println(" Time out. No input within time from client2!");
                     p2.println("TIME");
                     p1.println("WINNER");
                     System.out.println("Game over!! server shuts down");
                     //System.exit(0);
                }
                }, time );
        
        board.p1_print_board(board);
        System.out.println();
        System.out.println("Server waiting for client2 to respond");
        String res = sc2.nextLine();
        if(res.equals("P") || res.equals("OK") || 
                res.charAt(0)=='1' || res.charAt(0)=='2' || res.charAt(0)=='3' || 
                res.charAt(0)=='4' || res.charAt(0)=='5' || res.charAt(0)=='6' ||
                res.charAt(0)=='7' || res.charAt(0)=='8' || res.charAt(0)=='9' ){
            timer.cancel();
        }
        
        
        
        //client2 sends a pie move
        //server - updates its board/sends client1 P/sends client2 OK to move again /server waits to receive
        if(res.equals("P")){
            
            System.out.println("Server receives client2 pie move request.");
            System.out.println();
            game.board_flip(board);  //flip the board
            System.out.println();
            p2.println("OK");
            p1.println("P");
            c2_call(game, board,time, listener1,listener2,c1,c2);
        }
        
        //client2 sends OK
        //server - waits to receive
        if(res.equals("OK")){
            c2_call(game, board,time, listener1,listener2,c1,c2);
        }
        
        //client2 sends moves
        //server - updates its board / sends ok to client2 /calls client1 function by sending those update moves
        else{
            
            //converting the moves from string into int
            Vector<Integer> c2_moves  = new Vector<Integer>();
            String[] num_arr = res.split(" ");
            for (int i = 0; i < num_arr.length; i++) {
                int num_int = Integer.parseInt(num_arr[i]);
                c2_moves.add(num_int);
            }
            
            
            //server prints the moves
            System.out.println();
            for(int i =0;i<c2_moves.size();i++){
                System.out.println("Server receives client1 moves.");
                System.out.print(c2_moves.elementAt(i));
                System.out.println();
            }
            
            //check if any move was illegal
            for(int i =0;i<c2_moves.size();i++){
                if(c2_moves.elementAt(i)<board.pits.length/2 || c2_moves.elementAt(i)>=board.pits.length){
                    p2.println("ILLEGAL");
                    p1.println("WINNER");
                    System.out.println("Game over!! server shuts down");
                    //System.exit(0);
                }
            }
        
            //server updates its board and checks in every move if the game is over
            for(int i =0;i<c2_moves.size();i++){
                int ret = game.make_a_move(board,2,c2_moves.elementAt(i));
                int game_over = check.check_for_out_of_seed(board); //check for game over
                if(game_over ==1 || game_over ==2 || game_over == 3){  //p1 out of seed
                    move.make_finishing_move(board);
                    winner(p1,p2,board);
                }
            }
            
            //server sends OK to client2 saying it received the moves
            p2.println("OK");
            
            
            //server sends the moves to the client1
            p1.println(res);
            c1_call(game, board,time,listener1,listener2,c1,c2);
        }
    }
    
    public static void winner(PrintStream p1, PrintStream p2, Board board){
        if(board.get_p1_kalah()>board.get_p2_kalah()){
            p1.println("WINNER");
            p2.println("LOSER");
            System.out.println("Game over!! server shuts down");
            //System.exit(0);
        }
                    
        else if(board.get_p1_kalah()<board.get_p2_kalah()){
            p1.println("LOSER");
            p2.println("WINNER");
            System.out.println("Game over!! server shuts down");
            //System.exit(0);
        }
                    
        else{
            p1.println("TIE");
            p2.println("TIE");
            System.out.println("Game over!! server shuts down");
            //System.exit(0);
        }
    }
    
    
    
    


} 
        
        

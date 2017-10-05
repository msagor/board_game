
package mankala_kalah_client1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Mankala_kalah_client1 {
    public static void main(String[] args) throws IOException {
       
        
        Scanner ss= new Scanner(System.in);
        System.out.println();
        System.out.print("Enter the IP address(localhost): ");
        String ip = ss.nextLine();
        System.out.println();
        System.out.print("Enter the port no(1342): ");
        int port = ss.nextInt();
        
     
       
        BufferedReader in;
        PrintWriter out;
        
        Socket sock = new Socket("localhost", 1342);
        System.out.println("C-C: Client1 is connected!!!!");
        System.out.println();
        in = new BufferedReader(new InputStreamReader(
            sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
        
        
        //client receiving WELCOME
        String mes = in.readLine();
        if(mes.equals("WELCOME")){
           System.out.print("Client received "+mes);  
           System.out.println();
        }
        
        //client1 receiving info and parses it out
        String info = in.readLine();
        System.out.println("Client received info: "+info); 
        
        //pits
        int pits = Character.getNumericValue(info.charAt(5));
        
        //seeds
        String seeds_str="";
        for(int i =7;i<info.length();i++){
            String temp="";
            temp=temp+info.charAt(i);
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
        for(int i =(6+seeds_str.length()+2);i<info.length();i++){
            String temp="";
            temp=temp+info.charAt(i);
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
        client1_first_or_second_player= client1_first_or_second_player+info.charAt(6+seeds_str.length()+1+time_str.length()+2);
        
        //random_dist
        String random_dist="";
        random_dist  = random_dist+ info.charAt(6+1+seeds_str.length()+1+time_str.length()+3);
        
        
        
        //client creates its game/board
        Board board = new Board(pits,seeds);
        
        if(random_dist.equals("S")){
            kalah_game game = new kalah_game(board, random_dist); 
            
            //client sends READY upon receiving INFO after creation of the board
            out.println("READY");
            System.out.println("client sent READY");
            looping_and_heeping(board,game,client1_first_or_second_player,time, in,out);
        }
        
        else if(random_dist.equals("R")){
           
           kalah_game game = new kalah_game(board, random_dist);
           
           Vector<String> numbers_str = new Vector<String>();
           Vector<Integer> numbers = new Vector<Integer>();
           
           String temp="";
           if(random_dist.equals("R")){
                for(int i =(4+2+1+seeds_str.length()+1+time_str.length()+1+1+1+2);i<info.length();i++){
                    temp = temp+ info.charAt(i);
                }
            }
            String[] num_arr = temp.split(" ");
            for (int i = 0; i < num_arr.length; i++) {
                int num_int = Integer.parseInt(num_arr[i]);
                numbers.add(num_int);
            }
            for(int i =0;i<numbers.size();i++){
                System.out.println(numbers.elementAt(i));
            }
            
            for(int i =0;i<board.pits.length/2;i++){
                board.pits[i] = numbers.elementAt(i);
                board.pits[i+ board.pits.length/2] = numbers.elementAt(i);
            }
            
            //client sends READY upon receiving INFO after creation of the board
            out.println("READY");
            System.out.println("client sent READY");
            looping_and_heeping(board,game,client1_first_or_second_player,time, in,out);
        }
    }
    
    public static void looping_and_heeping(Board board, kalah_game game,String client1_first_or_second_player, int time, BufferedReader in,PrintWriter out) throws IOException{
        
        //client is taking input from the server MOVE OK P ILLEGAL WINNER-LOSER-TIE
        String res = in.readLine();
        //board.p1_print_board(board);
        System.out.println();
        
        if(res.equals("BEGIN")){
            System.out.println("client received BEGIN ");
            System.out.println();
            if(client1_first_or_second_player.equals("F")){  //client1 is first player
                //asks user for IO input as many moves needed within time
                Vector<Integer> c1_moves_int = new Vector<Integer>();
            
                int ret =1;    
                while(ret ==1){
                    String movess = input(time, board);      //take user move as string
                    int moves = Integer.parseInt(movess);
                    c1_moves_int.add(moves);  //store it in vector
                    ret = game.make_a_move(board, 1, moves);
                    if(ret == 1){
                        System.out.println("You earned a second move");
                        System.out.println();
                    }  
                }
            
                //convert the moves from int to str
                String fina = "";
                for(int i =0;i<c1_moves_int.size();i++){
                    String temp ="";
                    temp = temp + Integer.toString(c1_moves_int.elementAt(i))+" ";
                    fina= fina + temp;
                }
                String moves_to_be_sent_to_server="";
                for(int i =0;i<fina.length()-1;i++){
                    moves_to_be_sent_to_server = moves_to_be_sent_to_server + fina.charAt(i);
                }
                //send those moves to the server
                out.println(moves_to_be_sent_to_server);
            
                //call this funtion again
                looping_and_heeping(board,game,client1_first_or_second_player,time,in,out);
            }
              
            else if(client1_first_or_second_player.equals("S")){   //client1 is second player
                //call this funtion again
                looping_and_heeping(board,game,client1_first_or_second_player,time,in,out);
            }
        }
        
        //server sent OK
        //client - calls this function again
        else if(res.equals("OK")){
            System.out.println();
            System.out.println("Server sent OK.");
            looping_and_heeping(board,game,client1_first_or_second_player,time, in,out);
        }
        
        
        //server sends P
        //client - flips its board /  calls this function again
        else if(res.equals("P")){
            game.board_flip(board);
            System.out.println();
            System.out.println("Server sent P. Flipped the board.");
            looping_and_heeping(board,game,client1_first_or_second_player,time, in,out);
        }
        
        
       //SERVER SENDS ILLEGAL to client
       //client - declares that it lost and finishes the program execution
        else if(res.equals("ILLEGAL")){
            System.out.println();
            System.out.println("Server sent ILLEGAL. GAME OVER and You Lost.");
            System.exit(0);
        }
        
        
        //server sends WINNER - LOSER - TIE
        //client - declares decision and finishes the program
        else if(res.equals("WINNER")){
            System.out.println();
            System.out.println("Server sent WINNER. GAME OVER and You Won.");
            System.exit(0);
        }
        else if(res.equals("LOSER")){
            System.out.println();
            System.out.println("Server sent LOSER. GAME OVER and You Lost.");
            System.exit(0);
        }
        else if(res.equals("TIE")){
            System.out.println();
            System.out.println("Server sent TIE. GAME OVER and its a Tie..");
            System.exit(0);
        }
        
        //server sends move
        //client - parses the moves in int/ updates the baord / sends OK to server 
         // asks user to IO input as many moves needed within time/ sends those moves by conv in Str /
         //calls this funtion again
        else{
            
            //parses the moves in int
            Vector<Integer> server_moves = new Vector<Integer>();
            String[] num_arr = res.split(" ");
            for (int i = 0; i < num_arr.length; i++) {
                int num_int = Integer.parseInt(num_arr[i]);
                server_moves.add(num_int);
            }
            
            //updates the board
            for(int i =0;i<server_moves.size();i++){
                int ret = game.make_a_move(board, 2, server_moves.elementAt(i));
            }
            
            System.out.println("Client1 receives server moves and updated its board");
            board.p1_print_board(board);
            System.out.println();
            
            //sends OK to server
            out.println("OK");
            
            client_makes_move(res, board,game,client1_first_or_second_player,time, in,out);
        }  
    }
    
    public static void client_makes_move(String res, Board board, kalah_game game,String client1_first_or_second_player, int time, BufferedReader in,PrintWriter out) throws IOException{
            
            //asks user for IO input as many moves needed within time
            Vector<Integer> c1_moves_int = new Vector<Integer>();
            
            Timer timer = new Timer();
                timer.schedule( new TimerTask(){
                public void run() {
                    String mes;
                    try {
                        mes = in.readLine();
                        if(mes.equals("TIME")){
                            System.out.println("Client1 timed out and Lost");  
                            System.exit(0);
                        }
                    } catch (IOException ex) {}
                    
                    
                }
                }, time - 1000 );
            int ret =1;    
            while(ret ==1){
                String moves = input(time, board);      //take user move
                if(moves.equals("P")){
                   ret = 0;
                   game.board_flip(board);
                   out.println(moves);
                   String pie_ret = in.readLine();
                   if(pie_ret.equals("OK")){
                        timer.cancel();
                        client_makes_move(res, board,game,client1_first_or_second_player,time, in,out);
                   }
                }
                else{
                    int value = Integer.parseInt(moves);
                    c1_moves_int.add(value);            //store it in vector
                    ret = game.make_a_move(board, 1, value);
                }
                
                if(ret == 1){
                     System.out.println("You earned a second move");
                     System.out.println();
                }  
                
                if(ret ==0){
                   timer.cancel();
                }
            }
            
            
            //convert the moves from int to str
            String fina = "";
            for(int i =0;i<c1_moves_int.size();i++){
                String temp ="";
                temp = temp + Integer.toString(c1_moves_int.elementAt(i))+" ";
                fina= fina + temp;
            }
            String moves_to_be_sent_to_server="";
            for(int i =0;i<fina.length()-1;i++){
                moves_to_be_sent_to_server = moves_to_be_sent_to_server + fina.charAt(i);
            }
            
            //send those moves to the server
            out.println(moves_to_be_sent_to_server);
            
            //call this funtion again
            looping_and_heeping(board,game,client1_first_or_second_player,time,in,out);
    }
    
    public static String input(int time, Board board){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println();
        board.p1_print_board(board);  
        System.out.println();
        System.out.print("client1 please make a move: ");
        String input = sc.nextLine();
        return input;
    }
}

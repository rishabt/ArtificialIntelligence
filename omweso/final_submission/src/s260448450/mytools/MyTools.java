package s260448450.mytools;

import java.util.ArrayList;

import omweso.CCBoardState;
import omweso.CCMove;

public class MyTools {
	/*
	 * Assigning values to Heuristic function
	 */
	public static final int OP_GT_16 = 0;	// Opponent's seeds >16
	public static final int WIN = 1000;		// Game won
	public static final int OP_LT_16 = 100; // Opponent's seeds < 16
	public static final int CAPTURE = 50; 	// Capture move
	public static final int LOSS = -10;		// Loss of seeds
	public static final int SEEDS_LT_16 = -100; // Our seeds < 16
	public static final int NONE = 1;		//	None
	
	public static boolean hasWon = false;

    public static double getSomething(){
        return Math.random();
    }
   
    /*
     * Calculates heuristic values given initial and final board states
     */
    public static int calculateHeuristic(int[] myseeds_initial, int[] myseeds_final, CCBoardState bs, int play){
    	
    	int heuristic = 0;
    	
    	int[][] fin = bs.getBoard();
    	int[] myf;
    	int[] opf;
    	
    	if(play == 0){
    		myf = fin[0];
    		opf = fin[1];
    	}
    	else{
    		myf = fin[1];
    		opf = fin[0];
    	}
    	
    	int[] myi = myseeds_initial;
    	int[] opi = myseeds_final;
    	
    	int my_init = 0;
    	for(int i : myi){
    		my_init += i;
    	}
    	
    	int op_init = 0;
    	for(int i : opi){
    		op_init += i;
    	}
    	
    	int my_final = 0;
    	for(int i : myf){
    		my_final += i;
    	}
    	
    	int op_final = 0;
    	for(int i : opf){
    		op_final += i;
    	}
    	
    	if(op_final < 16){
    		heuristic = OP_LT_16;
    	}
    	else if(my_final < 16){
    		heuristic = SEEDS_LT_16;
    	}
    	else if(my_final - my_init < 0){
    		heuristic = LOSS;
    	}
    	else if(op_final - op_init < 0){
    		heuristic = CAPTURE;
    	}
    	else if(op_final > 16){
    		heuristic = OP_GT_16;
    	}
    	
    	return heuristic;
    }
    
    /*
     * Implementation of minimax algorithm with some optimization by cuttong off branches when a solutuion is found 
     * or if game runs into an infinite loop
     */
    public static int[] minimax(int level, int play, CCBoardState bs, int[] myseeds_initial, int[] opseeds_initial){
    	
    	// Variables to keep track of the best path using best_node and best_Score
    	int best_node = 0;
    	int curr_score = 0;
    	int best_score = (play == 0) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    	
    	ArrayList<CCMove> remaining_moves = bs.getLegalMoves();
    	if(level == 0 || remaining_moves.isEmpty()){
    		best_score = calculateHeuristic(myseeds_initial, opseeds_initial, bs, play);
    	}
    	else{
    		if(play == 0){		// If maximizing player
    			ArrayList<CCMove> moves = bs.getLegalMoves();

    			for(int i = 0; i < moves.size(); i++){		// Iterate within given legal moves
    				if(hasWon){
    					break;
    				}
    				CCMove m = moves.get(i);
    				int[][] state = bs.getBoard();
    				CCBoardState clone = (CCBoardState) bs.clone();	// Clone the board to analyze future moves
    				clone.move(m);
    				if(checkEdgeCase(clone.getBoard()[0])){			// Fix for corner case configuraion leading to infinite loops
    					return new int[] {1000, best_node};
    				}
    				if(bs.haveWon()){		// If we win in a future state return
    					best_node = i;
    					hasWon = true;
    					return new int[] {best_score, best_node};
    				}
    				//int next = (play == 0) ? 0 : 1;
    				curr_score = minimax(level - 1, 1, clone, state[0], state[1])[0];

    				if(curr_score > best_score){		// Maximize the heuristic score
    					best_score = curr_score;
    					best_node = i;
    					if(curr_score == 100){			// If max score found
    						break;
    					}
    				}
    			}
    		}
    		else{	// If minimizing player
    			ArrayList<CCMove> moves = bs.getLegalMoves();

    			for(int i = 0; i < moves.size(); i++){
    				if(hasWon){
    					break;
    				}
    				CCMove m = moves.get(i);
    				int[][] state = bs.getBoard();
    				CCBoardState clone = (CCBoardState) bs.clone();
    				clone.move(m);
    				
    				if(checkEdgeCase(clone.getBoard()[1])){
    					return new int[] {1000, best_node};
    				}
    				if(bs.haveLost()){
    					best_node = i;
    					hasWon = true;
    					return new int[] {best_score, best_node};
    				}
    				//int next = (play == 0) ? 0 : 1;
    				curr_score = minimax(level - 1, 0, clone, state[1], state[0])[0];

    				if(curr_score < best_score){		// Minimize the heuristic score
    					best_score = curr_score;
    					best_node = i;
    					if(curr_score == 0){			// If min score found
    						break;
    					}
    				}
    			}
    		}
    	}
    	    	
    	return new int[] {best_score, best_node};		// Return the array of best heuristic and best node to follow
    }
    
    /*
     * This method checks for the edge case in which the players run into 
     * infinite moves and cancel/timeout
     */
    public static boolean checkEdgeCase(int[] arr){
    	
    	int number_twos = 0;
    	int number_ones = 0;
    	
    	
    	for(int i : arr){
    		if(i == 2){
    			number_twos++;
    		}
    		else if(i == 1){
    			number_ones++;
    		}
    		else if(i != 0){
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    /*
     * This method was used while debugging the code to print out the legal moves
     */
    public static void printMoveList(ArrayList<CCMove> m){
    	
    	System.out.print("List of legal moves: " );
    	for(CCMove e : m){
    		System.out.print(e.getPit() + " ");
    	}
    	System.out.println();
    }
}

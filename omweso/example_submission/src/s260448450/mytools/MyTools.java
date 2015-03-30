package s260448450.mytools;

import java.util.ArrayList;

import omweso.CCBoardState;
import omweso.CCMove;
import s260448450.mytools.MiniMax;

public class MyTools {
	
	public static final int OP_GT_16 = 0;
	public static final int WIN = 1000;
	public static final int OP_LT_16 = 100; 
	public static final int CAPTURE = 50; 
	public static final int LOSS = -10;
	public static final int SEEDS_LT_16 = -100; 
	public static final int NONE = 1;

    public static double getSomething(){
        return Math.random();
    }
   
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
    
/*    public static int minimax(int level, int play, int[][] init_state, CCBoardState bs, CCBoardState moved){		// 0 -> computer, 1 -> opponent
    	
    	int best_node = 0;
    	int curr_score = 0;
    	int bestScore = (player == 0) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    	
    	ArrayList<CCMove> remaining_moves = bs.getLegalMoves();
    	if(level == 0 || remaining_moves.isEmpty()){
    		return calculate_heuristic(bs, moved);
    	}
    	
    	if(play == 0){
    		ArrayList<CCMove> moves = bs.getLegalMoves();
    		for(CCMove m : moves){
    			CCBoardState s = (CCBoardState) bs.clone();
    			curr_score = minimax(level - 1, 1, bs, moved);
    			if(curr_score > best_score){
    				best_node = m.getPit();
    			}
    		}
    	}
    	else{
    		ArrayList<CCMove> moves = bs.getLegalMoves();
    		for(CCMove m : moves){
    			CCBoardState s = (CCBoardState) bs.clone();
    			curr_score = minimax(level - 1, 0, bs, moved);
    			if(curr_score < best_score){
    				best_node = m.getPit();
    			}
    		}
    	}
    	
    	return best_node;
    }*/
    
    
    public static int[] minimax(int level, int play, CCBoardState bs, int[] myseeds_initial, int[] opseeds_initial){
    	
    	int best_score = 0;
    	int best_node = 0;
    	int curr_score = 0;
    	int bestScore = (play == 0) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    	
    	ArrayList<CCMove> remaining_moves = bs.getLegalMoves();
    	if(level == 0 || remaining_moves.isEmpty()){
    		best_score = calculateHeuristic(myseeds_initial, opseeds_initial, bs, play);
    	}
    	else{
    		if(play == 0){
    			ArrayList<CCMove> moves = bs.getLegalMoves();
    			for(int i = 0; i < moves.size(); i++){
    				CCMove m = moves.get(i);
    				int[][] state = bs.getBoard();
    				CCBoardState clone = (CCBoardState) bs.clone();
    				clone.move(m);
    				int next = (play == 0) ? 0 : 1;
    				curr_score = minimax(level - 1, next, clone, state[0], state[1])[0];
    				if(curr_score > best_score){
    					best_score = curr_score;
    					best_node = i;
    				}
    			}
    		}
    		else{
    			ArrayList<CCMove> moves = bs.getLegalMoves();
    			for(int i = 0; i < moves.size(); i++){
    				CCMove m = moves.get(i);
    				int[][] state = bs.getBoard();
    				CCBoardState clone = (CCBoardState) bs.clone();
    				clone.move(m);
    				int next = (play == 0) ? 0 : 1;
    				curr_score = minimax(level - 1, next, clone, state[1], state[0])[0];
    				if(curr_score < best_score){
    					best_score = curr_score;
    					best_node = i;
    				}
    			}
    		}
    	}
    	
    	return new int[] {best_score, best_node};
    }
}

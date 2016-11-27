package com.topdesk.cases.tictactoe.yoursolution;

import com.topdesk.cases.tictactoe.CellLocation;
import com.topdesk.cases.tictactoe.Consultant;
import com.topdesk.cases.tictactoe.GameBoard;
import com.topdesk.cases.tictactoe.CellState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@code Consultant} advises which move to make in an in-progress game of
 * Tic-tac-toe.
 * <p>
 * This consultant follows the strategy described in wikipedia : https://en.wikipedia.org/wiki/Tic-tac-toe#Strategy
 */
public class YourConsultant implements Consultant {

	@Override
	public CellLocation suggest(GameBoard gb) {
		// TODO Auto-generated method stub
		return FindBestStep(gb);
	}
        
        public CellState OtherPlayer(CellState np) {
            if (np == CellState.OCCUPIED_BY_X) return CellState.OCCUPIED_BY_O;
            else if (np == CellState.OCCUPIED_BY_O) return CellState.OCCUPIED_BY_X;
            return CellState.EMPTY;
        }
        
        public static final CellLocation[][] SAMELINE = {
            { CellLocation.BOTTOM_CENTRE, CellLocation.BOTTOM_LEFT, CellLocation.BOTTOM_RIGHT },
            { CellLocation.TOP_CENTRE, CellLocation.TOP_LEFT, CellLocation.TOP_RIGHT },
            { CellLocation.CENTRE_CENTRE, CellLocation.CENTRE_LEFT, CellLocation.CENTRE_RIGHT },
            { CellLocation.TOP_LEFT, CellLocation.CENTRE_CENTRE, CellLocation.BOTTOM_RIGHT },
            { CellLocation.TOP_RIGHT, CellLocation.CENTRE_CENTRE, CellLocation.BOTTOM_LEFT },
            { CellLocation.TOP_LEFT, CellLocation.CENTRE_LEFT, CellLocation.BOTTOM_LEFT },
            { CellLocation.TOP_CENTRE, CellLocation.CENTRE_CENTRE, CellLocation.BOTTOM_CENTRE },
            { CellLocation.TOP_RIGHT, CellLocation.CENTRE_RIGHT, CellLocation.BOTTOM_RIGHT }
        }; 
        
        public Map<CellLocation, Integer> Scores;
        
        public CellLocation FindBestStep(GameBoard gb) {
            Scores = new HashMap<CellLocation, Integer>();
            
            MGameBoard mgb = new MGameBoard(gb);
            CellState p = mgb.NextPlayer();
            MinMax(mgb, 0, 1, p);
            
            return CellLocation.BOTTOM_CENTRE;
        }
        
        public int MinMax(MGameBoard mgb, int depth, int turn, CellState p) {
            CellState winner = mgb.WhoIsWinner();
            if( winner == p )return 1;
            else if( winner == OtherPlayer(p) ) return -1;
            
            List<Integer> localScores = new ArrayList<>(); 
            for(CellLocation l : CellLocation.values()) {
                if( mgb.getCellState(l) == CellState.EMPTY ) { // try empty cells only
                    if ( turn == 1 ) { // original player
                        //mgb.setCellState(l, );
                    }
                    else if ( turn == 2 ) { // other player
                        
                    }
                }
            }
            
            if( localScores.size() == 0) return 0;
            else if( mgb.NextPlayer() == p ) return Collections.max(localScores);
            else return Collections.min(localScores);
            
        }
        
}
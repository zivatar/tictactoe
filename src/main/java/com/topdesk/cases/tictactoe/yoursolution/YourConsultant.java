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
	public CellLocation suggest(GameBoard gameBoard) {
		// TODO Auto-generated method stub
		return CellLocation.TOP_CENTRE;
	}
        
        public enum Player {
            X, 
            O,
            Empty;
        }
        
        public Player NextPlayer(GameBoard gameboard) {
            int numX = 0, numO = 0, numEmpty = 0;
            for(CellLocation l : CellLocation.values()) {
                switch(gameboard.getCellState(l)) {
                    case OCCUPIED_BY_X:
                        numX++;
                        break;
                    case OCCUPIED_BY_O:
                        numO++;
                        break;
                    default:
                        numEmpty++;
                        break;
                }
            }
            if ( numEmpty == 0 ) throw new IllegalStateException("There is no empty place");
            else if ( numX <= numO ) return Player.X;
            else return Player.O;
        }
        
        public Player OtherPlayer(Player np) {
            if (np == Player.X) return Player.O;
            return Player.X;
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
        
        public CellState CellStateFromPlayer(Player p) {
            if (p == Player.O) return CellState.OCCUPIED_BY_O;
            else if (p == Player.X) return CellState.OCCUPIED_BY_X;
            else return CellState.EMPTY;
        }
        
        public Player PlayerFromCellState(CellState cs) {
            if (cs == CellState.OCCUPIED_BY_O) return Player.O;
            else if (cs == CellState.OCCUPIED_BY_X) return Player.X;
            else return Player.Empty;
        }
        
        private Player WhoIsWinner(GameBoard gb) {
            for (CellLocation[] line : SAMELINE) {
                CellState[] l = { gb.getCellState(line[0]), gb.getCellState(line[1]), gb.getCellState(line[2]) };
                if (l[0] != CellState.EMPTY && l[0] == l[1] && l[0] == l[2]) {
                    return PlayerFromCellState(l[0]);
                }
            }
            return Player.Empty;
        }
        
        public Map<CellLocation, Integer> SCORE;
        
        public CellLocation FindBestStep(GameBoard gb) {
            SCORE = new HashMap<CellLocation, Integer>();
            Player p = NextPlayer(gb);
            MinMax(gb, p);
            
            return CellLocation.BOTTOM_CENTRE;
        }
        
        public int MinMax(GameBoard gb, Player p) {
            if( WhoIsWinner(gb) == p ) return 1;
            else if ( WhoIsWinner(gb) == OtherPlayer(p) ) return -1;
            
            List<Integer> scores = new ArrayList<>(); 
            for(CellLocation l : CellLocation.values()) {
                if( gb.getCellState(l) == CellState.EMPTY ) { // try empty cells only
                    
                }
            }
            
            if( scores.size() == 0) return 0;
            else if( p == NextPlayer(gb) ) return Collections.max(scores);
            else return Collections.min(scores);
            
        }
        
}
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
    
    /**
     * Stores the CellLocations which are considered as placed in the same line
     * (horizontally, vertically and diagonally)
     */
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
        
    /**
     * Stores the calculated scores of the cells
     */
    public Map<CellLocation, Integer> cellScores;

    /**
     * Suggests the next optimal step
     * @param gb
     *          a running game
     * @return the location of the next step
     * @throws IllegalStateException if the game is already won
     */
    @Override
    public CellLocation suggest(GameBoard gb) {
        MGameBoard mgb = new MGameBoard(gb);
        if ( mgb.whoIsWinner() == CellState.OCCUPIED_BY_O ) throw new IllegalStateException("Winner: O");
        else if ( mgb.whoIsWinner() == CellState.OCCUPIED_BY_X ) throw new IllegalStateException("Winner: X");
        else return findTheBestStep(mgb);
    }

    /**
     * Util function to decide the ID of the other player
     * @param np
     *          the CellState of a player
     * @return the CellState of the other player, or CellState.EMPTY if the input was EMPTY
     */
    public CellState otherPlayer(CellState np) {
        if (np == CellState.OCCUPIED_BY_X) return CellState.OCCUPIED_BY_O;
        else if (np == CellState.OCCUPIED_BY_O) return CellState.OCCUPIED_BY_X;
        return CellState.EMPTY;
    }
    
    /**
     * Initializes the minMax function and when scores are calculated, selects the maximum
     * @param mgb
     *          a running game
     * @return The CellLocation of the optimal choice
     */
    public CellLocation findTheBestStep(MGameBoard mgb) {
        cellScores = new HashMap<CellLocation, Integer>();

        miniMax(mgb, 0, 1);
            
        // find the location of the largest score
        int maxVal = Integer.MIN_VALUE;
        CellLocation maxLoc = CellLocation.CENTRE_CENTRE;
        for ( CellLocation loc : CellLocation.values() ) {
            if ( mgb.getCellState(loc) == CellState.EMPTY && cellScores.containsKey(loc) ) {
                int val = cellScores.get(loc);
                if ( val > maxVal ) {
                    maxVal = val;
                    maxLoc = loc;
                }
            }
        }
            
        return maxLoc;
    }
        
    /**
     * Searches for the optimal solution
     * @param mgb
     *          a mutable game board
     * @param depth
     *          depth of the search
     * @param turn
     *          which player has this turn
     * @return minimum or maximum of the scores
     */
    public int miniMax(MGameBoard mgb, int depth, int turn) {
        CellState winner = mgb.whoIsWinner();
        
        if( winner == mgb.getOriginalPlayer() )return 1;
        else if( winner == otherPlayer(mgb.getOriginalPlayer()) ) return -1;
           
        List<Integer> localScores = new ArrayList<>(); 
        for(CellLocation l : CellLocation.values()) {
            if( mgb.getCellState(l) == CellState.EMPTY ) { // try empty cells only
                CellState actualPlayer = mgb.nextPlayer();
                mgb.setCellState(l, actualPlayer); // try the empty cell
                
                int sc = miniMax(mgb, depth + 1, 3-turn); // turn: 2->1 1->2
                localScores.add(sc);
                if ( actualPlayer == mgb.getOriginalPlayer() && depth == 0 ) cellScores.put(l, sc);
       
                mgb.clearCellState(l); // make the cell empty again
            }
        }
            
        if ( localScores.isEmpty() ) return 0;
        else if ( turn == 1 ) return Collections.max(localScores);
        else return Collections.min(localScores);
    }
        
}
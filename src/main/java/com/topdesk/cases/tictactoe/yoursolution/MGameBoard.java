package com.topdesk.cases.tictactoe.yoursolution;

import com.topdesk.cases.tictactoe.CellLocation;
import com.topdesk.cases.tictactoe.CellState;
import com.topdesk.cases.tictactoe.GameBoard;
import static com.topdesk.cases.tictactoe.yoursolution.YourConsultant.SAMELINE;
import java.util.HashMap;
import java.util.Map;

/**
 * Mutable GameBoard with utils for finding the optimal next step
 */
public class MGameBoard {
    private Map<CellLocation, CellState> State = new HashMap<CellLocation, CellState>();
    
    private CellState originalPlayer;
    
    /**
     * Constuctor of the MutableGameBoard with an existing GameBoard
     * @param gb
     *          an in-progress game
     */
    public MGameBoard(GameBoard gb) {
        for( CellLocation loc : CellLocation.values() ) {
            this.State.put(loc, gb.getCellState(loc));
        }
        this.originalPlayer = nextPlayer();
    }
    
    /**
     * Gets the CellState of a field indexed with CellLocation
     * @param loc
     *          CellLocation where CellState is needed
     * @return CellState of the CellLocation
     */
    public CellState getCellState(CellLocation loc) {
        return this.State.get(loc);
    }
    
    public CellState getOriginalPlayer() {
        return this.originalPlayer;
    }
    
    /**
     * Sets a CellLocation to a specified CellState
     * @param loc
     *          Location of the cell to set
     * @param st 
     *          CellState to set
     * @throws IllegalStateException
     *          if someone tries to modify a non-empty cell
     */
    public void setCellState(CellLocation loc, CellState st) {
        if ( this.getCellState(loc) == CellState.EMPTY ) {
            this.State.put(loc, st);
        }
        else {
            throw new IllegalStateException("Tried to modify a non-empty cell.");
        }
    }
    
    /**
     * Makes a cell empty again
     * @param loc 
     *          Location of the cell to reset
     */
    public void clearCellState(CellLocation loc) {
        this.State.put(loc, CellState.EMPTY);
    }
    
    /**
     * Decides if a game is already won, and if it is, then which player is the winner
     * @return The ID of the winner player with CellState
     */
    public CellState whoIsWinner() {
        for (CellLocation[] line : SAMELINE) {
            CellState[] l = { this.getCellState(line[0]), this.getCellState(line[1]), this.getCellState(line[2]) };
            if (l[0] != CellState.EMPTY && l[0] == l[1] && l[0] == l[2]) {
                return l[0];
            }
        }
        return CellState.EMPTY;
    }
    
    /**
     * Decides which player is the next (or the board is already full)
     * @return CellState id of the next player
     * @throws IllegalStateException
     *          If the board is full
     */
    public CellState nextPlayer() {
            int numX = 0, numO = 0, numEmpty = 0;
            for(CellLocation l : CellLocation.values()) {
                switch(this.getCellState(l)) {
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
            else if ( numX <= numO ) return CellState.OCCUPIED_BY_X;
            else return CellState.OCCUPIED_BY_O;
        }
    
}

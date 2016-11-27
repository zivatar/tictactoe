package com.topdesk.cases.tictactoe.yoursolution;

import com.topdesk.cases.tictactoe.CellLocation;
import com.topdesk.cases.tictactoe.CellState;
import com.topdesk.cases.tictactoe.GameBoard;
import static com.topdesk.cases.tictactoe.yoursolution.YourConsultant.SAMELINE;
import java.util.HashMap;
import java.util.Map;


public class MGameBoard {
    private Map<CellLocation, CellState> State = new HashMap<CellLocation, CellState>();
    
    public MGameBoard(GameBoard gb) {
        for( CellLocation loc : CellLocation.values() ) {
            this.State.put(loc, gb.getCellState(loc));
        }
    }
    
    public CellState getCellState(CellLocation loc) {
        return this.State.get(loc);
    }
    
    public void setCellState(CellLocation loc, CellState st) {
        this.State.put(loc, st);
    }
    
    public CellState WhoIsWinner() {
        for (CellLocation[] line : SAMELINE) {
            CellState[] l = { this.getCellState(line[0]), this.getCellState(line[1]), this.getCellState(line[2]) };
            if (l[0] != CellState.EMPTY && l[0] == l[1] && l[0] == l[2]) {
                return l[0];
            }
        }
        return CellState.EMPTY;
    }
    
    public CellState NextPlayer() {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topdesk.cases.tictactoe.yoursolution;

import com.topdesk.cases.tictactoe.CellLocation;
import com.topdesk.cases.tictactoe.CellState;
import com.topdesk.cases.tictactoe.GameBoard;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lelo
 */
public class MGameBoard {
    private Map<CellLocation, CellState> State = new HashMap<CellLocation, CellState>();
    
    public void MGameBoard(GameBoard gb) {
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
}

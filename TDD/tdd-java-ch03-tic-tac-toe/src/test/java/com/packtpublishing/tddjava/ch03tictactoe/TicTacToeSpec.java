package com.packtpublishing.tddjava.ch03tictactoe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TicTacToeSpec {

    private static final int START_POSITION = 1;
    private static final int END_POSITION = 3;
    private static final String NO_WINNER = "No winner";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private TicTacToe ticTacToe;

    @Before
    public final void before(){
        ticTacToe = new TicTacToe();
    }

    @Test
    public void whenPieceOutsideXThenThrowRuntimeException(){
        exception.expect(RuntimeException.class);
        ticTacToe.play(START_POSITION + 4,START_POSITION + 1);

    }

    @Test
    public void whenPieceOutsideYThenThrowRuntimeException(){
        exception.expect(RuntimeException.class);
        ticTacToe.play(START_POSITION + 1, START_POSITION + 6);
    }

    @Test
    public void whenPieceOnOccupiedSpaceThenThrowRuntimeException(){
        ticTacToe.play(START_POSITION + 1, START_POSITION);
        exception.expect(RuntimeException.class);
        ticTacToe.play(START_POSITION + 1, START_POSITION);
    }

    @Test
    public void whenFirstTurnThenXPlayerTurn(){
        ticTacToe.play(START_POSITION + 1, START_POSITION);
        Assert.assertEquals("X", ticTacToe.getPlayer());
    }

    @Test
    public void whenLastPlayerXThen0PlayerTurn(){
        ticTacToe.play(START_POSITION + 1, START_POSITION);
        ticTacToe.play(START_POSITION + 2, START_POSITION);
        Assert.assertEquals("0", ticTacToe.getPlayer());
    }

    @Test
    public void whenLastPlayer0ThenXPlayerTurn(){
        ticTacToe.play(START_POSITION + 1, START_POSITION);
        ticTacToe.play(START_POSITION, START_POSITION + 2);
        ticTacToe.play(START_POSITION + 2,START_POSITION + 1);
        Assert.assertEquals("X", ticTacToe.getPlayer());
    }

    @Test
    public void whenNoWinningRuleThenNoWinner(){
        ticTacToe.play(START_POSITION + 1,START_POSITION);
        Assert.assertEquals(NO_WINNER, ticTacToe.getWinner());
    }

    @Test
    public void whenHorizontalWinningLineExistThenHaveWinner(){
        for (int i = START_POSITION; i <= END_POSITION ; i++) {
            ticTacToe.play(START_POSITION, i);
            if (ticTacToe.getWinner().equals(NO_WINNER)){
                ticTacToe.play(START_POSITION + 1,i);
            } else {
                break;
            }
        }
        Assert.assertNotEquals(NO_WINNER, ticTacToe.getWinner());
    }

    @Test
    public void whenVerticalWinningLineExistThenHaveWinner(){
        for (int i = START_POSITION; i <= END_POSITION ; i++) {
            ticTacToe.play(i, START_POSITION);
            if (ticTacToe.getWinner().equals(NO_WINNER)){
                ticTacToe.play(i,START_POSITION+1);
            } else {
                break;
            }
        }
        Assert.assertNotEquals(NO_WINNER, ticTacToe.getWinner());
    }

    @Test
    public void whenPrimaryDiagonalWinThenHaveWinner(){
        for(int i =START_POSITION; i <=END_POSITION; i++){
            ticTacToe.play(i, i);
            if (ticTacToe.getWinner().equals(NO_WINNER)){
                int yPos = i+1 > END_POSITION ? START_POSITION : i+1;
                ticTacToe.play(i, yPos);
            } else {
                break;
            }

        }
        Assert.assertNotEquals(NO_WINNER, ticTacToe.getWinner());
    }

    @Test
    public void whenSecondaryDiagonalWinThenHaveWinner(){
        for(int i = END_POSITION; i >= START_POSITION; i--){
            ticTacToe.play(END_POSITION + 1 - i, i);
            if (ticTacToe.getWinner().equals(NO_WINNER)){
                int yPos = i-1 < START_POSITION ? END_POSITION : i-1;
                ticTacToe.play(END_POSITION + 1 - i, yPos);
            } else {
                break;
            }

        }
        Assert.assertNotEquals(NO_WINNER, ticTacToe.getWinner());
    }

    @Test
    public void whenAllBoxesFilledThenEndGame(){
        for (int i=START_POSITION; i<=END_POSITION; i++){
            for (int j = START_POSITION; j <=END_POSITION ; j++) {
                ticTacToe.play(i, j);

            }
        }
        Assert.assertEquals(true, ticTacToe.isGameEnd());
    }

}

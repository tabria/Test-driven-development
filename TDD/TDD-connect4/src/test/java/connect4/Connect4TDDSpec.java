package connect4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;


public class Connect4TDDSpec {


    private static final int TEST_COLUMN = 1;
    private static final String RED_PLAYER = "RED";
    private static final String GREEN_PLAYER ="GREEN";
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    private Connect4TDD connect4TDD;
    private OutputStream output;

    @Before
    public void init(){
        output =new ByteArrayOutputStream();
        connect4TDD = new Connect4TDD(new PrintStream(output));
    }

    @Test
    public void whenInitBoardThenBoardIsEmpty(){
        Assert.assertEquals(0, connect4TDD.getNumberOfDiscs());
    }

    @Test
    public void whenInsertDiskInEmptyColumnThenDiskPosition0(){
        Assert.assertEquals(connect4TDD.putDiskInColumn(TEST_COLUMN), 0);
    }

    @Test
    public void whenInsertSecondDiskInSameColumnThenDiskPosition1(){
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        Assert.assertEquals(connect4TDD.putDiskInColumn(TEST_COLUMN), 1);
    }

    @Test
    public void whenInsertDiskThenNumberOfDisksIncreased(){
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        Assert.assertEquals(connect4TDD.getNumberOfDiscs(), 1);
    }

    @Test(expected = RuntimeException.class)
    public void whenPutDiskOutsideRowBoundaryThenRuntimeException(){
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        connect4TDD.putDiskInColumn(TEST_COLUMN);
    }

    @Test(expected = RuntimeException.class)
    public void whenPutDiskOutsideColumnBoundaryThenRuntimeException(){
        connect4TDD.putDiskInColumn(-1);
    }

    @Test
    public void whenGameStartThenRedPlayerTurn(){
        Assert.assertEquals( RED_PLAYER , connect4TDD.getCurrentPlayer());
    }

    @Test
    public void whenSecondPlayerPlayThenColorIsGreen(){
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        Assert.assertEquals(GREEN_PLAYER, connect4TDD.getCurrentPlayer());
    }

    @Test
    public void whenAskedForCurrentPlayerThenOutputReport(){
        connect4TDD.getCurrentPlayer();
        Assert.assertEquals(RED_PLAYER + " player turn", output.toString());
    }

    @Test
    public void whenTurnEndThenReport(){
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        Assert.assertEquals("|RED|", output.toString());
    }

    @Test
    public void whenGameStartThenIsFinishedIsFalse(){
        Assert.assertFalse("The game must not be finished", connect4TDD.isFinished());
    }

    @Test
    public void whenAllDisksAreOnBoardThenIsFinishedIsTrue(){
        for (int row = 0; row < ROWS ; row++) {
            for (int col = 0; col < COLUMNS ; col++) {
                connect4TDD.putDiskInColumn(col);
            }
        }
        Assert.assertTrue("The game ended with draw", connect4TDD.isFinished());
    }

    @Test
    public void when3DiskConnectedVerticallyThenNoWinner(){
        for (int i = 0; i < 3 ; i++) {
            connect4TDD.putDiskInColumn(TEST_COLUMN);
            connect4TDD.putDiskInColumn(TEST_COLUMN + 1);
        }
        Assert.assertEquals("", connect4TDD.getWinner());
    }

    @Test
    public void when4DisksConnectedVerticallyThenPlayerWins(){
        for (int i = 0; i < 3 ; i++) {
            connect4TDD.putDiskInColumn(TEST_COLUMN);
            connect4TDD.putDiskInColumn(TEST_COLUMN + 1);
        }
        connect4TDD.putDiskInColumn(TEST_COLUMN);
        Assert.assertEquals("RED player wins", connect4TDD.getWinner());
    }

    @Test
    public void when3DiskConnectedHorizontallyThenNoWinner(){
        for (int i = 0; i < 3 ; i++) {
            connect4TDD.putDiskInColumn(i);
            connect4TDD.putDiskInColumn(i);
        }
        Assert.assertEquals("", connect4TDD.getWinner());
    }

    @Test
    public void when4DiskConnectedHorizontallyThenWinner(){
        for (int i = 0; i < 3 ; i++) {
            connect4TDD.putDiskInColumn(i);
            connect4TDD.putDiskInColumn(i);
        }
        connect4TDD.putDiskInColumn(4);
        Assert.assertEquals("RED player wins", connect4TDD.getWinner());
    }

    @Test
    public void when4DisksConnectedPrimaryDiagonalThenWinner(){
        int[] play = new int[] {1, 2, 2, 3, 4, 3, 3, 4, 4, 5, 4};
        for (int column : play) {
            connect4TDD.putDiskInColumn(column);
        }
        Assert.assertEquals("RED player wins", connect4TDD.getWinner());
    }

    @Test
    public void when4DIsksConnectedSecondaryDiagonalThenWinner(){
        int play[] = {3, 4, 2, 3, 2, 2, 1, 1, 1, 1};
        for (int column : play){
            connect4TDD.putDiskInColumn(column);
        }
        Assert.assertEquals("GREEN player wins", connect4TDD.getWinner());
    }

}

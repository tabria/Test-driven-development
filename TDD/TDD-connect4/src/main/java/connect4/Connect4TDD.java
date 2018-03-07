package connect4;


import java.io.PrintStream;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Connect4TDD {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final String DELIMITER = "|";
    private static final String WIN_PLAYER_SUFFIX = " player wins";
    private static final int DISKS_TO_WIN = 4;

    private String[][] board;
    private String currentPlayer;
    private PrintStream printStream;
    private String winner;

    public Connect4TDD(PrintStream printStream) {
        this.printStream = printStream;
        this.board = new String[ROWS][COLUMNS];
        currentPlayer = PlayerColor.RED.toString();
        winner = "";
    }

    public String getCurrentPlayer() {
        printStream.printf("%s player turn", this.currentPlayer);
        return this.currentPlayer;
    }

    public int getNumberOfDiscs(){
        return IntStream.range(0, COLUMNS).map(this::getNumberOfDisksInColumn).sum();
    }

    public int putDiskInColumn(int column){
        checkIfColumnIsValid(column);
        int row =  getNumberOfDisksInColumn(column);
        checkIfRowIsValid(row, column);
        board[row][column] = currentPlayer;
        checkWinner(row, column);
        printBoard();
        changePlayerColor();
        return row;
    }

    public boolean isFinished(){
        return getNumberOfDiscs() >= ROWS * COLUMNS;
    }

    public String getWinner() {
        return this.winner;
    }

    private void checkWinner(int row, int column){
        verticalWinner(row,column);
        horizontalWinner(row, column);
        diagonalWinner(row, column);
        secondaryDiagonalWinner(row, column);
    }

    private void verticalWinner(int currentRow, int currentCol){
        int[] colorCount = {0};
        if (winner.isEmpty()) {
            for (int row = 0; row <= currentRow; row++) {
                if (isWinner(row, currentCol, colorCount)) {
                    break;
                }
            }
        }
    }

    private void horizontalWinner(int currentRow, int currentCol){
        int[] colorCount = {0};
        if (winner.isEmpty()){
            for (int col = 0; col <= currentCol ; col++) {
                if (isWinner(currentRow, col, colorCount)){
                    break;
                }
            }
        }
    }

    private boolean isWinner(int row, int col, int[] colorCount){
        if (board[row][col] != null){
            colorCount[0] = board[row][col].equals(currentPlayer) ? colorCount[0]+ 1 : 0;
            if (colorCount[0] >= DISKS_TO_WIN){
                winner = currentPlayer + WIN_PLAYER_SUFFIX;
                return true;
            }
        }
        return false;
    }

    private void diagonalWinner(int row, int col){
        if (winner.isEmpty()){
            int startRow = row - DISKS_TO_WIN < 0 ? 0 : row - DISKS_TO_WIN;
            int startCol = col - DISKS_TO_WIN + 1 < 0 ? 1 : col -DISKS_TO_WIN + 1;
            int countColor = 0;
            for (int cycle = 0; cycle <DISKS_TO_WIN ; cycle++) {
                if (board[startRow][startCol] == null){
                    break;
                }
                if (board[startRow][startCol].equals(currentPlayer)){
                    countColor++;
                    startRow++;
                    startCol++;
                }
                if (countColor >= DISKS_TO_WIN){
                    winner = currentPlayer + WIN_PLAYER_SUFFIX;
                    break;
                }
            }
        }
    }

    private void secondaryDiagonalWinner(int row, int col){
        if (winner.isEmpty()){
            int startRow = row - DISKS_TO_WIN < 0 ? 0 : row - DISKS_TO_WIN;
            int startCol = col + DISKS_TO_WIN - 1 >= COLUMNS ? COLUMNS-1 : col + DISKS_TO_WIN - 1;
            int countColor = 0;
            for (int cycle = 0; cycle <DISKS_TO_WIN ; cycle++) {
                if (board[startRow][startCol] == null){
                    break;
                }
                if (board[startRow][startCol].equals(currentPlayer)){
                    countColor++;
                    startRow++;
                    startCol--;
                }
                if (countColor >= DISKS_TO_WIN){
                    winner = currentPlayer + WIN_PLAYER_SUFFIX;
                    break;
                }
            }
        }
    }

    private void printBoard(){
        for (int row = ROWS-1; row >=0 ; row--) {
            StringJoiner sj = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
            for (int column = 0; column <COLUMNS-1 ; column++) {
                if (board[row][column] != null){
                    sj.add(board[row][column]);
                    printStream.printf(sj.toString());

                }
            }
////
////
//            StringJoiner sj = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
//            Stream.of(board[row]).filter(item-> item !=null).forEachOrdered(sj::add);
//            printStream.printf(sj.toString());
        }


    }

    private void changePlayerColor(){
        if (currentPlayer.equals(PlayerColor.RED.toString())){
            currentPlayer = PlayerColor.GREEN.toString();
        } else {
            currentPlayer = PlayerColor.RED.toString();
        }
    }

    private int getNumberOfDisksInColumn(int column){
        return (int) IntStream.range(0, ROWS).filter(i -> board[i][column] != null && !board[i][column].isEmpty()).count();
    }

    private void checkIfRowIsValid(int row, int column){
        if(row >= ROWS){
            throw new RuntimeException(String.format("Column %d is full %n", column));
        }
    }

    private void checkIfColumnIsValid(int column){
        if (column < 0 || column >= COLUMNS){
            throw new RuntimeException(String.format("Column %d is invalid", column));
        }
    }

}

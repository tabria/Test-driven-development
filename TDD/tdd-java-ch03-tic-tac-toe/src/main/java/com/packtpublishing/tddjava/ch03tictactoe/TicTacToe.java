package com.packtpublishing.tddjava.ch03tictactoe;

public class TicTacToe {

    private static final String NO_WINNER = "No winner";
    private static final int START_POSITION = 1;
    private static final int END_POSITION = 3;
    private static final String PIECE_OUTSIDE_BOARD = "Piece is outside the board";
    private static final String POSITION_OCCUPIED = "Position is occupied";
    private static final String WINNER_PLAYER = " is the winner";
    private static final String PLAYER_X = "X";
    private static final String PLAYER_0 = "0";

    private Character[][] board = {{'\0','\0','\0'}, {'\0','\0','\0'}, {'\0','\0','\0'}};
    private String player;
    private String winner;
    private int turns;

    public TicTacToe() {
        this.player = "";
        this.setWinner(NO_WINNER);
        this.turns = this.board.length * this.board.length;
    }

    public String getPlayer() {
        return this.player;
    }

    public String getWinner() {
        return winner;
    }

    public void play(int x, int y){
        if (winner.equals(NO_WINNER)){
            checkAxis(x);
            checkAxis(y);
            nextPlayer();
            setBox(x, y);
            turns--;
            if (isGameEnd()){
                System.out.println(winner);
            }
        }
    }


    public boolean isGameEnd(){
        String win = winnerRules();
        setWinner(win);
        return turns < 1 || !win.equals(NO_WINNER);
    }

    private String winnerRules(){
        String win = winnerByHorizontalLine();
        if (win.equals(NO_WINNER)){
            win = winnerByVerticalLine();
        }
        if (win.equals(NO_WINNER)) {
            win =  winnerByPrimaryDiagonal();
        }
        if (win.equals(NO_WINNER)) {
            win =  winnerBySecondaryDiagonal();
        }
        return win;
    }

    private void setWinner(String winner) {
        this.winner = winner;
    }


    private String winnerByPrimaryDiagonal(){
        int charSum = 0;
        for (int i = 0; i < END_POSITION ; i++) {
            charSum += board[i][i];
        }
        char playerChar = player.charAt(0);
        if (charSum == playerChar * 3){
            return player + WINNER_PLAYER;
        }
        return NO_WINNER;
    }

    private String winnerBySecondaryDiagonal(){
        int charSum = 0;
        for (int i = END_POSITION; i >= START_POSITION; i--) {
            charSum += board[END_POSITION - i][i - 1];
        }
        char playerChar = player.charAt(0);
        if (charSum == playerChar * 3){
            return player + WINNER_PLAYER;
        }
        return NO_WINNER;
    }

    private String winnerByHorizontalLine(){
        boolean isWinner = false;
        for (int i = 0; i < END_POSITION ; i++) {
            char lineSymbol = board[i][i];
            if (lineSymbol != '\0'){
                for(int k = 1; k < END_POSITION; k++){
                    if (board[i][k] == lineSymbol){
                        isWinner = true;
                    } else {
                        isWinner = false;
                        break;
                    }
                }
            }
            if (isWinner){
                return Character.toString(lineSymbol) + WINNER_PLAYER;
            }
        }
        return NO_WINNER;
    }

    private String winnerByVerticalLine(){
        boolean isWinner = false;
        for (int k = 0; k < END_POSITION ; k++) {
            char lineSymbol = board[k][k];
            if (lineSymbol !='\0'){
                for (int i = 0; i <END_POSITION ; i++) {
                    if (board[i][k] == lineSymbol){
                        isWinner = true;
                    } else {
                        isWinner = false;
                        break;
                    }
                }
            }
            if (isWinner){
                return Character.toString(lineSymbol) + WINNER_PLAYER;
            }
        }
        return NO_WINNER;
    }

    private void nextPlayer(){
        if (player.isEmpty() || player.equals(PLAYER_0)){
            player = PLAYER_X;
        } else if (player.equals(PLAYER_X)){
            player = PLAYER_0;
        }
    }

    private void checkAxis(int axis){
        if (axis < START_POSITION || axis > END_POSITION){
            throw new RuntimeException(PIECE_OUTSIDE_BOARD);
        }
    }

    private void setBox(int x, int y){
        if (board[x-1][y-1] != '\0'){
            throw  new RuntimeException(POSITION_OCCUPIED);
        } else {
            board[x-1][y-1] = player.charAt(0); ;
        }
    }



}

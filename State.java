package Spiel;

import Spiel.core.Board;
import Spiel.core.FlattenedBoard;
import Spiel.entities.MasterSquirrel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class State {
    private final Map<String, ArrayList<Integer>> scores = new HashMap<>();
    private int highScore = Integer.MIN_VALUE;
    private String highScoreHolder;
    private final Board board;

    public State(Board board) {
        this.board = board;
    }

    public void update() {
        board.nextStepAll();
    }

    public FlattenedBoard flattenedBoard() {
        return board.flatten();
    }

    public Board getBoard() {
        return board;
    }

    public void initializeScores() {
        for (MasterSquirrel master : board.getMasterSquirrel()) {
            scores.put(master.getName(), new ArrayList<>());
        }
    }

    public Map<String, ArrayList<Integer>> getScores() {
        return this.scores;
    }

    public void updateScores() {
        for (MasterSquirrel master : getBoard().getMasterSquirrel()) {
            getScores().get(master.getName()).add(master.getEnergy());
            Collections.sort(getScores().get(master.getName()));
        }
    }

    private void findHighScore() {
        for (MasterSquirrel master : getBoard().getMasterSquirrel()) {
           int max = getScores().get(master.getName()).get(getScores().get(master.getName()).size()-1);
           if (max > highScore) {
               highScore = max;
               highScoreHolder = master.getName();
           }
        }
    }

    public void printHighScore() {
        findHighScore();
        System.out.println("HighScore: " + highScoreHolder + " | " + highScore + " Points");
    }
}

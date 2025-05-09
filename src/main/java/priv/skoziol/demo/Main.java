package priv.skoziol.demo;

import priv.skoziol.mylib.Match;
import priv.skoziol.mylib.Scoreboard;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Scoreboard scoreboard = new Scoreboard();
        Long id = scoreboard.startNewMatch("England", "Wales");
        List<Match> summary = scoreboard.getSummary();
        System.out.println(summary);

        scoreboard.updateScore(id, 1,0);
        System.out.println(summary);

        scoreboard.endMatch(id);
        System.out.println(summary);


    }
}

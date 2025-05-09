package priv.skoziol.mylib;

import java.lang.reflect.Field;
import java.util.Random;

public class MatchTestBuilder {

    private static long matchId = 0L;

    private long id = matchId++;
    private Boolean isOngoing = Boolean.TRUE;
    private String homeTeam = "";
    private String awayTeam = "";
    private int homeTeamScore = 0;
    private int awayTeamScore = 0;

    public MatchTestBuilder withStatus(Boolean isOngoing) {
        this.isOngoing = isOngoing;
        return this;
    }

    public MatchTestBuilder withScore(Integer homeTeamScore, Integer awayTeamScore) {
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        return this;
    }

    public MatchTestBuilder withName(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        return this;
    }

    public Match build() {
        Match match = new Match();
        setField(match, "id", id);
        setField(match, "isOngoing", isOngoing);
        setField(match, "homeTeam", homeTeam);
        setField(match, "awayTeam", awayTeam);
        setField(match, "homeTeamScore", homeTeamScore);
        setField(match, "awayTeamScore", awayTeamScore);
        return match;
    }

    private void setField(Object obj, String fieldName, Object value) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(obj, value);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

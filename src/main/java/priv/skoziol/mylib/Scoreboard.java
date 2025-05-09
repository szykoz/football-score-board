package priv.skoziol.mylib;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Scoreboard {

    private final List<Match> matches = new ArrayList<>();

    public Long startNewMatch(String home, String away) {
        Match match = new Match();
        match.setId(Match.idSeq++);
        match.setHomeTeam(home);
        match.setAwayTeam(away);
        match.setHomeTeamScore(0);
        match.setAwayTeamScore(0);
        match.setOngoing(Boolean.TRUE);

        matches.add(match);
        return match.getId();
    }

    public void endMatch(Long matchId) {
        matches.stream().filter(m -> m.getId().equals(matchId))
                .findFirst()
                .ifPresent(m -> m.setOngoing(false));

    }

    public void updateScore(Long matchId, Integer homeScore, Integer awayScore) {
        matches.stream()
                .filter(m -> m.getId().equals(matchId))
                .findFirst()
                .map(m -> {
                    if (!m.isOngoing()) {
                        throw new ScoreUpdateNotAllowedException("Match is already finished");
                    }
                    m.setHomeTeamScore(homeScore);
                    m.setAwayTeamScore(awayScore);
                    return m;
                })
                .orElseThrow(() -> new NoSuchElementException("Match not found " + matchId));
    }

    public List<Match> getSummary() {
        return this.matches.stream()
                .sorted(Comparator.comparingInt((Match m) -> m.getHomeTeamScore() + m.getAwayTeamScore()))
                .toList()
                .reversed();
    }

}
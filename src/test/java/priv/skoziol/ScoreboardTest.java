package priv.skoziol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import priv.skoziol.match.MatchTestBuilder;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Match:
// id, homeTeamName, awayTeamName, homeTeamScore, awayTeamScore, isOngoing

// Scoreboard is class for managing matches
//    Scoreboard API:
//      List<Match> getSummary() returns sorted matches
//      Match startNewMatch(String homeTeam, String awayTeam) creates match and returns it
//      void updateMatch(int matchId, homeTeamScore, awayTeamScore); updates match score
//      void endMatch(int matchId)

class ScoreboardTest {

    Scoreboard underTest;

    @BeforeEach
    void setUp() {
        underTest = new Scoreboard();
    }

    @Test
    void canStartMatch() throws Exception {
        // Given
        Match expected = new MatchTestBuilder()
                .withScore(0, 0)
                .build();

        // When
        underTest.startNewMatch();

        // Then
        List<Match> summaryList = getMatches();

        assertEquals(1, summaryList.size());

        Match tested = summaryList.getFirst();

        assertTrue(tested.isOngoing());

        assertEquals(expected, tested);
    }

    @Test
    void canEndMatch() throws Exception {
        // Given
        Match newMatch = new MatchTestBuilder().build();

        // When
        underTest.endMatch(newMatch.getId());

        // Then
        List<Match> matches = getMatches();

        assertFalse(matches.getFirst().isOngoing());
    }

    @Test
    void canUpdateScoreWhileMatchIsOngoing() throws Exception {
        // Given
        Match newMatch = new MatchTestBuilder().build();
        setMatches(newMatch);

        // When
        underTest.updateScore(newMatch.getId(), 1, 0);

        // Then
        Match tested = getMatches().getFirst();
        assertEquals(1, tested.getHomeTeamScore());
        assertEquals(0, tested.getAwayTeamScore());
        assertTrue(tested.isOngoing());
    }

    @Test
    void cannotUpdateScoreAfterMatchHasEnded() throws Exception {
        // Given
        Match newMatch = new MatchTestBuilder().withStatus(false).build();
        setMatches(newMatch);

        // When
        // Then
        assertThrows(ScoreUpdateNotAllowedException.class, () -> {
            underTest.update(newMatch.getId(), 1, 0);
        });
    }

    @Test
    void getSummaryReturnsSortedList() throws Exception {
        // Given
        Match match1 = new MatchTestBuilder()
                .withName("Mexico", "Canada")
                .withScore(0, 5)
                .build();
        Match match2= new MatchTestBuilder()
                .withName("Spain", "Brazil")
                .withScore(10, 2)
                .build();
        Match match3 = new MatchTestBuilder()
                .withName("Germany", "France")
                .withScore(2, 2)
                .build();
        Match match4 = new MatchTestBuilder()
                .withName("Uruguay", "Italy")
                .withScore(6, 6)
                .build();
        Match match5 = new MatchTestBuilder()
                .withName("Argentina", "Australia")
                .withScore(3, 1)
                .build();
        setMatches(
                match1, match2, match3, match4, match5
        );


        // When
        List<Match> actualResult = underTest.getSummary();

        // Then
        List<Match> expectedResult = List.of(match4, match2, match1, match5, match3);
        assertEquals(expectedResult, actualResult);
    }

    private List<Match> getMatches() throws NoSuchFieldException, IllegalAccessException {
        Field field = Scoreboard.class.getDeclaredField("summaryList");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Match> summaryList = (List<Match>) field.get(underTest);
        return summaryList;
    }

    private void setMatches(Match... matches) throws NoSuchFieldException, IllegalAccessException {
        Field field = Scoreboard.class.getDeclaredField("summaryList");
        field.setAccessible(true);
        for (Match match : matches) {
            field.set(underTest, match);
        }
    }

}
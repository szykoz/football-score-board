package priv.skoziol.mylib;

public class Match {
    public static Long idSeq = 0L;

    private Long id;
    private String homeTeam;
    private String awayTeam;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Boolean isOngoing;

    public Long getId() {
        return id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public Boolean isOngoing() {
        return isOngoing;
    }

    void setOngoing(Boolean ongoing) {
        isOngoing = ongoing;
    }

    void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    void setId(Long id) {
        this.id = id;
    }

    void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public String toString() {
        return homeTeam + ' ' + homeTeamScore + '-' +
                awayTeam + ' ' + awayTeamScore +
                " (ongoing: " + isOngoing + ")\n";
    }
}

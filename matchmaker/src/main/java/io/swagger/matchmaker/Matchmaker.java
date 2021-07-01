package io.swagger.matchmaker;

import java.util.ArrayList;
import io.swagger.model.NewMatch;
import io.swagger.model.MatchInfo;
import io.swagger.matchmaker.Match;
import java.util.Date;
import java.util.UUID;

public class Matchmaker {

    static ArrayList<Match> matches = new ArrayList<Match>();
    
    public Matchmaker() {
        // this.matches = new ArrayList<Match>();
    }

    public void addMatch(Match match) {
        matches.add(match);
        System.out.println("Created new match: " + match.getMatchId());
    }

    public int getNumberOfMatches() {
        return matches.size();
    }

    public NewMatch joinMatch(String userId) {
        for (int i = 0; i < matches.size(); i++)  {
            if (matches.get(i).getPlayerCount() == 1) {
                Match match = matches.get(i);
                match.setPlayerCount(2);
                match.setPlayer2Id(userId);
                System.out.println("joined as player 2");

                NewMatch newMatch = new NewMatch();
                newMatch.setMatchId(match.getMatchId());
                newMatch.setPlayerId(userId);
                newMatch.setPlayerNum(2);

                return newMatch;
            }
        }

        String matchId = "m" + UUID.randomUUID().toString();
        Match match = new Match();
        match.setMatchId(matchId);
        match.setPlayerCount(1);
        match.setPlayer1Id(userId);
        this.addMatch(match);
        System.out.println("joined as player 1");

        NewMatch newMatch = new NewMatch();
        newMatch.setMatchId(match.getMatchId());
        newMatch.setPlayerId(userId);
        newMatch.setPlayerNum(1);

        return newMatch;
    }

    public MatchInfo getMatchInfoForMatchId(String matchId) {
        
        for (int i = 0; i < matches.size(); i++)  {
            System.out.println(matches.get(i).getMatchId());
            if(matches.get(i).getMatchId().equals(matchId)) {
                System.out.println("Found match with this ID");
                MatchInfo matchInfo = new MatchInfo();
                matchInfo.setMatchIdIsValid(true);
                matchInfo.setPlayer1Id(matches.get(i).getPlayer1Id());
                matchInfo.setPlayer2Id(matches.get(i).getPlayer2Id());
                return matchInfo;
            }
        }
        System.out.println("Match with this ID does not exist!");
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchIdIsValid(false);
        return matchInfo;

    }
}
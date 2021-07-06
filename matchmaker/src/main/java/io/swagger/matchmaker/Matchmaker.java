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
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public int getNumberOfMatches() {
        return matches.size();
    }

    public void deleteMatch(String matchId) {
        for (int i = 0; i < matches.size(); i++)  {
            if(matches.get(i).getMatchId().equals(matchId)) {
                matches.remove(i);
                return;
            }
        }
    }

    public NewMatch joinMatch(String userId) {
        for (int i = 0; i < matches.size(); i++)  {
            if (matches.get(i).getPlayerCount() == 1) {
                Match match = matches.get(i);
                match.setPlayerCount(2);
                match.setPlayer2Id(userId);

                NewMatch newMatch = new NewMatch();
                newMatch.setMatchId(match.getMatchId());
                newMatch.setPlayerId(userId);
                newMatch.setPlayerNum(2);

                return newMatch;
            }
        }

        String matchId = "m" + UUID.randomUUID().toString();
        System.out.println("new match: " + matchId);
        Match match = new Match();
        match.setMatchId(matchId);
        match.setPlayerCount(1);
        match.setPlayer1Id(userId);
        this.addMatch(match);

        NewMatch newMatch = new NewMatch();
        newMatch.setMatchId(match.getMatchId());
        newMatch.setPlayerId(userId);
        newMatch.setPlayerNum(1);

        System.out.println("num of matches: " + this.getNumberOfMatches());

        return newMatch;
    }

    public MatchInfo getMatchInfoForMatchId(String matchId) {
        
        for (int i = 0; i < matches.size(); i++)  {
            if(matches.get(i).getMatchId().equals(matchId)) {
                MatchInfo matchInfo = new MatchInfo();
                matchInfo.setMatchIdIsValid(true);
                matchInfo.setPlayer1Id(matches.get(i).getPlayer1Id());
                matchInfo.setPlayer2Id(matches.get(i).getPlayer2Id());
                return matchInfo;
            }
        }
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchIdIsValid(false);
        return matchInfo;

    }
}
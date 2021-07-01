package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NewMatch
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-01T12:16:14.800Z[GMT]")


public class NewMatch   {
  @JsonProperty("matchId")
  private String matchId = null;

  @JsonProperty("playerId")
  private String playerId = null;

  @JsonProperty("playerNum")
  private Integer playerNum = null;

  public NewMatch matchId(String matchId) {
    this.matchId = matchId;
    return this;
  }

  /**
   * Get matchId
   * @return matchId
   **/
  @Schema(description = "")
  
    public String getMatchId() {
    return matchId;
  }

  public void setMatchId(String matchId) {
    this.matchId = matchId;
  }

  public NewMatch playerId(String playerId) {
    this.playerId = playerId;
    return this;
  }

  /**
   * Get playerId
   * @return playerId
   **/
  @Schema(description = "")
  
    public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public NewMatch playerNum(Integer playerNum) {
    this.playerNum = playerNum;
    return this;
  }

  /**
   * Get playerNum
   * @return playerNum
   **/
  @Schema(description = "")
  
    public Integer getPlayerNum() {
    return playerNum;
  }

  public void setPlayerNum(Integer playerNum) {
    this.playerNum = playerNum;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewMatch newMatch = (NewMatch) o;
    return Objects.equals(this.matchId, newMatch.matchId) &&
        Objects.equals(this.playerId, newMatch.playerId) &&
        Objects.equals(this.playerNum, newMatch.playerNum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matchId, playerId, playerNum);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewMatch {\n");
    
    sb.append("    matchId: ").append(toIndentedString(matchId)).append("\n");
    sb.append("    playerId: ").append(toIndentedString(playerId)).append("\n");
    sb.append("    playerNum: ").append(toIndentedString(playerNum)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

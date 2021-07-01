package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MatchInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-01T12:16:14.800Z[GMT]")


public class MatchInfo   {
  @JsonProperty("matchIdIsValid")
  private Boolean matchIdIsValid = null;

  @JsonProperty("player1Id")
  private String player1Id = null;

  @JsonProperty("player2Id")
  private String player2Id = null;

  public MatchInfo matchIdIsValid(Boolean matchIdIsValid) {
    this.matchIdIsValid = matchIdIsValid;
    return this;
  }

  /**
   * Get matchIdIsValid
   * @return matchIdIsValid
   **/
  @Schema(description = "")
  
    public Boolean isMatchIdIsValid() {
    return matchIdIsValid;
  }

  public void setMatchIdIsValid(Boolean matchIdIsValid) {
    this.matchIdIsValid = matchIdIsValid;
  }

  public MatchInfo player1Id(String player1Id) {
    this.player1Id = player1Id;
    return this;
  }

  /**
   * Get player1Id
   * @return player1Id
   **/
  @Schema(description = "")
  
    public String getPlayer1Id() {
    return player1Id;
  }

  public void setPlayer1Id(String player1Id) {
    this.player1Id = player1Id;
  }

  public MatchInfo player2Id(String player2Id) {
    this.player2Id = player2Id;
    return this;
  }

  /**
   * Get player2Id
   * @return player2Id
   **/
  @Schema(description = "")
  
    public String getPlayer2Id() {
    return player2Id;
  }

  public void setPlayer2Id(String player2Id) {
    this.player2Id = player2Id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchInfo matchInfo = (MatchInfo) o;
    return Objects.equals(this.matchIdIsValid, matchInfo.matchIdIsValid) &&
        Objects.equals(this.player1Id, matchInfo.player1Id) &&
        Objects.equals(this.player2Id, matchInfo.player2Id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matchIdIsValid, player1Id, player2Id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchInfo {\n");
    
    sb.append("    matchIdIsValid: ").append(toIndentedString(matchIdIsValid)).append("\n");
    sb.append("    player1Id: ").append(toIndentedString(player1Id)).append("\n");
    sb.append("    player2Id: ").append(toIndentedString(player2Id)).append("\n");
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

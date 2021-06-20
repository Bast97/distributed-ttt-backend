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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-17T12:52:16.300Z[GMT]")


public class NewMatch   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("player_num")
  private Integer playerNum = null;

  @JsonProperty("color")
  private String color = null;

  public NewMatch id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public NewMatch color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Get color
   * @return color
   **/
  @Schema(description = "")
  
    public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
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
    return Objects.equals(this.id, newMatch.id) &&
        Objects.equals(this.playerNum, newMatch.playerNum) &&
        Objects.equals(this.color, newMatch.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, playerNum, color);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewMatch {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    playerNum: ").append(toIndentedString(playerNum)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
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

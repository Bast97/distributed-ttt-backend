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


public class ActiveMatches   {

  @JsonProperty("numActiveMatches")
  private int numActiveMatches = 0;

  // public ActiveMatches matchIdIsValid(int numActiveMatches) {
  //   this.numActiveMatches = numActiveMatches;
  //   return this;
  // }

  /**
   * Get numActiveMatches
   * @return numActiveMatches
   **/
  @Schema(description = "")
  
    public int getNumActiveMatches() {
    return numActiveMatches;
  }

  public void setNumActiveMatches(int numActiveMatches) {
    this.numActiveMatches = numActiveMatches;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActiveMatches activeMatches = (ActiveMatches) o;
    return Objects.equals(this.numActiveMatches, activeMatches.numActiveMatches);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numActiveMatches);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActiveMatches {\n");
    
    sb.append("    numActiveMatches: ").append(toIndentedString(numActiveMatches)).append("\n");
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

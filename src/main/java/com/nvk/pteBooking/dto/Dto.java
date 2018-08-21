package com.nvk.pteBooking.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Dto<T> implements Serializable {

  private static final long serialVersionUID = 6680860289742520573L;

  @JsonProperty("data")
  private T data;

  @JsonCreator
  public Dto(@JsonProperty("data") T data) {
    this.data = data;
  }
}

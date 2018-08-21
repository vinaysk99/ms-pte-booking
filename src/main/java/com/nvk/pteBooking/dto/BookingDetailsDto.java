package com.nvk.pteBooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nvk.pteBooking.util.serialization.JsonJodaLocalDateDeserializer;
import com.nvk.pteBooking.util.serialization.JsonJodaLocalDateSerializer;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookingDetailsDto implements Serializable {

  private static final long serialVersionUID = 5425911325251191557L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("emailId")
  private String emailId;

  @JsonProperty("phoneNumber")
  private String phoneNumber;

  @JsonProperty("address")
  private String address;

  @JsonProperty("bookingLocation")
  private String bookingLocation;

  @JsonProperty("bookingDate")
  @JsonSerialize(using = JsonJodaLocalDateSerializer.class)
  @JsonDeserialize(using = JsonJodaLocalDateDeserializer.class)
  private LocalDate bookingDate;

  @JsonProperty("bookingTime")
  private String bookingTime;
}

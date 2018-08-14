package com.nvk.pteBooking.domain;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookingDetails")
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetails {

  @Id
  private String id;

  private String firstName;

  private String lastName;

  private String emailId;

  private String phoneNumber;

  private String address;

  private String bookingLocation;

  private LocalDate bookingDate;

  private String bookingTime;
}

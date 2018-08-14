package com.nvk.pteBooking.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class BookingDetailsDtoTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(BookingDetailsDto.class)
      .suppress(
        Warning.NONFINAL_FIELDS,
        Warning.STRICT_INHERITANCE,
        Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .verify();
  }
}
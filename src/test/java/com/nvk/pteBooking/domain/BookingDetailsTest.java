package com.nvk.pteBooking.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class BookingDetailsTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(BookingDetails.class)
      .suppress(
        Warning.NONFINAL_FIELDS,
        Warning.STRICT_INHERITANCE,
        Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .verify();
  }
}

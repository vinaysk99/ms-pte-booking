package com.nvk.pteBooking.service;

import com.nvk.pteBooking.domain.BookingDetails;
import java.util.List;

public interface BookingService {

  /**
   * Create a Booking
   *
   * @param bookingDetails
   * @return
   */
  BookingDetails makeABooking(BookingDetails bookingDetails);

  /**
   * Get BookingDetails for id
   *
   * @param id
   * @return
   */
  BookingDetails getBooking(String id);

  /**
   * Get all Bookings
   * @return
   */
  List<BookingDetails> getBookings();

  /**
   * Update Booking
   *
   * @param id
   * @param bookingDetails
   * @return
   */
  BookingDetails updateBooking(String id, BookingDetails bookingDetails);

  /**
   * Delete a booking
   *
   * @param id
   */
  boolean deleteBooking(String id);
}

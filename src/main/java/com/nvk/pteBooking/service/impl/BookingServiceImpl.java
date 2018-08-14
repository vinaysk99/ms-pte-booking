package com.nvk.pteBooking.service.impl;

import com.nvk.pteBooking.domain.BookingDetails;
import com.nvk.pteBooking.repository.BookingDetailsMongoRepository;
import com.nvk.pteBooking.service.BookingService;
import com.nvk.pteBooking.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

  private static final Logger log = LoggerFactory.getLogger(BookingService.class);

  @Autowired
  private BookingDetailsMongoRepository bookingDetailsMongoRepository;

  @Override
  public BookingDetails makeABooking(BookingDetails bookingDetails) {
    BookingDetails bookingDetailsCreated = bookingDetailsMongoRepository.insert(bookingDetails);
    log.info("Created booking for : {} with id :{}", bookingDetails.getFirstName(),
      bookingDetails.getId());
    return bookingDetailsCreated;
  }

  @Override
  public BookingDetails getBooking(String id) {
    Optional<BookingDetails> bookingDetailsOptional = bookingDetailsMongoRepository.findById(id);
    if (!bookingDetailsOptional.isPresent()) {
      String exceptionMessage = String.format("BookingDetails with id : %s not found ", id);
      log.error(exceptionMessage);
      throw new EntityNotFoundException(exceptionMessage);
    }
    log.info("Successfully retrieved booking details for id : {}", id);
    return bookingDetailsOptional.get();
  }

  @Override
  public List<BookingDetails> getBookings() {
    List<BookingDetails> bookingDetailsList = bookingDetailsMongoRepository.findAll();
    log.info("Found {} bookings", bookingDetailsList.size());
    return bookingDetailsList;
  }

  @Override
  public BookingDetails updateBooking(String id, BookingDetails bookingDetails) {
    BookingDetails bookingDetailsUpdated = bookingDetailsMongoRepository.save(bookingDetails);
    log.info("Updated BookingDetails for id : {}", id);
    return bookingDetailsUpdated;
  }

  @Override
  public boolean deleteBooking(String id) {
    bookingDetailsMongoRepository.deleteById(id);
    log.info("Deleted booking for id : {}", id);
    return true;
  }
}

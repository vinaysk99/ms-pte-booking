package com.nvk.pteBooking.repository;

import com.nvk.pteBooking.domain.BookingDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * BookingDetails repo
 */
public interface BookingDetailsMongoRepository extends MongoRepository<BookingDetails, String> {
}

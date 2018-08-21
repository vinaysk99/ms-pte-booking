package com.nvk.pteBooking.controller;

import com.nvk.pteBooking.domain.BookingDetails;
import com.nvk.pteBooking.dto.BookingDetailsDto;
import com.nvk.pteBooking.dto.Dto;
import com.nvk.pteBooking.service.BookingService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookingController {

  private static final Logger log = LoggerFactory.getLogger(BookingController.class);

  private final BookingService bookingService;

  private final ModelMapper modelMapper;

  /**
   * Create a new sample record.
   *
   * @param bookingDetailsDto New booking to be created.
   * @param request    Servlet request
   * @return Newly created booking including generated Id.
   */
  @PostMapping(value = "/v1/bookingDetails",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "A short optional description for the CREATE Endpoint",
    notes = "Implementation Notes on the Create Endpoint")
  public Dto<BookingDetailsDto> createBookingDetails(
    @RequestBody BookingDetailsDto bookingDetailsDto, HttpServletRequest request) {
    log.info("[{}] Request to create new BookingDetails", "");
    BookingDetails bookingDetails = modelMapper.map(bookingDetailsDto, BookingDetails.class);
    BookingDetails bookedDetails = bookingService.makeABooking(bookingDetails);
    BookingDetailsDto bookingDetailsDtoCreated = modelMapper.map(bookedDetails,
      BookingDetailsDto.class);
    return new Dto(bookingDetailsDtoCreated);
  }

  /**
   * Update record.
   *
   * @param bookingId  Id of bookings to be updated.
   * @param bookingDetailsDto New record content.
   * @param request    Servlet request
   * @return Updated content.
   */
  @PutMapping(value = "/v1/bookingDetails/{bookingId}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "A short optional description for the UPDATE Endpoint",
    notes = "Implementation Notes on the Update Endpoint")
  public Dto<BookingDetailsDto> updateBookingDetails(
      @PathVariable String bookingId,
      @RequestBody BookingDetailsDto bookingDetailsDto,
      HttpServletRequest request) {
    log.info("[{}] Request to update BookingDetails for id : {}", "", bookingId);
    BookingDetails bookingDetails = modelMapper.map(bookingDetailsDto, BookingDetails.class);
    BookingDetails updatedBookingDetails = bookingService.updateBooking(bookingId, bookingDetails);
    BookingDetailsDto bookingDetailsDtoUpdated = modelMapper.map(
      updatedBookingDetails, BookingDetailsDto.class);
    return new Dto(bookingDetailsDtoUpdated);
  }

  /**
   * Get bookingDetails endpoint.
   *
   * @param bookingId Id to be retrieved.
   * @param request   Servlet request.
   * @return Record if found.
   */
  @GetMapping(value = "/v1/bookingDetails/{bookingId}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "A short optional description for the GET Endpoint",
    notes = "Implementation Notes on the GET Endpoint")
  public Dto<BookingDetailsDto> getBookingDetailsForId(@PathVariable String bookingId,
                                                       HttpServletRequest request) {
    log.info("[{}] Request to get BookingDetails for id : {}", "", bookingId);
    BookingDetails bookingDetails = bookingService.getBooking(bookingId);
    BookingDetailsDto bookingDetailsDto = modelMapper.map(bookingDetails, BookingDetailsDto.class);
    return new Dto(bookingDetailsDto);
  }

  /**
   * Lists bookings using a filter.
   *
   * @param userId  UserId to be filtered for.
   * @param request Servlet request
   * @return records matching filter.
   */
  @GetMapping(value = "/v1/bookingDetails",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "A short optional description for the GET Endpoint",
    notes = "Implementation Notes on the GET Endpoint")
  public Dto<List<BookingDetailsDto>> getAllBookingDetails(
      @RequestParam(defaultValue = "") String userId, HttpServletRequest request) {
    log.info("[{}] Request to get BookingDetailss for userId : {}", "", userId);
    List<BookingDetails> bookingDetails = bookingService.getBookings();
    List<BookingDetailsDto> bookingDetailsDtos = modelMapper.map(bookingDetails,
      new TypeToken<List<BookingDetailsDto>>() {}.getType());
    return new Dto(bookingDetailsDtos);
  }

  /**
   * Delete bookings by id.
   *
   * @param bookingId Id of bookings to be deleted.
   * @param request   servlet request.
   */
  @DeleteMapping(value = "/v1/bookingDetails/{bookingId}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "A short optional description for the DELETE Endpoint",
    notes = "Implementation Notes on the DELETE Endpoint")
  public void deleteBookingDetails(@PathVariable String bookingId, HttpServletRequest request) {
    log.info("[{}] Request to delete BookingDetails for id : {}", "", bookingId);
    bookingService.deleteBooking(bookingId);
  }
}

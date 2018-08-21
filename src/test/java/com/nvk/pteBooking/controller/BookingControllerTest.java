package com.nvk.pteBooking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nvk.pteBooking.base.IntegrationTest;
import com.nvk.pteBooking.domain.BookingDetails;
import com.nvk.pteBooking.dto.BookingDetailsDto;
import com.nvk.pteBooking.dto.Dto;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookingControllerTest extends IntegrationTest {

  private String bookingEndpoint = "/v1/bookingDetails";

  @Before
  public void setUp() {
    super.setUp();
    collectionsToBeCleared = Arrays.asList(BookingDetails.class);
    typeReference = new TypeReference<BookingDetailsDto>() {};
    typeReferenceDto = new TypeReference<Dto<BookingDetailsDto>>() {};
    typeReferenceDtoList = new TypeReference<Dto<List<BookingDetailsDto>>>() {};
  }

  @Test
  public void shouldCreateNewBookingDetails() {
    BookingDetailsDto bookingDetailsDto = (BookingDetailsDto) getObjectFromFile(
      "bookingDetails/createBookingDetails1.json");
    BookingDetailsDto createdBookingDetailsDto = (BookingDetailsDto) postObjectToRESTEndPoint(
      bookingEndpoint, bookingDetailsDto, true);
    ReflectionTestUtils.setField(bookingDetailsDto, "id", createdBookingDetailsDto.getId());
    assertThat(createdBookingDetailsDto, is(bookingDetailsDto));
  }

  @Test
  public void shouldUpdateBookingDetails() {
    BookingDetailsDto bookingDetailsDto = (BookingDetailsDto) getObjectFromFile(
      "bookingDetails/createBookingDetails1.json");

    BookingDetails bookingDetails = modelMapper.map(bookingDetailsDto, BookingDetails.class);
    mongoTemplate.insert(bookingDetails);

    BookingDetailsDto bookingDetailsDtoToUpdate = (BookingDetailsDto) getObjectFromFile(
      "bookingDetails/updateBookingDetails1.json");
    ReflectionTestUtils.setField(bookingDetailsDtoToUpdate, "id", bookingDetails.getId());

    BookingDetailsDto updatedBookingDetailsDto = (BookingDetailsDto) putObjectToRESTEndPoint(
      bookingEndpoint + "/" + bookingDetails.getId(), bookingDetailsDtoToUpdate, true);
    assertThat(updatedBookingDetailsDto, is(bookingDetailsDtoToUpdate));
  }

  @Test
  public void shouldGetBookingDetails() {
    BookingDetailsDto bookingDetailsDto = (BookingDetailsDto) getObjectFromFile(
      "bookingDetails/getBookingDetails1.json");

    BookingDetails bookingDetails = modelMapper.map(bookingDetailsDto, BookingDetails.class);
    mongoTemplate.insert(bookingDetails);

    BookingDetailsDto bookingDetailsDtoRetrieved = (BookingDetailsDto) getObjectFromRESTEndPoint(
      bookingEndpoint + "/" + bookingDetails.getId(), true);

    ReflectionTestUtils.setField(bookingDetailsDto, "id", bookingDetails.getId());
    assertThat(bookingDetailsDtoRetrieved, is(bookingDetailsDto));
  }

  @Test
  public void shouldGetBookingDetailsForParams() {
    BookingDetailsDto bookingDetailsDto1 = (BookingDetailsDto) getObjectFromFile(
      "bookingDetails/getBookingDetailsForUserId1.json");

    BookingDetails bookingDetails1 = modelMapper.map(bookingDetailsDto1, BookingDetails.class);
    mongoTemplate.insert(bookingDetails1);

    BookingDetailsDto bookingDetailsDto2 = (BookingDetailsDto) getObjectFromFile(
      "bookingDetails/getBookingDetailsForUserId2.json");

    BookingDetails bookingDetails2 = modelMapper.map(bookingDetailsDto2, BookingDetails.class);
    mongoTemplate.insert(bookingDetails2);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    //map.set("userId", bookingDetailsDto1.getUserId());

    List<BookingDetailsDto> bookingDetailsDtos = (List<BookingDetailsDto>) (List<?>)
      getObjectsForParamsFromRESTEndPoint(bookingEndpoint, map, true);

    assertThat(bookingDetailsDtos.size(), is(2));
  }

  @Test
  public void shouldDeleteBookingDetails() {
    BookingDetailsDto bookingDetailsDto = (BookingDetailsDto) getObjectFromFile(
      "bookingDetails/deleteBookingDetails1.json");
    BookingDetails bookingDetails = modelMapper.map(bookingDetailsDto, BookingDetails.class);
    mongoTemplate.insert(bookingDetails);
    deleteObjectAtRESTEndPoint(bookingEndpoint + "/" + bookingDetails.getId(), true);
  }
  //
  //@Test
  //public void shouldReturnNotFoundIfBookingDetailsDoesNotExist() {
  //  getObjectFromRESTEndPointWithStatus(bookingEndpoint + "/invalidId",
  //    false, HttpStatus.NOT_FOUND.value());
  //}
}

package com.apitests.models.response;

import com.apitests.models.request.CreateBookingRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Модель ответа при создании бронирования.
 * Ответ содержит: bookingid + вложенный объект booking с деталями.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {

    @JsonProperty("bookingid")
    private int bookingid;

    @JsonProperty("booking")
    private CreateBookingRequest booking;

    public int getBookingid()              { return bookingid; }
    public CreateBookingRequest getBooking() { return booking; }

    @Override
    public String toString() {
        return "BookingResponse{bookingid=" + bookingid + ", booking=" + booking + "}";
    }
}
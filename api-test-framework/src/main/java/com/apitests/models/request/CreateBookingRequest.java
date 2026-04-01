package com.apitests.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Модель тела запроса для создания бронирования (Restful Booker API).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBookingRequest {

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("totalprice")
    private int totalprice;

    @JsonProperty("depositpaid")
    private boolean depositpaid;

    @JsonProperty("bookingdates")
    private BookingDates bookingdates;

    @JsonProperty("additionalneeds")
    private String additionalneeds;

    private CreateBookingRequest() {}

    public String getFirstname()        { return firstname; }
    public String getLastname()         { return lastname; }
    public int getTotalprice()          { return totalprice; }
    public boolean isDepositpaid()      { return depositpaid; }
    public BookingDates getBookingdates() { return bookingdates; }
    public String getAdditionalneeds() { return additionalneeds; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final CreateBookingRequest request = new CreateBookingRequest();

        public Builder firstname(String firstname)           { request.firstname = firstname;           return this; }
        public Builder lastname(String lastname)             { request.lastname = lastname;             return this; }
        public Builder totalprice(int totalprice)            { request.totalprice = totalprice;         return this; }
        public Builder depositpaid(boolean depositpaid)      { request.depositpaid = depositpaid;       return this; }
        public Builder bookingdates(BookingDates dates)      { request.bookingdates = dates;            return this; }
        public Builder additionalneeds(String needs)         { request.additionalneeds = needs;         return this; }

        public CreateBookingRequest build() { return request; }
    }

    /**
     * Вложенный класс для дат бронирования.
     */
    public static class BookingDates {

        @JsonProperty("checkin")
        private String checkin;

        @JsonProperty("checkout")
        private String checkout;

        public BookingDates() {}

        public BookingDates(String checkin, String checkout) {
            this.checkin = checkin;
            this.checkout = checkout;
        }

        public String getCheckin()  { return checkin; }
        public String getCheckout() { return checkout; }
    }

    @Override
    public String toString() {
        return "CreateBookingRequest{firstname='" + firstname + "', lastname='" + lastname +
                "', totalprice=" + totalprice + ", depositpaid=" + depositpaid + "}";
    }
}
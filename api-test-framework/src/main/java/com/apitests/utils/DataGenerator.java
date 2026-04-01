package com.apitests.utils;

import com.apitests.models.request.CreateBookingRequest;
import com.apitests.models.request.CreateUserRequest;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Утилита для генерации тестовых данных через JavaFaker.
 * Все методы статические — не нужно создавать объект.
 *
 * Использование:
 *   String email = DataGenerator.generateEmail();
 *   CreateUserRequest user = DataGenerator.buildCreateUserRequest();
 */
public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DataGenerator() {}

    public static String generateName()     { return faker.name().fullName(); }
    public static String generateEmail()    { return faker.internet().emailAddress(); }
    public static String generatePhone()    { return faker.phoneNumber().phoneNumber(); }
    public static String generateUsername() { return faker.name().username(); }
    public static String generateWebsite()  { return faker.internet().domainName(); }
    public static int generatePrice()       { return faker.number().numberBetween(50, 500); }
    public static String generateWord()     { return faker.lorem().word(); }

    // Дата через N дней от сегодня в формате "yyyy-MM-dd"
    public static String generateFutureDate(int daysFromNow) {
        return LocalDate.now().plusDays(daysFromNow).format(DATE_FORMAT);
    }

    // Готовый объект запроса для создания пользователя
    public static CreateUserRequest buildCreateUserRequest() {
        return CreateUserRequest.builder()
                .name(generateName())
                .username(generateUsername())
                .email(generateEmail())
                .phone(generatePhone())
                .website(generateWebsite())
                .build();
    }

    // Готовый объект запроса для создания бронирования
    public static CreateBookingRequest buildCreateBookingRequest(String checkin, String checkout) {
        return CreateBookingRequest.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(generatePrice())
                .depositpaid(faker.bool().bool())
                .bookingdates(new CreateBookingRequest.BookingDates(checkin, checkout))
                .additionalneeds(faker.lorem().sentence())
                .build();
    }
}
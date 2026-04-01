package com.apitests.tests;

import com.apitests.base.BaseTest;
import com.apitests.models.request.CreateBookingRequest;
import com.apitests.models.response.BookingResponse;
import com.apitests.utils.DataGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Booking API")
@Feature("CRUD /booking")
public class BookingApiTest extends BaseTest {

    // ID созданного бронирования — передаётся между тестами
    private int createdBookingId;
    private CreateBookingRequest bookingRequest;

    @Test(priority = 1)
    @Story("Создание бронирования")
    public void createBooking_shouldReturn200() {
        step("Генерируем тестовые данные");
        bookingRequest = DataGenerator.buildCreateBookingRequest(
                DataGenerator.generateFutureDate(1),
                DataGenerator.generateFutureDate(5)
        );

        step("Отправляем POST /booking");
        Response response = apiClient.post("/booking", bookingRequest);

        assertThat(response.statusCode())
                .as("Ожидали статус 200 при создании бронирования")
                .isEqualTo(200);

        BookingResponse bookingResponse = response.as(BookingResponse.class);
        createdBookingId = bookingResponse.getBookingid();

        assertThat(createdBookingId).isPositive();
        assertThat(bookingResponse.getBooking().getFirstname())
                .isEqualTo(bookingRequest.getFirstname());

        System.out.println("Создано бронирование с ID: " + createdBookingId);
    }

    @Test(priority = 2, dependsOnMethods = "createBooking_shouldReturn200")
    @Story("Получение бронирования")
    public void getBooking_shouldReturnCreatedData() {
        step("Отправляем GET /booking/" + createdBookingId);
        Response response = apiClient.get("/booking/" + createdBookingId);

        assertThat(response.statusCode())
                .as("Ожидали статус 200 при получении бронирования")
                .isEqualTo(200);

        assertThat(response.jsonPath().getString("firstname"))
                .isEqualTo(bookingRequest.getFirstname());
        assertThat(response.jsonPath().getString("lastname"))
                .isEqualTo(bookingRequest.getLastname());
    }

    @Test(priority = 3, dependsOnMethods = "getBooking_shouldReturnCreatedData")
    @Story("Обновление бронирования")
    public void updateBooking_shouldReturn200() {
        CreateBookingRequest updatedRequest = DataGenerator.buildCreateBookingRequest(
                DataGenerator.generateFutureDate(2),
                DataGenerator.generateFutureDate(7)
        );

        step("Отправляем PUT /booking/" + createdBookingId + " с Basic Auth");
        Response response = apiClient.putWithBasicAuth(
                "/booking/" + createdBookingId,
                updatedRequest,
                "admin", "password123"
        );

        assertThat(response.statusCode())
                .as("Ожидали статус 200 при обновлении бронирования")
                .isEqualTo(200);
    }

    @Test(priority = 4, dependsOnMethods = "updateBooking_shouldReturn200")
    @Story("Удаление бронирования")
    public void deleteBooking_shouldReturn201() {
        step("Отправляем DELETE /booking/" + createdBookingId + " с Basic Auth");
        Response response = apiClient.deleteWithBasicAuth(
                "/booking/" + createdBookingId,
                "admin", "password123"
        );

        assertThat(response.statusCode())
                .as("Ожидали статус 201 при удалении бронирования")
                .isEqualTo(201);
    }
}
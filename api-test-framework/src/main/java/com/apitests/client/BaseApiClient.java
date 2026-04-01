package com.apitests.client;

import com.apitests.config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

/**
 * Базовый API клиент — обёртка над Rest Assured.
 * Берёт baseURI и настройки из ConfigManager.
 *
 * Использование:
 *   BaseApiClient client = new BaseApiClient();
 *   Response response = client.get("/users/1");
 *   Response response = client.post("/booking", requestBody);
 */
public class BaseApiClient {

    private final RequestSpecification requestSpec;

    public BaseApiClient() {
        this(ConfigManager.getInstance().getBaseUrl());
    }

    public BaseApiClient(String baseUrl) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("X-Request-ID", UUID.randomUUID().toString())
                .addFilter(new AllureRestAssured()); // логирование запросов в Allure отчёт

        if (ConfigManager.getInstance().getBoolean("log.request")) {
            builder.addFilter(new RequestLoggingFilter());
        }
        if (ConfigManager.getInstance().getBoolean("log.response")) {
            builder.addFilter(new ResponseLoggingFilter());
        }

        this.requestSpec = builder.build();
    }

    // GET запрос
    public Response get(String path) {
        return given(requestSpec)
                .when()
                .get(path)
                .then()
                .extract().response();
    }

    // GET с query параметрами: ?key=value&key2=value2
    public Response get(String path, Map<String, Object> queryParams) {
        return given(requestSpec)
                .queryParams(queryParams)
                .when()
                .get(path)
                .then()
                .extract().response();
    }

    // POST запрос с телом
    public Response post(String path, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract().response();
    }

    // PUT запрос с телом
    public Response put(String path, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .put(path)
                .then()
                .extract().response();
    }

    // PATCH запрос с телом
    public Response patch(String path, Object body) {
        return given(requestSpec)
                .body(body)
                .when()
                .patch(path)
                .then()
                .extract().response();
    }

    // DELETE запрос
    public Response delete(String path) {
        return given(requestSpec)
                .when()
                .delete(path)
                .then()
                .extract().response();
    }

    // POST с Bearer токеном (для авторизованных запросов)
    public Response postWithAuth(String path, Object body, String token) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract().response();
    }

    // PUT с Basic Auth (нужен для Restful Booker API)
    public Response putWithBasicAuth(String path, Object body, String username, String password) {
        return given(requestSpec)
                .auth().basic(username, password)
                .body(body)
                .when()
                .put(path)
                .then()
                .extract().response();
    }

    // DELETE с Basic Auth (нужен для Restful Booker API)
    public Response deleteWithBasicAuth(String path, String username, String password) {
        return given(requestSpec)
                .auth().basic(username, password)
                .when()
                .delete(path)
                .then()
                .extract().response();
    }
}
package com.apitests.tests;

import com.apitests.base.BaseTest;
import com.apitests.models.response.UserResponse;
import com.apitests.utils.SchemaValidator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("User API")
@Feature("GET /users")
public class UserApiTest extends BaseTest {

    private Response response;

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUp() {
        super.setUp();
        step("Выполняем GET /users/1");
        response = usersApiClient.get("/users/1");
    }

    @Test
    @Story("Статус код")
    public void getUserById_shouldReturn200() {
        assertThat(response.statusCode())
                .as("Ожидали статус 200")
                .isEqualTo(200);
    }

    @Test
    @Story("Время ответа")
    public void getUserById_responseTimeShouldBeFast() {
        SchemaValidator.assertResponseTime(response, 3000);
    }

    @Test
    @Story("JSON Schema")
    public void getUserById_shouldMatchJsonSchema() {
        SchemaValidator.validateJsonSchema(response, "user-response-schema.json");
    }

    @Test
    @Story("Поля ответа")
    public void getUserById_bodyFieldsShouldBeCorrect() {
        UserResponse user = response.as(UserResponse.class);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getName()).isNotBlank();
        assertThat(user.getEmail()).contains("@");
        assertThat(user.getUsername()).isNotBlank();
    }
}
package com.apitests.utils;

import com.apitests.config.ConfigManager;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Утилита для валидации JSON/XSD схем и времени ответа.
 * Все методы статические.
 *
 * Использование:
 *   SchemaValidator.validateJsonSchema(response, "user-response-schema.json");
 *   SchemaValidator.assertResponseTime(response, 3000);
 */
public class SchemaValidator {

    private SchemaValidator() {}

    /**
     * Валидация JSON Schema.
     * @param response  — ответ от API
     * @param schemaFileName — только имя файла, например "user-response-schema.json"
     */
    public static void validateJsonSchema(Response response, String schemaFileName) {
        String schemaPath = ConfigManager.getInstance().get("schema.path.json") + schemaFileName;
        MatcherAssert.assertThat(
                response.getBody().asString(),
                JsonSchemaValidator.matchesJsonSchema(new File(schemaPath))
        );
    }

    /**
     * Валидация XSD схемы.
     * @param response      — ответ от API
     * @param xsdFileName   — только имя файла, например "booking-response.xsd"
     */
    public static void validateXmlSchema(Response response, String xsdFileName) {
        String schemaPath = ConfigManager.getInstance().get("schema.path.xsd") + xsdFileName;
        MatcherAssert.assertThat(
                response.getBody().asString(),
                io.restassured.matcher.RestAssuredMatchers.matchesXsd(new File(schemaPath))
        );
    }

    /**
     * Проверка что время ответа не превышает maxMillis.
     * @param response   — ответ от API
     * @param maxMillis  — максимально допустимое время в миллисекундах
     */
    public static void assertResponseTime(Response response, long maxMillis) {
        assertThat(response.time())
                .as("Время ответа превысило %d мс (фактически: %d мс)", maxMillis, response.time())
                .isLessThanOrEqualTo(maxMillis);
    }
}
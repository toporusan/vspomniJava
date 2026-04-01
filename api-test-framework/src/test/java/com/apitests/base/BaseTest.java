package com.apitests.base;

import com.apitests.client.BaseApiClient;
import com.apitests.config.ConfigManager;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

/**
 * Базовый класс для всех тест-классов.
 * Наследуй его: public class MyTest extends BaseTest
 *
 * Предоставляет:
 * - apiClient — готовый API клиент
 * - usersApiClient — клиент для jsonplaceholder.typicode.com
 * - setup/teardown хуки
 */
@Listeners(AllureTestNg.class)
public abstract class BaseTest {

    protected BaseApiClient apiClient;
    protected BaseApiClient usersApiClient;

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        System.out.println("=== Запуск тест-сьюта ===");
        RestAssured.useRelaxedHTTPSValidation(); // игнорируем SSL сертификаты в тестах
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.out.println("--- Запуск класса: " + getClass().getSimpleName() + " ---");
        apiClient = new BaseApiClient(ConfigManager.getInstance().getBaseUrl());
        usersApiClient = new BaseApiClient(ConfigManager.getInstance().getUsersBaseUrl());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("--- Завершение класса: " + getClass().getSimpleName() + " ---");
    }

    @AfterSuite(alwaysRun = true)
    public void globalTearDown() {
        System.out.println("=== Тест-сьют завершён ===");
    }

    // Вспомогательный метод — создаёт именованный шаг в Allure отчёте
    @Step("{stepName}")
    protected void step(String stepName) {
        System.out.println("Шаг: " + stepName);
    }
}
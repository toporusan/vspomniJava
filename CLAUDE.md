# vspomniJava — API Testing Learning Project

## Что это за проект
Учебный проект по REST API тестированию с использованием **Rest Assured** (Java).
Структурирован по дням (Day1–Day6), каждый день — новая тема.

## Стек
- **Java 16**
- **Maven** — сборка
- **TestNG 7.11.0** — основной тест-раннер (запускается через testng.xml)
- **JUnit 5 (JUnit Jupiter 5.10.2)** — второй тест-раннер, используется параллельно с TestNG
- **Rest Assured 6.0.0** — основной фреймворк для API тестов
- **Hamcrest** — assertions
- **JavaFaker** — генерация тестовых данных
- **ScribeJava** — OAuth

## Структура
```
src/test/java/
├── Day1/  — базовые HTTP запросы (GET, POST, PUT)
├── Day2/  — создание тела запроса (HashMap, JSON, POJO)
├── Day3/  — заголовки, куки, query/path параметры
├── Day4/  — парсинг JSON-ответов
├── Day5/  — парсинг XML, загрузка/скачивание файлов
└── Day6/  — валидация JSON и XML схем (XSD, JSON Schema)

src/test/resources/ — ресурсы (XSD-схемы и т.д.)
```

## Запуск тестов
```bash
mvn test
```

## Важные моменты
- Проект учебный — тесты гоняются против публичных API
- XSD-схемы лежат в `src/test/resources/`
- JSON-схемы лежат в `src/test/java/Day6/resourse/`
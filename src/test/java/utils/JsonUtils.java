package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Сериализует Java объект в JSON строку.
     *
     * @param object любой Java объект (PetClass, UserClass и т.д.)
     * @return JSON строка

     * Пример:
     *   PetClass pet = new PetClass();
     *   String json = toJson(pet);
     *   // {"id":1,"name":"buddy",...}
     */

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации в JSON", e);
        }
    }

    /**
     * Десериализует JSON строку в Java объект нужного класса.
     *
     * @param json  JSON строка
     * @param clazz класс в который нужно превратить JSON (например PetClass.class)
     * @return объект типа T

     * Пример:
     *   PetClass pet = fromJson(jsonString, PetClass.class);
     */

    public static<T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка десериализации из JSON", e);
        }
    }

    /**
     * Десериализует тело ответа API в Java объект нужного класса.
     * Внутри вызывает fromJson() — просто достаёт строку из Response.
     *
     * @param response ответ от API (Rest Assured Response)
     * @param clazz    класс в который нужно превратить тело ответа
     * @return объект типа T

     * Пример:
     *   PetClass pet = fromResponse(response, PetClass.class);
     */

    public static<T> T fromResponse(Response response, Class<T> clazz) {
        return fromJson(response.getBody().asString(), clazz);
    }

    /**
     * Сериализует Java объект в красиво отформатированный JSON с отступами.
     * Удобно использовать для вывода в консоль через System.out.println().
     *
     * @param object любой Java объект
     * @return JSON строка с отступами

     * Пример вывода:
     *   {
     *     "id" : 1,
     *     "name" : "buddy"
     *   }
     */

    public static String toPrettyJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка форматирования JSON", e);
        }
    }

}
package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.response.Response;

public class XmlUtils {

    private static final XmlMapper xmlMapper;

    static {
        xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Сериализует Java объект в XML строку.
     *
     * @param object любой Java объект (PetClass, UserClass и т.д.)
     * @return XML строка
     *
     * <p>Пример:</p>
     * <pre>{@code
     * PetClass pet = new PetClass();
     * String xml = XmlUtils.toXml(pet);
     * // <PetClass><id>1</id><name>buddy</name></PetClass>
     * }</pre>
     */
    public static String toXml(Object object) {
        try {
            return xmlMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сериализации в XML", e);
        }
    }

    /**
     * Десериализует XML строку в Java объект нужного класса.
     *
     * @param xml   XML строка
     * @param clazz класс в который нужно превратить XML (например PetClass.class)
     * @return объект типа T
     *
     * <p>Пример:</p>
     * <pre>{@code
     * PetClass pet = XmlUtils.fromXml(xmlString, PetClass.class);
     * }</pre>
     */
    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            return xmlMapper.readValue(xml, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка десериализации из XML", e);
        }
    }

    /**
     * Десериализует тело ответа API в Java объект нужного класса.
     * Внутри вызывает fromXml() — просто достаёт строку из Response.
     *
     * @param response ответ от API (Rest Assured Response)
     * @param clazz    класс в который нужно превратить тело ответа
     * @return объект типа T
     *
     * <p>Пример:</p>
     * <pre>{@code
     * PetClass pet = XmlUtils.fromXmlResponse(response, PetClass.class);
     * }</pre>
     */
    public static <T> T fromXmlResponse(Response response, Class<T> clazz) {
        return fromXml(response.getBody().asString(), clazz);
    }
}
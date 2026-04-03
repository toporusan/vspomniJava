package Day6;

import Day6.POJOClass.Category;
import Day6.POJOClass.PetClass;
import Day6.POJOClass.TagsItem;
import utils.JsonUtils;
import utils.SchemaValidatorUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.List;
import static io.restassured.RestAssured.given;
import static utils.JsonUtils.fromResponse;

public class SerilizationDeserilization {


    @Test
    void POSTSetPetSerilization() throws JsonProcessingException {

        Category category = new Category();
        category.setId(1022);
        category.setName("dogs");

        TagsItem tag1 = new TagsItem();
        tag1.setId(2013434);
        tag1.setName("friendly");

        TagsItem tag2 = new TagsItem();
        tag2.setId(2023434);
        tag2.setName("trained");

        PetClass pet = new PetClass();
        pet.setId(171717);
        pet.setName("Polkan");
        pet.setCategory(category);
        pet.setTags(List.of(tag1, tag2));
        pet.setStatus("available");
        pet.setPhotoUrls(List.of("https://images.dog.ceo/breeds/labrador/n02099712_5643.jpg", "https://images.dog.ceo/breeds/husky/n02110185_1469.jpg"));

        System.out.println(pet);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pet);
        System.out.println(json);

        Response response = given()
                .header("Content-Type", "application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .body(pet).when()
                .post("/pet")
                .then().extract().response();

        assertThat(response.statusCode()).as("Статус код должен быть 200").isEqualTo(200);
        SchemaValidatorUtility.JSONSchemavalidator(response, "src/test/java/Day6/resourse/PetClassSchema.json");
        assertThat(response.getBody().as(PetClass.class).getCategory().getId()).as("ID категории должен быть 1022").isEqualTo(1022);
        assertThat(response.getBody().as(PetClass.class).getCategory().getName()).as("Название категории должно быть 'dogs'").isEqualTo("dogs");
        assertThat(response.body().jsonPath().getString("name")).isEqualTo("Polkan");

    }

    @Test
    void GETPetDeserilization() throws JsonProcessingException {

        Response response = given()
                .header("Content-Type", "application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .when().get("/pet/11111111111")
                .then()
                .extract().response();


        PetClass pet = fromResponse(response, PetClass.class);

        assertThat(pet.getName()).as("Имя собаки 'buddy'").isEqualTo("buddy");
        SchemaValidatorUtility.JSONSchemavalidator(response, "src/test/java/Day6/resourse/PetClassSchema.json");


    }
}

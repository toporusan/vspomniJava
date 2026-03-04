package Day5;


import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FileUploadAndDownload {

    // Загрузка одного файла
    @Test
    public void singleFileUpload() {
        File f = new File("src/test/java/Day4/store.json");

        given()
                .baseUri("https://httpbin.org")
                .multiPart("file", f)
                .contentType("multipart/form-data")
                .when()
                .post("/post")
                .then()
                .log().all();

    }

    // Загрузка двух файлов
    @Test
    public void multiFileUpload() {
        File f = new File("src/test/java/Day4/store.json");
        File f2 = new File("src/test/java/Day2/students.json");

        given()
                .baseUri("https://httpbin.org")
                .multiPart("file", f)
                .multiPart("file2", f2)
                .contentType("multipart/form-data")
                .when()
                .post("/post")
                .then()
                .log().all();

    }

    // Скачаивание файлов
    @Test
    public void downloadFile() {

        given()
                .baseUri("https://httpbin.org")
                .contentType("multipart/form-data")
                .when()
                .get("/image/png")
                .then()
                .log().all();
    }


}

package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class subjectCategories {
    String subjectcategoryID;
    String subjectCategoryName;

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    Faker faker=new Faker();



    @BeforeClass
    public void Login() {

        baseURI="https://test.mersys.io";
        Map<String, String> userCredential= new HashMap<>();
        userCredential.put("username", "turkeyts");
        userCredential.put("password", "TechnoStudy123");
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()

                        .contentType(ContentType.JSON)
                        .body(userCredential)

                        .when()
                        .post("/auth/login")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
                ;
        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
        resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void createSubjectCategory() {

        Map<String, String> subjectcategory= new HashMap<>();
        subjectCategoryName = faker.animal().name();
        subjectcategory.put("name", subjectCategoryName);
        subjectcategory.put("code",faker.address().buildingNumber());

        subjectcategoryID=
        given()
                .spec(reqSpec)
                .body(subjectcategory)
                .log().body()

                .when()
                .post("school-service/api/subject-categories")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;
        System.out.println("subjectcategoryID = " + subjectcategoryID);

    }

    @Test(dependsOnMethods = "createSubjectCategory")
    public void createCountryNegative() {

        Map<String, String> subjectcategory= new HashMap<>();
        subjectcategory.put("name", subjectCategoryName);
        subjectcategory.put("code",faker.address().buildingNumber());


                given()
                        .spec(reqSpec)
                        .body(subjectcategory)
                        .log().body()

                        .when()
                        .post("school-service/api/subject-categories")

                        .then()
                        .log().body()
                        .statusCode(400)
                        .body("message", containsString("already"))

        ;
        System.out.println("subjectCategoryName = " + subjectCategoryName);

    }
    @Test(dependsOnMethods = "createCountryNegative")
    public void updateCountry() {
        given()
                .when()
                .then()
        ;
    }
    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry() {
        given()
                .when()
                .then()
        ;
    }
    @Test(dependsOnMethods = "deleteCountry")
    public void deleteeCountryNegative() {
        given()
                .when()
                .then()
        ;
    }
}


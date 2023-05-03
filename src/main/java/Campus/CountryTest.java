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

public class CountryTest {

    String countryID;
    String countryName;
    RequestSpecification reqSpec;
    //ResponseSpecification resSpec;
    Faker faker=new Faker();



    @BeforeClass
    public void Login() {

        baseURI="https://test.mersys.io/";
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
      //  resSpec = new ResponseSpecBuilder()
        //        .expectStatusCode(200)
          //      .expectContentType(ContentType.JSON)
            //    .build();

    }

    @Test
    public void createCountry() {

        Map<String, String> country= new HashMap<>();
        countryName = faker.country().name()+"bbb";
        country.put("name",countryName);
        country.put("code",faker.address().countryCode());

        countryID =
        given()
                .spec(reqSpec)
                .body(country)
                .log().body()

                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")

        ;

        System.out.println("countryID = " + countryID);

    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative() {
        Map<String, String> country= new HashMap<>();
        country.put("name",countryName);
        country.put("code",faker.address().countryCode());

                given()
                        .spec(reqSpec)
                        .body(country)
                        .log().body()

                        .when()
                        .post("school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(400)
                        .body("message", containsString("already"))

        ;

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


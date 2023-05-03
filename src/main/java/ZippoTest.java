import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test(){
        given()

                .when()


                .then()

        ;
    }
    @Test
    public void statusCodeTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                ;
    }
    @Test
    public void checkCountryInResponseBody(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("country", equalTo("United States"))
                .body("places[0].'state'", equalToIgnoringCase("CaliFornia"))
        ;
    }
    @Test
    public void checkHasItem(){

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .statusCode(200)
                .body("places.'place name'", hasItem("Çaputçu Köyü")) //places array inde place name i çapulcu köyü olan var mı ?
        ;
    }
    @Test
    public void bodyArrayHasSizeTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(1)) //places array inin size ı bir mi ?
        ;
    }

    @Test
    public void pathParamTest(){
        given()
                .pathParam("ulke","us")
                .pathParam("postaKod", 90210)
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")

                .then()
                .statusCode(200)
                ;
    }
    @Test
    public void queryParamTest(){

        for (int i = 1; i < 10; i++)

             given()
                     .params("page",i)
                     .log().uri()

                     .when()
                     .get("https://gorest.co.in/public/v2/users")

                     .then()
                     .log().body()
                     .statusCode(200)
                     .body("meta.pagination.page", equalTo(i)) //places array inin size ı bir mi ?
        ;
    }

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup(){

        baseURI = "https://gorest.co.in/public/v1";
        requestSpec = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void ReqResponseSpecTest(){
        given()
                .param("page",1)
                .spec(requestSpec)

                .when()
                .get("users")

                .then()
                .spec(responseSpec)
        ;
    }

    @Test
    public void extractJsonPath(){

        String countryName =

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().path("country")
        ;
        System.out.println("countryName= " + countryName);
        Assert.assertEquals(countryName, "United States");
    }
    @Test
    public void extractJsonPath2(){

        String placeName =

                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("places[0].'place name'")
                ;
        System.out.println("placeName= " + placeName);
        Assert.assertEquals(placeName, "Beverly Hills");
    }

    @Test
    public void POJOOrnegi(){

        User user=

                given()

                        .when()
                        .get("http://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().as(User.class)
                ;

        System.out.println("To Do User: " + user);
        System.out.println("To Do Title: " + user.getTitle());

    }
    @Test
    public void POJOOrnegi2(){

        User user=

                given()

                        .when()
                        .get("http://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().body().as(User.class)
                ;

        System.out.println("To Do User: " + user);
        System.out.println("To Do Title: " + user.getTitle());
        Assert.assertEquals(user.getTitle(), "quis ut nam facilis et officia qui");

    }
    @Test
    public void POJOOrnegi2ninHamCrestliHali(){

                given()

                        .when()
                        .get("http://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("title", equalTo("quis ut nam facilis et officia qui"))
                ;
    }
    @Test
    public void POJOOrnegi3(){

       Boolean b=
        given()

                .when()
                .get("http://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false))
                .extract().path("completed")
        ;
        System.out.println(b);
        Assert.assertEquals(b,"false");
    }








}


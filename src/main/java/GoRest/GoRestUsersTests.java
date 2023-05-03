package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static javafx.beans.binding.Bindings.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class GoRestUsersTests {

    Faker randomUretici = new Faker();
    int userID;

    @Test(enabled = false)
    public void createUserJSON(){

        String rndFullName= randomUretici.name().fullName();
        String rndEmail= randomUretici.internet().emailAddress();

        userID=
        given()
                .header("Authorization", "Bearer 23060d9c3749983801c21e7c973ad070ba75c0b3cd5f45b92f3cec646e10e81d")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+rndFullName+"\", \"gender\":\"female\", \"email\":\""+rndEmail+"\", \"status\":\"active\"}")

                .when()
                .post("https://gorest.co.in/public/v2/users")

                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id");

        ;

    }

    @Test
    public void createUserByMAP(){

        String rndFullName= randomUretici.name().fullName();
        String rndEmail= randomUretici.internet().emailAddress();

        Map<String, String> newUser= new HashMap<>();
        newUser.put("name", rndFullName);
        newUser.put("gender", "female");
        newUser.put("email", rndEmail);
        newUser.put("status", "active");

        userID=
                given()
                        .header("Authorization", "Bearer 23060d9c3749983801c21e7c973ad070ba75c0b3cd5f45b92f3cec646e10e81d")
                        .contentType(ContentType.JSON)
                        .body(newUser)

                        .when()
                        .post("https://gorest.co.in/public/v2/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");
        ;

    }
    @Test(enabled = false)
    public void createUserByClass(){

        String rndFullName= randomUretici.name().fullName();
        String rndEmail= randomUretici.internet().emailAddress();

       UserGoRest newUser= new UserGoRest();
       newUser.setName(rndFullName);
       newUser.setGender("female");
       newUser.setEmail(rndEmail);
       newUser.setStatus("active");

        userID=
                given()
                        .header("Authorization", "Bearer 23060d9c3749983801c21e7c973ad070ba75c0b3cd5f45b92f3cec646e10e81d")
                        .contentType(ContentType.JSON)
                        .body(newUser)

                        .when()
                        .post("https://gorest.co.in/public/v2/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");
        ;

    }
//
    @Test(dependsOnMethods = "createUserByMAP")
    public void getUserByID(){
        given()
                .header("Authorization", "Bearer 23060d9c3749983801c21e7c973ad070ba75c0b3cd5f45b92f3cec646e10e81d")

                .when()
                .get("https://gorest.co.in/public/v2/users/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(userID))
                ;

    }

   RequestSpecification reqSpec;
   ResponseSpecification responseSpec;
   @BeforeClass
   public void Setup(){

       baseURI= "https://gorest.co.in/public/v2/users/";

       reqSpec =  new RequestSpecBuilder()
               .addHeader("Authorization", "Bearer 23060d9c3749983801c21e7c973ad070ba75c0b3cd5f45b92f3cec646e10e81d")
               .setContentType(ContentType.JSON)
               .log(LogDetail.URI)
               .build();
       responseSpec = new ResponseSpecBuilder()
               .expectContentType(ContentType.JSON)
               .expectStatusCode(200)
               .build()
               ;
   }

    @Test(dependsOnMethods = "getUserByID")
    public void updateUser(){

       Map<String, String> updateUser = new HashMap<>();
       updateUser.put("name", "Burcu Cokcan");
        given()
                .spec(reqSpec)
                .body(updateUser)
                .when()
                .put(""+ userID)


                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(userID))
                .body("name", equalTo("Burcu Cokcan"))
                ;

    }
    @Test(dependsOnMethods = "updateUser")
    public void deleteUser(){

                given()
                        .spec(reqSpec)

                        .when()
                        .delete(""+ userID)


                        .then()
                        .statusCode(204)

        ;
    }
    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative(){


        given()
                .spec(reqSpec)

                .when()
                .delete(""+ userID)

                .then()
                .statusCode(404)
        ;
    }

}


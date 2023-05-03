import GoRest.UserGoRest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class PathAndJsonPath {

    @Test
    public void extractPath(){

        int postCode=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                 .then()
                 .log().body()
                 .extract().path("'post code'")
                ;
        System.out.println("postCode: " + postCode);
    }

    @Test
    public void extractJsonPath(){

        int postCode=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().jsonPath().getInt("'post code'")
        ;
        System.out.println("postCode: " + postCode);
    }

    @Test
    public void getUsers(){
        Response response=
        given()
                .when()
                .get("http://gorest.co.in/public/v2/users")
                .then()
                .log().body()
                .extract().response()
                ;
        int idPath=response.path("[2].id");
        int jsonPath=response.jsonPath().getInt("[2].id");
        System.out.println("idPath = " + idPath);
        System.out.println("jsonPath = " + jsonPath);

        UserGoRest[] userPath= response.as(UserGoRest[].class);
        List<UserGoRest> userJsonPath=response.jsonPath().getList("", UserGoRest.class);
        System.out.println("userPath = " + Arrays.toString(userPath));
        System.out.println("userJsonPath = " + userJsonPath);
    }

    @Test
    public void getUsersV1(){

        Response response=
        given()
                .when()
                .get("http://gorest.co.in/public/v1/users")
                .then()
                .log().body()
                .extract().response()
                ;
        List<UserGoRest> userList=response.jsonPath().getList("data", UserGoRest.class);
        System.out.println("userList = " + userList);
    }
}


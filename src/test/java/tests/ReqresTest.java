package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.reqres.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTest {
    @Test
    public void checkCreationUser() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();
        Response response =
                given()
                        .body(user)
                        .log().all()
                        .when()
                        .post("https://reqres.in/api/users")
                        .then()
                        .log().all()
                        .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_CREATED, "Error of user creation");
    }

    @Test
    public void checkListUsers() {
        Response response =
                given()
                        .log().all()
                        .when()
                        .get("https://reqres.in/api/users?page")
                        .then()
                        .log().all()
                        .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error of users list");
    }

    @Test
    public void checkSingleUser() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("support.url", equalTo("https://reqres.in/#support-heading"));

    }

    @Test
    public void verifyWhenSingleUserNotFoundTest() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void verifyListResourceTest() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void verifySingleResourceTest() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("data.name", equalTo("fuchsia rose"));
    }

    @Test
    public void verifySingleResourceNotFoundTest() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void verifyUpdateTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .log().all()
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void verifyUpdateUserInfoTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .log().all()
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void verifyDeleteUserTest() {
        given()
                .log().all()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_NO_CONTENT);
    }

    @Test
    public void verifyRegisterSuccessfulTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void verifyRegistrUnsuccessfulTest() {
        User user = User.builder()
                .email("sydney@fife")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void verifyLoginSuccessfulTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        given()
                .body(user)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void verifyUnsuccessfulLoginTest() {
        User user = User.builder()
                .email("peter@klaven")
                .build();
        given()
                .body(user)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void verifyDelayedResponseTest() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are " +
                        "appreciated!"));
    }


}



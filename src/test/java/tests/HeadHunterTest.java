package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import objects.head_hunter.VacansisList;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class HeadHunterTest {
    @Test
    public void checkVacancyList() {
        String body =
        given()
                .log().all()
                .when()
                .get("https://api.hh.ru/vacancies?text=QA automation")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();
        VacansisList vacansisList = new Gson().fromJson(body, VacansisList.class);
        int salaryTo = vacansisList.getItems().get(1).getSalary().getTo();
        System.out.println(salaryTo);
        VacansisList vacansisListWithoutExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(body, VacansisList.class);
        System.out.println(vacansisList);
        System.out.println(vacansisListWithoutExpose);

    }
}

package niffler.api.service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import niffler.model.SpendDto;


import java.util.List;

import static io.restassured.RestAssured.given;

public class SpendClient {

    public ValidatableResponse postSpend(SpendDto body) {
        return given()
                .spec(requestSpec())
                .body(body)
                .post("/addSpend")
                .then();
    }

    public ValidatableResponse getSpend(SpendDto body) {
        return given()
                .spec(requestSpec())
                .queryParams("username", body.getUsername())
                .get("/spends")
                .then();
    }

    public ValidatableResponse deleteSpend(SpendDto body) {
        return given()
                .spec(requestSpec())
                .param("username", body.getUsername())
                .param("ids", List.of(body.getId()))
                .delete("/deleteSpends")
                .then();
    }

    private RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8093")
                .build();
    }
}

package org.example.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.api.model.Courier;
import org.example.api.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH_CREATED = "/api/v1/courier";
    private static final String PATH_LOGIN = "/api/v1/courier/login";
    private static final String PATH_DELETE = "/api/v1/courier:id";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH_CREATED)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierCredentials courierCredentials) {

        return given()
                .spec(getSpec())
                .body(courierCredentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .body(id)
                .when()
                .delete(PATH_DELETE)
                .then();
    }
}
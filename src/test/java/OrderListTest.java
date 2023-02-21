import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.api.client.CourierClient;
import org.example.api.client.OrderClient;
import org.example.api.model.Courier;
import org.example.api.model.CourierCredentials;
import org.example.api.model.OrderList;
import org.example.api.util.CourierGenerator;
import org.example.api.util.OrderListGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {
    private OrderList orderList;
    private OrderClient orderClient;
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }

    @DisplayName("Получение списка заказов с валидными данными")
    @Test
    public void orderListCanBeGet() {
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreate = courierClient.createCourier(courier); //создание курьера
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierCredentials); //логин курьера
        int id = responseLogin.extract().path("id");
        orderList = OrderListGenerator.getValidOrderList(id);
        ValidatableResponse responseGet = orderClient.getOrder(orderList);
        int statusCode = responseGet.extract().statusCode();
        assertEquals(SC_OK, statusCode);
        assertNotNull(responseGet.extract().path("orders"));
    }

    @DisplayName("Получение дефолтного списка заказов")
    @Test
    public void getDefaultOrderList() {
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreate = courierClient.createCourier(courier); //создание курьера
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierCredentials);
        orderList = OrderListGenerator.getDefaultList();
        ValidatableResponse responseGet = orderClient.getOrder(orderList);
        int statusCode = responseGet.extract().statusCode();
        assertEquals(SC_OK, statusCode);
        assertNotNull(responseGet.extract().path("orders"));
    }

    @DisplayName("Получение списка заказов с некорректным ид курьера")
    @Test
    public void getOrderListWithIncorrectIdCourier() {
        int loginCourier = ThreadLocalRandom.current().nextInt(800000, 900000);
        orderList = OrderListGenerator.getValidOrderList(loginCourier);
        ValidatableResponse responseGet = orderClient.getOrder(orderList);
        int statusCode = responseGet.extract().statusCode();
        String expectedErrorText = "Курьер с идентификатором" + loginCourier + "не найден";
        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals(expectedErrorText, responseGet.extract().path("message"));
    }
}

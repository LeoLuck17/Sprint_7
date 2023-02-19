import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class OrderListTest {
    private OrderList orderList;
    private OrderClient orderClient;
    private Courier courier;
    private CourierClient courierClient;
    @Before
    public void setUp(){
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }
    @Test
    public void orderListCanBeGet(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreate = courierClient.createCourier(courier); //создание курьера
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierCredentials); //логин курьера
        int id = responseLogin.extract().path("id");
        orderList = OrderListGenerator.getValidOrderList(id);
        ValidatableResponse responseGet = orderClient.getOrder(orderList);
        int statusCode = responseGet.extract().statusCode();
        ArrayList<String> bodyOrderList = responseGet.extract().path("orders");
        assertEquals(SC_OK, statusCode);
        System.out.println(bodyOrderList);
    }
    @Test
    public void getDefaultOrderList(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreate = courierClient.createCourier(courier); //создание курьера
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierCredentials);
        orderList = OrderListGenerator.getDefaultList();
        ValidatableResponse responseGet = orderClient.getOrder(orderList);
        int statusCode = responseGet.extract().statusCode();
        ArrayList<String> bodyOrderList = responseGet.extract().path("orders");
        assertEquals(SC_OK, statusCode);
        System.out.println(bodyOrderList);
    }
    @Test
    public void getOrderListWithIncorrectIdCourier(){
        int loginCourier = ThreadLocalRandom.current().nextInt(800000,900000);
        orderList = OrderListGenerator.getValidOrderList(loginCourier);
        ValidatableResponse responseGet = orderClient.getOrder(orderList);
        int statusCode = responseGet.extract().statusCode();
        String expectedErrorText = "Курьер с идентификатором"+loginCourier + "не найден";
        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals(expectedErrorText, responseGet.extract().path("message"));
    }
}

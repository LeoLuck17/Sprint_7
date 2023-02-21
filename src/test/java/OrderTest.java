import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.api.client.OrderClient;
import org.example.api.model.Order;
import org.example.api.util.OrderGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderTest {
    private final String[] color;
    OrderClient orderClient;

    public OrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Авторизация пользователя. Тестовые данные: {0}")
    public static String[][][] setData() {
        return new String[][][]{
                {{"BLACK"}},
                {{"GREY"}},
                {{"BLACK", "GREY"}},
                {{null}}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void orderCanBeCreated() {
        Order order = OrderGenerator.getValidOrder(color);
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        assertNotNull(response.extract().path("track"));
    }
}

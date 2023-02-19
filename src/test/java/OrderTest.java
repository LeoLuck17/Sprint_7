import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.example.OrderClient;
import org.example.OrderGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class OrderTest {
    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }
    OrderClient orderClient;
    private final String[] color;

    public OrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static String[][][] setData(){
        return new String[][][]{
                {{"BLACK"}},
                {{"GREY"}},
                {{"BLACK", "GREY"}},
                {{null}}
        };
    }
    @Test
    public void orderCanBeCreated(){
        System.out.println(Arrays.toString(color));
        Order order = OrderGenerator.getValidOrder(color);
        System.out.println(order);
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        int trackOrder = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        System.out.println(trackOrder);
    }
}

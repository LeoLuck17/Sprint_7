import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierTest  {

    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp(){
        courierClient.delete(id);
    }

    @Test
    public void  courierCanBeCreated(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        boolean responseBody = response.extract().path("ok");
        assertEquals(SC_CREATED, statusCode);
        assertTrue(responseBody);
    }
    @Test
    public void cannotCreatedDoubleCourier(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse response = courierClient.createCourier(courier);
        ValidatableResponse responseDouble = courierClient.createCourier(courier);
        int successfulStatusCode = response.extract().statusCode();
        int failedStatusCode = responseDouble.extract().statusCode();
        String actualTextFailedRequest = responseDouble.extract().path("message");
        assertEquals(SC_CREATED, successfulStatusCode);
        assertEquals(SC_CONFLICT, failedStatusCode);
        assertEquals("Этот логин уже используется", actualTextFailedRequest); //баг в теле ответа
    }
    @Test
    public void createdCourierWithoutPassword(){
        courier = CourierGenerator.getCourierWithoutPassword();
        ValidatableResponse response = courierClient.createCourier(courier);
        int failedStatusCode = response.extract().statusCode();
        String actualTextFailedRequest = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, failedStatusCode);
        assertEquals("Недостаточно данных для создания учетной записи", actualTextFailedRequest);
    }
    @Test
    public void createdCourierWithoutLogin(){
        courier = CourierGenerator.getCourierWithoutLogin();
        ValidatableResponse response = courierClient.createCourier(courier);
        int failedStatusCode = response.extract().statusCode();
        String actualTextFailedRequest = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, failedStatusCode);
        assertEquals("Недостаточно данных для создания учетной записи", actualTextFailedRequest);
    }
    @Test
    public void createdCourierWithoutFirstName(){
        courier = CourierGenerator.getCourierWithoutFirstName();
        ValidatableResponse response = courierClient.createCourier(courier);
        int failedStatusCode = response.extract().statusCode();
        String actualTextFailedRequest = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, failedStatusCode); //в документации не указано, что FirstName не обязательный
        assertEquals("Недостаточно данных для создания учетной записи", actualTextFailedRequest);
    }
    @Test
    public void courierCanBeLogin(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreated = courierClient.createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierCredentials);
        int createdStatusCde = responseCreated.extract().statusCode();
        int successfulStatusCode = responseLogin.extract().statusCode();
        id = responseLogin.extract().path("id");
        assertEquals(SC_CREATED,createdStatusCde);
        assertEquals(SC_OK, successfulStatusCode);
    }
    @Test
    public void courierLoginWithoutLogin(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreated = courierClient.createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(null, courier.getPassword());
        ValidatableResponse responseLogin = courierClient.login(courierCredentials);
        int createdStatusCde = responseCreated.extract().statusCode();
        int loginStatusCode = responseLogin.extract().statusCode();
        String actualTextFailedRequest = responseLogin.extract().path("message");
        assertEquals(SC_CREATED,createdStatusCde);
        assertEquals(SC_BAD_REQUEST, loginStatusCode);
        assertEquals("Недостаточно данных для входа", actualTextFailedRequest);
    }
    @Test
    public void courierLoginWithoutPassword(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreated = courierClient.createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), null);
        ValidatableResponse responseLogin = courierClient.login(courierCredentials);
        int createdStatusCde = responseCreated.extract().statusCode();
        int loginStatusCode = responseLogin.extract().statusCode();
        String actualTextFailedRequest = responseLogin.extract().path("message");
        assertEquals(SC_CREATED,createdStatusCde);
        assertEquals(SC_BAD_REQUEST, loginStatusCode);
        assertEquals("Недостаточно данных для входа", actualTextFailedRequest);
    }
    @Test
    public void courierLoginWithInvalidLoginPasswordPair(){
        courier = CourierGenerator.getValidCourier();
        ValidatableResponse responseCreated = courierClient.createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), GenerateRandomData.generateRandomData(7));
        ValidatableResponse responseLogin = courierClient.login(courierCredentials);
        int createdStatusCde = responseCreated.extract().statusCode();
        int failedStatusCode = responseLogin.extract().statusCode();
        String actualTextFailedRequest = responseLogin.extract().path("message");
        assertEquals(SC_CREATED,createdStatusCde);
        assertEquals(SC_NOT_FOUND, failedStatusCode);
        assertEquals("Учетная запись не найдена", actualTextFailedRequest);
    }
    @Test
    public void courierLoginWithNonexistentCredentials() {
        CourierCredentials courierCredentials = new CourierCredentials(GenerateRandomData.generateRandomData(6), GenerateRandomData.generateRandomData(7));
        ValidatableResponse responseLogin = courierClient.login(courierCredentials);
        int failedStatusCode = responseLogin.extract().statusCode();
        String actualTextFailedRequest = responseLogin.extract().path("message");
        assertEquals(SC_NOT_FOUND, failedStatusCode);
        assertEquals("Учетная запись не найдена", actualTextFailedRequest);
    }
}
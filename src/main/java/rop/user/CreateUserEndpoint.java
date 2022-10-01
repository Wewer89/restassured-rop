package rop.user;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pojo.apiresponse.ApiResponse;
import pojo.user.User;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class CreateUserEndpoint extends BaseEndpoint<CreateUserEndpoint, ApiResponse> {

    User user;

    public CreateUserEndpoint setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    protected Type getModelType() {
        return ApiResponse.class;
    }

    @Step("Create User")
    @Override
    public CreateUserEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .body(user)
                .when()
                .post("/user");
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}

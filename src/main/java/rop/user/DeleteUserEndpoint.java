package rop.user;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pojo.apiresponse.ApiResponse;
import pojo.user.User;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class DeleteUserEndpoint extends BaseEndpoint<DeleteUserEndpoint, ApiResponse> {

    String userName;

    public DeleteUserEndpoint setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    protected Type getModelType() {
        return ApiResponse.class;
    }

    @Step("Delete User")
    @Override
    public DeleteUserEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .when()
                .delete("/user/{username}", userName);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}

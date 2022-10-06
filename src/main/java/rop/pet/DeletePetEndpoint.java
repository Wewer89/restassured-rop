package rop.pet;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pojo.apiresponse.ApiResponse;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class DeletePetEndpoint extends BaseEndpoint<DeletePetEndpoint, ApiResponse> {
    Integer petId;

    public DeletePetEndpoint setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }

    @Override
    protected Type getModelType() {
        return ApiResponse.class;
    }

    @Step("Delete Pet")
    @Override
    public DeletePetEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .when()
                .delete("/pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}

package rop.pet;

import org.apache.http.HttpStatus;
import pojo.apiresponse.ApiResponse;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class UpdatePetNameStatusEndpoint extends BaseEndpoint<UpdatePetNameStatusEndpoint, ApiResponse> {

    private Integer petId;
    private String petName;
    private String status;

    public UpdatePetNameStatusEndpoint setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }

    public UpdatePetNameStatusEndpoint setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public UpdatePetNameStatusEndpoint setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    protected Type getModelType() {
        return ApiResponse.class;
    }

    @Override
    public UpdatePetNameStatusEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getUrlEncodedSpecBuilder())
                .formParam("name", petName)
                .formParam("status", status)
                .when()
                .post("pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}

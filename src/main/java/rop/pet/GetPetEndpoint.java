package rop.pet;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pojo.pet.Pet;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class GetPetEndpoint extends BaseEndpoint<GetPetEndpoint, Pet> {


    Integer petId;

    public GetPetEndpoint setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }

    @Override
    protected Type getModelType() {
        return Pet.class;
    }

    @Step("Get Pet")
    @Override
    public GetPetEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .when()
                .get("/pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}

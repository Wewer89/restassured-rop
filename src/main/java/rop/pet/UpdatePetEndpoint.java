package rop.pet;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pojo.pet.Pet;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class UpdatePetEndpoint extends BaseEndpoint<UpdatePetEndpoint, Pet> {

    Pet pet;

    public UpdatePetEndpoint setPet(Pet pet) {
        this.pet = pet;
        return this;
    }

    @Override
    protected Type getModelType() {
        return Pet.class;
    }

    @Step("Update Pet")
    @Override
    public UpdatePetEndpoint sendRequest() {
        response = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .body(pet)
                .when()
                .put("/pet");
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}

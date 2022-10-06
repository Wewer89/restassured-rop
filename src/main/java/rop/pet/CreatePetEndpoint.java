package rop.pet;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pojo.pet.Pet;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class CreatePetEndpoint extends BaseEndpoint<CreatePetEndpoint, Pet> {

  Pet pet;

  public CreatePetEndpoint setPet(Pet pet) {
    this.pet = pet;
    return this;
  }

  @Override
  protected Type getModelType() {
    return Pet.class;
  }

  @Step("Create Pet")
  @Override
  public CreatePetEndpoint sendRequest() {
    response =
        given()
            .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
            .body(pet)
            .when()
            .post("/pet");
    return this;
  }

  @Override
  protected int getSuccessStatusCode() {
    return HttpStatus.SC_OK;
  }
}

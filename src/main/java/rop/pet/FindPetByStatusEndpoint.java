package rop.pet;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import pojo.pet.Pet;
import request.configuration.RequestConfigurationBuilder;
import rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class FindPetByStatusEndpoint extends BaseEndpoint<FindPetByStatusEndpoint, Pet[]> {

  String petStatus;

  public FindPetByStatusEndpoint setPetStatus(String petStatus) {
    this.petStatus = petStatus;
    return this;
  }

  @Override
  protected Type getModelType() {
    return Pet[].class;
  }

  @Step("Get Pets by status")
  @Override
  public FindPetByStatusEndpoint sendRequest() {
    given()
        .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
        .queryParam("status", petStatus)
        .when()
        .get("/pet/findByStatus");
    return this;
  }

  @Override
  protected int getSuccessStatusCode() {
    return HttpStatus.SC_OK;
  }
}

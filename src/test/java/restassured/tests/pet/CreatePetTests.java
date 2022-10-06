package restassured.tests.pet;

import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pojo.pet.Pet;
import restassured.tests.testbases.SuiteTestBase;
import rop.pet.CreatePetEndpoint;
import test.data.PetTestDataGenerator;

public class CreatePetTests extends SuiteTestBase {
  private Pet pet;

  @Issue("DEFECT-1")
  @TmsLink("ID-1")
  @Severity(SeverityLevel.CRITICAL)
  @Test
  @Description(
      "The goal of this test is to create pet and check if returned Pet object is the same")
  public void givenPetWhenPostPetThenPetIsCreatedTest() {
    pet = new PetTestDataGenerator().generatePate();
    petIdToDelete = pet.getId();

    /*        Pet actualPet = given()
    .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
    .body(pet)
    .when()
    .post("pet")
    .then()
    .assertThat()
    .statusCode(HttpStatus.SC_OK)
    .extract()
    .as(Pet.class);*/

    Pet actualPet =
        new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();

    /*        Assert.assertEquals(actualPet.getId(), pet.getId(), "Pet id is incorrect");
    Assert.assertEquals(actualPet.getStatus(), pet.getStatus(), "Pet status is incorrect");*/

    // Klas Assertions pozwala na porównywanie POJO
    Assertions.assertThat(actualPet)
        .describedAs("Pet was not created by API")
        .usingRecursiveComparison()
        .isEqualTo(this.pet);
  }
}

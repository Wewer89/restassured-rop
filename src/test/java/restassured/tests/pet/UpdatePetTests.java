package restassured.tests.pet;

import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo.pet.Pet;
import restassured.tests.testbases.SuiteTestBase;
import rop.pet.CreatePetEndpoint;
import rop.pet.GetPetEndpoint;
import rop.pet.UpdatePetEndpoint;
import test.data.PetTestDataGenerator;

public class UpdatePetTests extends SuiteTestBase {

  public Pet petBeforeUpdate;
  public Pet newPet;
  @BeforeTest
  public void beforeTest() {
    Pet pet = new PetTestDataGenerator().generatePate();
    petIdToDelete = pet.getId();

    petBeforeUpdate =
        new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();
  }

  @Issue("DEFECT-3")
  @TmsLink("ID-3")
  @Severity(SeverityLevel.CRITICAL)
  @Description("The goal of this test is to update pet and check if it was updated")
  @Test
  public void givenPetWhenPetGetsUpdatedThenPetIsUpdatedTest() {
    newPet = new PetTestDataGenerator().generatePate();
    newPet.setId(petBeforeUpdate.getId());

    Pet updatedPet =
        new UpdatePetEndpoint()
            .setPet(newPet)
            .sendRequest()
            .assertRequestSuccess()
            .getResponseModel();

    Assertions.assertThat(updatedPet)
        .describedAs("Updated pet was the same as create pet")
        .usingRecursiveComparison()
        .isNotEqualTo(petBeforeUpdate);

    Assertions.assertThat(updatedPet)
        .describedAs("Updated pet was not the same as create pet")
        .usingRecursiveComparison()
        .isEqualTo(newPet);

    Pet petAfterUpdate =
        new GetPetEndpoint()
            .setPetId(newPet.getId())
            .sendRequest()
            .assertRequestSuccess()
            .getResponseModel();

    Assertions.assertThat(petAfterUpdate)
        .describedAs("Updated pet was the same as create pet")
        .usingRecursiveComparison()
        .isNotEqualTo(petBeforeUpdate);

    Assertions.assertThat(petAfterUpdate)
            .describedAs("Updated pet was not the same as create pet")
            .usingRecursiveComparison()
            .isEqualTo(newPet);
  }
}

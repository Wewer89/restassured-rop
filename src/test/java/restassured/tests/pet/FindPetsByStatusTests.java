package restassured.tests.pet;

import enums.PetStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.Assertions;
import org.testng.annotations.*;
import pojo.pet.Pet;
import restassured.tests.testbases.SuiteTestBase;
import rop.pet.CreatePetEndpoint;
import rop.pet.DeletePetEndpoint;
import rop.pet.FindPetByStatusEndpoint;
import test.data.PetTestDataGenerator;

import java.util.LinkedList;
import java.util.List;

public class FindPetsByStatusTests extends SuiteTestBase {

  private List<Pet> availablePetsList = new LinkedList<>();
  private String status = PetStatus.AVAILABLE.getStatus();

  @BeforeMethod
  public void createPets() {
    for (int i = 0; i < 3; i++) {
      Pet pet = new PetTestDataGenerator().generatePate();
      pet.setStatus(status);
      Pet createdPet = new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();
      availablePetsList.add(createdPet);
    }
  }

  @TmsLink("ID-7")
  @Severity(SeverityLevel.NORMAL)
  @Description("The goal of this test is to find pets by status available")
  @Test
  public void givenPetWithStatusAvailableWhenFindByStatusAvailableThenPetsAreFound() {
    Pet[] actualPets = new FindPetByStatusEndpoint().setPetStatus(status).sendRequest().assertRequestSuccess().getResponseModel();

    Assertions.assertThat(actualPets)
            .describedAs("Actual pets does not contains pet with status available")
            .usingRecursiveFieldByFieldElementComparator()
            .containsAll(availablePetsList);
  }

  @AfterMethod
  public void cleanUpAfterTest() {
    availablePetsList.forEach(pet -> new DeletePetEndpoint().setPetId(pet.getId()).sendRequest().assertRequestSuccess());
  }

}

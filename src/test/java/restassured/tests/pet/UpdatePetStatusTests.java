package restassured.tests.pet;

import enums.PetStatus;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.*;
import pojo.apiresponse.ApiResponse;
import pojo.pet.Pet;
import restassured.tests.testbases.SuiteTestBase;
import rop.pet.*;
import test.data.PetTestDataGenerator;

public class UpdatePetStatusTests extends SuiteTestBase {

  Pet petBeforeUpdate;
  ApiResponse expectedApiResponse;

  @BeforeTest
  public void createPetBeforeTest() {
    petBeforeUpdate = new PetTestDataGenerator().generatePate();
    petBeforeUpdate.setStatus("invalid status");
    petIdToDelete = petBeforeUpdate.getId();

    new CreatePetEndpoint().setPet(petBeforeUpdate).sendRequest().assertRequestSuccess();
  }

  @Issue("DEFECT-6")
  @TmsLink("ID-6")
  @Severity(SeverityLevel.CRITICAL)
  @TmsLink("")
  @Description("The goal of this test is to update pet name or/and petStatus")
  @Test(dataProvider = "petStatusAndName")
  public void givenPetWhenPetGetsUpdatedNameOrStatusThenPetIsUpdatedTest(PetStatus petStatus) {
    ApiResponse actualResponseModel =
        new UpdatePetNameStatusEndpoint()
            .setPetId(petIdToDelete)
            .setPetName("Toby")
            .setStatus(petStatus.getStatus())
            .sendRequest()
            .assertRequestSuccess()
            .getResponseModel();

    expectedApiResponse = new ApiResponse();
    expectedApiResponse.setCode(HttpStatus.SC_OK);
    expectedApiResponse.setType("unknown");
    expectedApiResponse.setMessage(petIdToDelete.toString());

    Assertions.assertThat(actualResponseModel)
        .describedAs("Pet was not updated by API")
        .usingRecursiveComparison()
        .isEqualTo(expectedApiResponse);

    Pet petAfterUpdate =
        new GetPetEndpoint()
            .setPetId(petIdToDelete)
            .sendRequest()
            .assertRequestSuccess()
            .getResponseModel();

    Assertions.assertThat(petAfterUpdate.getStatus())
        .describedAs("Pet statuses are equal")
        .isEqualTo(petStatus.getStatus());
  }

  @DataProvider
  public Object[][] petStatusAndName() {
    return new Object[][] {
      {PetStatus.AVAILABLE}, {PetStatus.PENDING}, {PetStatus.SOLD},
    };
  }
}

package restassured.tests.pet;

import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pojo.apiresponse.ApiResponse;
import pojo.pet.Pet;
import restassured.tests.testbases.SuiteTestBase;
import rop.pet.CreatePetEndpoint;
import rop.pet.DeletePetEndpoint;
import test.data.PetTestDataGenerator;

import static io.restassured.RestAssured.given;

public class CreatePetTests extends SuiteTestBase {
    private Pet pet;

    @Issue("DEFECT-1")
    @TmsLink("ID-1")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Description("The goal of this test is to create pet and check if returned Pet object is the same")
    public void givenPetWhenPostPetThenPetIsCreatedTest() {
        pet = new PetTestDataGenerator().generatePate();

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

        Pet actualPet = new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();

/*        Assert.assertEquals(actualPet.getId(), pet.getId(), "Pet id is incorrect");
        Assert.assertEquals(actualPet.getStatus(), pet.getStatus(), "Pet status is incorrect");*/

        // Klas Assertions pozwala na porównywanie POJO
        Assertions
                .assertThat(actualPet)
                .describedAs("Pet was not created by API")
                .usingRecursiveComparison()
                .isEqualTo(this.pet);
    }

    @AfterMethod
    public void cleanUpAfterTest() {
        Integer petId = pet.getId();

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(petId.toString());

/*        ApiResponse actualApiResponse = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .when()
                .delete("/pet/{petId}", pet.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ApiResponse.class);*/

        ApiResponse actualApiResponse = new DeletePetEndpoint().setPetId(petId).sendRequest().assertRequestSuccess().getResponseModel();

/*        Assert.assertEquals(actualApiResponse.getCode(), Integer.valueOf(200), "Code is incorrect");
        Assert.assertEquals(actualApiResponse.getType(), "unknown", "Type is incorrect");*/

        Assertions
                .assertThat(actualApiResponse)
                .describedAs("Pet was not deleted by API")
                .usingRecursiveComparison()
                .isEqualTo(expectedApiResponse);
    }
}

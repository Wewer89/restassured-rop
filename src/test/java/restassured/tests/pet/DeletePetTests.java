package restassured.tests.pet;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restassured.tests.testbases.SuiteTestBase;
import rop.pet.DeletePetEndpoint;

public class DeletePetTests extends SuiteTestBase {

  Integer nonExistingPetId;

  @BeforeMethod
  public void beforeTest() {
    nonExistingPetId  = new Faker().number().numberBetween(1000, 10_000);
    new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest();
  }

  @Issue("DEFECT-2")
  @TmsLink("ID-2")
  @Severity(SeverityLevel.CRITICAL)
  @Test
  @Description("The goal of this test is to fail to delete non existing pet")
  public void givenNonExistingPetWhenDeletingPetThenPetNotFoundTest() {
    new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
  }
}

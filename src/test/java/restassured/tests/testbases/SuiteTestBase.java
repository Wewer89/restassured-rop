package restassured.tests.testbases;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pojo.apiresponse.ApiResponse;
import properties.EnvironmentConfig;
import rop.pet.DeletePetEndpoint;

import java.util.Optional;

public class SuiteTestBase {

  public Integer petIdToDelete;

  @BeforeSuite
  public void setUp() {
    EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
    RestAssured.baseURI = environmentConfig.baseUri();
    RestAssured.basePath = environmentConfig.basePath();

    RestAssured.filters(
        new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
  }

  /**
   * Optional.ofNullable(petIdToDelete).ifPresent(petIdToDelete -> {}) Interfejs Optional jak głosi
   * jego nazwa, pozwala na opcjonalne użycie/wykonanie czegoś. Tym czymś jest zawartość metody
   * ifPresent(). Kiedy metoda ifPresent() będzie wykonana? Wtedy kiedy wartość w metodzie
   * ofNullable() nie będzie nullem.
   */
  @AfterSuite
  public void cleanUp() {
    Optional.ofNullable(petIdToDelete)
        .ifPresent(
            petIdToDelete -> {
              ApiResponse actualApiResponse =
                  new DeletePetEndpoint()
                      .setPetId(petIdToDelete)
                      .sendRequest()
                      .assertRequestSuccess()
                      .getResponseModel();

              ApiResponse expectedApiResponse = new ApiResponse();
              expectedApiResponse.setCode(HttpStatus.SC_OK);
              expectedApiResponse.setType("unknown");
              expectedApiResponse.setMessage(petIdToDelete.toString());

              Assertions.assertThat(actualApiResponse)
                  .describedAs("API Response from system was not as expected")
                  .usingRecursiveComparison()
                  .isEqualTo(expectedApiResponse);
            });
  }
}

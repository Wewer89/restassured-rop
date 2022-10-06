package restassured.tests.user;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pojo.apiresponse.ApiResponse;
import pojo.user.User;
import restassured.tests.testbases.SuiteTestBase;
import rop.user.CreateUserEndpoint;
import rop.user.DeleteUserEndpoint;
import test.data.UserTestDataGenerator;

import static io.restassured.RestAssured.given;

public class CreateUserTests extends SuiteTestBase {
    private User user;
    private ApiResponse pet;

    @TmsLink("ID-3")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to create new user")
    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {
        user = new UserTestDataGenerator().generateUser();

        pet = new ApiResponse();
        pet.setCode(HttpStatus.SC_OK);
        pet.setType("unknown");
        pet.setMessage((user.getId().toString()));

/*        ApiResponse actualApiResponse = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .body(user)
                .when()
                .post("/user")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ApiResponse.class);*/

        ApiResponse actualPet = new CreateUserEndpoint().setUser(user).sendRequest().assertRequestSuccess().getResponseModel();

/*        Assert.assertEquals(actualApiResponse.getCode(), apiResponse.getCode(), "API code is incorrect");
        Assert.assertEquals(actualApiResponse.getType(), apiResponse.getType(), "API type is incorrect");*/

        Assertions
                .assertThat(actualPet)
                .describedAs("User was not created by API")
                .usingRecursiveComparison()
                .isEqualTo(pet);
    }

    @AfterMethod
    public void cleanUpAfterTest() {
/*        ApiResponse actualApiResponse = given()
                .spec(RequestConfigurationBuilder.getDefaultSpecBuilder())
                .when()
                .delete("/user/{username}", user.getUsername())
                .then()
                .assertThat()
                .extract()
                .as(ApiResponse.class);*/

/*        Assert.assertEquals(actualApiResponse.getCode(), Integer.valueOf(200), "API code is incorrect");
        Assert.assertEquals(actualApiResponse.getType(), "unknown");*/

        ApiResponse actualPet = new DeleteUserEndpoint().setUserName(user.getUsername()).sendRequest().getResponseModel();

        pet.setMessage(user.getUsername());

        Assertions
                .assertThat(actualPet)
                .describedAs("User was not deleted by API")
                .usingRecursiveComparison()
                .isEqualTo(pet);
    }
}

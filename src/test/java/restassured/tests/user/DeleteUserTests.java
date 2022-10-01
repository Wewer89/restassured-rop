package restassured.tests.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restassured.tests.testbases.SuiteTestBase;
import rop.user.DeleteUserEndpoint;

public class DeleteUserTests extends SuiteTestBase {

    String nonExistingUserName;

    @BeforeMethod
    public void beforeTest() {
        nonExistingUserName = new Faker().funnyName().name();
        new DeleteUserEndpoint().setUserName(nonExistingUserName).sendRequest();
    }

    @TmsLink("ID-4")
    @Severity(SeverityLevel.NORMAL)
    @Description("The goal of this test is to fail to delete non-existing user")
    @Test
    public void  givenNonExistingUserWhenDeletingUserThenUserNotFoundTest() {
        new DeleteUserEndpoint().setUserName(nonExistingUserName).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }
}

package request.configuration;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

public class RequestConfigurationBuilder {

    public RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setConfig(getDefaultRestAssuredConfig());
    }

    public static RequestSpecification getDefaultSpecBuilder() {
        return new RequestConfigurationBuilder().getRequestSpecBuilder().build();
    }

    private RequestSpecBuilder getUrlEncodedRequestSpecBuilderI() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.URLENC)
                .setConfig(getDefaultRestAssuredConfig());
    }

    public static RequestSpecification getUrlEncodedSpecBuilder() {
        return new RequestConfigurationBuilder().getUrlEncodedRequestSpecBuilderI().build();
    }

    // Dobrą praktyką jest ustawienie Object Mapper dla każdego żądania
    private RestAssuredConfig getDefaultRestAssuredConfig() {
        return RestAssuredConfig.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON));
    }
}

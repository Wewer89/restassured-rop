package properties;

import org.aeonbits.owner.Config;

@Config.Sources(("classpath:EnvironmentConfig.properties"))
public interface EnvironmentConfig extends Config {

    @Key("base_uri")
    String baseUri();

    @Key("base_path")
    String basePath();
}

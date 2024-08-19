package com.eightaugusto.jenkins.jcasc.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** SwaggerConfig. */
@Configuration
public class SwaggerConfig {

  private static final String APPLICATION_NAME_SEPARATOR = "-";

  /**
   * Swagger OpenAPI.
   *
   * @param applicationName Application Name.
   * @param applicationVersion Application Version.
   * @return OpenApi.
   */
  @Bean
  public OpenAPI openApi(
      @Value("${spring.application.name}") final String applicationName,
      @Value("${spring.application.version}") final String applicationVersion) {
    return new OpenAPI()
        .info(
            new Info()
                .title(applicationName)
                .description(
                    Arrays.stream(applicationName.split(APPLICATION_NAME_SEPARATOR))
                        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                        .collect(Collectors.joining(StringUtils.SPACE)))
                .version(applicationVersion));
  }
}

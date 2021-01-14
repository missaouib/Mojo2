/*
 * Copyright 2020 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.mojo.restapi.generator;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TODO: Fill me in
 */
public class ConfigurationFactoryTests {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog().muteForSuccessfulTests();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();
    Configuration configuration;

    @BeforeEach
    public void setUpEachTime() {
        configuration = ConfigurationFactory.defaultConfiguration();
    }


    @Test
    public void shouldHaveTemplateLoaderAsPartOfTheConfiguration() {
        assertThat(configuration).isNotNull();
        assertThat(configuration.getTemplateLoader()).isNotNull();
    }

    /**
     * Verify the Configuration object is well-defined and that the
     * Configuration can load templates.  A random selection of our templates
     * are used.
     *
     * @param templatePath the path to the template, relative to the "/restapi/templates/"
     * @throws IOException if something goes wrong
     */
    @ParameterizedTest
    @CsvSource(value = {
            "common/Copyright.ftl",
            "build/BuildDotGradle.ftl",
            "main/endpoint/RestController.ftl"
    })
    public void shouldFindTemplates(String templatePath) throws IOException {
        Template template = configuration.getTemplate(templatePath);
        assertThat(template).isNotNull();
    }
}

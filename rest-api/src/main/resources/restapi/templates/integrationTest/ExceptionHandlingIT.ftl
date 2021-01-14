<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.common.AbstractIntegrationTest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Verify exception handling
 */
@ExtendWith(SpringExtension.class)
public class ${endpoint.entityName}ExceptionHandlingIT extends AbstractIntegrationTest {

    @MockBean
    private ${endpoint.entityName}Service ${endpoint.entityVarName}Service;

    @Nested
    public class ExceptionTests {

        @Test
        public void shouldNotReturnStackTrace() throws Exception {
            // given
            given( ${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(any(Long.class))).willThrow(new RuntimeException("Boom!"));
            given( ${endpoint.entityVarName}Service.update${endpoint.entityName}(any(${endpoint.entityName}Resource.class))).willThrow(new RuntimeException("Bad data"));

            ${endpoint.entityName}Resource payload = ${endpoint.entityName}Resource.builder().resourceId(1L).text("update me").build();

            // when/then
            mockMvc.perform(post("${endpoint.basePath}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)))
                    .andExpect(jsonPath("$.stackTrace").doesNotExist())
                    .andDo((print()))
                    .andReturn();
        }
    }
}

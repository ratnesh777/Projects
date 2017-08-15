package com.mars;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebAppConfiguration
@WithMockUser(roles = { "Admin User" }, username ="test@gmail.com")
public abstract class AbstractControllerTest extends AbstractSpringTest
{
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
  
    protected String getNotNullErrorExpression(String resourceName, String fieldName)
    {
        return String.format(
                "$.fieldErrors[?(@.resource == '%s' && @.code == 'NotNull' && @.message == 'may not be null' && @.field == '%s')]",
                resourceName, fieldName);
    }

    protected String getNotBlankErrorExpression(Object resourceName, String fieldName)
    {
        return String.format(
                "$.fieldErrors[?(@.resource == '%s' && @.code == 'NotBlank' && @.message == 'may not be empty' && @.field == '%s')]",
                resourceName, fieldName);
    }

    protected String getNotValidEmailErrorExpression(Object resourceName, String fieldName)
    {
        return String.format(
                "$.fieldErrors[?(@.resource == '%s' && @.code == 'Email' && @.message == 'not a well-formed email address' && @.field == '%s')]",
                resourceName, fieldName);
    }

    protected String getNotValidIpAddressErrorExpression(Object resourceName, String fieldName)
    {
        return String.format(
                "$.fieldErrors[?(@.resource == '%s' && @.code == 'Pattern' && @.message == 'should be valid IPv4 address' && @.field == '%s')]",
                resourceName, fieldName);
    }

    protected String getContextItem(String key, String value)
    {
        return String.format("$.errorContext[?(@.key == '%s' && @.value == '%s')]", key, value);
    }
}

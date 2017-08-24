package com.mars;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.mars.models.Login;
import com.mars.mongo.repository.entity.Customer;
import com.mars.mongo.repository.entity.Role;
import com.mars.mongo.repository.entity.User;


public abstract class AbstractTest
{
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected User composeUserModel()
    {
        User model = new User();
        model.setId("1");
        model.setFirstName("FN");
        model.setLastName("LN");
        model.setEmail("test@mars.com");
        Role roleModel = new Role();
        roleModel.setId("2");
        roleModel.setName("role");
        model.setRole(roleModel);
        Customer companyModel = new Customer();
        companyModel.setId("1000");
        companyModel.setName("company");
        model.setCompany(companyModel);
        model.setPassword("some password");
        return model;
    }

    protected Customer getCompanyModel()
    {
        Customer company = new Customer();
        company.setId("1001");
        company.setName("Mars-TEST");
        company.setEmailDomain("@mars.com");
        

        return company;
    }

    protected Login getLoginModel()
    {
        Login login = new Login();
        login.setEmail("test@mars.com");
        login.setPassword("WElcome12#");
        return login;
    }
}

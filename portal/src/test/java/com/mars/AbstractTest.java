package com.mars;

import static java.util.Arrays.asList;

import java.util.HashSet;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.mars.models.BackRoom;
import com.mars.models.Company;
import com.mars.models.Login;
import com.mars.models.Role;
import com.mars.models.User;
import com.mars.repository.entity.BackRoomEntity;
import com.mars.repository.entity.CompanyEntity;
import com.mars.repository.entity.RoleEntity;
import com.mars.repository.entity.UserEntity;
import com.mars.repository.entity.UserStatus;

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
        model.setEmail("test@ipc.com");
        model.setStatus(UserStatus.ACTIVATED);
        Role roleModel = new Role();
        roleModel.setId("2");
        roleModel.setName("role");
        model.setRole(roleModel);
        Company companyModel = new Company();
        companyModel.setId("1000");
        companyModel.setName("company");
        model.setCompany(companyModel);
        model.setPassword("some password");
        model.setSiteId("siteId");
        return model;
    }

    protected UserEntity getUserEntity()
    {
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setFirstName("FN");
        entity.setLastName("LN");
        entity.setEmail("test@ipc.com");
        entity.setStatus(UserStatus.ACTIVATED);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(2);
        roleEntity.setName("role");
        entity.setRole(roleEntity);
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1000);
        companyEntity.setName("company");
        entity.setCompany(companyEntity);
        entity.setPassword("some password");
        entity.setSiteId("siteId");
        return entity;
    }

    protected Company getCompanyModel()
    {
        Company company = new Company();
        company.setId("1001");
        company.setName("IPC-TEST");
        return company;
    }

    protected CompanyEntity getCompanyEntity()
    {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1001);
        companyEntity.setName("IPC-TEST");
        return companyEntity;
    }

    protected Login getLoginModel()
    {
        Login login = new Login();
        login.setEmailId("test@gmail.com");
        login.setPassword("WElcome12#");
        return login;
    }

    protected UserEntity getEntity()
    {
        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setName("IPC Operations");
        userEntity.setRole(roleEntity);
        userEntity.setPassword("$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO");
        userEntity.setStatus(UserStatus.REGISTERED);
        return userEntity;
    }

    protected BackRoomEntity getBackRoomEntity()
    {
        BackRoomEntity entity = new BackRoomEntity();
        entity.setId(1);
        entity.setName("back room 1");
        entity.setHomeZoneIP("11.11.11.11");
        entity.setManagementProxyIP("12.22.22.22");
        entity.setVoipProxyIP("13.33.33.33");
        entity.setCompany(getCompanyEntity());
        entity.setUsers(new HashSet<>(asList(getUserEntity())));
        return entity;
    }

    protected BackRoom getBackRoomModel()
    {
        BackRoom model = new BackRoom();
        model.setId("1");
        model.setName("back room 1");
        model.setHomeZoneIP("11.11.11.11");
        model.setManagementProxyIP("12.22.22.22");
        model.setVoipProxyIP("13.33.33.33");
        model.setCompany(getCompanyModel());
        model.setUsers(asList(composeUserModel()));
        return model;
    }
}

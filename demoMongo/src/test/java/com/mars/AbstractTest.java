package com.mars;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/*import com.ipc.models.Login;
import com.ipc.mongo.repository.entity.Customer;
import com.ipc.mongo.repository.entity.DataCenter;
import com.ipc.mongo.repository.entity.Endpoint;
import com.ipc.mongo.repository.entity.HomeZone;
import com.ipc.mongo.repository.entity.Product;
import com.ipc.mongo.repository.entity.Role;
import com.ipc.mongo.repository.entity.Ticket;
import com.ipc.mongo.repository.entity.Turret;
import com.ipc.mongo.repository.entity.User;
import com.ipc.repository.entity.UserStatus;
*/
public abstract class AbstractTest
{
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

  /*  protected User composeUserModel()
    {
        User model = new User();
        model.setId("1");
        model.setFirstName("FN");
        model.setLastName("LN");
        model.setEmail("test@ipc.com");
        model.setStatus(UserStatus.ACTIVATED.name());
        Role roleModel = new Role();
        roleModel.setId("2");
        roleModel.setName("role");
        model.setRole(roleModel);
        Customer companyModel = new Customer();
        companyModel.setId("1000");
        companyModel.setName("company");
        model.setCompany(companyModel);
        model.setPassword("some password");
        model.setSiteId("siteId");
        return model;
    }

    protected Customer getCompanyModel()
    {
        Customer company = new Customer();
        company.setId("1001");
        company.setName("IPC-TEST");
        company.setEmailDomain("@ipc.com");
        company.setNtrServerFQDN("cloudPortal.ipc.com");
        company.setNtrURL("http://unigyapp.com");
        company.setSiteID("siteId1");
        company.setSkypeURL("https://www.skype.com/en/business");
        company.setSoftClientURL("http://unigyapp.com");

        return company;
    }

    protected Login getLoginModel()
    {
        Login login = new Login();
        login.setEmail("test@gmail.com");
        login.setPassword("WElcome12#");
        return login;
    }

    protected User getEntity()
    {
        User userEntity = new User();
        Role roleEntity = new Role();
        roleEntity.setId("1");
        roleEntity.setName("IPC Operations");
        userEntity.setRole(roleEntity);
        userEntity.setPassword("$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO");
        userEntity.setStatus(UserStatus.REGISTERED.name());
        return userEntity;
    }

    protected HomeZone getHomeZone()
    {
        HomeZone model = new HomeZone();
        model.setId("1");
        model.setName("back room 1");
        model.setUnigyHomeZoneVIP("11.11.11.11");
        model.setInternetWebProxyAddr("12.22.22.22");
        model.setInternetWebProxyPort("1111");
        model.setCustomer(getCompanyModel());
        model.setConnexusWebProxyAddr("14.44.44.44");
        model.setConnexusWebProxyPort("1000");
        model.setIsUsingConnexus(true);
        return model;
    }

    protected Endpoint getEndpointModel()
    {
        Endpoint endpoint = new Endpoint();
        endpoint.setId("1000");
        endpoint.setProduct(getProductModel());
        endpoint.setCustomer(getCompanyModel());
        endpoint.setMacAddr("00:E0:A7:10:82:F2");
        return endpoint;
    }

    protected Product getProductModel()
    {
        Product product = new Product();
        product.setId("1");
        product.setName("touch");
        return product;
    }

    protected Turret getTurretModel()
    {
        Turret turret = new Turret();
        turret.setId("1001");
        turret.setMacAddress("000XXX999YYY");
        turret.setPartNumber("12");
        turret.setSerialNumber("1S");
        turret.setIsAssigned(false);
        return turret;
    }

    protected DataCenter getDataCenterModel()
    {
        DataCenter dc = new DataCenter();
        dc.setId("1");
        dc.setName("DataCenter1");
        dc.setRegion("myDC");
        dc.setConnexusReverseProxyFQDN("portal.uni360.com");
        dc.setConnexusSBCFQDN("portal.uni360.com");
        dc.setConnexusWebRTCFQDN("portal.uni360.com");
        dc.setConnexusBastionFQDN("portal.uni360.com");
        dc.setInternetReverseProxyFQDN("portal.uni360.com");
        dc.setInternetSBCFQDN("portal.uni360.com");
        dc.setInternetWebRTCFQDN("portal.uni360.com");
        dc.setInternetBastionFQDN("portal.uni360.com");
        return dc;
    }

    protected Ticket getTikcetModel()
    {
        Ticket ticket = new Ticket();
        ticket.setId("1000");
        ticket.setIssueType("Report a problem");
        ticket.setUserName("test@ipc.com");
        ticket.setMessage("Facing issue in accessing portal");
        return ticket;
    }*/
}

package com.mars;

import static java.util.Arrays.asList;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

/*import com.ipc.mongo.repository.CompanyRepository;
import com.ipc.mongo.repository.DataCenterRepository;
import com.ipc.mongo.repository.EndpointRepository;
import com.ipc.mongo.repository.HomeZoneRepository;
import com.ipc.mongo.repository.ProductRepository;
import com.ipc.mongo.repository.RoleRepository;
import com.ipc.mongo.repository.TurretRepository;
import com.ipc.mongo.repository.UserRepository;
import com.ipc.mongo.repository.entity.Customer;
import com.ipc.mongo.repository.entity.DataCenter;
import com.ipc.mongo.repository.entity.Endpoint;
import com.ipc.mongo.repository.entity.HomeZone;
import com.ipc.mongo.repository.entity.Product;
import com.ipc.mongo.repository.entity.Role;
import com.ipc.mongo.repository.entity.Turret;
import com.ipc.mongo.repository.entity.User;
import com.ipc.repository.entity.UserStatus;*/

/**
 * Copyright (c) 2017 IPC Systems, Inc. Created by Viktor Bondarenko on 1/27/2017.
 */
public class DBLoader
{
	/*@Autowired
	DataCenterRepository dataCenterRepository;
	
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HomeZoneRepository homeZoneRepository;

    @Autowired
    TurretRepository turretRepository;

    @Autowired
    EndpointRepository endpointRepository;

    @PostConstruct
    public void load()
    {
        addRoles();
        addProducts();
        addCustomers();
        addUsers();
        addHomeZones();
        addEndpoints();
        addTurrets();
        addDataCenters();
    }

    public void addDataCenters()
    {
    	dataCenterRepository.save(createDataCenters("1", "DataCenter1", "ASIA", false, "portal.uni360.com", "portal.uni360.com", "portal.uni360.com", "portal.uni360.com", "portal.uni360.com", "portal.uni360.com", "portal.uni360.com", "portal.uni360.com"));
    }
    
    

	public void addTurrets()
    {
        turretRepository.save(createTurret("1000", "00:E0:A7:10:82:F2", "10001", "11s", false));
        turretRepository.save(createTurret("1002", "000XXX9991YY", "10001", "12s", false));
        turretRepository.save(createTurret("1004", "00:E0:A7:0E:CD:66", "10001", "12s", false));
    }

    public void addHomeZones()
    {
        homeZoneRepository.save(asList(
                createHomeZone("1", "11.11.11.11", "12.22.22.22", "back room 1", "1111", "1001",
                        "13.33.33.33", "14.44.44.44", "1000", true, "IPC-TEST"),
                createHomeZone("2", "21.11.11.11", "22.22.22.22", "back room 2", "2222", "1000",
                        "23.33.33.33", "24.44.44.44", "2000", true, "some customer"),
                createHomeZone("3", "31.11.11.11", "32.22.22.22", "back room 3", "3333", "1001",
                        "33.33.33.33", "34.44.44.44", "3000", false, "IPC-TEST")));
    }

    public void addUsers()
    {
        userRepository.save(asList(
                createUser("1", "test@ipc.com", "FN1", "LN", UserStatus.CREATED.name(), "1000", "1",
                        "$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO", "site id",
                        null, null),
                createUser("2", "test2@ipc.com", "FN3", "LN", UserStatus.REGISTERED.name(), "1001", "2",
                        "$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO", "site id2",
                        null, null),
                createUser("3", "test@gmail.com", "FN2", "LN", UserStatus.REGISTERED.name(), "1000", "1",
                        "$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO", "site id",
                        "f74780df-6af7-468a-a3a2-53a8d5521cc7", DateUtils.addHours(new Date(), 24)),
                createUser("4", "test_update@ipc.com", "FN233", "LN", UserStatus.ACTIVATED.name(), "1000",
                        "1", "$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO",
                        "site id", null, null)));
    }

    public void addCustomers()
    {
        companyRepository.save(
                asList(createCustomer("1000", "some company"), createCustomer("1001", "IPC-TEST")));
    }

    private void addProducts()
    {
        productRepository.save(asList(createProduct("1", "touch"), createProduct("2", "pulse"),
                createProduct("3", "soft client")));
    }

    private void addRoles()
    {
        roleRepository
                .save(asList(createRole("1", "IPC Operations"), createRole("2", "Manufacturing"),
                        createRole("3", "Customer Admin"), createRole("4", "Customer End User")));
    }

    public void addEndpoints()
    {
        endpointRepository.save(createEndpoint("1001", "000XXX999YYY", "1000", null));
        endpointRepository.save(createEndpoint("1002", "000XXX999ZZZ", "1000", "back room 1"));
        endpointRepository.save(createEndpoint("1003", "000XXX9991YY", "1000", "back room 1"));
        endpointRepository.save(createEndpoint("1004", "00:E0:A7:0E:CD:66", "1000", "back room 1"));
        
    }

    protected Endpoint createEndpoint(String id, String uniqueId, String customerId,
            String homeZoneId)
    {
        Endpoint endpoint = new Endpoint();
        endpoint.setId(id);
        endpoint.setMacAddr(uniqueId);
        endpoint.setProduct(createProduct("1", null));
        endpoint.setCustomer(createCustomer(customerId, null));
        // endpoint.setBackRoom();
        // endpoint.setBackRoomName(backroomName);
        return endpoint;
    }
    
    private DataCenter createDataCenters(String id, String name, String region, Boolean isUsingConnexus, String internetReverseProxyFQDN,
    										String internetSBCFQDN, String internetWebRTCFQDN, String internetBastionFQDN,
    										String connexusReverseProxyFQDN, String connexusSBCFQDN, String connexusWebRTCFQDN,
    										String connexusBastionFQDN) 
    {
    DataCenter dc = new DataCenter();
    dc.setName(name);
    dc.setRegion(region);
    dc.setConnexusReverseProxyFQDN(connexusReverseProxyFQDN);
    dc.setConnexusWebRTCFQDN(connexusWebRTCFQDN);
    dc.setConnexusBastionFQDN(connexusBastionFQDN);
    dc.setConnexusSBCFQDN(connexusSBCFQDN);
    dc.setId(id);
    dc.setInternetBastionFQDN(internetBastionFQDN);
    dc.setInternetReverseProxyFQDN(internetReverseProxyFQDN);
    dc.setInternetSBCFQDN(internetSBCFQDN);
    dc.setInternetWebRTCFQDN(internetWebRTCFQDN);
    dc.setIsUsingConnexus(isUsingConnexus);
    
    return dc; 
    
    }

    private HomeZone createHomeZone(String id, String homeZoneIp, String webProxyIP, String name, 
            String webProxyPort, String customerId, String connexusHomeZoneIP,
            String connexusWebProxyIp, String connexusWebProxyPort, Boolean isUsingConnexus,
            String customerName)
    {
        HomeZone homeZone = new HomeZone();
        homeZone.setId(id);
        homeZone.setName(name);
        homeZone.setUnigyHomeZoneVIP(homeZoneIp);
        homeZone.setInternetWebProxyAddr(webProxyIP);
        homeZone.setInternetWebProxyPort(webProxyPort);
        homeZone.setCustomer(createCustomer(customerId, customerName));
        homeZone.setConnexusWebProxyAddr(connexusWebProxyIp);
        homeZone.setConnexusWebProxyPort(connexusWebProxyPort);
        homeZone.setIsUsingConnexus(isUsingConnexus);
        return homeZone;
    }

    private User createUser(String id, String email, String firstName, String lastName,
    		String status, String customerId, String roleId, String password, String siteId,
            String token, Date tokenExpirationDate)
    {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setStatus(status);
        user.setPassword(password);
        user.setSiteId(siteId);
        user.setCompany(createCustomer(customerId, "some company"));
        user.setRole(createRole(roleId, "IPC Operations"));
        user.setPasswordToken(token);
        user.setPasswordTokenExpirationDate(tokenExpirationDate);
        return user;
    }

    private Customer createCustomer(String id, String name)
    {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        return customer;
    }

    private Product createProduct(String id, String name)
    {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        return product;
    }

    private Role createRole(String id, String name)
    {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }

    private Turret createTurret(String id, String macAddress, String partNumber,
            String serialNumber, Boolean isAssigned)
    {
        Turret turret = new Turret();
        turret.setId(id);
        turret.setIsAssigned(isAssigned);
        turret.setMacAddress(macAddress);
        turret.setPartNumber(partNumber);
        turret.setSerialNumber(serialNumber);

        return turret;
    }*/

}

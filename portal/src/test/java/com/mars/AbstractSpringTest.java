package com.mars;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mars.AppConfig;
import com.mars.WebMvcConfig;


@RunWith(SpringJUnit4ClassRunner.class)
//@Profile({ "dev" })
@ContextConfiguration(classes = {WebMvcConfig.class, AppConfig.class})
@Transactional(transactionManager = "entityTransactionManager")
@Rollback
@WebAppConfiguration
public abstract class AbstractSpringTest extends AbstractTest{

}

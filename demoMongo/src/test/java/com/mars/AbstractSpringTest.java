package com.mars;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
// @Profile({ "dev" })
@ContextConfiguration(classes = { WebMvcConfig.class, AppConfig.class,
        SpringMongoConfigTest.class })
@WebAppConfiguration
public abstract class AbstractSpringTest extends AbstractTest
{

}

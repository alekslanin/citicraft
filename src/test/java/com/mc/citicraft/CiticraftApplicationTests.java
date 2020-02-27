package com.mc.citicraft;


import com.mc.citicraft.web.service.ConnectorService;
import com.mc.citicraft.web.service.FileService;
import com.mc.citicraft.web.service.IConnectorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
//@FixMethodOrder
class ConnectorServiceTests {

	private static final String STORAGE = "config/cities.txt";

	private IConnectorService service;

	@Test
	void contextLoads() {
		Assert.assertTrue(true);
	}

	@Before
	public void init() throws IOException {
		service = new ConnectorService();
		InputStream resource = new ClassPathResource(STORAGE).getInputStream();
		FileService fs = new FileService();
		fs.initConnector(service, resource);
	}

	@Test
	public void initTest() {
		Assert.assertTrue(service.isInitialized());
	}

	@Test
	public void workPositiveTests() {
		
	}
}

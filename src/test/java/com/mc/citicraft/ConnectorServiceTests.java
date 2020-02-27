package com.mc.citicraft;

import com.mc.citicraft.web.exception.BadRequestException;
import com.mc.citicraft.web.service.ConnectorService;
import com.mc.citicraft.web.service.ConnectorServiceResult;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@FixMethodOrder
public class ConnectorServiceTests {

	private static final String STORAGE = "payload.txt";

	private IConnectorService service;

	@Before
	public void init() throws IOException {
		service = new ConnectorService();
		boolean canFind = new ClassPathResource(STORAGE).exists();
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
		ConnectorServiceResult r = service.work("Moscow", "Tver");
		Assert.assertTrue(r.isOk());

		r = service.work("Albany", "New York");
		Assert.assertTrue(r.isOk());

		r = service.work("New York", "Albany");
		Assert.assertTrue(r.isOk());

		r = service.work("Portland", "Albany");
		Assert.assertTrue(r.isOk());

		r = service.work("New York", "Albany");
		Assert.assertTrue(r.isOk());
	}


	@Test
	public void workNegativeTests() {
		ConnectorServiceResult r = service.work("Moscow", "Quebec");
		Assert.assertFalse(r.isOk());

		r = service.work("Albany", "Minsk");
		Assert.assertFalse(r.isOk());

		r = service.work("Portland", "Uhta");
		Assert.assertFalse(r.isOk());
	}

	@Test
	public void workExceptionThrownTests() {
		Exception e = assertThrows(BadRequestException.class, () -> service.work("", ""));
		Assert.assertTrue(e.getMessage().contains(ConnectorService.message));

		e = assertThrows(BadRequestException.class, () -> service.work("", "b"));
		Assert.assertTrue(e.getMessage().contains(ConnectorService.message));

		e = assertThrows(BadRequestException.class, () -> service.work("a", ""));
		Assert.assertTrue(e.getMessage().contains(ConnectorService.message));
	}
}

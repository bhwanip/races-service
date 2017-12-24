package com.microservice.cloudnative.racesservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.microservice.cloudnative.racesservice.RacesServicesApplication.Race;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RacesServicesApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testGetRaces() {
		ResponseEntity<Race[]> responseEntity = restTemplate.getForEntity("http://localhost:8282/", Race[].class);
		for (Race r : responseEntity.getBody()) {
			System.out.println(r);
		}
	}

}


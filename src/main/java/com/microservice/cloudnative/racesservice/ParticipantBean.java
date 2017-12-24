package com.microservice.cloudnative.racesservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.cloudnative.racesservice.RacesServicesApplication.Participant;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class ParticipantBean {
	
	@Autowired
	private ParticipantClient participantClient;
	
	@HystrixCommand(fallbackMethod = "defaultParticipant")
	public List<Participant> getParticipant(String raceId) {
		return participantClient.getParticipant(raceId);
	}
	
	public List<Participant> defaultParticipant(String raceId) {
		return new ArrayList<Participant>();
	}

}

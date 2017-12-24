package com.microservice.cloudnative.racesservice;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microservice.cloudnative.racesservice.RacesServicesApplication.Participant;

@FeignClient("participant")
public interface ParticipantClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/race/{raceId}")
	public List<Participant> getParticipant(@PathVariable("raceId") String raceId);

}


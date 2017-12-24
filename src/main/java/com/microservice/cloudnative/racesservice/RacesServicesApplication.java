package com.microservice.cloudnative.racesservice;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class RacesServicesApplication implements CommandLineRunner {


	private static final List<Race> races = new ArrayList<>();
	
	@Autowired
	private ParticipantBean participantBean;
	
	public static void main(String[] args) {
		SpringApplication.run(RacesServicesApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		races.add(new Race ("Delhi Marathon", 100, "DELHI", "DELHI"));
		races.add(new Race ("Mumbai Marathon", 200, "MUMBAI", "MAHARASHTRA"));

		//printClassPath();

	}

	private void printClassPath() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader)cl).getURLs();

		for(URL url: urls){
			System.out.println(url.getFile());
		}
	}

	@RequestMapping("/")
	public  List<Race> getRaces() {
		return races;
	}
	
	@RequestMapping("/participants")
	public List<RaceWithParticipants> participants(){
		List<RaceWithParticipants> result = new ArrayList<>();
		for (Race race : races) {
			result.add(new RaceWithParticipants(race, participantBean.getParticipant(String.valueOf(race.getId()))));
		}
		return result;
	}

	public static class Race {

		@Override
		public String toString() {
			return "Race [name=" + name + ", id=" + id + ", city=" + city
					+ ", state=" + state + "]";
		}

		public Race() {}
		public Race(String name, Integer id, String city, String state) {
			super();
			this.name = name;
			this.id = id;
			this.city = city;
			this.state = state;
		}

		public String getName() {
			return name;
		}
		public Integer getId() {
			return id;
		}
		public String getCity() {
			return city;
		}
		public String getState() {
			return state;
		}
		private String name;
		private Integer id;
		private String city;
		private String state;
	}
	public static class Participant {

		public Participant () {}

		public Participant(String firstNAme, String lastName, String shirtSize,
				String homeState, Set<Integer> races) {
			super();
			this.firstNAme = firstNAme;
			this.lastName = lastName;
			this.shirtSize = shirtSize;
			this.homeState = homeState;
			this.races = races;
		}

		private String firstNAme;
		private String lastName;
		private String shirtSize;
		private String homeState;
		private Set<Integer> races;

		public String getFirstNAme() {
			return firstNAme;
		}
		public String getLastName() {
			return lastName;
		}
		public String getShirtSize() {
			return shirtSize;
		}
		public String getHomeState() {
			return homeState;
		}
		public Set<Integer> getRaces() {
			return races;
		}

		@Override
		public String toString() {
			return "Participant [firstNAme=" + firstNAme + ", lastName="
					+ lastName + ", shirtSize=" + shirtSize + ", homeState="
					+ homeState + ", races=" + races + "]";
		}
	}
	
	public static class RaceWithParticipants extends Race {
		
		public RaceWithParticipants(Race race, List<Participant> participants) {
			super(race.getName(), race.getId(), race.getCity(), race.getState());
			this.participants = participants;
		}

		private List<Participant> participants;

		public List<Participant> getParticipants() {
			return participants;
		}
		
	}
}

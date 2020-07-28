package com.desafiob2w.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultApi {

	private List<PlanetaApi> results = new ArrayList<PlanetaApi>();

	@Scheduled(fixedDelay = 43200000)
	public ResponseEntity buscarTodosApiExterna() {

		try {

			RestTemplate restTemplate = new RestTemplate();

			final String url = "https://swapi.dev/api/planets/";

			ResponseEntity<ResultApi> response = restTemplate.getForEntity(url, ResultApi.class); // 3

			results = response.getBody().getResults();

		} catch (HttpStatusCodeException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		}
		return null;

	}
}

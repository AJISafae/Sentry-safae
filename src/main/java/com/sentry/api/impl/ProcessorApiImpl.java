package com.sentry.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.sentry.api.ProcessorApi;
import com.sentry.model.HistoricalDataEntry;
import com.sentry.model.ProcessorApiResponse;
import com.sentry.model.ProcessorHistoricalDataResponse;

@Repository
public class ProcessorApiImpl implements ProcessorApi {

	@Autowired
	private RestTemplate restTemplate;
	private final String API_URL = "https://xdemo.sentrysoftware.com/rest";
	private final String END_POINT_ALL_PROCESSOR = "/namespace/NT_CPU";
	private final String END_POINT_PROCESSOR_DATA = "/console/NT_CPU/";

	@Override
	public List<String> getAllProcessors() {
		ResponseEntity<ProcessorApiResponse> response = restTemplate.exchange(API_URL + END_POINT_ALL_PROCESSOR,
				HttpMethod.GET, null, new ParameterizedTypeReference<ProcessorApiResponse>() {
				});

		ProcessorApiResponse processorApiResponse = response.getBody();

		if (processorApiResponse == null || processorApiResponse.getSubnodes() == null) {
			return Collections.emptyList();
		}

		return processorApiResponse.getSubnodes().stream().filter(id -> !"CPU__Total".equalsIgnoreCase(id))
				.collect(Collectors.toList());
	}

	@Override
	public List<Double> getHistoricalData(String processorId, int history) {
		String urlHistoricalData = API_URL + END_POINT_PROCESSOR_DATA
				+ "{processorId}/CPUprcrProcessorTimePercent?max={history}";

		ResponseEntity<ProcessorHistoricalDataResponse> response = restTemplate.exchange(urlHistoricalData,
				HttpMethod.GET, null, new ParameterizedTypeReference<ProcessorHistoricalDataResponse>() {
				}, processorId, history);

		List<Double> historicalValues = new ArrayList<>();
		List<HistoricalDataEntry> historyEntries = response.getBody().getHistory();
		if (historyEntries == null) {
			return historicalValues;
		}

		return historyEntries.stream().filter(entry -> entry != null).map(entry -> entry.getValue())
				.collect(Collectors.toList());
	}

}

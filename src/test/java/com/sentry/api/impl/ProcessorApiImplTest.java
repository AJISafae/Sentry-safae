package com.sentry.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sentry.model.HistoricalDataEntry;
import com.sentry.model.ProcessorApiResponse;
import com.sentry.model.ProcessorHistoricalDataResponse;

@ExtendWith(MockitoExtension.class)
public class ProcessorApiImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProcessorApiImpl processorApi;

    @Test
    public void testGetAllProcessors() {
   
        ProcessorApiResponse processorApiResponse = new ProcessorApiResponse();
        List<String> subnodes = new ArrayList<>();
        subnodes.add("processor1");
        subnodes.add("processor2");
        processorApiResponse.setSubnodes(subnodes);
        
        ResponseEntity<ProcessorApiResponse> responseEntity = new ResponseEntity<>(processorApiResponse, HttpStatus.OK);
        
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(), 
                eq(new ParameterizedTypeReference<ProcessorApiResponse>() {})))
            .thenReturn(responseEntity);

        List<String> processors = processorApi.getAllProcessors();
        assertEquals(subnodes, processors);
    }

    @Test
    public void testGetHistoricalData() {
        String processorId = "exampleProcessorId";
        int history = 10;

        List<Double> historyValues = Arrays.asList(10.0, 15.0, 20.0);

        ProcessorHistoricalDataResponse responseBody = new ProcessorHistoricalDataResponse();
        
        List<HistoricalDataEntry> historyEntries = new ArrayList<>();
        for (Double value : historyValues) {
            historyEntries.add(new HistoricalDataEntry(value));
        }
        
        responseBody.setHistory(historyEntries);
        
        ResponseEntity<ProcessorHistoricalDataResponse> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(), 
                eq(new ParameterizedTypeReference<ProcessorHistoricalDataResponse>() {}), eq(processorId), eq(history)))
            .thenReturn(responseEntity);
       
        List<Double> historicalValuesReturned = processorApi.getHistoricalData(processorId, history);

        assertEquals(historyValues, historicalValuesReturned);
    }


}

package com.sentry.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sentry.api.ProcessorApi;
import com.sentry.model.Processor;
import com.sentry.utilities.OperationEnum;

public class ProcessorServiceImplTest {

	@Mock
	private ProcessorApi processorApi;

	@InjectMocks
	private ProcessorServiceImpl processorService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetProcessorValuesWithValidData() {
		List<String> processors = Arrays.asList("CPU_01", "CPU_02", "CPU_03");
		List<Double> historicalData = Arrays.asList(50.0, 60.0, 70.0);

		when(processorApi.getAllProcessors()).thenReturn(processors);
		when(processorApi.getHistoricalData(anyString(), anyInt())).thenReturn(historicalData);

		List<Processor> resultMax = processorService.getProcessorValues(10, OperationEnum.MAX);

		assertEquals(3, resultMax.size());
		assertEquals("Processor-01", resultMax.get(0).getName());
		assertEquals(70.0, resultMax.get(0).getValue());

		List<Processor> resultMin = processorService.getProcessorValues(10, OperationEnum.MIN);

		assertEquals(3, resultMin.size());
		assertEquals("Processor-01", resultMin.get(0).getName());
		assertEquals(50.0, resultMin.get(0).getValue());
	}

	@Test
	public void testGetProcessorValuesWithEmptyData() {
		List<String> processors = Collections.emptyList();

		when(processorApi.getAllProcessors()).thenReturn(processors);

		List<Processor> result = processorService.getProcessorValues(10, OperationEnum.MAX);

		assertEquals(0, result.size());

		List<Processor> result2 = processorService.getProcessorValues(10, OperationEnum.MIN);

		assertEquals(0, result2.size());
	}

}

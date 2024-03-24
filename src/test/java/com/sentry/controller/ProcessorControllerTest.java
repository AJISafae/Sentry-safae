package com.sentry.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sentry.model.Processor;
import com.sentry.service.ProcessorService;
import com.sentry.utilities.OperationEnum;

@ExtendWith(MockitoExtension.class)
public class ProcessorControllerTest {

	@Mock
	private ProcessorService processorService;

	@InjectMocks
	private ProcessorController processorController;

	@Test
	public void testGetMaxProcessorValues() {
	    int history = 10;

	    List<Processor> expectedValues = new ArrayList<>();
	    expectedValues.add(new Processor("CPU_01", 70.0));
	    expectedValues.add(new Processor("CPU_02", 60.0));
	    expectedValues.add(new Processor("CPU_03", 80.0));

	    when(processorService.getProcessorValues(history, OperationEnum.MAX)).thenReturn(expectedValues);

	    ResponseEntity<List<Processor>> responseEntity = processorController.getMaxProcessorValues(history);

	    assertEquals(expectedValues, responseEntity.getBody());
	}


}

package com.sentry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sentry.model.Processor;
import com.sentry.service.ProcessorService;
import com.sentry.utilities.OperationEnum;

/**
 * Controller class for managing processor-related endpoints.
 */
@RestController
@RequestMapping("/processors")
public class ProcessorController {

	@Autowired
	private ProcessorService processorService;

	/**
	 * Endpoint to retrieve maximum processor usage values.
	 * 
	 * @param history
	 * @return An HTTP response containing a list of processors with max values.
	 */
	@GetMapping("/CPUprcrProcessorTimePercent/max")
	public ResponseEntity<List<Processor>> getMaxProcessorValues(@RequestParam("history") int history) {

		List<Processor> maxValues = processorService.getProcessorValues(history, OperationEnum.MAX);

		return ResponseEntity.ok(maxValues);
	}

	/**
	 * Endpoint to retrieve minimum processor usage values.
	 * 
	 * @param history
	 * @return An HTTP response containing a list of processors with min values.
	 */
	@GetMapping("/CPUprcrProcessorTimePercent/min")
	public ResponseEntity<List<Processor>> getMinProcessorValues(@RequestParam("history") int history) {

		List<Processor> minValues = processorService.getProcessorValues(history, OperationEnum.MIN);

		return ResponseEntity.ok(minValues);
	}
}

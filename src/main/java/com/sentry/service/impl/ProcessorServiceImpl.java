package com.sentry.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sentry.api.ProcessorApi;
import com.sentry.model.Processor;
import com.sentry.service.ProcessorService;
import com.sentry.utilities.OperationEnum;

/**
 * Service implementation for managing processor-related operations.
 */
@Service
public class ProcessorServiceImpl implements ProcessorService {

	@Autowired
	private ProcessorApi processorRepository;

	@Override
	public List<Processor> getProcessorValues(int history, OperationEnum operation) {

		List<String> processors = processorRepository.getAllProcessors();

		List<CompletableFuture<Processor>> futures = processors.stream()
				.map(elmt -> fetchValueAsync(elmt, history, operation)).collect(Collectors.toList());
		
		// Wait for all asynchronous tasks to complete
		CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		allOf.join();
		
		return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	//Asynchronously fetches historical data for a given processor.
	private CompletableFuture<Processor> fetchValueAsync(String processorId, int history, OperationEnum operation) {
		return CompletableFuture.supplyAsync(() -> {
			// Retrieve historical data for the processor
			List<Double> historicalData = processorRepository.getHistoricalData(processorId, history);
			if (historicalData == null) {
				return new Processor(transformName(processorId), Double.NaN);
			}
			double value = calculate(historicalData, operation);

			return new Processor(transformName(processorId), value);

		});
	}

	private double calculate(List<Double> historicalData, OperationEnum operation) {
		if (historicalData == null || historicalData.isEmpty()) {
			return Double.NaN;
		}
		if (OperationEnum.AVG.equals(operation)) {
			return historicalData.stream().filter(value -> value != null).mapToDouble(value -> value).average()
					.orElse(Double.NaN);
		}
		if (OperationEnum.MIN.equals(operation)) {
			return Collections.min(historicalData);
		}
		return Collections.max(historicalData);
	}

	private String transformName(String originalName) {
		if (originalName == null) {
			return null;
		}
		char digitChar = originalName.charAt(4);
		int digit = Character.getNumericValue(digitChar) + 1;
		String formattedDigit = String.format("%02d", digit);
		return "Processor-" + formattedDigit;

	}

}
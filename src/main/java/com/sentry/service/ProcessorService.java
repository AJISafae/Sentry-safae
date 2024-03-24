package com.sentry.service;

import java.util.List;

import com.sentry.model.Processor;
import com.sentry.utilities.OperationEnum;

/**
 * Service interface for managing processor-related operations.
 */
public interface ProcessorService {

	/**
	 *  Retrieves the max values for each processor.
	 *
	 * @param history
	 * @param operation The type of operation max.
	 * @return A list of max values for each processor.
	 */
	public List<Processor> getProcessorValues(int history, OperationEnum operation);

}

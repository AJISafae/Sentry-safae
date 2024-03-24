package com.sentry.api;

import java.util.List;
/**
 * Interface defining the ProcessorAPI.
 */
public interface ProcessorApi {

	/**
	 * Method to retrieve all processors.
	 * 
	 * @return List of processors.
	 */
	public List<String> getAllProcessors();
	
	/**
	 * Method to retrieve historical usage data of the processor.
	 * 
	 * @param processorId 
	 * @param history  
	 * @return A list of historical usage values of the processor.
	 */
	public List<Double> getHistoricalData(String processorId, int history);
}

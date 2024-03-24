package com.sentry.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricalDataEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	private double value;
	private long timestamp;

	public HistoricalDataEntry(Double value) {
		this.value = value;
	}
	
	@JsonCreator
    public HistoricalDataEntry(@JsonProperty("value") double value, @JsonProperty("timestamp") long timestamp ) {
        this.value= value;
		this.timestamp = timestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(timestamp, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricalDataEntry other = (HistoricalDataEntry) obj;
		return timestamp == other.timestamp && Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}

	@Override
	public String toString() {
		return "HistoricalDataEntry [value=" + value + ", timestamp=" + timestamp + "]";
	}

}

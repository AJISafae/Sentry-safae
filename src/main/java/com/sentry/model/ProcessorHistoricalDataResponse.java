package com.sentry.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProcessorHistoricalDataResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<HistoricalDataEntry> history;

	public List<HistoricalDataEntry> getHistory() {
		return history;
	}

	public void setHistory(List<HistoricalDataEntry> history) {
		this.history = history;
	}

	@Override
	public int hashCode() {
		return Objects.hash(history);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessorHistoricalDataResponse other = (ProcessorHistoricalDataResponse) obj;
		return Objects.equals(history, other.history);
	}

	@Override
	public String toString() {
		return "ProcessorHistoricalDataResponse [history=" + history + "]";
	}

}

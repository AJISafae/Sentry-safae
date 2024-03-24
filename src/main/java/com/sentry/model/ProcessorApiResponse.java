package com.sentry.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessorApiResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> subnodes;

	public List<String> getSubnodes() {
		return subnodes;
	}

	public void setSubnodes(List<String> subnodes) {
		this.subnodes = subnodes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(subnodes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessorApiResponse other = (ProcessorApiResponse) obj;
		return Objects.equals(subnodes, other.subnodes);
	}

	@Override
	public String toString() {
		return "ProcessorApiResponse [subnodes=" + subnodes + "]";
	}
	
}

package com.appdynamics.extensions.logmonitor.apache.processors;

import static com.appdynamics.extensions.logmonitor.apache.util.ApacheLogMonitorUtil.*;

import java.util.Set;
import java.util.regex.Pattern;

import com.appdynamics.extensions.logmonitor.apache.metrics.ApacheLogMetrics;

/**
 * @author Florencio Sarmiento
 *
 */
public class VisitorProcessor {
	
	private Pattern metricExcludesPattern;
	
	private Pattern displayIncludesPattern;

	public VisitorProcessor(Set<String> metricExcludes,
			Set<String> displayIncludes) {
		
		this.metricExcludesPattern = createPattern(metricExcludes);
		this.displayIncludesPattern = createPattern(displayIncludes);
	}
	
	public boolean isToMonitor(String host) {
		return isNotMatch(host, metricExcludesPattern);
	}
	
	public void processMetrics(String host, Integer bandwidth, 
			boolean isPageView, ApacheLogMetrics apacheLogMetrics) {
		
		if (isMatch(host, displayIncludesPattern)) {
			apacheLogMetrics.getVisitorMetrics()
				.incrementGroupAndMemberMetrics(host, bandwidth, isPageView);
			
		} else {
			apacheLogMetrics.getVisitorMetrics()
				.incrementGroupMetrics(bandwidth, isPageView);
		}
	}
}
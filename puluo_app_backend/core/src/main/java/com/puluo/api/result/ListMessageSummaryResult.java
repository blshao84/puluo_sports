package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class ListMessageSummaryResult extends HasJSON{
	public List<MessageSummaryResult> summaries;

	public ListMessageSummaryResult(List<MessageSummaryResult> summaries) {
		super();
		this.summaries = summaries;
	}
	
	public static ListMessageSummaryResult dummy() {
		List<MessageSummaryResult> sum = new ArrayList<MessageSummaryResult>();
		sum.add(new MessageSummaryResult(
				"Baolin", "Shao", "de305d54-75b4-431b-adb2-eb6b9e546013", 
				"http://upyun.com/baolin.jpg",1427007059034L, "de305d54-75b4-431b-adb2-eb6b9e546013"));
		sum.add(new MessageSummaryResult(
				"Tracey", "Boydston", "de305d54-75b4-431b-adb2-eb6b9e546013", 
				"http://upyun.com/tracey.jpg",1427007059034L, "de305d54-75b4-431b-adb2-eb6b9e546013"));
		return new ListMessageSummaryResult(sum);
	}
}

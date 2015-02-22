package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;


public class EventMemoryResult extends HasJSON{

	public List<String> memories;
	
	public EventMemoryResult(ArrayList<String> memories) {
		super();
		this.memories = memories;
	}

	public static EventMemoryResult dummy() {
		ArrayList<String> memories = new ArrayList<String>();
		memories.add("http://upyun.com/puluo/image1.jpg");
		memories.add("http://upyun.com/puluo/image2.jpg");
		return new EventMemoryResult(memories);
	}
}

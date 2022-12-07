package ru.school21.swingy.event.type;

import lombok.Value;
import ru.school21.swingy.event.ModelEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Value
public class LogModelEvent implements ModelEvent {

	List<String> logs;

	public LogModelEvent(String ... logs) {
		this.logs = logs != null && logs.length > 0 ? Arrays.asList(logs) : Collections.emptyList();
	}

	public LogModelEvent(List<String> logs) {
		this.logs = logs;
	}
}

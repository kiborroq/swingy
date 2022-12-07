package ru.school21.swingy.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractEventPublisher implements EventPublisher {

	private final Map<Class<? extends ModelEvent>, Set<EventListener>> listenersByEventType;

	protected abstract Set<Class<? extends ModelEvent>> getSupportedEventTypes();

	protected AbstractEventPublisher() {
		listenersByEventType = new HashMap<>();
		for (Class<? extends ModelEvent> eventTypeClass : getSupportedEventTypes()) {
			listenersByEventType.put(eventTypeClass, new HashSet<>());
		}
	}

	@Override
	public final void addListener(EventListener listener, Class<? extends ModelEvent> eventTypeClass) {
		Set<EventListener> listeners = listenersByEventType.get(eventTypeClass);
		if (listeners != null) {
			listeners.add(listener);
		}
	}

	@Override
	public final void removeListener(EventListener listener, Class<? extends ModelEvent> eventTypeClass) {
		Set<EventListener> listeners = listenersByEventType.get(eventTypeClass);
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	protected final void publishEvent(ModelEvent modelEvent) {
		Set<EventListener> listeners = listenersByEventType.get(modelEvent.getClass());
		if (listeners != null && !listeners.isEmpty()) {
			for (EventListener listener : listeners) {
				listener.publishEvent(modelEvent);
			}
		}
	}

}

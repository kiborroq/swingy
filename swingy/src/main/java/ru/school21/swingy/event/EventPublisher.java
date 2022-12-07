package ru.school21.swingy.event;

public interface EventPublisher {

	void addListener(EventListener listener, Class<? extends ModelEvent> eventTypeClass);
	void removeListener(EventListener listener, Class<? extends ModelEvent> eventTypeClass);

}

package ru.school21.swingy.event.type;

import ru.school21.swingy.event.ModelEvent;

public enum ApplicationModelEvent implements ModelEvent {
	EXIT,
	SWITCH_TO_GUI,
	SWITCH_TO_CONSOLE;
}

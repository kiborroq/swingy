package ru.school21.swingy.event.type;

import ru.school21.swingy.event.ModelEvent;

public enum GameModelEvent implements ModelEvent {
	MAP_CHANGED,
	FIGHT,
	LOST,
	ARTEFACT_FOUND;
}

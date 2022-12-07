package ru.school21.swingy.event.type;

import ru.school21.swingy.event.ModelEvent;

public enum SelectHeroModelEvent implements ModelEvent {
	ERRORS_CHANGED,
	HERO_SELECTED;
}

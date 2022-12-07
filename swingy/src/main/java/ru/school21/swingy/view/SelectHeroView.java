package ru.school21.swingy.view;

import ru.school21.swingy.event.EventListener;

public interface SelectHeroView extends EventListener {
	void render();
	void stop();
}

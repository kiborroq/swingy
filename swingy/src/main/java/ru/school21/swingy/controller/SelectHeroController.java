package ru.school21.swingy.controller;

import ru.school21.swingy.model.SelectHeroModel;

public class SelectHeroController {

	private final SelectHeroModel model;

	public SelectHeroController(SelectHeroModel model) {
		this.model = model;
	}

	public void inputName(String name) {
		model.setHeroName(name);
	}

	public void selectClazz(String clazz) {
		model.setHeroType(clazz);
	}

	public void selectName(String name) {
		model.selectHeroName(name);
	}

	public void selectHero() {
		model.selectHero();
	}

	public void selectHeroMode() {
		model.selectHeroMode();
	}

	public void createHeroMode() {
		model.createHeroMode();
	}

	public void exit() {
		model.exit();
	}
}

package ru.school21.swingy.controller;

import ru.school21.swingy.model.GameModel;
import ru.school21.swingy.type.DirectionType;

public class GameController {

	private final GameModel model;

	public GameController(GameModel model) {
		this.model = model;
	}

	public void doStep(DirectionType direction) {
		model.doStep(direction);
	}

	public void save() {
		model.save();
	}

	public void exit() {
		model.exit();
	}

	public void fight() {
		model.fight();
	}

	public void flee() {
		model.flee();
	}

	public void takeArtefact() {
		model.takeArtefact();
	}

	public void throwArtefact() {
		model.throwArtefact();
	}
}

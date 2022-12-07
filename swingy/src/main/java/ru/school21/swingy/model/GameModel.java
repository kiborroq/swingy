package ru.school21.swingy.model;

import ru.school21.swingy.event.EventPublisher;
import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.model.dto.MapDto;
import ru.school21.swingy.model.dto.VillainDto;
import ru.school21.swingy.type.DirectionType;

public interface GameModel extends HeroStatsModel, EventPublisher {
	MapDto getMap();
	VillainDto getVillainStats();
	ArtefactDto getFoundArtefactStats();

	void doStep(DirectionType direction);
	void fight();
	void flee();
	void takeArtefact();
	void throwArtefact();

	void save();
	void exit();
}

package ru.school21.swingy.model;

import ru.school21.swingy.event.EventPublisher;
import ru.school21.swingy.model.dto.HeroStatsDto;

public interface HeroStatsModel extends EventPublisher {

	HeroStatsDto getHeroStats();

}

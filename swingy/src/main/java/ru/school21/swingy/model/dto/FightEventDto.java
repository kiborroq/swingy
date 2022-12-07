package ru.school21.swingy.model.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class FightEventDto {
	List<String> events;
}

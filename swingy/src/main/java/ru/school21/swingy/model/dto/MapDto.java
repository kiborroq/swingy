package ru.school21.swingy.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import ru.school21.swingy.type.DirectionType;

import java.util.List;

@Value
@Builder
public class MapDto {

	String terrain;
	List<List<MapItem>> map;
	Integer heroX;
	Integer heroY;
	DirectionType lastStepDirection;

	@Value
	@NonFinal
	public static abstract class MapItem {
	}

	@Value
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	public static class VillainItem extends MapItem {
		String name;
	}

	@Value
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	public static class HeroItem extends MapItem {
		String clazz;
	}
}

package ru.school21.swingy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HelmType {
	UNICORN_HELM("Helm of the Alabaster Unicorn", "/images/helms/Helm_of_the_Alabaster_Unicorn.gif", 1),
	SKULL_HELMET("Skull Helmet", "/images/helms/Skull_Helmet.gif", 2),
	CHAOS_HELM("Helm of Chaos", "/images/helms/Helm_of_Chaos.gif", 3),
	MAGI_CROWN("Crown of the Supreme Magi", "/images/helms/Crown_of_the_Supreme_Magi.gif", 4),
	HELLSTORM_HELMET("Hellstorm Helmet", "/images/helms/Hellstorm_Helmet.gif", 5),
	DRAGONTOOTH_CROWN("Crown of Dragontooth", "/images/helms/Crown_of_Dragontooth.gif", 6),
	THUNDER_HELMET("Thunder Helmet", "/images/helms/Thunder_Helmet.gif", 7),
	SEA_CAPTAIN_HAT("Sea Captain's Hat", "/images/helms/Sea_Captain's_Hat.gif", 8),
	HEAVENLY_HELM("Helm of Heavenly", "/images/helms/Helm_of_Heavenly_Enlightenment.gif", 9);

	private final String name;
	private final String image;
	private final Integer hpIncrease;

	public final static int LENGTH = 25;
}

package ru.school21.swingy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeroType {
	ALCHEMIST("Alchemist", "Female",
			"Alchemists are skilled in physical and chemical magic, particularly so in the building and animation of golems. " +
					"Working their craft builds muscular strength, which makes Alchemists ready learners of military skills as well. " +
					"Their armor is composed of rare metal alloys created through their skill in alchemy.",
			"Neela.png", "Alchemist.png", 1, 1, 2, 15, 15, 20),
	KNIGHT("Knight", "Male",
			"Knights are brave and noble warriors dedicated to good and righteousness. " +
			"While they can learn magic skills, they are by far more dedicated to the pursuit of martial knowledge",
			"Orrin.png", "Knight.png", 2, 1, 1, 20, 20, 10),
	NECROMANCER("Necromancer", "Male",
			"Necromancers are magic users seduced by the easy power of death magic. " +
					"The price of their art is its practice slowly drains life from its wielders — eventually transforming them into liches.",
			"Sandro.png", "Necromancer.png", 2, 0, 2, 20, 10, 20),
	DEATH_KNIGHT("Death Knight", "Female",
			"Death Knights are knights resurrected as liches. " +
					"While they maintain their former martial learnings, their new forms open them more freely to the learning of magic.",
			"Tamika.png", "DeathKnight.png", 1, 1, 2, 20, 20, 10),
	WIZARD("Wizard", "Male",
			"Wizards are dedicated pursuers of mystical and magical knowledge. " +
					"While they may lend little to the direct military aspect of a battle, they are likely to turn the tide in their favor with use of mighty arcane forces." +
					" Wizards seldom wear any armor, relying on their control of magic to protect them.",
			"Theodorus.png", "Wizard.png", 1, 1, 2, 15, 15, 20),
	CLERIC("Cleric", "Female",
			"Clerics are members of the fighting forces of their holy orders. They pursue a range of knowledge, both martial and mystical.",
			"Sanya.png", "Cleric.png", 1, 1, 2, 15, 15, 20),
	DEMONIAC("Demoniac", "Male",
				   "Demoniacs are people (often ex-Heretics) who have been completely possessed by one or more demons. " +
						   "Though they have a natural predisposition to gaining magical power, Demoniacs also acquire balancing military skills as well.",
				   "Calh.png", "Demoniac.png", 2, 2, 1, 25, 15, 10),
	DRUID("Druid", "Male",
			"Druids are mystics who draw their power from a harmonic relationship with the land. " +
					"While they pursue their mystical knowledge, their outdoor living causes them to acquire a balance of physical skills.",
			"Elleshar.png", "Druid.png", 1, 2, 2, 10, 20, 20),
	OVERLORD("Overlord", "Male",
			"Overlords build dungeon lairs to protect their gains acquired through conquest. " +
					"Ruling through intimidation, they tend to be warriors who know the value of magic. " +
					"They often wear armor designed to enhance the ferocity of their appearance.",
			"Ajit.png", "Overlord.png", 2, 2, 1, 20, 20, 10);

	private final String clazz;
	private final String gender;
	private final String description;
	private final String avatar;
	private final String miniatur;
	private final Integer attack;
	private final Integer defense;
	private final Integer hp;
	private final Integer attackIncrease;
	private final Integer defenseIncrease;
	private final Integer hpIncrease;

	public final static int LENGTH = 25;
}

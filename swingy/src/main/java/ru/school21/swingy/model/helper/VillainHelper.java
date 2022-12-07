package ru.school21.swingy.model.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.school21.swingy.model.entity.Villain;
import ru.school21.swingy.util.ImageUtil;
import ru.school21.swingy.util.JsonUtils;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillainHelper {

	private static final String VILLAINS_CONFIG = "/json/villains.json";
	private static final Map<String, VillainInfo> VILLAIN_BY_NAME;
	private static final Map<Integer, List<VillainInfo>> VILLAINS_BY_LEVEL;
	private static final List<Integer> LEVELS;

	static {
		VILLAIN_BY_NAME =  new HashMap<>();
		VILLAINS_BY_LEVEL =  new HashMap<>();

		List<VillainInfo> villainInfos = JsonUtils.readList(VILLAINS_CONFIG, VillainInfo.class);
		for (VillainInfo villain : villainInfos) {
			VILLAIN_BY_NAME.put(villain.getName(), villain);

			List<VillainInfo> villains = VILLAINS_BY_LEVEL.get(villain.getLevel());
			if (villains == null) {
				villains = new ArrayList<>();
				VILLAINS_BY_LEVEL.put(villain.getLevel(), villains);
			}

			villains.add(villain);
		}

		LEVELS = new ArrayList<>(VILLAINS_BY_LEVEL.keySet());
		Collections.sort(LEVELS);
	}

	public static Villain create(int level, int posX, int posY, double experienceIncrease, boolean addArtefact) {
		List<VillainHelper.VillainInfo> infos = VILLAINS_BY_LEVEL.get(level);
		if (infos == null) {
			return null;
		}

		VillainHelper.VillainInfo info = infos.get(RandomHelper.before(infos.size()));
		Villain villain = new Villain();
		villain.setName(info.getName());
		villain.setLevel(info.getLevel());
		villain.setExperience((int) (info.getHp() * 10 * experienceIncrease));
		villain.setAttack(info.getAttack());
		villain.setHp(info.getHp());
		villain.setPosX(posX);
		villain.setPosY(posY);
		villain.setArtefact(addArtefact ? ArtefactHelper.getAnyByLevel(info.getLevel()) : null);

		return villain;
	}

	public static ImageIcon getImage(String name) {
		return VILLAIN_BY_NAME.get(name).getImage();
	}

	public static boolean has(int level) {
		return VILLAINS_BY_LEVEL.containsKey(level);
	}

	public static boolean hasBefore(int level) {
		return level > LEVELS.get(0);
	}

	@Value
	private static class VillainInfo {
		String name;
		ImageIcon image;
		Integer level;
		Integer attack;
		Integer hp;

		public VillainInfo(@JsonProperty("name") String name,
						   @JsonProperty("image") String image,
						   @JsonProperty("level") Integer level,
						   @JsonProperty("attack") Integer attack,
						   @JsonProperty("hp") Integer hp) {
			this.name = name;
			this.image = ImageUtil.getImageIcon(image);
			this.level = level;
			this.attack = attack;
			this.hp = hp;
		}
	}

}

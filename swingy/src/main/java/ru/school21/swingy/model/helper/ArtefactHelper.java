package ru.school21.swingy.model.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.type.ArtefactType;
import ru.school21.swingy.util.ImageUtil;
import ru.school21.swingy.util.JsonUtils;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArtefactHelper {

	private static final String ARTEFACTS_CONFIG = "/json/artefacts.json";
	private static final Map<Integer, List<ArtefactInfo>> ARTEFACT_BY_LEVEL;
	private static final Map<String, ArtefactInfo> ARTEFACT_BY_NAME;
	private static final int MAX_LEVEL;
	private static final int MIN_LEVEL;

	static {
		ARTEFACT_BY_LEVEL = new HashMap<>();
		ARTEFACT_BY_NAME = new HashMap<>();

		List<ArtefactInfo> infos = JsonUtils.readList(ARTEFACTS_CONFIG, ArtefactInfo.class);
		for (ArtefactInfo info : infos) {
			List<ArtefactInfo> infosByLevel = ARTEFACT_BY_LEVEL.get(info.getLevel());
			if (infosByLevel == null) {
				infosByLevel = new ArrayList<>();
				ARTEFACT_BY_LEVEL.put(info.getLevel(), infosByLevel);
			}
			infosByLevel.add(info);

			ARTEFACT_BY_NAME.put(info.getName(), info);
		}

		MAX_LEVEL = Collections.max(ARTEFACT_BY_LEVEL.keySet());
		MIN_LEVEL = Collections.min(ARTEFACT_BY_LEVEL.keySet());
	}


	public static ImageIcon getImageByName(String name) {
		return ARTEFACT_BY_NAME.get(name).getImage();
	}

	public static ArtefactDto getByName(String name) {
		if (name == null || !ARTEFACT_BY_NAME.containsKey(name)) {
			return null;
		}

		ArtefactInfo info = ARTEFACT_BY_NAME.get(name);

		return ArtefactDto.builder()
				.increase(info.getType() == ArtefactType.HELM ? info.getLevel() * 5 : info.getLevel())
				.name(info.getName())
				.type(info.getType())
				.build();
	}

	public static int getIncreaseByName(String name) {
		if (name == null) {
			return 0;
		}

		ArtefactInfo info = ARTEFACT_BY_NAME.get(name);
		return info.getType() == ArtefactType.HELM ? info.getLevel() * 5 : info.getLevel();
	}

	public static String getAnyByLevel(Integer level) {
		level = Math.min(Math.max(level, MIN_LEVEL), MAX_LEVEL);

		List<ArtefactInfo> infos = ARTEFACT_BY_LEVEL.get(level);
		if (infos != null) {
			return infos.get(RandomHelper.before(infos.size())).getName();
		}

		return null;
	}


	@Value
	private static class ArtefactInfo {
		String name;
		ImageIcon image;
		Integer level;
		ArtefactType type;

		public ArtefactInfo(@JsonProperty("name") String name,
							@JsonProperty("image") String image,
							@JsonProperty("level") Integer level,
							@JsonProperty("type") ArtefactType type) {
			this.name = name;
			this.image = ImageUtil.getImageIcon(image);
			this.level = level;
			this.type = type;
		}
	}

}

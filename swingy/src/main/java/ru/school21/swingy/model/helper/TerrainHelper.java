package ru.school21.swingy.model.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.school21.swingy.util.ImageUtil;
import ru.school21.swingy.util.JsonUtils;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TerrainHelper {

	private static final String TERRAIN_CONFIG = "/json/terrains.json";
	private static final Map<String, TerrainInFo> TERRAIN_BY_NAME;

	static {
		TERRAIN_BY_NAME = new HashMap<>();

		List<TerrainInFo> terrainInfos = JsonUtils.readList(TERRAIN_CONFIG, TerrainInFo.class);
		for (TerrainInFo info : terrainInfos) {
			TERRAIN_BY_NAME.put(info.getName(), info);
		}
	}


	public static ImageIcon getImageByName(String name) {
		return TERRAIN_BY_NAME.get(name).getImage();
	}

	public static String generate() {
		List<String> terrains = new ArrayList<>(TERRAIN_BY_NAME.keySet());
		return terrains.get(RandomHelper.before(terrains.size()));
	}


	@Value
	private static class TerrainInFo {
		String name;
		ImageIcon image;

		public TerrainInFo(@JsonProperty("name") String name,
						   @JsonProperty("image") String image) {
			this.name = name;
			this.image = ImageUtil.getImageIcon(image);
		}
	}

}

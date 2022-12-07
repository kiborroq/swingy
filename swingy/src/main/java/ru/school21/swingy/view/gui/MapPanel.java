package ru.school21.swingy.view.gui;

import ru.school21.swingy.model.dto.MapDto;
import ru.school21.swingy.model.helper.HeroHelper;
import ru.school21.swingy.model.helper.TerrainHelper;
import ru.school21.swingy.model.helper.VillainHelper;
import ru.school21.swingy.type.DirectionType;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

public class MapPanel extends JPanel {

	private final int panelSize;

	private Image background;
	private int cellSize;
	private Dimension heroDim;

	public MapPanel(int panelSize) {
		this.panelSize = panelSize;
	}

	public void update(MapDto dto) {
		this.removeAll();

		List<List<MapDto.MapItem>> items = dto.getMap();
		Integer heroX = dto.getHeroX();
		Integer heroY = dto.getHeroY();

		this.background = TerrainHelper.getImageByName(dto.getTerrain()).getImage();
		this.cellSize = Math.max(60, panelSize / items.size());

		int xSize = items.size();
		int ySize = items.get(0).size();
		setLayout(new GridLayout(xSize, ySize, 0, 0));

		boolean heroFlip = dto.getLastStepDirection() == DirectionType.WEST;
		for (int j = items.size() - 1; j >= 0; j--) {
			List<MapDto.MapItem> itemRow = items.get(j);
			for (int i = 0; i < itemRow.size(); i++) {
				MapDto.MapItem item = itemRow.get(i);
				JPanel cell = new JPanel();
				cell.setLayout(new GridLayout());
				cell.add(toMapCell(item, i > heroX || (i == heroX && j == heroY && heroFlip)));
				add(cell);
			}
		}

		this.heroDim = new Dimension(heroX * cellSize + cellSize / 2, cellSize * items.size() - (heroY * cellSize + cellSize / 2));
	}

	public Dimension getHeroDim() {
		return heroDim;
	}

	private JPanel toMapCell(MapDto.MapItem item, boolean flip) {
		Image foreground = null;
		int foregroundSize = cellSize;
		Border border = BorderFactory.createLineBorder(Color.BLACK);

		if (item instanceof MapDto.HeroItem) {
			foreground = HeroHelper.getMiniature(((MapDto.HeroItem) item).getClazz()).getImage();
			border = BorderFactory.createLineBorder(Color.RED, 2);
		} else if (item instanceof MapDto.VillainItem) {
			foreground = VillainHelper.getImage(((MapDto.VillainItem) item).getName()).getImage();
			foregroundSize = (int) (cellSize * 0.80);
		}

		return (new SpritePanel(background, foreground, cellSize, foregroundSize, flip, border));
	}

}

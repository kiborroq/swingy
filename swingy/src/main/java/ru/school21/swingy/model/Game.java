package ru.school21.swingy.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.school21.swingy.type.MapType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game extends AbstractModel {

	@Column(nullable = false)
	private Integer size;

	@Column(nullable = false, length = MapType.LENGTH)
	@Enumerated(EnumType.STRING)
	private MapType type;

	@Column(nullable = false)
	private Integer heroX;

	@Column(nullable = false)
	private Integer heroY;

	@BatchSize(size = BATCH_SIZE)
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	List<Villain> villains = new ArrayList<>();
}

package ru.school21.swingy.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.school21.swingy.type.DirectionType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "game_map")
public class GameMap extends AbstractEntity {

	@Column(nullable = false)
	private Integer size;

	@Column(nullable = false, length = COMMON_LENGTH)
	private String terrain;

	@Column(nullable = false)
	private Integer heroX;

	@Column(nullable = false)
	private Integer heroY;

	@Column(length = DirectionType.LENGTH)
	@Enumerated(EnumType.STRING)
	private DirectionType lastStepDirection;

	@Setter(AccessLevel.PRIVATE)
	@BatchSize(size = BATCH_SIZE)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "game_map_id", nullable = false)
	private Set<Villain> villains = new HashSet<>();

}

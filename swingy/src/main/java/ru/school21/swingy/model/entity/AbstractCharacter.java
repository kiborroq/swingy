package ru.school21.swingy.model.entity;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractCharacter extends AbstractEntity {

	@NotNull(message = "Name is not specified")
	@Length(min = 1, max = COMMON_LENGTH, message = "Name length should be 1-25")
	@Column(nullable = false, updatable = false, length = COMMON_LENGTH)
	private String name;

	@NotNull(message = "Level is not specified")
	@Column(nullable = false)
	private Integer level;

	@NotNull(message = "Experience is not specified")
	@Column(nullable = false)
	private Integer experience;

	@NotNull(message = "Attack is not specified")
	@Column(nullable = false)
	private Integer attack;

	@NotNull(message = "Hit points value is not specified")
	@Column(nullable = false)
	private Integer hp;

}

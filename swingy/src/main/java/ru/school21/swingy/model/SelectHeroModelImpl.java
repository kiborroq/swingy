package ru.school21.swingy.model;

import lombok.extern.slf4j.Slf4j;
import ru.school21.swingy.event.AbstractEventPublisher;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.ApplicationModelEvent;
import ru.school21.swingy.event.type.HeroStatsModelEvent;
import ru.school21.swingy.model.helper.HeroHelper;
import ru.school21.swingy.model.repository.HeroHibernateRepository;
import ru.school21.swingy.model.repository.HeroRepository;
import ru.school21.swingy.model.dto.HeroStatsDto;
import ru.school21.swingy.model.entity.Hero;
import ru.school21.swingy.event.type.SelectHeroModelEvent;
import ru.school21.swingy.util.HibernateUtil;
import ru.school21.swingy.util.UnmodifiableUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SelectHeroModelImpl extends AbstractEventPublisher implements SelectHeroModel {

	private final static Set<Class<? extends ModelEvent>> SUPPORTED_EVENTS = UnmodifiableUtils.set(
			SelectHeroModelEvent.class,
			HeroStatsModelEvent.class,
			ApplicationModelEvent.class);

	private final static Hero EMPTY_HERO = new Hero();

	private final HeroRepository heroRepository;
	private final Validator validator;

	private final Map<String, Hero> heroByName;
	private final Map<String, Hero> defaultHeroByClazz;
	private final Map<String, String> errors;

	private Hero currentHero;
	private Hero oldHero;
	private Hero newHero;

	private boolean createHeroMode;
	private String name = null;

	@Override
	protected Set<Class<? extends ModelEvent>> getSupportedEventTypes() {
		return SUPPORTED_EVENTS;
	}

	public SelectHeroModelImpl() {
		heroRepository = new HeroHibernateRepository();
		validator = Validation.buildDefaultValidatorFactory().getValidator();

		errors = new HashMap<>();
		newHero = EMPTY_HERO;
		oldHero = EMPTY_HERO;
		currentHero = newHero;
		createHeroMode = true;

		heroByName = HibernateUtil.execute(new Callable<List<Hero>>() {
					@Override
					public List<Hero> call() throws Exception {
						return heroRepository.findAllHeroes();
					}
				})
				.stream()
				.collect(Collectors.toMap(new Function<Hero, String>() {
					@Override
					public String apply(Hero hero) {
						return hero.getName();
					}
				}, Function.identity()));

		defaultHeroByClazz = HeroHelper.getByClazzMap();
	}

	@Override
	public Set<String> getHeroes() {
		return heroByName.keySet();
	}

	@Override
	public Set<String> getHeroClasses() {
		return defaultHeroByClazz.keySet();
	}

	@Override
	public void selectHero() {
		if (errors.containsKey("name") || errors.containsKey("clazz")) {
			errors.remove("name");
			errors.remove("clazz");
			publishEvent(SelectHeroModelEvent.ERRORS_CHANGED);
		}

		SelectHeroModelEvent eventType;

		if (createHeroMode) {
			Set<ConstraintViolation<Hero>> violations = validator.validate(currentHero);
			if (violations.size() > 0) {
				for (ConstraintViolation<Hero> v : violations) {
					String field = v.getPropertyPath().toString();
					if (field.equals("clazz") || field.equals("name")) {
						errors.put(v.getPropertyPath().toString(), v.getMessage());
					}
				}
				eventType = SelectHeroModelEvent.ERRORS_CHANGED;
			} else {
				eventType = HibernateUtil.execute(new Callable<SelectHeroModelEvent>() {
					@Override
					public SelectHeroModelEvent call() {
						if (heroByName.containsKey(currentHero.getName())) {
							errors.put("name", "Name is busy");
							return SelectHeroModelEvent.ERRORS_CHANGED;
						}

						heroRepository.save(newHero);

						return SelectHeroModelEvent.HERO_SELECTED;
					}
				});
			}
		} else if (currentHero == EMPTY_HERO) {
			errors.put("hero", "Hero not selected");
			eventType = SelectHeroModelEvent.ERRORS_CHANGED;
		} else {
			eventType = SelectHeroModelEvent.HERO_SELECTED;
		}

		publishEvent(eventType);
	}

	@Override
	public void selectHeroMode() {
		if (createHeroMode) {
			newHero = currentHero;
			currentHero = oldHero;
			createHeroMode = false;

			if (currentHero != EMPTY_HERO) {
				publishEvent(HeroStatsModelEvent.UPDATED);
			}
		}
	}

	@Override
	public void createHeroMode() {
		if (!createHeroMode) {
			oldHero = currentHero;
			currentHero = newHero;
			currentHero.setName(name);
			createHeroMode = true;

			if (newHero != EMPTY_HERO) {
				publishEvent(HeroStatsModelEvent.UPDATED);
			}
		}
	}

	@Override
	public void setHeroName(String name) {
		this.name = name;

		if (currentHero == newHero && !Objects.equals(currentHero.getName(), name)) {
			currentHero.setName(name);
			publishEvent(HeroStatsModelEvent.UPDATED);
		}
	}

	@Override
	public void setHeroType(String clazz) {
		if (currentHero == newHero &&  !Objects.equals(currentHero.getClazz(), clazz)) {
			currentHero = clazz != null ? defaultHeroByClazz.get(clazz) : newHero;
			newHero = currentHero;
			currentHero.setClazz(clazz);
			currentHero.setName(name);
			publishEvent(HeroStatsModelEvent.UPDATED);
		}
	}

	@Override
	public void selectHeroName(String name) {
		Hero hero = heroByName.get(name);
		if (hero == null) {
			currentHero = EMPTY_HERO;
			publishEvent(HeroStatsModelEvent.UPDATED);
		} else if (!Objects.equals(currentHero, hero)) {
			currentHero = hero;
			publishEvent(HeroStatsModelEvent.UPDATED);
		}
	}

	@Override
	public Map<String, String> getErrors() {
		return Collections.unmodifiableMap(errors);
	}

	@Override
	public HeroStatsDto getHeroStats() {
		return HeroHelper.map(currentHero);
	}

	@Override
	public void exit() {
		publishEvent(ApplicationModelEvent.EXIT);
	}

}

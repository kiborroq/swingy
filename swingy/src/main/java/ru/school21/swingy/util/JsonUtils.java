package ru.school21.swingy.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.school21.swingy.exception.SwingyRuntimeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static <T> List<T> readList(String filename, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(
					JsonUtils.class.getResourceAsStream(filename),
					OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
		} catch (IOException e) {
			throw new SwingyRuntimeException("Error during json read", e);
		}
	}

}

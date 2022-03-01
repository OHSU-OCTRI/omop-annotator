package org.octri.omop_annotator.view;

import java.util.HashMap;
import java.util.Map;

import org.octri.omop_annotator.domain.app.AbstractEntity;

/**
 * Utility methods for generating view-related code.
 * 
 * @author lawhead
 *
 */
public class ViewUtils {

	/**
	 * Utility method for creating a map object with a nested entity. Useful for using entity components in different
	 * contexts.
	 * 
	 * @param entity
	 * @return
	 */
	public static Map<String, Object> entityWrapper(AbstractEntity entity) {
		Map<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("entity", entity);
		return wrapper;
	}
}
package com.b2w.starwars.api.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Simone Santos
 */
public class SwApiUtil {
	/**
	 * Metodo responsavel por verificar se o objecto e nulo ou vazio. 
	 * @param obj objeto
	 * @return true ou false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return obj.toString().trim().equals("");
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		if (obj instanceof List) {
			return ((List) obj).size() == 0;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		return false;
	}

}

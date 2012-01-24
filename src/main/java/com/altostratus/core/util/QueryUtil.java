package com.altostratus.core.util;


import java.util.HashMap;
import java.util.Map;

public class QueryUtil {
	public static Map<String, Object> toMap(String paramName, Object paramValue) {
		return create(paramName, paramValue).toMap();
	}
	public static QueryParam create(){
		return new QueryParam();
	}

	public static QueryParam create(String paramName, Object paramValue){
		return new QueryParam().add(paramName, paramValue);
	}

	public static Map<String, Object> emptyParams() {
		return new HashMap<String, Object>();
	}
	public static final class QueryParam {
		private Map<String, Object> params = new HashMap<String, Object>();
		
		public Map<String, Object> toMap() {
			return params;
		}
		
		public QueryParam add(String paramName, Object paramValue) {
			params.put(paramName, paramValue);
			return this;
		}
	}
}

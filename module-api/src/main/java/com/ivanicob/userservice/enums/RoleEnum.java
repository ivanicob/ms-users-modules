package com.ivanicob.userservice.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RoleEnum {
	
	ROLE_ADMIN("ROLE_ADMIN"), 
	ROLE_USER("ROLE_USER");
	
    private static final Map<String, RoleEnum> nameToValueMap = new HashMap<String, RoleEnum>();

    static {
        for (RoleEnum value : EnumSet.allOf(RoleEnum.class)) {
            nameToValueMap.put(value.name(), value);
        }
    }	
	
	private String value;
	
	private RoleEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public static RoleEnum forName(String name) {
        return nameToValueMap.get(name);
    }	

}

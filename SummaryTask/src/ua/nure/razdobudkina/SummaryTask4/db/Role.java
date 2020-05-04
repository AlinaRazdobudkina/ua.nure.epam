package ua.nure.razdobudkina.SummaryTask4.db;

import ua.nure.razdobudkina.SummaryTask4.db.entity.User;

/**
 * Role entity.
 * 
 * @author A.Razdobudkina
 * 
 */

public enum Role {
	ADMIN, TEACHER, STUDENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}

package ua.nure.razdobudkina.SummaryTask4.db.entity;

/**
 * User entity.
 * 
 * @author A.Razdobudkina
 * 
 */
public class User extends Entity {

	private static final long serialVersionUID = -6889036256149495388L;
	
	private String login;
	
	private String password;
	
	private String firstName;
	
	private String phone;
	
	private String email;
	
	private String lastName;

	private boolean active;
	
	private int roleId;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getFullName() {
		return (firstName + "\u00A0" + lastName);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [login=" + login
				+ ", firstName=" + firstName
				+ ", phone=" + phone
				+ ", email=" + email
				+ ", lastName=" + lastName
				+ ", active=" + active
				+ ", roleId=" + roleId + "]";
	}

	
}

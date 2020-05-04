package ua.nure.razdobudkina.SummaryTask4.db.entity;

public class Category extends Entity {

	private static final long serialVersionUID = -1043219368136728724L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
	
	
}

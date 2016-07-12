package models.entity;

public class Administrator {
	
	private int id;
	private String name ;
	private String password;

	public Administrator(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = Encryption.base64encode(password);
	}
	
	public boolean verifyPassword(String password){
		return Encryption.base64decode(this.password).equals(password);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
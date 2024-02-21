package entities;

public class Crypto {
	
	private Integer id;
	private String name;
	
	public Crypto() {}
	
	public Crypto(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Crypto [id=" + id + ", name=" + name +  "]";
	}


	
	
	
	
	
	
	

}

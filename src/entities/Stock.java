package entities;

public class Stock extends Crypto{
	private Integer id;
	private Crypto crypto;
	private Double value;
	

	public Stock(Integer id, Crypto crypto, Double value) {
		this.id = id;
		this.crypto = crypto;
		this.value = value;
	}
	
	public Stock() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Crypto getCrypto() {
		return crypto;
	}
	public void setCrypto(Crypto crypto) {
		this.crypto = crypto;
	}
	

	@Override
	public String toString() {
		return "Stock [id=" + id + ", crypto=" + crypto + ", value=" + value + "]";
	}
	
	
	
	
	
	
	
	

}

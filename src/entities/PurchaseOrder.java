package entities;

import java.util.Date;

public class PurchaseOrder {
	
	private Integer id;
	private Crypto crypto;
	private Double value;
	private Date date;
	
	public PurchaseOrder() {}
	
	public PurchaseOrder(Integer id, Crypto crypto, Double value, Date date) {
		this.id = id;
		this.crypto = crypto;
		this.value = value;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Crypto getCrypto() {
		return crypto;
	}
	public void setCrypto(Crypto crypto) {
		this.crypto = crypto;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "PurchaseOrder [id=" + id + ", crypto=" + crypto + ", value=" + value + ", date=" + date + "]";
	}

}

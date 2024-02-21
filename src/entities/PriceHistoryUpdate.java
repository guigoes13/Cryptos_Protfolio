package entities;

import java.util.Date;

public class PriceHistoryUpdate {
	
	private Integer id;
	private Crypto crypto;
	private Double price;
	private Date date;
	private int type;
	
	public PriceHistoryUpdate() {}
	
	public PriceHistoryUpdate(Integer id, Crypto crypto, Double price, Date date, int type) {
		this.id = id;
		this.crypto = crypto;
		this.price = price;
		this.date = date;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setDate(Date date) {
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


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Date getDate() {
		return date;
	}


	public void setData(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "PriceHistoryUpdate [id=" + id + ", crypto=" + crypto + ", price=" + price + ", date=" + date + ", type="
				+ type + "]";
	}


	
	
	
	
	
	
	

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.PriceHistoryUpdate;
import entities.Stock;

public class DaoPriceHistory {
	public DaoPriceHistory(){
		String inst = "CREATE TABLE IF NOT EXISTS PriceHistory"
				+"(id INT NOT NULL"
				+", Crypto INT NOT NULL"
				+", Value Double NOT NULL"
				+",  Date VARCHAR(40) NOT NULL"
				+",  type INT NOT NULL"
				+ ", PRIMARY KEY (id)"
				+" )ENGINE = InnoDB DEFAULT CHARSET = latin1;";
		
		try {
			try (PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				pS.execute();
			}
			DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
			
		} catch (SQLException e){
			throw new RuntimeException(e.getMessage());
			
		}
		
	}

	
	public boolean include(Object o) {
		boolean result = true;
		
		PriceHistoryUpdate ph = (PriceHistoryUpdate)o; 
		String inst = "Insert into PriceHistory(Id, Crypto, Value, Date, type) values (?,?,?,?,?)";
		try {
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				pS.setInt(1, ph.getId());
				pS.setInt(2, ph.getCrypto().getId());
				pS.setDouble(3, ph.getPrice());
				pS.setString(4, ph.getDate().toString());
				pS.setInt(5, ph.getType());
				pS.execute();
				Update(ph);
				
			}
			DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
		}catch (SQLException e){
			result = false;
			throw new RuntimeException(e.getMessage());
		}
		return(result);
	}
	public boolean Update(Object o) {
		boolean result = true;
		PriceHistoryUpdate ph = (PriceHistoryUpdate)o; 
		DaoStock dS = new DaoStock();
		Object o1 = dS.search(ph.getCrypto().getId(), 0);
		Stock st = (Stock)o1;

		String inst = "Update Stock set  Value = ? ";
				inst += "where Id = ? "; 
		try {
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
			
				pS.setDouble(1, ph.getPrice() +st.getValue());
				pS.setInt(2, st.getId());
				pS.execute();
			}
			DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
		}catch (SQLException e){
			result = false;
			throw new RuntimeException(e.getMessage());
		}
		return(result);
	}

	public int VerifyID(int id) {
		String inst = "SELECT MAX(id) AS max_id FROM  PriceHistory";
		ResultSet rS;
		int newID = 1;
		try {
			Connection con = DaoConnection.getInstancia().getCon();
			try(PreparedStatement pS = con.prepareStatement(inst)){
				rS = pS.executeQuery();
				DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
				if(rS.next()){ 
					int maxID = rS.getInt("max_id");
	                newID = maxID + 1; 
				}
			}
		}catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		}
		return(newID);
	}
	
	public Double Total(int id) {
		double profit = 0;
		String inst = "SELECT SUM(Value) AS TotalProfit FROM PriceHistory ";
			inst += "WHERE Crypto = ?";
			
		ResultSet rS;
		try {
			Connection con = DaoConnection.getInstancia().getCon();
			try(PreparedStatement pS = con.prepareStatement(inst)){
				pS.setInt(1,id);
				rS = pS.executeQuery();
				DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
				if(rS.next()){ 
					 profit = rS.getDouble("TotalProfit");
				}
			}
		}catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		}
		return(profit);
	}
	
	public Double Profit(int id) {
		Double TotalType = null;
		Double TotalAll= null;
		String inst = "SELECT"
				+ "    (SELECT SUM(Value) FROM pricehistory WHERE Crypto = ? and type = 1 ) AS TotalType,"
				+ "    (SELECT SUM(Value) FROM pricehistory WHERE Crypto = ?) AS TotalAll;";
			
		ResultSet rS;
		try {
			Connection con = DaoConnection.getInstancia().getCon();
			try(PreparedStatement pS = con.prepareStatement(inst)){
				pS.setInt(1,id);
				pS.setInt(2,id);

				rS = pS.executeQuery();
				DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
				if(rS.next()){ 
					 TotalType = rS.getDouble("TotalType");
					 TotalAll = rS.getDouble("TotalAll");
					
				}
			}
		}catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		}
		return(TotalAll - TotalType);
	}
	
	


	
}

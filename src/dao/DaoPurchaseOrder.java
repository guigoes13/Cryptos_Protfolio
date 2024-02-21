package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import entities.Crypto;
import entities.PriceHistoryUpdate;
import entities.PurchaseOrder;
import entities.Stock;



public class DaoPurchaseOrder {
	
	DaoPriceHistory dPHU =  new DaoPriceHistory();
	SimpleDateFormat sdf = new SimpleDateFormat();
	public DaoPurchaseOrder(){
		String inst = "CREATE TABLE IF NOT EXISTS PurchaseOrder"
				+"(id INT NOT NULL"
				+", Crypto INT NOT NULL"
				+", Value Double NOT NULL"
				+",  Date VARCHAR(40) NOT NULL"
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
		
		PurchaseOrder p = (PurchaseOrder)o; 
		String inst = "Insert into PurchaseOrder(Id, Crypto, Value, Date) values (?,?,?,?)";
		try {
			
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				pS.setInt(1, p.getId());
				pS.setInt(2, p.getCrypto().getId());
				pS.setDouble(3, p.getValue());
				pS.setString(4, p.getDate().toString());
				pS.execute();
				PriceHistoryUpdate pHU = new PriceHistoryUpdate(dPHU.VerifyID(0)
						,p.getCrypto(),p.getValue(),p.getDate(),1);
				dPHU.include(pHU) ;
				
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
		PurchaseOrder p = (PurchaseOrder)o; 
		DaoStock dS = new DaoStock();
		Object o1 = dS.search(p.getCrypto().getId(), 0);
		Stock s = (Stock)o1;

		String inst = "Update Stock set  Value = ? ";
				inst += "where Id = ?"; 
		try {
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
			
				pS.setDouble(1,s.getValue() + p.getValue());
				pS.setInt(2, s.getId());
				pS.execute();
			}
			DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
		}catch (SQLException e){
			result = false;
			throw new RuntimeException(e.getMessage());
		}
		return(result);
	}

	
	
	public Object search(int id, int iD2) {
		String inst = "Select * from PurchaseOrder where id = ?";
		PurchaseOrder pO = null;
		ResultSet rS;
		try {
			Connection con = DaoConnection.getInstancia().getCon();
			try(PreparedStatement pS = con.prepareStatement(inst)){
				pS.setInt(1,id);
				rS = pS.executeQuery();
				DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
				if(rS.next()){ 
					pO = new PurchaseOrder();
					pO.setId(rS.getInt("id"));
					pO.setValue(rS.getDouble("Value"));
					pO.setDate(rS.getDate("Date"));
					DaoCrypto dC = new DaoCrypto();
					Object oc =  dC.search(rS.getInt("Crypto"), 0);
					Crypto c = (Crypto)oc;
					pO.setCrypto(c);
				}
			}
		}catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		}
		return(pO);
	}
	


	
	public int VerifyID(int id) {
		String inst = "SELECT MAX(id) AS max_id FROM  PurchaseOrder";
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



	
}

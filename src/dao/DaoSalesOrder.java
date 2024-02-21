package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.PriceHistoryUpdate;
import entities.SalesOrder;
import entities.Stock;

public class DaoSalesOrder {
	DaoPriceHistory dPHU =  new DaoPriceHistory();
	SimpleDateFormat sdf = new SimpleDateFormat();
	
	public DaoSalesOrder(){
		String inst = "CREATE TABLE IF NOT EXISTS SalesOrder"
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
		
		SalesOrder s = (SalesOrder)o; 
		String inst = "Insert into SalesOrder(Id, Crypto, Value, Date) values (?,?,?,?)";
		try {
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				pS.setInt(1, s.getId());
				pS.setInt(2, s.getCrypto().getId());
				pS.setDouble(3, s.getValue());
				pS.setString(4, s.getDate().toString());
				pS.execute();
				
				PriceHistoryUpdate pHU = new PriceHistoryUpdate(dPHU.VerifyID(0)
						,s.getCrypto(),s.getValue()- (2*s.getValue()),s.getDate(), 2) ;
				dPHU.include(pHU);
				
				
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
	    SalesOrder s = (SalesOrder)o; 
	    DaoStock dS = new DaoStock();
	    Object o1 = dS.search(s.getCrypto().getId(), 0);
	    Stock st = (Stock)o1;

	    String inst = "Update Stock set Value = ? ";
	    inst += "where Id = ? "; 
	    try {
	        try(PreparedStatement pS =
	                DaoConnection.getInstancia().getCon().prepareStatement(inst)){
	        
	            pS.setDouble(1, st.getValue() - s.getValue());
	            pS.setInt(2, st.getId());
	            pS.execute();
	        }
	        DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
	    }catch (SQLException e){
	        result = false;
	        throw new RuntimeException(e.getMessage());
	    }
	    return result;
	}


	
	public int VerifyID(int id) {
		String inst = "SELECT MAX(id) AS max_id FROM  SalesOrder";
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

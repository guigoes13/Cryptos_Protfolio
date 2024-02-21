package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entities.Stock;

public class DaoStock  {
	public DaoStock(){
		String inst = "CREATE TABLE IF NOT EXISTS Stock"
				+"(id INT NOT NULL"
				+", Value Double NOT NULL"
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
		
		Stock s = (Stock)o; 
		String inst = "Insert into Stock(Id, Value) values (?,?)";
		try {
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				pS.setInt(1, s.getId());
				pS.setDouble(2, 0.00);
				pS.execute();
			}
			DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
		}catch (SQLException e){
			result = false;
			throw new RuntimeException(e.getMessage());
		}
		return(result);
	}

	public boolean Update(int id, int crypto, double value) {
		boolean result = true;
		String inst = "Update Stock set  Value = ? , Crypto = ?";
				inst += "where Id = ? "; 
		try {
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
			
				pS.setInt(2,id);
				pS.setDouble(1, value);
				pS.setInt(3, id);
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
		String inst = "Select * from Stock where id = ?";
		Stock s = null;
		ResultSet rS;
		try {
			Connection con = DaoConnection.getInstancia().getCon();
			try(PreparedStatement pS = con.prepareStatement(inst)){
				pS.setInt(1,id);
				rS = pS.executeQuery();
				DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
				if(rS.next()){ //
					s = new Stock();
					s.setId(rS.getInt("id"));
					s.setValue(rS.getDouble("Value"));
				}
			}
		}catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		}
		return(s);
	}
	


	
}

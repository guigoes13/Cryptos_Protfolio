package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Crypto;
import entities.Stock;

public class DaoCrypto  {
	DaoStock dS = null;


	public DaoCrypto(){
		String inst = "CREATE TABLE IF NOT EXISTS Crypto"
				+"(id INT NOT NULL"
				+", Name VARCHAR(40) NOT NULL"
				+ ", PRIMARY KEY (id)"
				+" )ENGINE = InnoDB DEFAULT CHARSET = latin1;";
		
		try {
			try (PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				pS.execute();
				dS = new DaoStock();

			}
			DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
			
		} catch (SQLException e){
			throw new RuntimeException(e.getMessage());
			
		}
		
	}

	
	public boolean include(Object o) {
		boolean result = true;
		
		Crypto c = (Crypto)o; 
		String inst = "Insert into Crypto(Id, Name) values (?,?)";
		try {
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				pS.setInt(1, c.getId());
				pS.setString(2, c.getName());
				pS.execute();
				dS.include(c);
				
				
			}
			DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
		}catch (SQLException e){
			result = false;
			throw new RuntimeException(e.getMessage());
		}
		return(result);
	}

	
	public Object search(int id, int iD2) {
		String inst = "Select * from Crypto where id = ?";
		Crypto c = null;
		ResultSet rS;
		try {
			Connection con = DaoConnection.getInstancia().getCon();
			try(PreparedStatement pS = con.prepareStatement(inst)){
				pS.setInt(1,id);
				rS = pS.executeQuery();
				DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
				if(rS.next()){ //
					c = new Crypto();
					c.setId(rS.getInt("id"));
					c.setName(rS.getString("name"));
				}
			}
		}catch (SQLException e){
			throw new RuntimeException(e.getMessage());
		}
		return(c);
	}

	
	public List<Object> load() {
		String inst="Select * from Crypto order by id";
		List<Object> lista = new ArrayList<>();
		ResultSet rS;
		Object o;
		
		try{
			try(PreparedStatement pS =
					DaoConnection.getInstancia().getCon().prepareStatement(inst)){
				rS = pS.executeQuery(inst);
				DaoConnection.getInstancia().setCon(DaoConnection.getInstancia().getCon());
				if (rS!= null)
					while (rS.next()){
						o = search (rS.getInt("id"), 0);
						lista.add(o);
					}
				pS.close();
			}
		}catch(SQLException e){
			throw new RuntimeException(e.getMessage());
		}
		return(lista);
	
}
	public int VerifyID(int id) {
		String inst = "SELECT MAX(id) AS max_id FROM  Crypto";
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

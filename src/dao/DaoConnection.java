package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class DaoConnection {
	private static DaoConnection instancia = null;
	private Connection con;
	private String servidor;
	private String bD;
	private String usuario;
	private String senha;

	
	private DaoConnection(){
	        Properties prop = new Properties();

	        try (FileReader reader = new FileReader("src/config.properties")) {
	            prop.load(reader);

	            this.servidor = prop.getProperty("servidor");
	            this.bD = prop.getProperty("bD");
	            this.usuario = prop.getProperty("usuario");
	            this.senha = prop.getProperty("senha");
	        } catch (IOException e) {
	            e.printStackTrace();
	        
	    }
		final String Driver = "com.mysql.cj.jdbc.Driver";
		String url;
		url = "jdbc:mysql://" + servidor + '/' + bD;
		url += "?createDatabaseIfNotExist=true";
		url += "&user=" + usuario + "&password=" +senha;
		try{
			Class.forName(Driver).newInstance();
			con = DriverManager.getConnection(url);
		}
		catch(ClassNotFoundException | IllegalAccessException |InstantiationException |SQLException e){
		    final JPanel panel = new JPanel();

			JOptionPane.showMessageDialog(panel , "Erro de conexão" + e.getMessage());
			
		}
		
	}
	public synchronized static DaoConnection getInstancia(){
		if (instancia == null){
			instancia = new DaoConnection();
		}
		return instancia;
		
	}
	//Execyta sem permitir que o processo seja interrompido 
	
	public static void setInstancia(DaoConnection instancia){
		DaoConnection.instancia = instancia;
		
	}
	public Connection getCon(){
		if (con == null)
			getInstancia();
		return con;
	}
	public void setCon(Connection con){
		this.con = con;
	}
	
}

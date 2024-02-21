package services;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import dao.DaoCrypto;
import dao.DaoPriceHistory;
import dao.DaoStock;
import entities.Crypto;
import entities.PriceHistoryUpdate;

//Teste!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

public class PriceUpdateService {
	public String getPrice(String coin) {
	    try {
	        String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=" + coin + "&vs_currencies=brl";

	        URL url = new URL(apiUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");

	        BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder response = new StringBuilder();
	        String inputLine;

	        while ((inputLine = inputReader.readLine()) != null) {
	            response.append(inputLine);
	        }
	        inputReader.close();

	        String jsonResponse = response.toString();
	        String[] parts = jsonResponse.split(":");
	        if (parts.length >= 3) {
	            String price = parts[2].replaceAll("[^\\d.]", ""); // Tratar a resposta JSON corretamente
	            return price;
	        } else {
	            // Se não puder encontrar o preço desejado, retorne 'o'
	            return "o";
	        }
	    } catch (IOException e) {
	        // Em caso de erro de IO, você pode lidar com a exceção aqui
	        e.printStackTrace(); // Isso é apenas para depuração, você pode remover ou modificar conforme necessário
	        return "o"; // Se ocorrer uma exceção, retorne 'o'
	    }
	}

	 public  String getPriceOnDate(String coin, String date) throws IOException {
	        // Constrói a URL com o endpoint para dados históricos da CoinGecko
	        String apiUrl = "https://api.coingecko.com/api/v3/coins/" + coin + "/history?date=" + date;

	        URL url = new URL(apiUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");

	        BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder response = new StringBuilder();
	        String inputLine;

	        while ((inputLine = inputReader.readLine()) != null) {
	            response.append(inputLine);
	        }
	        inputReader.close();

	        // Analisa a resposta JSON para obter o preço na data específica
	        String jsonResponse = response.toString();
	        String price = jsonResponse.split("\"")[15]; // Ajuste isso conforme a estrutura da resposta JSON

	        return price;
	    }

		
		
	 private void UpdateValue() {
		 
		
		 
	 }
	
}


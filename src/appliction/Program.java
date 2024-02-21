package appliction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.DaoCrypto;
import dao.DaoPriceHistory;
import dao.DaoPurchaseOrder;
import dao.DaoSalesOrder;
import dao.DaoStock;
import entities.Crypto;
import entities.PriceHistoryUpdate;
import entities.PurchaseOrder;
import entities.SalesOrder;
import entities.Stock;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String Mask = "R$ ";
		int code = 0;
		
		DaoStock dST = new DaoStock();
		DaoCrypto dC = new DaoCrypto();
		DaoPurchaseOrder dP = new DaoPurchaseOrder();
		DaoSalesOrder dS = new DaoSalesOrder();
		DaoPriceHistory dPH = new DaoPriceHistory();

		
		String res ="S";
		while(res.equals("S")) {
			
		System.out.println("Insert a new crypto [1]");
		System.out.println("Insert a new sale [2]");
		System.out.println("Insert a new purchase [3]");
		System.out.println("Update the value of some crypto manually[4]");
		System.out.println("How much profit did It make at the moment? [5]");
		System.out.println("See how much you have [6]");


		int res1 = sc.nextInt();
		System.out.println();
		System.out.println();


		
		switch(res1) {
		case 1 : 
			int x = 0;
			System.out.println("How many cryptos do you want to add?");
			x = sc.nextInt();
			for(int i=0; i<x;i++) {
				 System.out.print("Enter with the name: ");
				 String name = sc.next();
				 int id = dC.VerifyID(0);
				 Stock st = new Stock();
				 Crypto c =st;
				 c.setId(id);
				 c.setName(name);
				 dC.include(c);		
				 }
				
			break;
			
		case 2 :
			System.out.println("How many sales do you want to add?");
			x = sc.nextInt();
			for(int i=0; i<x;i++) {
				 System.out.println("Chose the crypto (Enter with the code):");
				 List<Object> cryptos = dC.load();
				 for(Object o : cryptos) {
					 Crypto c = (Crypto)o;
					 System.out.println(c.getName() + " [" +c.getId()+"]");	 
				 }
				  code = sc.nextInt();
				 System.out.print("Enter with the value: ");
				 double value = sc.nextDouble();
				 System.out.print("Enter with the date(dd/MM/yyyy): ");
				 String date = sc.next();
				 Crypto c = (Crypto) dC.search(code, 0);
				 SalesOrder so = new SalesOrder(dS.VerifyID(0), c , value,sdf.parse(date));	
				 dS.include(so);			
			}
			break;
			
		case 3:
			System.out.println("How many purchase do you want to add?");
			 x = sc.nextInt();
				for(int i=0; i<x;i++) {
					 System.out.println("Chose the crypto (Enter with the code):");
					 List<Object> cryptos = dC.load();
					 for(Object o : cryptos) {
						 Crypto c = (Crypto)o;
						 System.out.println(c.getName() + " [" +c.getId()+"]");	 
					 }
					  code = sc.nextInt();
					 System.out.print("Enter with the value: ");
					 double value = sc.nextDouble();
					 System.out.print("Enter with the date(dd/MM/yyyy): ");
					 String date = sc.next();
					
					 Crypto c = (Crypto) dC.search(code, 0);
					 PurchaseOrder po = new PurchaseOrder(dP.VerifyID(0), c , value,sdf.parse(date));	
					 dP.include(po);
				}
				break;
				
		case 4:
			System.out.println("How many updates do you want to add?");
			 x = sc.nextInt();
				for(int i=0; i<x;i++) {
					 System.out.println("Chose the crypto (Enter with the code):");
					 List<Object> cryptos = dC.load();
					 for(Object o : cryptos) {
						 Crypto c = (Crypto)o;
						 System.out.println(c.getName() + " [" +c.getId()+"]");	 
					 }
					 code = sc.nextInt();
					 System.out.print("Enter with the value: ");
					 double value = sc.nextDouble();
					 Date date = new Date();
					 Crypto c = (Crypto) dC.search(code, 0);
					 PriceHistoryUpdate pHU = new PriceHistoryUpdate(dPH.VerifyID(0), c , 
							  value - dPH.Total(code),date, 3);	
					 dPH.include(pHU);
				}
				break;
				
		case 5:
			System.out.println("Chose the crypto (Enter with the code):");
			 List<Object> cryptos = dC.load();
			 for(Object o : cryptos) {
				 Crypto c = (Crypto)o;
				 System.out.println(c.getName() + " [" +c.getId()+"]");	 
			 }
			 code = sc.nextInt();
			 System.out.println( Mask + dPH.Profit(code));
			 break;
			 
		case 6:
			System.out.println("Chose the crypto (Enter with the code):");
			 List<Object> crypto = dC.load();
			 for(Object o : crypto) {
				 Crypto c = (Crypto)o;
				 System.out.println(c.getName() + " [" +c.getId()+"]");
			 }
				 code = sc.nextInt();
				Object oS = dST.search(code, 0);
				Stock st = (Stock)oS;
				System.out.println("You have: " + Mask + st.getValue() );
				 break;

			 }

		
			 	
		System.out.print("Do you wanna do somehing else? (S/N)");
		 res = sc.next();
		 
		 System.out.println();
		 System.out.println();
		}
		System.out.println("Thanks! Goodbye!!!");
		System.exit(0);
		sc.close();
	
}
}

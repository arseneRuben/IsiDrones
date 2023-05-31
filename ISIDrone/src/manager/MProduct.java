package manager;

import entities.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MProduct {
    public static List<Item> getAll(){
		
		List<Item> productList= new ArrayList<Item>();
		
		try {
			
			MDB.connect();

			String query = "SELECT product.name,.category,product.price,product.stockQty FROM `product` AS product INNER JOIN category AS category ON category.id = product.category;";
			
			PreparedStatement ps = MDB.getPS(query);

			ResultSet rs = ps.executeQuery();
                        Item item;
			// Une ligne = un item avec qte
			while(rs.next()) {
								
					// Nouveau produit
					item = new Item();
					item.setCategory(rs.getInt("category.category"));
					item.setName(rs.getString("product.name"));
                                        item.setPrice(rs.getDouble("product.price"));
                                        item.setStock(rs.getInt("product.stockQty"));
					// Ajouter le produit a la liste	
					productList.add(item);
				}
				
				
			} catch (SQLException ex) {
            Logger.getLogger(MProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
			
			
       return productList;
			
			
   }
    
    public static void main(String args[]){
       List<Item> items=MProduct.getAll();
       for(Item item:items){
       System.out.println("Product "+item.getName());
       }
    }
}

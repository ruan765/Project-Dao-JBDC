package apliccation;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		
		System.out.println("Test 1 Seller FindById");
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
		
		System.out.println("Test 2 Seller FindById");
		
		Department dep = new Department(1,null);
		
		List<Seller> list = sellerDao.findByDepartment(dep);
		
		for(Seller sellr: list) {
			System.out.println(sellr);
		}
		
		System.out.println("\n Teste 3 FindAll");
		
		List<Seller> findAll = sellerDao.findAll();
		
		for(Seller sellers: findAll) {
			System.out.println(sellers);
		}
		
		System.out.println("\n teste 4 INSERT");
		
		Seller obj = new Seller(null , "ruan","ruan@gmail.com",new Date(), 1500.0,new Department(1,null));
		sellerDao.insert(obj); 
		System.out.println("Inserted! New Id " + obj.getId());
	
		System.out.println("TESTE 5 Seller UPDATE");
		
		seller = sellerDao.findById(1);
		seller.setName("Martha waine");
		sellerDao.update(seller);
		System.out.println("Update completed!");
		
		
		System.out.println("Teste 6 DeleById");
		sellerDao.deleteById(19);
		System.out.println("Delete Completed");

	}

}

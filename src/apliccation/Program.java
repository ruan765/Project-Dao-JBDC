package apliccation;

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
		
	
		
		

	}

}

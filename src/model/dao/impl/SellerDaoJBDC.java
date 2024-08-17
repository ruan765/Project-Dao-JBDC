package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.dbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJBDC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJBDC(Connection conn) {
		this.conn = conn;
	}
	
	
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer Id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer Id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st= conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName \r\n"
					+ "		FROM seller INNER JOIN department \r\n"
					+ "		ON seller.DepartmentId = department.Id \r\n"
					+ "		WHERE seller.Id = ?"
					);
			st.setInt(1, Id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dep = instatiateDepartment(rs);
				
				Seller obj = instantiateSeller(rs, dep);
				return obj;
				
			}
			return null;
			
		}
		catch(SQLException e) {
			throw new dbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStament(st);
		}
		
	}

	private Seller instantiateSeller(ResultSet rs, Department dep)throws SQLException {
		
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		
		return obj;
	}



	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		
		return dep;
	}



	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName \r\n"
					+ "FROM seller INNER JOIN department \r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "ORDER BY Id\r\n");
			
			rs = st.executeQuery();
			List<Seller> sells = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					 dep = instatiateDepartment(rs);
					 
					 map.put(rs.getInt("DepartmentId"), dep);
				}
				
				
				Seller seller = instantiateSeller(rs, instatiateDepartment(rs));
				
				sells.add(seller);
				
				
			}
			
			return sells;

		}catch(SQLException e) {
			throw new dbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStament(st);
		}
		
		
	}



	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st= conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName \r\n"
					+ "FROM seller INNER JOIN department \r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "WHERE DepartmentId = ?\r\n"
					+ "ORDER BY Name"
					);
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					 dep = instatiateDepartment(rs);
					 
					 map.put(rs.getInt("DepartmentId"), dep);
				}
				
				
				
				Seller obj = instantiateSeller(rs, dep);
				
				sellers.add(obj);
				
				
				
			}
			return sellers;
			
			
		}
		
		catch(SQLException e) {
			throw new dbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStament(st);
		}
		
	}

}

package com.noahark.calcedit.db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Database {

	//stmt.executeUpdate("create table hehe(id number, name varchar(32))");
	
	public Database() {
		super();
		try {
			
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:data.db");
			conn.setAutoCommit(false);
			
			pstQueryTableExists = conn.prepareStatement("select count(*) from sqlite_master where type='table' and name=?");
			
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private PreparedStatement pstQueryTableExists;
	private PreparedStatement insertObject;
	private Statement stmt;
	
	private Connection conn = null;
	
	//check table is exists.
	private boolean tableIsExists(String tabName) throws SQLException{
		int count=0;
		pstQueryTableExists.setString(1, tabName);
	
		ResultSet rset = null;
		try {
			
			rset = pstQueryTableExists.executeQuery();
			while (rset.next()){  
				count = rset.getInt(1);
	            System.out.println(count); 
	        }  
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rset != null){
				rset.close();
			}
		}
		
		
		if (count > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public void commit() throws SQLException{
		conn.commit();
	}
	
	public void rollback() throws SQLException{
		conn.rollback();
	}
	
	public void close(){
		try {
			
			if (stmt != null){
				stmt.close();
			}
			
			
			if (pstQueryTableExists != null){
				pstQueryTableExists.close();
			}
			
			
			if (insertObject != null){
				insertObject.close();
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertObject(String app, String code, String name, String parent, String type) throws SQLException{
		if (insertObject == null){
			insertObject = conn.prepareStatement("insert into hsp_object(hsp_app,object_code,object_name,object_parent,object_type) values (?,?,?,?,?)");
		}
		insertObject.setString(1, app);
		insertObject.setString(2, code);
		insertObject.setString(3, name);
		insertObject.setString(4, parent);
		insertObject.setString(5, type);
		insertObject.executeUpdate();
	}
	public void insertObject(String app, HspObject obj) throws SQLException{
		if (insertObject == null){
			insertObject = conn.prepareStatement("insert into hsp_object(hsp_app,object_code,object_name,object_parent,object_type) values (?,?,?,?,?)");
		}
		insertObject.setString(1, app);
		insertObject.setString(2, obj.getObjectCode());
		insertObject.setString(3, obj.getObjectName());
		insertObject.setString(4, obj.getObjectParent());
		insertObject.setString(5, obj.getObjectType());
		insertObject.executeUpdate();
		
	}
	
	public Map<String,String> queryObjectsByApp(String app, String QueryType){
		Map<String,String> map = new HashMap<String,String>();
		ResultSet rset = null;
		PreparedStatement pst = null;
		
		try {
			pst = conn.prepareStatement("select object_code,object_name from hsp_object where hsp_app=?");
			pst.setString(1, app);
			rset = pst.executeQuery();
			
			if (QueryType.equals("code")){
				while (rset.next()){
					String code = rset.getString("object_code");
					String name = rset.getString("object_name");
					
					map.put(code, name);
				}
			} else {
				while (rset.next()){
					String code = rset.getString("object_code");
					String name = rset.getString("object_name");
					
					map.put(name,code);
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if (rset != null){
				try {
					rset.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (pst != null){
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		return map;
		
	}
	
	public void deleteAllData(String tabName) throws SQLException{
		stmt.executeUpdate("delete from " + tabName);
	}
	
	//drop table 
	public void dropTable(String tabName) throws SQLException{
		stmt.executeUpdate("drop table " + tabName);
	}
	
	//create table;
	public void createTable(String tabName, String createSql, boolean isDrop) throws SQLException{	
		if (isDrop){
			dropTable(tabName);
			//create
			stmt.executeUpdate(createSql);
		} else {
			if (!tableIsExists(tabName)){
				stmt.execute(createSql);						
			}
		}
				
		
	}
	
	
	public void readMembers(String fileName){
		String propLine = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("无法以utf8模式访问文件：" + "forms.txt",e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("无法访问文件：" + "forms.txt",e);
		}
		
		
		try {
			while ((propLine = in.readLine()) != null) {
				if (propLine.length() != 0) {
					String trimLine = propLine.trim();
					String[] tmp = trimLine.split("\t");
					
					if (tmp.length >= 5){
						System.out.println(tmp[1]);
						try {
							insertObject(tmp[0],tmp[1],tmp[3],tmp[2],tmp[4]);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		} catch (IOException e) {
			throw new RuntimeException("无法读取文件：" + "forms.txt",e);
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("系统错误！！！");
			}
		}
	}

	public static void main(String[] args) {
		Database conn = new Database();
		
		try{
			//conn.createTable("hsp_object", "create table hsp_object(hsp_app varchar(100), object_code varchar(250), object_name varchar(250), object_parent varchar(250), object_type varchar(100))", false);
			//conn.commit();			
			//conn.readMembers("D:\\Oracle\\YW.txt");
			//conn.commit();
			Map<String,String> objects = conn.queryObjectsByApp("HPYW", "code");
			
			//System.out.println(objects);
			
		} catch (Exception e){
			System.out.print(e);
		} finally {
			conn.close();
		}
		
		System.out.print("Success!!");
	}

}

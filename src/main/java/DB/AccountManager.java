package DB;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import View.AccountView;
import View.AccountIDView;

public class AccountManager {
	
	private String year;
	private String month;
	private String day;
	
	private ArrayList<String> adayCostRecordIDList = new ArrayList<String>();
	
	public AccountManager(String year, String month , String day) {
	  this.year = year;
	  this.month = month;
	  this.day = day;
	}

	public void createTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE Account( ID INTEGER PRIMARY KEY AUTOINCREMENT ,YEAR TEXT NOT NULL,MONTH TEXT NOT NULL,DAY TEXT NOT NULL,CONTENT TEXT NOT NULL,COST TEXT NOT NULL, TYPE TEXT NOT NULL);";
					
			stmt.executeUpdate(sql);
			c.setAutoCommit(false);
			c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	public void executeSQL(String sqlStmt) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			stmt.executeUpdate(sqlStmt);
			c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}

	public void addCostRecord(CostRecord data) {
		String sql = "insert into Account( YEAR, MONTH, DAY,CONTENT, COST, TYPE )" + "VALUES(\'" + data.getYear() + "','"
				+ data.getMonth() + "','" + data.getDay() + "','" + data.getContent() + "','" + data.getcost() + 
				"','"+ data.gettype()+ "\');";
		executeSQL(sql);
		setCostRecord();
	}

	public void editCostRecord(CostRecord data, String idEdit) {
		String sql = "update Account set CONTENT='" + data.getContent() + "',COST = '" + data.getcost() +"',TYPE='" + data.gettype() +"'where id = '" +idEdit + "';"; 
		executeSQL(sql);
		setCostRecord();
	}	

	public void deleteDayCostRecord(String id) {
		String sql = "delete from Account where id ='" + id + "'";
		executeSQL(sql);
		setCostRecord();
	}
	
	public ArrayList<String> getadayCostRecordIDList(){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			System.out.println(year);
			String sql = "SELECT * FROM Account where year='" + year +"'AND month='" + month +"'AND day='" + day + "';";
			ResultSet rs = stmt.executeQuery(sql);
			adayCostRecordIDList.clear(); 
			while (rs.next()) {
	            adayCostRecordIDList.add( rs.getString("id"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	  return adayCostRecordIDList;
	}
	
	public CostRecord getIdCostRecord(String id){
		Connection c = null;
		Statement stmt = null;
		CostRecord costRecord = new CostRecord();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "SELECT * FROM Account where id='" + id +"';";
			
			ResultSet rs = stmt.executeQuery(sql);
			costRecord.setDay(rs.getString("day"));
			costRecord.setYear(rs.getString("year"));
			costRecord.setMonth(rs.getString("month"));
			costRecord.setcost(rs.getString("cost"));
			costRecord.settype(rs.getString("type"));
			costRecord.setContent(rs.getString("CONTENT"));
			costRecord.setId(rs.getString("id"));	
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully----------");
		return costRecord;
	}
	
	public void setCostRecord(){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Calendar.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			System.out.println(year);
			String sql = "SELECT * FROM Account where year='" + year +"'AND month='" + month +"'AND day='" + day + "';";
			
			ResultSet rs = stmt.executeQuery(sql);
			AccountIDView.idBox.removeAllItems();
			DefaultComboBoxModel model = (DefaultComboBoxModel)AccountIDView.idBox.getModel();
			DefaultTableModel tm = (DefaultTableModel)AccountView.table.getModel();
			ResultSetMetaData rsmd = rs.getMetaData();
			adayCostRecordIDList.clear();
			tm.setColumnCount(0);
			tm.setRowCount(0);
			tm.addColumn("ID"); 
			tm.addColumn("COST"); 
			tm.addColumn("TYPE"); 
			tm.addColumn("CONTENT"); 
			int i = 1;
			while (rs.next()) {
				String[] a = new String[4];
	            a[0] =  Integer.toString(i);
	            a[1] = rs.getString("cost");
	            a[2] = rs.getString("type");
	            a[3] = rs.getString("CONTENT");
	            tm.addRow(a);
	            i++;
	            adayCostRecordIDList.add( rs.getString("id"));
			}
			for (int x = 0; x < adayCostRecordIDList.size(); x++) {
				model.addElement(Integer.toString(x+1));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}

	public static void main(String args[]) {
		AccountManager jd = new AccountManager("2018", "4", "27");
		CostRecord costRecord = new CostRecord();
		costRecord.setDay("30");
		costRecord.setYear("2018");
		costRecord.setMonth("4");
		costRecord.setcost("400");
		costRecord.settype("eat");
		costRecord.setContent("dinner");
		jd.addCostRecord(costRecord);
	}
}
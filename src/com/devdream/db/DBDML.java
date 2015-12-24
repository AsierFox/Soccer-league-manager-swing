package com.devdream.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devdream.util.QueryGenerator;

/**
 * Class for the Data Manipulation (DML) language.
 *
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class DBDML extends DBConnectionManager {

	private PreparedStatement preparedStmt;
	
	public DBDML() {
		super();
		preparedStmt = null;
	}
	
	// TODO Remove
	public void testSQLLite(String sql) {
		try {
			preparedStmt = getConnection().prepareStatement(sql);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			closePreparedStatement();
			super.close();
		}
	}
	
	/**
	 * Create a table passing the model class.
	 * @param model The model of the table
	 * @param hasIdCol If the table has an ID column
	 * @throws SQLException
	 */
	public void createTable(Class<?> model, boolean hasIdCol) throws SQLException {
		try {
			preparedStmt = getConnection().prepareStatement(QueryGenerator.createTable(model, hasIdCol));
			preparedStmt.executeUpdate();
		} finally {
			closePreparedStatement();
			super.close();
		}
	}
	
//	/**
//	 * Method to execute a query passing the SQL query and the number of the column numbers.
//	 * @param sqlQuery The SQL Query
//	 * @param ncols Number of columns
//	 */
//	public void executeQuery(String sqlQuery, int ncols) throws SQLException {
//		try {
//			prepStmt = getPreparedStatement(sqlQuery);
//			ResultSet rs = prepStmt.executeQuery();
//			
//			while (rs.next()) {
//				for (int i = 1; i <= ncols; ++i) {
//					System.out.print(rs.getString(i) + ", ");
//				}
//				System.out.println();
//			}
//		} finally {
//			closePreparedStatement();
//			super.close();
//		}
//	}
//	
//	/**
//	 * Executes an SQL query for the update, modification or deletion of a row.
//	 * @param sqlUpdate SQL Query
//	 * @return int Affected rows
//	 */
//	public int executeUpdate(String sqlUpdate) throws SQLException {
//		int affectedRows = 0;		
//		try {
//			prepStmt = getPreparedStatement(sqlUpdate);
//			affectedRows = prepStmt.executeUpdate();
//		} finally {
//			closePreparedStatement();
//			super.close();
//		}
//		return affectedRows;
//	}
//	
//	/**
//	 * Executes a query for selecting some fields.
//	 * @param tableName
//	 * @param fields
//	 */
//	public void selectCols(String tableName, String... fields) throws SQLException {
//		String query = Query.createSelect(tableName, fields);
//		try {
//			prepStmt = getPreparedStatement(query);
//			ResultSet rs = prepStmt.executeQuery();
//			
//			while (rs.next()) {
//				for (int ncols = fields.length, i = 1; i <= ncols; ++i) {
//					System.out.print(rs.getString(i) + ", ");
//				}
//				System.out.println();
//			}
//		} finally {
//			closePreparedStatement();
//			super.close();
//		}
//	}
//	
//	/**
//	 * Updates a row to the database, passing the information to update a row.
//	 * @param tableName The name of the table
//	 * @param fields An array using the fields
//	 * @param values An array of the values
//	 * @param colCond The column name for the conditional
//	 * @param valueCond The value to compare with on the conditional
//	 * @throws SQLException
//	 */
//	public int updateRow(String tableName, String[] fields, String[] values, String colCond, String valueCond) throws SQLException {
//		String query = Query.createUpdate(tableName, fields, values, colCond, valueCond);
//		int affectedRows = 0;
//		try {
//			prepStmt = getPreparedStatement(query);
//			affectedRows = prepStmt.executeUpdate();
//		} finally {
//			closePreparedStatement();
//			super.close();
//		}
//		return affectedRows;
//	}
//	
//	/**
//	 * Deletes a row to the database, passing the information to delete a row.
//	 * @param tableName The name of the table
//	 * @param colCond The column name for the conditional
//	 * @param valueCond The value to compare with on the conditional
//	 * @return String The SQL query for an insert
//	 * @throws SQLException
//	 */
//	public int deleteRow(String tableName, String colCond, String valueCond) throws SQLException {
//		String query = Query.createDelete(tableName, colCond, valueCond);
//		int affectedRows = 0;
//		
//		try {
//			prepStmt = getPreparedStatement(query);
//			affectedRows = prepStmt.executeUpdate();
//		} finally {
//			closePreparedStatement();
//			super.close();
//		}
//		return affectedRows;
//	}
//	
//	/**
//	 * Inserts a row to the database, passing the information to create the query.
//	 * @param tableName The name of the table
//	 * @param fieldNames Fields to add to the update query
//	 * @param values The values for the insertion
//	 * @return String The SQL query for an insert
//	 * @throws SQLException
//	 */
//	public int insertRow(String tableName, String[] fieldNames, String... values) throws SQLException {
//		String query = Query.createInsert(tableName, fieldNames, values);
//		System.out.println(query);
//		int affectedRows = 0;
//		
//		try {
//			prepStmt = getPreparedStatement(query);
//			affectedRows = prepStmt.executeUpdate();
//		} finally {
//			closePreparedStatement();
//			super.close();
//		}
//		return affectedRows;
//	}
	
	/** Closes the prepared statement. */
	private void closePreparedStatement() {
		try {
			if (preparedStmt != null) {
				preparedStmt.close();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}

package com.devdream.util;

import java.lang.reflect.Field;

import com.devdream.db.DBConnectionManager;

/**
 * Class containing static methods for creating dynamic queries.
 *
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class QueryGenerator extends DBConnectionManager {
	/**
	 * The SQL syntax for the typical ID primary key.
	 */
	private static final String ID_ATTR = "'id' INTEGER PRIMARY KEY AUTOINCREMENT";
	
	/**
	 * Creates the select for creating a table.
	 * @param model The class of the model
	 * @return String The SQL query to create the table basing on the model
	 */
	public static String createTable(Class<?> model, boolean hasIdAttr) {
		Field[] fields = model.getDeclaredFields();
		String sql = "CREATE TABLE '" + model.getSimpleName().toLowerCase() + "' (";
		if (hasIdAttr) {
			sql += ID_ATTR;
		}
		for (int i = 1, nfields = fields.length; i < nfields; ++i) {
			Field field = fields[i];
			sql += ", '" + field.getName() + "' ";
			switch (field.getType().getSimpleName()) {
				case "int":
					sql += "INTEGER";
					break;
				case "float":
				case "double":
					sql += "REAL";
					break;
				case "Date":
				case "String":
					sql += "TEXT";
			}
		}
		return sql + ");";
	}
	
	/**
	 * Creates the select for an SQL query.
	 * @param tableName The name of the table
	 * @param cols The columns to select
	 * @return String SQL Select query
	 */
	public static String createSelect(String tableName, String... cols) {
		String sql = "SELECT " + cols[0];
		for (int i = 1, nfields = cols.length; i < nfields; ++i) {
			sql += ", " + cols[i];
		}
		sql += " FROM " + tableName + ";";
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * Creates an update SQL syntax.
	 * @param tableName The name of the table
	 * @param fields An array using the fields
	 * @param values An array of the values
	 * @param colCond The column name for the conditional
	 * @param valueCond The value to compare with on the conditional
	 * @return String SQL update syntax
	 */
	public static String createUpdate(String tableName, String[] fields, String[] values, String colCond, String valueCond) {
		String query = "UPDATE " + tableName + " SET " + fields[0] + "=" + values[0];
		int nfields = fields.length;
		for (int i = 1; i < nfields; ++i) {
			query += ", " + fields[i] + "=" + values[i];
		}
		query += " WHERE " + colCond + "=" + valueCond;
		return query + ";";
	}

	/**
	 * Creates the SQL query to make an insert.
	 * @param tableName The name of the table
	 * @param fieldNames Fields to add to the update query
	 * @param values The values for the insertion
	 * @return String The SQL query for an insert
	 */
	public static String createInsert(String tableName, String[] fieldNames, String... values) {
		String query = "INSERT INTO " + tableName + " ('" + fieldNames[0] + "'";
		int nfields = fieldNames.length;
		for (int i = 1; i < nfields; ++i)
			query += ", '" + fieldNames[i] + "'";
		query += ") VALUES ('" + values[0] + "'";
		for (int i = 1; i < nfields; ++i)
			query += ", '" + values[i] + "'";
		return query + ");";
	}

	/**
	 * Creates the SQL query for an deletion.
	 * @param tableName The name of the table
	 * @param colCond The column name for the conditional
	 * @param valueCond The value to compare with on the conditional
	 * @return String The SQL query for an insert
	 */
	public static String createDelete(String tableName, String colCond, String valueCond) {
		String query = "DELETE FROM " + tableName + " WHERE " + colCond + "=" + valueCond;
		return query + ";";
	}
	
}

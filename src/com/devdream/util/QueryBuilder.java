package com.devdream.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.devdream.db.DBConnectionManager;
import com.devdream.db.vo.DBKey.Key;

/**
 * Class containing static methods for creating dynamic queries.
 *
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class QueryBuilder extends DBConnectionManager {
	/**
	 * The SQL syntax for the typical ID primary key.
	 */
	private static final String PK_AUTOINCREMENT_ATTR = " PRIMARY KEY AUTOINCREMENT";
	
	/**
	 * Creates the select for creating a table.
	 * @param vo The class of the model
	 * @return String The SQL query to create the table basing on the model
	 */
	public static String createTable(Class<?> vo) {
		Field[] fields = vo.getDeclaredFields();
		String extraSQL = "";
		String sql = "CREATE TABLE '" + getTableNameFromVO(vo.getSimpleName()) + "' (";
		for (int i = 0, nfields = fields.length; i < nfields; ++i) {
			Field field = fields[i];
			String fieldName = StringHelper.capitalizeFirst(field.getName());
			if (i != 0) {
				sql += ", ";
			}
			sql += "'" + fieldName + "' ";
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
			// If the attribute has annotation
			if (field.getAnnotations().length > 0) {
				Key keyField = null;
				Annotation annotation = field.getAnnotations()[0];
				Method[] attrs = annotation.annotationType().getDeclaredMethods();
				try {
					keyField = (Key) attrs[0].invoke(annotation, (Object[]) null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				 // If it has a primary or foreign attribute notation
				if (keyField == Key.PRIMARY) {
					sql += PK_AUTOINCREMENT_ATTR;
				}
				else if (keyField == Key.FOREIGN) {
					String referenceTable = "", referenceAttr = "";
					try {
						referenceTable = attrs[1].invoke(annotation, (Object[]) null).toString();
						referenceAttr = attrs[2].invoke(annotation, (Object[]) null).toString();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
					extraSQL += ", FOREIGN KEY ('" + StringHelper.capitalizeFirst(field.getName()) + "')";
					extraSQL += " REFERENCES '" + referenceTable + "'";
					extraSQL += " ('" + referenceAttr + "')";
				}
			}
		}
		sql += extraSQL;
		return sql + ");";
	}
	
	/**
	 * Creates the SQL select for selecting the passed columns.
	 * @param dao The Value Object Class
	 * @param cols The columns to select
	 * @return String SQL Select query
	 */
	public static String createSelect(Class<?> dao, String... cols) {
		String sql = "SELECT " + cols[0];
		for (int i = 1, nfields = cols.length; i < nfields; ++i) {
			sql += ", " + cols[i];
		}
		sql += " FROM " + getTableNameFromDAO(dao.getSimpleName());
		return sql;
	}
	
	/**
	 * Creates an update SQL syntax.
	 * @param dao The Value Object class
	 * @param fields An array using the fields
	 * @param values An array of the values
	 * @param colCond The column name for the conditional
	 * @param valueCond The value to compare with on the conditional
	 * @return String SQL update syntax
	 */
	public static String createUpdate(Class<?> dao, String[] fields, String[] values, String colCond, String valueCond) {
		String query = "UPDATE " + getTableNameFromDAO(dao.getSimpleName()) + " SET " + fields[0] + "=" + values[0];
		int nfields = fields.length;
		for (int i = 1; i < nfields; ++i) {
			query += ", " + fields[i] + "=" + values[i];
		}
		query += " WHERE " + colCond + "=" + valueCond;
		return query + ";";
	}

	/**
	 * Creates the SQL query to make an insert.
	 * @param dao The Data Access Object class
	 * @param fieldNames Fields to add to the update query
	 * @param values The values for the insertion
	 * @return String The SQL query for an insert
	 */
	public static String createInsert(Class<?> dao, String[] fieldNames, String[] values) {
		String query = "INSERT INTO '" + getTableNameFromDAO(dao.getSimpleName()) + "' ('" + fieldNames[0] + "'";
		int nfields = fieldNames.length;
		for (int i = 1; i < nfields; ++i) {
			query += ", '" + fieldNames[i] + "'";
		}
		query += ") VALUES ('" + values[0] + "'";
		for (int i = 1; i < nfields; ++i) {
			query += ", '" + values[i] + "'";
		}
		return query + ");";
	}

	/**
	 * Creates the SQL query for an deletion.
	 * @param vo The Value Object class name
	 * @param colCond The column name for the conditional
	 * @param valueCond The value to compare with on the conditional
	 * @return String The SQL query for an insert
	 */
	public static String createDelete(Class<?> vo, String colCond, String valueCond) {
		String query = "DELETE FROM " + getTableNameFromVO(vo.getSimpleName()) + " WHERE " + colCond + "=" + valueCond;
		return query + ";";
	}
	
	/**
	 * Formats the table name of a Value Object.
	 * @param vo The Value Object class
	 * @return The table name formatted
	 */
	public static String getTableNameFromVO(String vo) {
		return vo.replace("VO", "s");
	}

	/**
	 * Formats the table name of the Data Access Object.
	 * @param vo The Data Access Object class name
	 * @return The table name formatted
	 */
	public static String getTableNameFromDAO(String dao) {
		return dao.replace("DAO", "s");
	}
	
}

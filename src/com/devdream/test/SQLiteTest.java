package com.devdream.test;

import com.devdream.db.DBDML;
import com.devdream.util.QueryGenerator;

public class SQLiteTest {

	public static void main(String[] args) {
		DBDML db = new DBDML();
		db.testSQLLite(QueryGenerator.createSelect("datos", "id", "name"));
	}
	
}

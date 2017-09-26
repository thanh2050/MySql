package com.dimitrisli.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLJava {

	enum TestTableColumns{
		id,TEXT;
	}
	
	private final String jdbcDriverStr;
	private final String jdbcURL;
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	
	public MySQLJava(String jdbcDriverStr, String jdbcURL){
		this.jdbcDriverStr = jdbcDriverStr;
		this.jdbcURL = jdbcURL;
	}
	
	public void readData() throws Exception {
		try {
			Class.forName(jdbcDriverStr);
			connection = DriverManager.getConnection(jdbcURL);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from javaTestDB.test_table;");
			//getResultSet(resultSet);

			List<Entity> output = getResultSet(resultSet);

			for (Entity item : output) {
				System.out.println("id: "+item.id);
				System.out.println("text: "+item.text);
			}

			//System.out.println("id: "+id);
			//System.out.println("text: "+text);

			/*preparedStatement = connection.prepareStatement("insert into javaTestDB.test_table values (default,?)");
			preparedStatement.setString(1,"insert test from java");
			preparedStatement.executeUpdate();*/

		}finally{
			close();
		}
	}
	//TestTableColumns.id.toString() string value = "id";

	private List<Entity>  getResultSet(ResultSet resultSet) throws Exception {
		List<Entity> result = new ArrayList<Entity>() ;


		while(resultSet.next()){
			Entity item = new Entity();
			Integer id = resultSet.getInt(TestTableColumns.id.toString());
			String text = resultSet.getString(TestTableColumns.TEXT.toString());

			item.id = id;
			item.text = text;


			result.add(item);



		}

		return result;
	}
	
	private void close(){
		try {
			if(resultSet!=null) resultSet.close();
			if(statement!=null) statement.close();
			if(connection!=null) connection.close();
		} catch(Exception e){}
	}
}

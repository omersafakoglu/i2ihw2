package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Oracle {
    static String DB_URL = "jdbc:oracle:thin:@localhost:1521:TEST";
    static String DB_USERNAME = "i2iomer";
    static String DB_PASSWORD = "i2ifaruk";

    public static void main(String[] args) {
        int firstValue = 20000;
        int secondValue = 100000;

        Insert(firstValue);
        Select(firstValue);

        Insert(secondValue);
        Select(secondValue);
    }
    public static void  Select(int count){
        List<Integer> randomNumbers = new ArrayList<>();

        try {
            Properties props = new Properties();
            props.setProperty("user", DB_USERNAME);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("oracle.jdbc.convertNcharLiterals", "true");
            props.setProperty("oracle.jdbc.defaultNChar", "true");
            props.setProperty("charset", "AL32UTF8");

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            String Select = "SELECT sayi FROM Numbers ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Select);

            long startTime = System.nanoTime();
            while (resultSet.next()) {
                randomNumbers.add(resultSet.getInt("sayi"));
            }
            long endTime = System.nanoTime();
            long retrievalTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            System.out.println("Oracle "+ count +" sayı seçme süresi: " + retrievalTime + " ms");

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
    public static void Insert(int count) {

        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String InsertQuery = "INSERT INTO Numbers (sayi) VALUES (TRUNC(DBMS_RANDOM.VALUE(1, 100)))";
            PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery);

            long startTime = System.nanoTime();
            for(int i=0;i<count;i++){
                int RandomNumbers=(int) Math.random();
                preparedStatement.executeUpdate();
            }
            long endTime = System.nanoTime();
            long insertionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            System.out.println("Oracle "+ count+ " sayı eklemesi süresi: " + insertionTime + " ms");

            preparedStatement.close();
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

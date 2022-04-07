package Investments.Bolt.QuantConnectDeploy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class TickerChooser {
    // static Class variables
    // private Instance variable
    private HashSet<String> tickerS = new HashSet<>();

    // Initializer block
    {
    }
    // Constructors
    TickerChooser(File iFile) {
//		https://javadevblog.com/kak-schitat-csv-fajl-v-java.html !!
//		https://metanit.com/java/tutorial/6.9.php
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(iFile))) {
            String line = null;
            Scanner scanner = null;
            int index;
            bufferedReader.readLine(); // this will read the first line => skip first line
            while ((line = bufferedReader.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter("\t");
                index = 0;
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    if (index == 1)
                        this.tickerS.add(data);
                    index++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    TickerChooser() {
    	String mysqlUrlConnection = (new DB_Credentials()).getProperty("mySqlUrlConnection");
    	String sqlCommand = 
				"""
				SELECT DISTINCT
				    ticker
				FROM
				    GetYahooPrices.prices_qc_valid;
                """;
    	try (Connection conn = DriverManager.getConnection(mysqlUrlConnection)) { // Подключился к БД из DB_Credentials()
            // https://www.examclouds.com/ru/java/java-core-russian/try-with-resources
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            String ticker;
            while (resultSet.next()) {
            	// получение содержимого строк
                ticker = resultSet.getString("ticker");
                this.tickerS.add(ticker);
            }
        } catch (Exception ex) {
            System.out.println("\nConnection failed...");
            ex.printStackTrace();
        }
    }
    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    HashSet<String> getTickers() {
        return this.tickerS;
    }
}

package Investments.Bolt.QuantConnectDeploy;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

// http://it.kgsu.ru/JA_OS/ja_os125.html
// https://metanit.com/java/database/1.1.php
class ActualPrediction {
    // static Class variables
    // private Instance variable
    private HashMap<LocalDate, HashMap<String, Integer>> predictedTickers;

    // Initializer block
    {
        predictedTickers = new HashMap<>();
    }

    // Constructors
    ActualPrediction(NexTradingDay nexTradingDay) {
        String mysqlUrlConnection = (new DB_Credentials()).getProperty("mySqlUrlConnection");
        @SuppressWarnings("preview")
		String sqlCommand = 
				"""
				SELECT
                    dealDate, ticker, forecast
                FROM
                    GetYahooPrices.Prognosis
                WHERE
                    forecastTime = (SELECT
                            MAX(forecastTime)
                        FROM
                            GetYahooPrices.Prognosis);
                """;
        try (Connection conn = DriverManager.getConnection(mysqlUrlConnection)) { // Подключился к БД из DB_Credentials()
            // https://www.examclouds.com/ru/java/java-core-russian/try-with-resources
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            LocalDate dealDate = LocalDate.of(1914, 7, 28);;
            String ticker;
            HashMap<String, Integer> tempHM = new HashMap<String, Integer>();
            int forecast;
            while (resultSet.next()) {
                dealDate = resultSet.getDate("dealDate").toLocalDate();
                ticker = resultSet.getString("ticker");
                forecast = resultSet.getInt("forecast");
                tempHM.put(ticker, forecast);
                // получение содержимого строк
            }
            this.predictedTickers.put(dealDate, tempHM);
        } catch (Exception ex) {
            System.out.println("\nConnection failed...");
            ex.printStackTrace();
        }
    }
    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    public HashMap<LocalDate, HashMap<String, Integer>> getPredictedTickers() {
        return predictedTickers;
    }
}

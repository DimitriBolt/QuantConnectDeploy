package Investments.Bolt.QuantConnectDeploy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

class TickerChooser {
    // static Class variables
    // private Instance variable
    private HashSet<String> tickers = new HashSet<>();

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
                        this.tickers.add(data);
                    index++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    HashSet<String> getTickers() {
        return this.tickers;
    }

}

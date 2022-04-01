package Investments.Bolt.QuantConnectDeploy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

class DB_Credentials {

    // static Class variables
    // private Instance variable
    private String property;
    private Properties props;

    // Initializer block
    {
        props = new Properties();
    }

    DB_Credentials() {

        String userHomePath = System.getProperty("user.home");
        Path path = Paths.get(userHomePath, "Documents", "DB_Credentials.properties");
        try (InputStream in = Files.newInputStream(path)) {
            this.props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Constructors
    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    String getProperty(String key) {
        this.property = this.props.getProperty(key);
        return property;
    }
}

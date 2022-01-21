package Extrator.extratorLSG.api.readers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Data
@AllArgsConstructor
public class Inicializador {
    public static String defFolder;
    public static String hostdb;
    public static String namedb;
    public static String userdb;
    public static String passdb;

    public static Connection c;

    public static void init() throws ClassNotFoundException, SQLException {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);
            defFolder = prop.getProperty("defFolder");
            hostdb = prop.getProperty("hostdb");
            namedb = prop.getProperty("namedb");
            userdb = prop.getProperty("userdb");
            passdb = prop.getProperty("passdb");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * ConexÃ£o com o banco
         */
        Class.forName("org.postgresql.Driver");
        System.out.println(passdb);
        c = DriverManager
                .getConnection("jdbc:postgresql://" + hostdb + ":5432/" + namedb, userdb, passdb);
        System.out.println(c);

    }

    public static void finalizar() {
        try {
            c.close();
        }catch(Exception e) {

        }
    }
}

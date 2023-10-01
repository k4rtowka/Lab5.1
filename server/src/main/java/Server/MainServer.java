package Server;

import Models.CollectionManagerToSQL;

public class MainServer {
    public static void main(String[] args) {
        try {
            final String DB_URL = "jdbc:postgresql://localhost:5432/DBMarines";
            final String DB_USERNAME = "postgres";
            final String DB_PASSWORD = "postgres";
            final int idUser = -1;
            TCPServerMultiThread server = new TCPServerMultiThread(new CollectionManagerToSQL(DB_URL, DB_USERNAME, DB_PASSWORD, idUser), System.in, 8080);
            server.Start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

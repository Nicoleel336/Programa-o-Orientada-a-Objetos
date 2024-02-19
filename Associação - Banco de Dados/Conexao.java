package nicoleEllen.associacaoBD;

import java.sql.Connection;

public class Conexao {
	//1º: registrar o driver
	//2º: criar a conexão
	private static final String URL = "jdbc:mysql://localhost:3306/associacao";
	private static final String user = "root";
	private static final String password = "root123";
	
	private static Connection con;

    public static Connection getConexao() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = java.sql.DriverManager.getConnection(URL, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return con;
    }

}

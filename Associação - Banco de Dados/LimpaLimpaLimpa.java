package nicoleEllen.associacaoBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LimpaLimpaLimpa {

	public static final void limpar(){
	    try{
	        Connection con = Conexao.getConexao();
	        Statement st = con.createStatement();
	        String comando = "DELETE FROM associacao where numero > 0";
	        st.execute(comando);
	        String comando2 = "DELETE FROM associado where numero > 0";
	        st.execute(comando2);    
	        String comando3 = "DELETE FROM taxa where id_associacao > 0";
	        st.execute(comando3);
	        String comando4 = "DELETE FROM pagamento where id_associado > 0";
	        st.execute(comando4);
	        st.close();
	    }catch(SQLException e){
	
	    }
	    
	}
	
	public static void main(String[] args) {
	    limpar();
	}

}

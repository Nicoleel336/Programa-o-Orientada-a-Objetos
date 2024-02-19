package nicoleEllen.associacaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOAssociado {
    private Connection conexao; // conexão

    // construtor
    public DAOAssociado() {
        this.conexao = Conexao.getConexao();
    }

    // método para inserir um associado
    public void inserir(Associado associado, int numAssociacao) {
        String sql = "INSERT INTO associado (numero, nome, telefone, nascimento, dataAssociacao, remido, dataRemissao, id_associacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, associado.getNumero());
            ps.setString(2, associado.getNome());
            ps.setString(3, associado.getTelefone());
            ps.setLong(4, associado.getNascimento());
            ps.setLong(5, associado.getDataAssociacao());
            ps.setInt(8, numAssociacao);
            if(associado instanceof AssociadoRemido){
                ps.setBoolean(6, true);
                ps.setLong(7, ((AssociadoRemido) associado).getDataRemissao());
            }
            else {
                ps.setBoolean(6, false);
                ps.setLong(7, 0);
            }
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // método para remover um associado pelo número
    public void remover(int numero) {
        String sql = "DELETE FROM associado WHERE numero = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, numero);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // método para remover todos os associados
    public void removerTodos() {
        String sql = "DELETE FROM associado";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // método para atualizar um associado
    // Não sei se é necessário
    public void atualizar(Associado associado, int numAssociacao) {
        String sql = "UPDATE associado SET nome = ?, telefone = ?, nascimento = ?, dataAssociacao = ?, remido = ?, dataRemissao = ?, id_associacao = ? WHERE numero = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, associado.getNome());
            ps.setString(2, associado.getTelefone());
            ps.setLong(3, associado.getNascimento());
            ps.setLong(4, associado.getDataAssociacao());
            ps.setInt(8, numAssociacao);
            if(associado instanceof AssociadoRemido){
                ps.setBoolean(5, true);
                ps.setLong(6, ((AssociadoRemido) associado).getDataRemissao());
            }
            else {
                ps.setBoolean(5, false);
                ps.setLong(6, 0);
            }
            ps.setInt(7, numAssociacao);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

 // método para buscar um associado pelo número
    public Associado pesquisarPorNumero(int numero) {
        String sql = "SELECT * FROM associado WHERE numero = ?";
        PreparedStatement ps = null;
        Associado associado = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if(rs.getBoolean("remido") == true) {
                    associado = new AssociadoRemido(rs.getInt("numero"), rs.getString("nome"), rs.getString("telefone"), rs.getLong("dataAssociacao"), rs.getLong("nascimento"), rs.getLong("dataRemissao"));
                } else {
                    associado = new Associado(rs.getInt("numero"), rs.getString("nome"), rs.getString("telefone"), rs.getLong("dataAssociacao"), rs.getLong("nascimento"));
                }
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return associado;
    }

    public ArrayList<Associado> getAssociadosDaAssociacao(int numeroAssociacao) {
        String sql = "SELECT * FROM associado WHERE id_associacao = ?";
        PreparedStatement ps = null;
        ArrayList<Associado> associados = new ArrayList<Associado>();

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, numeroAssociacao);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getBoolean("remido") == true) {
                	System.out.println("lapda");
                    associados.add(new AssociadoRemido(rs.getInt("numero"), rs.getString("nome"), rs.getString("telefone"), rs.getLong("dataAssociacao"), rs.getLong("nascimento"), rs.getLong("dataRemissao")));
                } else {
                    associados.add(new Associado(rs.getInt("numero"), rs.getString("nome"), rs.getString("telefone"), rs.getLong("dataAssociacao"), rs.getLong("nascimento")));
                }
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        return associados;
    }

    public ArrayList<Pagamento> getPagamentosDeAssociado(int numAssociado){

        ArrayList<Pagamento> pagamentos = new DAOPagamentos().getPagamentos(numAssociado);

        return pagamentos;
    }

}
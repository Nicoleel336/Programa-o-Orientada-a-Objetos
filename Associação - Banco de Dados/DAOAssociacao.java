package nicoleEllen.associacaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

import com.mysql.cj.xdevapi.Statement;

public class DAOAssociacao {
    private Connection conexao;

    // construtor
    public DAOAssociacao() {
        this.conexao = Conexao.getConexao();
    }

    // método para inserir uma nova associação
    public void inserir(Associacao associacao) {
        String sql = "INSERT INTO associacao (numero, nome) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, associacao.getNum());
            ps.setString(2, associacao.getNome());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // método para excluir uma associação por número
    public void removerPorNumero(int numero) {
        String sql = "DELETE FROM associacao WHERE numero = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, numero);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // método para excluir todas as associações
    public void removerTodos() {
        String sql = "DELETE FROM associacao";
        PreparedStatement ps = null;
 
        try {
            ps = conexao.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // método para pesquisar associação por número
    public Associacao pesquisarPorNumero(int numero) {
        String sql = "SELECT * FROM associacao WHERE numero = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Associacao associacao = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, numero);
            rs = ps.executeQuery();
            if (rs.next()) {
                associacao = new Associacao(rs.getInt("numero"), rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return associacao;
    }

    // método para pesquisar por nome
    public Associacao pesquisarPorNome(String nome) {
        String sql = "SELECT * FROM associacao WHERE nome = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Associacao associacao = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (rs.next()) {
                associacao = new Associacao(rs.getInt("numero"), rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return associacao;
    }

    // método para pesquisar todas 
    public ArrayList<Associacao> pesquisarTodas() {
        String sql = "SELECT * FROM associacao";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Associacao> associacoes = new ArrayList<Associacao>();

        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Associacao associacao = new Associacao(rs.getInt("numero"), rs.getString("nome"));
                associacoes.add(associacao);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return associacoes;
    }

    // método para atualizar uma associação
    public void atualizar(Associacao associacao) {
        String sql = "UPDATE associacao SET nome = ? WHERE numero = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, associacao.getNome());
            ps.setInt(2, associacao.getNum());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Associado> getAssociadosDaAssociacao(int numAssociacao){
        DAOAssociado dao = new DAOAssociado();
        ArrayList<Associado> associados = dao.getAssociadosDaAssociacao(numAssociacao);
        return associados;
    }

    public ArrayList<Taxa> getTaxasDaAssociacao(int numAssociacao){
        DAOTaxa dao = new DAOTaxa();
        ArrayList<Taxa> taxas = dao.getTaxasDaAssociacao(numAssociacao);
        return taxas;
    }
    
}

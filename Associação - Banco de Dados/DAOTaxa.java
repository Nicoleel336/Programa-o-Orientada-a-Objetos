package nicoleEllen.associacaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOTaxa {
	private Connection conexao; //conexão
	
	//construtor
	public DAOTaxa() {
		this.conexao = Conexao.getConexao();
	}
	
	// método para inserir uma taxa
	// precisa colocar o número da associação correspondente?
	public void inserir(Taxa taxa, int numAssociacao) {
		String sql = "INSERT INTO taxa (nome, valorAno, vigencia, parcelas, administrativa, id_associacao) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(sql);
			ps.setString(1, taxa.getNome());
			ps.setDouble(2, taxa.getValorAno());
			ps.setInt(3, taxa.getVigencia());
			ps.setInt(4, taxa.getParcelas());
			ps.setBoolean(5, taxa.isAdministrativa());
			ps.setInt(6, numAssociacao);
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
	
	// método para excluir uma taxa
	public void remover(Taxa taxa) {
		String sql = "DELETE FROM taxa WHERE nome = ?";
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(sql);
			ps.setString(1, taxa.getNome());
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

	// método para removar todas as taxas
	public void removerTodas() {
		String sql = "DELETE FROM taxa";
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(sql);
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

	// método para atualizar uma taxa
	// precisa colocar o número da associação correspondente?
	public void atualizar(Taxa taxa, int numAssociacao) {
		String sql = "UPDATE taxa SET valorAno = ?, vigencia = ?, parcelas = ?, administrativa = ?, id_associacao = ? WHERE nome = ?";
		PreparedStatement ps = null;
	
		try {
			ps = conexao.prepareStatement(sql);
			ps.setDouble(1, taxa.getValorAno());
			ps.setInt(2, taxa.getVigencia());
			ps.setInt(3, taxa.getParcelas());
			ps.setBoolean(4, taxa.isAdministrativa());
			ps.setInt(5, numAssociacao);
			ps.setString(6, taxa.getNome());
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

	// método para buscar uma taxa por nome
	public Taxa pesquisarPorNome(String nome) {
		String sql = "SELECT * FROM taxa WHERE nome = ?";
		PreparedStatement ps = null;
		Taxa taxa = null;

		try {
			ps = conexao.prepareStatement(sql);
			ps.setString(1, nome);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				taxa = new Taxa(rs.getString("nome"), rs.getInt("vigencia"), rs.getDouble("valorAno"), rs.getInt("parcelas"), rs.getBoolean("administrativa"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return taxa; // retorna a taxa encontrada
	}

	// método para buscar todas as taxas presentes no banco de dados
	public ArrayList<Taxa> pesquisarTodas() {
		String sql = "SELECT * FROM taxa"; //comando de consulta
		PreparedStatement ps = null;
		ArrayList<Taxa> taxas = new ArrayList<Taxa>();

		try {
			ps = conexao.prepareStatement(sql); // conecta e prepara a consulta
			ResultSet rs = ps.executeQuery(); // consulta e armazena o resultado em rs
			while (rs.next()) {
				Taxa taxa = new Taxa(rs.getString("nome"), rs.getInt("vigencia"), rs.getDouble("valorAno"), rs.getInt("parcelas"), rs.getBoolean("administrativa"));
				taxas.add(taxa); // vai adicionando as taxas no arraylist
			}
		} catch(Exception e) { 
			e.printStackTrace();
		} finally {
			try {
				ps.close(); // fecha o preparedStatement para evitar vazamento de memória
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return taxas; // retorna um arraylist com as taxas encontradas
	}

	public ArrayList<Taxa> getTaxasDaAssociacao(int numAssociacao) {
		String sql = "SELECT * FROM taxa WHERE id_associacao = ?";
		PreparedStatement ps = null;
		ArrayList<Taxa> taxas = new ArrayList<Taxa>();

		try {
			ps = conexao.prepareStatement(sql);
			ps.setInt(1, numAssociacao);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Taxa taxa = new Taxa(rs.getString("nome"), rs.getInt("vigencia"), rs.getDouble("valorAno"), rs.getInt("parcelas"), rs.getBoolean("administrativa"));
				taxas.add(taxa);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return taxas;
	}

	// pesquisar por nome e vigencia
	public Taxa pesquisarPorNomeVigencia(String nome, int vigencia) {
		String sql = "SELECT * FROM taxa WHERE nome = \'"+ nome +"\' AND vigencia = " + vigencia;
		PreparedStatement ps = null;
		Taxa taxa = null;

		try {
			ps = conexao.prepareStatement(sql);
//			ps.setString(1, nome);
//			ps.setInt(2, vigencia);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				taxa = new Taxa(nome, vigencia, rs.getDouble("valorAno"), rs.getInt("parcelas"), rs.getBoolean("administrativa"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return taxa;
	}
	
}

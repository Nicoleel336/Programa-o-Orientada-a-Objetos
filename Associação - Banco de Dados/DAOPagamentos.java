package nicoleEllen.associacaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPagamentos {
	private Connection conexao; // conexão

	// construtor
	public DAOPagamentos() {
		this.conexao = Conexao.getConexao();
	}

	// método para inserir um pagamento
	// CHECAR DATE/LONG
	public void inserir(Pagamento pagamento) {
		String sql = "INSERT INTO pagamento (nomeTaxa, id_associado, valorPago, data, taxa_vigencia) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(sql);
			ps.setString(1, pagamento.getTaxa().getNome());
			ps.setInt(2, pagamento.getNumeroAssociado());
			ps.setDouble(3, pagamento.getPago());
			ps.setLong(4, pagamento.getData());
			ps.setInt(5, pagamento.getVigencia());
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

	public ArrayList<Pagamento> pagamentosEntre(Taxa taxa, long dataInit, long dataFim, Associado associado) {
		String sql = "select * from pagamento where associado = \'" + associado.getNumero() + "\' and taxa =\'"
				+ taxa.getNome() + "\' and data between " + dataInit + " and " + dataFim;
		System.out.println(sql);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Pagamento> pagamentos = new ArrayList<Pagamento>();

		try {
			ps = conexao.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String nomeTaxa = rs.getString("nomeTaxa");
				double valorPago = rs.getDouble("valorPago");
				long data = rs.getLong("data");
				Pagamento pagamento = new Pagamento(taxa, valorPago, data, taxa.getVigencia());
				pagamento.setNumeroAssociado(associado.getNumero());
				pagamento.setPago(valorPago);
				pagamentos.add(pagamento);
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

		return pagamentos;
	}

	// método para excluir um pagamento por número do associado
	// PRECISA?
	public void removerPorAssociado(Pagamento pagamento) {
		String sql = "DELETE FROM pagamento WHERE id_associado = ?";
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(sql);
			ps.setInt(1, pagamento.getNumeroAssociado());
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

	// método para excluir todos os pagamentos
	public void removerTodos() {
		String sql = "DELETE FROM pagamento";
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
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

	// método para mostrar todos os pagamentos de um associado
	public ArrayList<Pagamento> getPagamentos(int numAssociado) {
		String sql = "SELECT * FROM pagamento WHERE id_associado = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Pagamento> pagamentos = new ArrayList<Pagamento>();

		try {
			ps = conexao.prepareStatement(sql);
			ps.setInt(1, numAssociado);
			rs = ps.executeQuery();
			while (rs.next()) {
				String nomeTaxa = rs.getString("nomeTaxa");
				double valorPago = rs.getDouble("valorPago");
				long data = rs.getLong("data");
				Taxa taxa = new DAOTaxa().pesquisarPorNomeVigencia(nomeTaxa, rs.getInt("taxa_vigencia"));
				Pagamento pagamento = new Pagamento(taxa, valorPago, data, taxa.getVigencia());
				pagamento.setNumeroAssociado(numAssociado);
				pagamento.setPago(valorPago);
				pagamentos.add(pagamento);
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

		return pagamentos;
	}

	// método pra atualizar pagamento
	// não sei se precisa
	public void atualizar(Pagamento pagamento) {
		String sql = "UPDATE pagamento SET valorPago = ?, data = ? WHERE nomeTaxa = ? AND id_associado = ?, taxa_vigencia = ?";
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(sql);
			ps.setDouble(1, pagamento.getPago());
			ps.setLong(2, pagamento.getData());
			ps.setString(3, pagamento.getTaxa().getNome());
			ps.setInt(4, pagamento.getNumeroAssociado());
			ps.setInt(5, pagamento.getVigencia());
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

}
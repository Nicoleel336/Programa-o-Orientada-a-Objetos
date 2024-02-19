package nicoleEllen.estoqueComProdutoPerecivelExcecoes;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class EstoqueComExcecoesTestResumido {

    @Test
    public void testIncluirProdutoAindaNaoIncluido() throws ProdutoJaCadastrado, DadosInvalidos, ProdutoInexistente {
        // Criação de objetos necessários para o teste
        Fornecedor fornecedor = new Fornecedor(123456789, "Fornecedor A");
        Produto produto = new Produto(1, "Produto A", 10, 1.5);

        // Criação da instância do Estoque
        Estoque estoque = new Estoque();

        // Teste de inclusão de um produto
        estoque.incluir(produto);

        // Verificação do resultado esperado
        Produto produtoEncontrado = null;
        produtoEncontrado = estoque.pesquisar(1);
        assertEquals(produto.getCodigo(), produtoEncontrado.getCodigo());
    }
    
    @Test
    public void incluirProdutoComMinNegativo() throws ProdutoJaCadastrado, ProdutoInexistente {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Unilever");
        Fornecedor forn2 = new Fornecedor(33, "Nestle");
        Produto prod1 = new Produto(12, "Condicionador", -1, 2);
        Produto prod2 = new ProdutoPerecivel(14, "Shampoo", -1, 2);

        try {
            estoque.incluir(prod1);
            fail("Deveria ter dado erro de dados invalidos");
        } catch (DadosInvalidos e) {
            // TODO: handle exception
        }
        try {
            estoque.incluir(prod2);
            fail("Deveria ter dado erro de dados invalidos");
        } catch (DadosInvalidos e) {
            // TODO: handle exception
        }
    }
    

    @Test
    public void testIncluirProdutoExistente() throws DadosInvalidos, ProdutoInexistente, ProdutoJaCadastrado {
        // Criação de objetos necessários para o teste
        Fornecedor fornecedor = new Fornecedor(123456789, "Fornecedor A");
        Produto produto = new Produto(1, "Produto A", 10, 1.5);
        Produto produto2 = new Produto(1, "Produto B", 10, 1.5);

        // Criação da instância do Estoque
        Estoque estoque = new Estoque();
        estoque.incluir(produto);

        // Teste de inclusão do mesmo produto novamente
        try {
            estoque.incluir(produto2);
            fail("Deveria ter dado erro de prod já cadastrado");	
        } catch (ProdutoJaCadastrado e) {
        }
    }
  
    
    @Test
    public void testIncluirProdutosComNomeNulo () throws DadosInvalidos, ProdutoInexistente, ProdutoJaCadastrado {
        // Criação de objetos necessários para o teste
        Fornecedor fornecedor = new Fornecedor(123456789, "Fornecedor A");
        Produto produto = new Produto(1, null, 10, 1.5);

        // Criação da instância do Estoque
        Estoque estoque = new Estoque();

        // Teste de inclusão do mesmo produto novamente
        try {
            estoque.incluir(produto);
            fail("Deveria ter dado erro de dados invalidos");	
        } catch (DadosInvalidos e) {
        }
    }
    
    @Test
    public void testIncluirProdutosComNomeVazio () throws DadosInvalidos, ProdutoInexistente, ProdutoJaCadastrado {
        // Criação de objetos necessários para o teste
        Fornecedor fornecedor = new Fornecedor(123456789, "Fornecedor A");
        Produto produto = new Produto(1, "", 10, 1.5);
        Produto produto2 = new Produto(2, "   ", 10, 1.5);

        // Criação da instância do Estoque
        Estoque estoque = new Estoque();

        // Teste de inclusão do mesmo produto novamente
        try {
            estoque.incluir(produto);
            fail("Deveria ter dado erro de dados invalidos");	
        } catch (DadosInvalidos e) {
        }
        // Teste de inclusão do mesmo produto novamente
        try {
            estoque.incluir(produto2);
            fail("Deveria ter dado erro de dados invalidos");	
        } catch (DadosInvalidos e) {
        }
    }
        
    @Test
    public void compraProdutoNaoPerecivel()
            throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoJaCadastrado, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Unilever");
        Fornecedor forn2 = new Fornecedor(19, "Gilette");
        Produto prod1 = new Produto(12, "Shampoo", 5, 2);
        Produto prod2 = new Produto(13, "Aparelho de Barbear", 5, 1);

        estoque.incluir(prod1);
        estoque.incluir(prod2);
        estoque.comprar(prod1.getCodigo(), 10, 5, null);
        estoque.comprar(prod2.getCodigo(), 5, 2.5, null);
        
        int quant1 = estoque.quantidade(12);
        int quant2 = estoque.quantidade(13);
        assertEquals(10, quant1);
        assertEquals(5, quant2);

    }
    
    @Test
    public void testComprarProdutoNaoPerecivelComData() throws ProdutoJaCadastrado, DadosInvalidos, ProdutoInexistente, ProdutoVencido {
        // Criação de objetos necessários para o teste
        Fornecedor fornecedor = new Fornecedor(123456789, "Fornecedor A");
        Produto produto = new Produto(2, "Produto B", 5, 2.0);

        // Criação da instância do Estoque
        Estoque estoque = new Estoque();

        estoque.incluir(produto);

        try {
            estoque.comprar(2, 5, 2.0, new Date());
            fail("Deveria ter dado erro de compra de prod nao perecivel com data de validade");	
        } catch (ProdutoNaoPerecivel e) {
        }
    }
    
    @Test
    public void compraProdutoPerecivel()
            throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoJaCadastrado, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(33, "Nestle");
        Fornecedor forn2 = new Fornecedor(24, "Ambev");
        Produto prod1 = new ProdutoPerecivel(14, "Sorvete", 5, 2);
        Produto prod2 = new ProdutoPerecivel(15, "Cerveja", 5, 1);
        Date data = new Date();
        data.setTime(data.getTime() + 120000);

        estoque.incluir(prod1);
        estoque.incluir(prod2);
        estoque.comprar(prod1.getCodigo(), 24, 8, data);
        estoque.comprar(prod1.getCodigo(), 10, 5, data);
        estoque.comprar(prod2.getCodigo(), 11, 4.23, data);
        estoque.comprar(prod2.getCodigo(), 5, 2.5, data);
        
        int quant1 = estoque.quantidade(14);
        int quant2 = estoque.quantidade(15);
        assertEquals(34, quant1);
        assertEquals(16, quant2);
    }

    @Test
    public void vendaProdutoPerecivelVariosLotes()
            throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoJaCadastrado, ProdutoVencido {
        Estoque estoque = new Estoque();
        Produto prod1 = new ProdutoPerecivel(14, "Sorvete", 5, 2);
        Produto prod2 = new ProdutoPerecivel(15, "Cerveja", 5, 1);
        Date data1 = new Date();
        data1.setTime(data1.getTime() + 120000);
        Date data2 = new Date();
        data2.setTime(data2.getTime() + 120000);

        estoque.incluir(prod1);
        estoque.incluir(prod2);
        estoque.comprar(prod1.getCodigo(), 10, 8, data1);
        estoque.comprar(prod1.getCodigo(), 10, 5, data2);
        estoque.comprar(prod2.getCodigo(), 5, 4.23, data1);
        estoque.comprar(prod2.getCodigo(), 5, 2.5, data2);
        
        int quant1 = estoque.quantidade(14);
        int quant2 = estoque.quantidade(15);
        
        assertEquals(20, quant1);
        assertEquals(10, quant2);
        
        data2.setTime(data2.getTime() - 140000);
        
        try {
            estoque.vender(14, 11);
            fail("Nao deveria ter vendido quant maior que a valida");
        } catch (ProdutoVencido e) {
            assertEquals(20, estoque.quantidade(14));
        }
    }

    @Test
    public void vendaProdutoPerecivelComValidadeMenorQueDataAtual()
            throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoJaCadastrado, ProdutoVencido, InterruptedException {
        Estoque estoque = new Estoque();
        Produto prod1 = new ProdutoPerecivel(14, "Sorvete", 5, 2);
        Date dataHj = new Date();
        dataHj.setTime(dataHj.getTime() + 120000);

        estoque.incluir(prod1);
        estoque.comprar(prod1.getCodigo(), 24, 8, dataHj);

        Thread.sleep(100);
        dataHj.setTime(dataHj.getTime() - 140000);
        
        try {
            estoque.vender(14, 20);
            fail("Deveria ter dado excecao de prod vencido");
        } catch (ProdutoVencido e) {
        }
    }
    
    
    @Test
    public void vendeItensQuantidadeNegativa() throws ProdutoJaCadastrado, DadosInvalidos, ProdutoInexistente, ProdutoNaoPerecivel, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new Produto(12, "Sorvete", 5, 1);
        estoque.incluir(prod1);
        estoque.comprar(12, 20, 5, null);
        // Verifica se o valor total da venda esta correto
        try {
            estoque.vender(12, -1);
            fail("Não deveria ter vendido itens quant negativa");
        } catch (DadosInvalidos e) {
            // TODO: handle exceptio
        }
    }
    
    @Test
    public void vendeItens() throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoJaCadastrado, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new Produto(12, "Sorvete", 5, 1);
        Produto prod2 = new Produto(13, "Sorvete", 5, 0.1);
        estoque.incluir(prod1);
        estoque.comprar(12, 20, 5, null);
        estoque.incluir(prod2);
        estoque.comprar(13, 20, 5, null);
        // Verifica se o valor total da venda esta correto
        assertEquals(5.5, estoque.vender(13, 1), 0.001);
    }
    
    @Test
    public void vendeItensQuantidadeMaiorQueEstoque() throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel, ProdutoJaCadastrado, ProdutoVencido {
        Estoque estoque = new Estoque();
        Fornecedor forn1 = new Fornecedor(48, "Nestle");
        Produto prod1 = new Produto(12, "Sorvete", 5, 1);
        estoque.incluir(prod1);
        estoque.comprar(12, 20, 5, null);
        // Verifica se o valor total da venda esta correto
        try {
            estoque.vender(12, 30);
            fail("Nao deveria ter vendido quant maior que estoque");
        } catch (DadosInvalidos e) {
            // TODO: handle exception
        }
        assertEquals(20, estoque.quantidade(12));
    }
    
        
    @Test
    public void incluirProdutoNull() throws ProdutoJaCadastrado, ProdutoInexistente {
        Estoque estoque = new Estoque();
        
        try {
            estoque.incluir(null);
            fail("Não deveria comprar item null");
        } catch (DadosInvalidos e) {
        	// TODO: handle exception
        }
    }
    
}

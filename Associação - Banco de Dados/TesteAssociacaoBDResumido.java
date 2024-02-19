package nicoleEllen.associacaoBD;
/*
 * Meses para o Calendario Gregoriano são 0-based:
 * 0 - Janeiro
 * 1 - Fevereiro
 * .....
 * 11 - Dezembro
 */

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class TesteAssociacaoBDResumido {

    @Test
    public void testarCadastroDeAssociacao() throws AssociacaoJaExistente, ValorInvalido {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(100, "");
        a1.setNome("Novo Olhar");
        controle.adicionar(a1);

        try {
            controle.adicionar(a1);
            fail("Deveria ter dado erro! Cadastra associacao ja existente.");
        } catch (AssociacaoJaExistente e) {
            // Ok, retornou erro esperado.
        }
    }

    @Test
    public void testarCadastroDeAssociacaoComNomeVazio() throws AssociacaoJaExistente, ValorInvalido {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(206, "");

        //Tentativa de cadastrar associacao sem nome
        try {
            controle.adicionar(a1);
            fail("Deveria ter dado erro! Associacao sem nome");
        } catch (ValorInvalido e) {
            // Ok, retornou erro esperado.
        }
    }


    @Test
    public void testarCadastroDeAssociado() throws AssociacaoJaExistente, ValorInvalido, AssociacaoNaoExistente, AssociadoJaExistente {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(21, "Perto da Chegada");
        controle.adicionar(a1);
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(1975, 0, 25);
        long nasc = gc.getTimeInMillis();
        Date hoje = new Date();
        Associado associado1 = new Associado(1, "Priscila", "4002-8922", hoje.getTime(), nasc);
        controle.adicionar(21, associado1);

        try {
            controle.adicionar(21, associado1);
            fail("Era pra ter dado erro! Cadastro de mesmo associado");
        } catch (AssociadoJaExistente e) {
            // Ok, retornou erro esperado.
        }
    }

    @Test
    public void testarCadastroDeAssociadoComNomeNulo() throws AssociacaoJaExistente, ValorInvalido, AssociacaoNaoExistente, AssociadoJaExistente {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(13, "Dia Um");
        controle.adicionar(a1);
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(1975, 0, 25);
        long nasc = gc.getTimeInMillis();
        Date hoje = new Date();
        Associado associado1 = new Associado(1, "    ", "4002-8922", hoje.getTime(), nasc);

        try {
            controle.adicionar(13, associado1);
            fail("Era pra ter dado erro! Cadastro de associado com nome em branco");
        } catch (ValorInvalido e) {
            // Ok, era pra ter dado erro!
        }
    }


    @Test
    public void testarCadastroDeAssociadoComDataAssociacaoMenorQueZero() throws AssociacaoJaExistente, ValorInvalido, AssociacaoNaoExistente, AssociadoJaExistente {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(31, "Pluma Azul");
        controle.adicionar(a1);
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(2000, 2, 18);
        long nasc = gc.getTimeInMillis();
        Associado associado1 = new Associado(1, "Laura", "3232-3232", -10, nasc);

        try {
            controle.adicionar(31, associado1);
            fail("Era pra ter dado erro! Cadastro de associado com data de associacao igual -10");
        } catch (ValorInvalido e) {
            // Ok, retornou erro esperado.
        }
    }


    @Test
    public void testarCadastroDeTaxa() throws AssociacaoNaoExistente, AssociacaoJaExistente, ValorInvalido, TaxaJaExistente {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(1, "O Sol Raiou");
        controle.adicionar(a1);
        Taxa taxa1 = new Taxa("Manutencao", 2024, 500, 12, true);
        controle.adicionar(1, taxa1);
        try {
            controle.adicionar(1, taxa1);
            fail("Era pra ter dado erro! Cadastro de mesma taxa.");
        } catch (TaxaJaExistente e) {
            // Ok, retornou erro esperado.
        }
    }

    @Test
    public void cadastroDeTaxaSemAssociacao() throws Exception {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Taxa taxa1 = new Taxa("Manutencao", 2024, 500, 12, true);

        try {
            controle.adicionar(1, taxa1);
            fail("Era pra ter dado erro! Cadastro de taxa sem associacao existente.");
        } catch (AssociacaoNaoExistente e) {
        	// Ok, retornou erro esperado.
        }

    }


    @Test
    public void testarPagamento() throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente, AssociacaoJaExistente, ValorInvalido, AssociadoJaExistente, TaxaJaExistente, AssociadoJaRemido {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(15, "Girassol");
        controle.adicionar(a1);

        GregorianCalendar gc = new GregorianCalendar();
        gc.set(1999, 9, 19);
        long nasc = gc.getTimeInMillis();
        gc.set(2024, 01, 01);
        long data1 = gc.getTimeInMillis();
        Associado associado1 = new Associado(1, "Iudi", "4002-8922", data1, nasc);
        controle.adicionar(15, associado1);

        //Cadastro de associado remido ap처s 3 meses (01/04/24)
        gc.set(2024, 03, 01);
        long remissao = gc.getTimeInMillis();
        Associado associado2 = new AssociadoRemido(2, "Zeruma", "0101-0101", data1, nasc, remissao);
        controle.adicionar(15, associado2);

        Taxa taxa1 = new Taxa("Manutencao", 2024, 600, 12, true);
        controle.adicionar(15, taxa1);

        Taxa taxa2 = new Taxa("Reforma", 2024, 1200, 12, false);
        controle.adicionar(15, taxa2);

        Date hoje = new Date();

        try {
            controle.registrarPagamento(15, "Manutencao", 2024, 1, hoje.getTime(), 49);
            fail("Nao deveria deixar pagar esse valor, pois o valor minimo da parcela eh 50.");
        } catch (ValorInvalido e) {
            // Ok. Barrou pagamento abaixo da parcela!
        }
        controle.registrarPagamento(15, "Manutencao", 2024, 1, hoje.getTime(), 60);
        controle.registrarPagamento(15, "Reforma", 2024, 1, hoje.getTime(), 100);

        double valor = controle.somarPagamentoDeAssociado(15, 1, "Manutencao", 2024, hoje.getTime(), hoje.getTime());
        assertEquals(60, valor, 0.01);
        double valor2 = controle.somarPagamentoDeAssociado(15, 1, "Reforma", 2024, hoje.getTime(), hoje.getTime());
        assertEquals(100, valor2, 0.01);

        try {
            controle.registrarPagamento(15, "Manutencao", 2024, 2, hoje.getTime(), 60);
            fail("O associado eh remido e n찾o deveria pagar taxas administrativas");
        } catch (AssociadoJaRemido e) {
            // Ok. Barrou pagamento de taxa administrativa!
        }

        controle.registrarPagamento(15, "Reforma", 2024, 2, hoje.getTime(), 150);
        valor = controle.somarPagamentoDeAssociado(15, 2, "Manutencao", 2024, hoje.getTime(), hoje.getTime());
        assertEquals(0, valor, 0.01);
        valor2 = controle.somarPagamentoDeAssociado(15, 2, "Reforma", 2024, hoje.getTime(), hoje.getTime());
        assertEquals(150, valor2, 0.01);
        controle.registrarPagamento(15, "Reforma", 2024, 2, hoje.getTime(), 1000);
        valor2 = controle.somarPagamentoDeAssociado(15, 2, "Reforma", 2024, hoje.getTime(), hoje.getTime());
        assertEquals(1150, valor2, 0.01);
        controle.registrarPagamento(15, "Reforma", 2024, 2, hoje.getTime(), 50);
        valor2 = controle.somarPagamentoDeAssociado(15, 2, "Reforma", 2024, hoje.getTime(), hoje.getTime());
        assertEquals(1200, valor2, 0.01);
    }


    @Test
    public void testeCalcularTotalDeTaxa() throws Exception {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(10, "Passarinho");
        controle.adicionar(a1);
        Taxa taxa1 = new Taxa("A", 2024, 600, 12, true);
        Taxa taxa2 = new Taxa("B", 2024, 400, 12, true);
        Taxa taxa3 = new Taxa("C", 2024, 200, 6, true);
        
        controle.adicionar(10, taxa1);
        controle.adicionar(10, taxa2);
        controle.adicionar(10, taxa3);
        assertEquals(1200, controle.calcularTotalDeTaxas(10, 2024), 0.001);
    }


    @Test
    public void testePagarOQueFaltaMenorQueOValorDaParcela() throws Exception {
        InterfaceAssociacao controle = new MinhaAssociacao();
        Associacao a1 = new Associacao(1, "Jaguar");
        controle.adicionar(a1);

        GregorianCalendar gc = new GregorianCalendar();
        gc.set(2004, 1, 15);
        long nasc = gc.getTimeInMillis();
        gc.set(2024, 01, 01);
        long data1 = gc.getTimeInMillis();
        Associado associado1 = new Associado(1, "Bete", "1331-1331", data1, nasc);
        controle.adicionar(1, associado1);

        // Cadastro de associado remido após 3 meses (01/04/24)
        gc.set(2024, 03, 01);
        long remissao = gc.getTimeInMillis();
        Associado associado2 = new AssociadoRemido(2, "Jaime", "9999-9999", data1, nasc, remissao);
        controle.adicionar(1, associado2);

        Taxa taxa1 = new Taxa("A", 2024, 600, 12, true);
        controle.adicionar(1, taxa1);

        Taxa taxa2 = new Taxa("B", 2024, 1200, 12, false);
        controle.adicionar(1, taxa2);
        Date hoje = new Date();

        controle.registrarPagamento(1, "B", 2024, 2, hoje.getTime(), 100);
        controle.registrarPagamento(1, "B", 2024, 2, hoje.getTime(), 1080);

        try {
            controle.registrarPagamento(1, "B", 2024, 2, data1, 20);
        } catch (ValorInvalido e) {
            fail("O valor menor que a parcela deve ser aceito se for tudo o que falta para quitar a taxa!");
        }

    }
}
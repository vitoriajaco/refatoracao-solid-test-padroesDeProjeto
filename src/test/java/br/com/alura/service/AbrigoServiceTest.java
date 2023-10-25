package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Abrigo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbrigoServiceTest {

    private ClientHttpConfiguration client = mock(ClientHttpConfiguration.class); //Ao executar o teste ele simulara uma instancia desse objeto
    private AbrigoService abrigoService = new AbrigoService(client); //é instanciada passando o client no construtor que é o mock
    private HttpResponse<String> response = mock(HttpResponse.class); //É necessario tbm mockar a resposta, pois no metodo dispararRequisicaoGET() existe um response HttpResponse
    private Abrigo abrigo = new Abrigo("Teste", "61981880392", "abrigo_alura@gmail.com");

    @Test
    public void deveVerificarSeDispararRequisicaoGetSeraChamado() throws IOException, InterruptedException {
        abrigo.setId(0L);
        String expectedAbrigosCadastrados = "Abrigos cadastrados:";
        String expectedIdENome = "0 - Teste";

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //Ao chamar a função pelo teste, ele vai pegar todos os retornos do System.out.println.
        // Para isso, precisamos pegar como um ByteArrayOutputStream(). Por isso, temos uma instância que é um array de bytes chamado baos, que é como pegamos o retorno do System.out.println, do método listarAbrigo()
        PrintStream printStream = new PrintStream(baos); //Criamos uma instância de PrintStream, que é para escrever no array de bytes. E também configuramos este printSream, que está sendo escrito no nosso array de bytes, no setOut().
        System.setOut(printStream);

        when(response.body()).thenReturn("[{"+abrigo.toString()+"}]");
        when(client.dispararRequisicaoGET(anyString())).thenReturn(response);

        abrigoService.listarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator()); //Dentro do array de bytes baos que contém as duas strings que retornam no System.out.println, devemos pegar o actualAbrigosCadastrados no espaço 0 do array. Ou seja, a mesma mensagem de "Abrigos cadastrados" para o teste passar.
        String actualAbrigosCadastrados = lines[0];
        String actualIdENome = lines[1]; //No espaço 1, precisamos pegar a string "0 - Teste" para que é o actualIdENome. Somente assim o teste poderá passar.

        Assertions.assertEquals(expectedAbrigosCadastrados, actualAbrigosCadastrados);
        Assertions.assertEquals(expectedIdENome, actualIdENome);
    }



}

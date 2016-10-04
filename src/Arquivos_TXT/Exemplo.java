package Arquivos_TXT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Exemplo {

    public static void main(String[] args) {

        String nome = "C:\\Users\\fernando.m.souza\\Desktop\\Arquivos referência FAC Velox\\Relatorio_Portas_DSL.txt";

        System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            while (linha != null) {
                System.out.printf("%s\n", linha);

                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }

        System.out.println();
    }
}

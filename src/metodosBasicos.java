import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class metodosBasicos {
    //---------------------
    //Ficheiros
    public static List<String> lerTodasLinhas(String caminhoFicheiro) {
        List<String> linhas = new ArrayList<>();
        File file = new File(caminhoFicheiro);
        if (!file.exists()) return linhas;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String l = sc.nextLine().trim();
                if (!l.isEmpty()) {
                    linhas.add(l);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler ficheiro " + caminhoFicheiro);
        }
        return linhas;
    }
    public static void acrescentarLinhaFicheiro(String caminhoFicheiro, String linha) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoFicheiro, true))) {
            //o true mantem todo o texto q ja esta no ficheiro
            // printWriter da muda de linha automaticamente e transforma int e double automaticamente em string
            writer.println(linha);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no ficheiro " + caminhoFicheiro);
        }
    }

    public static void reescreverFicheiro(String caminhoFicheiro, List<String> linhas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoFicheiro, false))) {
            //o false faz o opsto do de cima. apaga tudo q foi escrito
            for (String l : linhas) {//os dois pontos serve para cada linha dentro da lista vc faz algo
                writer.println(l);
            }
        } catch (IOException e) {
            System.out.println("Erro ao reescrever ficheiro " + caminhoFicheiro);
        }
    }
    public static int obterProximoId(String caminhoFicheiro) {
        List<String> linhas = lerTodasLinhas(caminhoFicheiro);
        int maiorId = 0;
        for (String l : linhas) {
            String[] partes = l.split(";");
            if (partes.length > 0) {
                try {
                    int id = Integer.parseInt(partes[0].trim());
                    if (id > maiorId) {
                        maiorId = id;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return maiorId + 1;
    }

    //---------------------------------------------
    //Ler numeros
    public static int lerInteiro(Scanner sc) {

        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor introduza um número inteiro válido: ");
            }
        }
    }

    public static double lerDouble(Scanner sc) {

        while (true) {
            try {
                String input = sc.nextLine().trim().replace(",", ".");
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor introduza um valor decimal válido: ");
            }
        }
    }
//----------------------------------------------------
    //datas
public static int converterParaDiasAbsolutos(String dataStr) {
    try {
        String[] p = dataStr.split("-");
        int dia = Integer.parseInt(p[0].trim());
        int mes = Integer.parseInt(p[1].trim());
        int ano = Integer.parseInt(p[2].trim());
        // Simplificação acessível: 360 dias por ano, 30 por mês
        return (ano * 360) + (mes * 30) + dia;
    } catch (Exception e) {
        return 0;
    }
}
    public static int compararDatas(String d1, String d2) {
        return Integer.compare(converterParaDiasAbsolutos(d1), converterParaDiasAbsolutos(d2));
    }





}

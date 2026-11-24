import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class funcoes {
    public static final String DATA_REFERENCIA = "08-03-2024";

    public static boolean efetuarLogin() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("\n--- LOGIN ---");
            System.out.print("Username (ou '0' para sair): ");
            String username = input.nextLine().trim();

            if (username.equals("0")) {
                return false;
            }

            System.out.print("Password: ");
            String password = input.nextLine().trim();

            if (validarCredenciais(username, password)) {
                return true;
            } else {
                System.out.println("Credenciais incorretas! Tente novamente.");
            }
        }
    }

    public static boolean validarCredenciais(String username, String password) {
        File file = new File("Files/loginHotel.txt");
        if (!file.exists()) {
            // Se ficheiro não existir, permite credencial por defeito para testes
            return username.equals("admin") && password.equals("1234");
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                if (linha.isEmpty()) continue;
                String[] partes = linha.split(";");
                if (partes.length >= 2) {
                    if (partes[0].trim().equals(username) && partes[1].trim().equals(password)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler ficheiro Autenticacao.csv");
        }
        return false;
    }

    //----------------------------------------------------------------------------------------
//Gerir base de dados
    public static void criarNovoCliente() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n-- Criar Novo Cliente --");
        String novoId = metodosBasicos.obterProximoId("Files/clientesHotel.csv");
        System.out.print("Nome: ");
        String nome = input.nextLine().trim();
        System.out.print("Data de Nascimento (DD-MM-AAAA): ");
        String dataNasc = input.nextLine().trim();
        System.out.print("Telemóvel: ");
        String telemovel = input.nextLine().trim();
        System.out.print("E-mail: ");
        String email = input.nextLine().trim();

        String linha = novoId + ";" + nome + ";" + dataNasc + ";" + telemovel + ";" + email;
        metodosBasicos.acrescentarLinhaFicheiro("Files/clientesHotel.csv", linha);
        System.out.println("Cliente registado com sucesso! ID atribuído: " + novoId);
    }

    public static void atualizarContactosCliente() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n-- Atualizar Contactos de Cliente --");
        System.out.print("Introduza o ID do Cliente: ");
        String idProcurado = input.nextLine().trim();

        File file = new File("Files/clientesHotel.csv");
        if (!file.exists()) {
            System.out.println("Ficheiro clientesHotel.csv não encontrado.");
            return;
        }

        List<String> linhas = metodosBasicos.lerTodasLinhas("Files/clientesHotel.csv");
        boolean encontrado = false;
        List<String> novasLinhas = new ArrayList<>();

        for (String linha : linhas) {
            String[] dados = linha.split(";");
            if (dados.length == 5 && dados[0].trim().equalsIgnoreCase(idProcurado)) {
                encontrado = true;
                System.out.println("Cliente encontrado: " + dados[1].trim());
                System.out.print("Novo Telemóvel (pressione Enter para manter [" + dados[3].trim() + "]): ");
                String novoTel = input.nextLine().trim();
                if (!novoTel.isEmpty()) dados[3] = novoTel;

                System.out.print("Novo E-mail (pressione Enter para manter [" + dados[4].trim() + "]): ");
                String novoEmail = input.nextLine().trim();
                if (!novoEmail.isEmpty()) dados[4] = novoEmail;

                linha = String.join(";", dados);
            }
            novasLinhas.add(linha);
            if (encontrado) {
                metodosBasicos.reescreverFicheiro("Files/clientesHotel.csv", novasLinhas);
                System.out.println("Contactos do cliente atualizados com sucesso!");
            } else {
                System.out.println("Cliente com ID " + idProcurado + " não foi encontrado.");
            }
        }


    }

    public static void criarNovoTema() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n-- Criar Novo Tema --");
        String novoId = metodosBasicos.obterProximoId("Files/temasHotel.csv");
        System.out.print("Nome do Tema: ");
        String nome = input.nextLine().trim();
        System.out.print("Multiplicador de Preço (ex: 1.2): ");
        double mult = metodosBasicos.lerDouble(input);

        String linha = novoId + ";" + nome + ";" + mult;
        metodosBasicos.acrescentarLinhaFicheiro("Files/temasHotel.csv", linha);
        System.out.println("Tema registado com sucesso! ID: " + novoId);
    }

    public static void criarNovoQuarto() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n-- Criar Novo Quarto --");
        System.out.print("Número do Quarto: ");
        int numQuarto = metodosBasicos.lerInteiro(input);
        System.out.print("ID do Tema: ");
        String idTema = input.nextLine();
        System.out.print("Tipo de Quarto (SINGLE, DOUBLE, SUITE): ");
        String tipo = input.nextLine().trim().toUpperCase();

        String linha = numQuarto + ";" + idTema + ";" + tipo;
        metodosBasicos.acrescentarLinhaFicheiro("Files/quartosHotel.csv", linha);
        System.out.println("Quarto " + numQuarto + " criado com sucesso!");
    }

    public static void criarNovasCredenciais() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n-- Adicionar Novas Credenciais --");
        System.out.print("Username: ");
        String user = input.nextLine().trim();
        System.out.print("Password: ");
        String pass = input.nextLine().trim();

        String linha = user + ";" + pass;
        metodosBasicos.acrescentarLinhaFicheiro("Files/loginHotel.txt", linha);
        System.out.println("Credenciais adicionadas com sucesso!");
    }

    public static void excluirCredenciais() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n-- Excluir Credenciais --");
        System.out.print("Username a eliminar: ");
        String userProcurado = input.nextLine().trim();

        List<String> linhas = metodosBasicos.lerTodasLinhas("Files/loginHotel.txt");
        List<String> novasLinhas = new ArrayList<>();
        boolean removido = false;

        for (String linha : linhas) {
            String[] partes = linha.split(",");
            if (partes.length >= 1 && partes[0].trim().equalsIgnoreCase(userProcurado)) {
                removido = true;
            } else {
                novasLinhas.add(linha);
            }
        }

        if (removido) {
            metodosBasicos.reescreverFicheiro("Files/loginHotel.txt", novasLinhas);
            System.out.println("Credencial eliminada com sucesso!");
        } else {
            System.out.println("Username não encontrado.");
        }
    }

    public static void criarNovoProduto() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n-- Criar Novo Produto/Serviço --");
        int novoId = metodosBasicos.obterProximoId("Files/produtosHotel.csv");
        System.out.print("Descrição do Produto/Serviço: ");
        String desc = input.nextLine().trim();
        System.out.print("Preço Unitário (€): ");
        double preco = metodosBasicos.lerDouble(input);

        String linha = novoId + ";" + desc + ";" + preco;
        metodosBasicos.acrescentarLinhaFicheiro("Files/produtosHotel.csv", linha);
        System.out.println("Produto criado com sucesso! ID: " + novoId);
    }

    public static void pesquisarClientePorColuna(int indiceColuna, String termo) {
        List<String> linhas = metodosBasicos.lerTodasLinhas("Files/clientesHotel.csv");
        boolean achou = false;

        for (String linha : linhas) {
            String[] dados = linha.split(";");
            if (indiceColuna < dados.length && dados[indiceColuna].trim().equalsIgnoreCase(termo)) {
                System.out.println("\n[Registo Encontrado]");
                System.out.println("ID Cliente: " + dados[0].trim());
                System.out.println("Nome: " + dados[1].trim());
                System.out.println("Data Nasc: " + (dados.length > 2 ? dados[2].trim() : "Vazio"));
                System.out.println("Telemóvel: " + (dados.length > 3 ? dados[3].trim() : "Vazio"));
                System.out.println("E-mail: " + (dados.length > 4 ? dados[4].trim() : "Vazio"));
                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Nenhum cliente encontrado com o critério indicado.");
        }
    }

    //-------------------------------------------------------------+
    //quartos

    public static void listarQuartosDisponiveis() {
        List<String> quartos = metodosBasicos.lerTodasLinhas("Files/quartosHotel.csv");
        List<String> reservas = metodosBasicos.lerTodasLinhas("Files/reservasHotel.csv");

            Set<String> quartosOcupados = new HashSet<>();
            for (String res : reservas) {
                String[] dados = res.split(";");
                if (dados.length >= 5) {
                    String numQuarto = dados[4].trim();
                    String dataFim = dados[2].trim();
                    if (metodosBasicos.compararDatas(dataFim, DATA_REFERENCIA) >= 0) {
                        quartosOcupados.add(numQuarto);
                    }
                }
            }

            System.out.println("\nQuartos Disponíveis:");
            System.out.printf("%-15s %-10s %-15s\n", "Nº Quarto", "ID Tema", "Tipo Quarto");

            boolean algum = false;
            for (String q : quartos) {
                String[] dados = q.split(";");
                if (dados.length >= 3) {
                    String numQ = dados[0].trim();
                    String idTema = dados[1].trim();
                    String tipoQ = dados[2].trim().toUpperCase();

                    if (quartosOcupados.contains(numQ)) continue;

                    System.out.printf("%-15s %-10s %-15s\n", numQ, idTema, tipoQ);
                    algum = true;
                }
            }

            if (!algum) {
                System.out.println("Nenhum quarto disponível encontrado.");
            }
        }

// 2. Filtra os quartos disponíveis por tema
        public static void listarQuartosTema(String tema) {
            List<String> quartos = metodosBasicos.lerTodasLinhas("Quartos.csv");
            List<String> reservas = metodosBasicos.lerTodasLinhas("Reservas.csv");

            Set<String> quartosOcupados = new HashSet<>();
            for (String res : reservas) {
                String[] dados = res.split(";");
                if (dados.length >= 5) {
                    String numQuarto = dados[4].trim();
                    String dataFim = dados[2].trim();
                    if (metodosBasicos.compararDatas(dataFim, DATA_REFERENCIA) >= 0) {
                        quartosOcupados.add(numQuarto);
                    }
                }
            }

            System.out.println("\nQuartos Disponíveis (Tema: " + tema + "):");
            System.out.println("----------------------------------------");
            System.out.printf("%-15s %-10s %-15s\n", "Nº Quarto", "ID Tema", "Tipo Quarto");
            System.out.println("----------------------------------------");

            boolean algum = false;
            for (String q : quartos) {
                String[] dados = q.split(",");
                if (dados.length >= 3) {
                    String numQ = dados[0].trim();
                    String idTema = dados[1].trim();
                    String tipoQ = dados[2].trim().toUpperCase();

                    if (quartosOcupados.contains(numQ)) continue;
                    if (!idTema.equalsIgnoreCase(tema)) continue;

                    System.out.printf("%-15s %-10s %-15s\n", numQ, idTema, tipoQ);
                    algum = true;
                }
            }

            if (!algum) {
                System.out.println("Nenhum quarto disponível encontrado para o tema indicado.");
            }
        }

// 3.Filtra os quartos disponíveis por tipo
        public static void listarQuartosTipo(String tipo) {
            List<String> quartos = metodosBasicos.lerTodasLinhas("Quartos.csv");
            List<String> reservas = metodosBasicos.lerTodasLinhas("Reservas.csv");

            Set<String> quartosOcupados = new HashSet<>();
            for (String res : reservas) {
                String[] dados = res.split(";");
                if (dados.length >= 5) {
                    String numQuarto = dados[4].trim();
                    String dataFim = dados[2].trim();
                    if (metodosBasicos.compararDatas(dataFim, DATA_REFERENCIA) >= 0) {
                        quartosOcupados.add(numQuarto);
                    }
                }
            }

            System.out.println("\nQuartos Disponíveis (Tipo: " + tipo + "):");
            System.out.printf("%-15s %-10s %-15s\n", "Nº Quarto", "ID Tema", "Tipo Quarto");

            boolean algum = false;
            for (String q : quartos) {
                String[] dados = q.split(",");
                if (dados.length >= 3) {
                    String numQ = dados[0].trim();
                    String idTema = dados[1].trim();
                    String tipoQ = dados[2].trim().toUpperCase();

                    if (quartosOcupados.contains(numQ)) continue;
                    if (!tipoQ.equalsIgnoreCase(tipo)) continue;

                    System.out.printf("%-15s %-10s %-15s\n", numQ, idTema, tipoQ);
                    algum = true;
                }
            }

            if (!algum) {
                System.out.println("Nenhum quarto disponível encontrado para o tipo indicado.");
            }
        }





    //reservas
    //listar reservas
    public static void listarReservas(boolean apenasAtivas) {
        List<String> reservas = metodosBasicos.lerTodasLinhas("Files/reservasHotel.csv");
        System.out.println(apenasAtivas ? "\n- Reservas ativas" : "\n- Historico de reservas ");
        System.out.println("----------------------------------------------------------");

        boolean achou = false;
        for (String res : reservas) {
            String[] dados = res.split(";");
            if (dados.length >= 5) {
                String idRes = dados[0].trim();
                String inicio = dados[1].trim();
                String fim = dados[2].trim();
                String idCli = dados[3].trim();
                String numQuarto = dados[4].trim();

                boolean eAtiva = metodosBasicos.compararDatas(fim, DATA_REFERENCIA) >= 0;

                if ((apenasAtivas && eAtiva) || (!apenasAtivas && !eAtiva)) {
                    System.out.printf("\n", idRes, inicio, fim, idCli, numQuarto);
                    achou = true;
                }
            }
        }

        if (!achou) {
            System.out.println("Nenhuma reserva encontrada.");
        }
    }


    //registrar reserva
    public static void registarNovaReserva() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Rgistrar nova Reserva---");
        String novoId = metodosBasicos.obterProximoId("Files/reservasHotel.csv");
        System.out.print("ID do Cliente: ");
        String idCli = input.nextLine().trim();
        System.out.print("Número do Quarto: ");
        String numQuarto = input.nextLine().trim();
        System.out.print("Data Início (DD-MM-AAAA): ");
        String dataInicio = input.nextLine().trim();
        System.out.print("Data Fim (DD-MM-AAAA): ");
        String dataFim = input.nextLine().trim();

        // Validar se o quarto esta disponivel no intervalo de datas
        if (validarDisponibilidadeQuarto(numQuarto, dataInicio, dataFim)) {
            String linha = novoId + ";" + dataInicio + ";" + dataFim + ";" + idCli + ";" + numQuarto;
            metodosBasicos.acrescentarLinhaFicheiro("Reservas.csv", linha);
            System.out.println("Reserva nº " + novoId + " registada com sucesso para o quarto " + numQuarto + "!");
        } else {
            System.out.println("O quarto " + numQuarto + " não está disponível nas datas solicitadas.");
        }

    }

    public static boolean validarDisponibilidadeQuarto(String numQuarto, String novaInicio, String novaFim) {
        List<String> reservas = metodosBasicos.lerTodasLinhas("Files/reservasHotel.csv");

        for (String res : reservas) {
            String[] dados = res.split(";");
            if (dados.length >= 5) {
                String q = dados[4].trim();
                if (q.equals(numQuarto)) {
                    String resInicio = dados[1].trim();
                    String resFim = dados[2].trim();

                    // Existe sobreposição se: novaInicio <= resFim E novaFim >= resInicio
                    if (metodosBasicos.compararDatas(novaInicio, resFim) <= 0 && metodosBasicos.compararDatas(novaFim, resInicio) >= 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


//-----------------------------------------------------------------------------------
    //funcoes do dashboard

    //num de noites reserva
    public static int calcularNoites(String dInicio, String dFim) {
        int diasInicio = metodosBasicos.converterParaDiasAbsolutos(dInicio);
        int diasFim = metodosBasicos.converterParaDiasAbsolutos(dFim);
        int diff = diasFim - diasInicio;
        return diff > 0 ? diff : 1;
    }

    public static int obterAnoReserva(String data) {
        try {
            String[] p = data.split("-");
            return Integer.parseInt(p[2].trim());
        } catch (Exception e) {
            return 2024;// Valor padrão caso haja erro
        }
    }

    public static double obterPrecoBaseQuarto(String numQuarto) {
        List<String> quartos = metodosBasicos.lerTodasLinhas("Files/quartosHotel.csv");
        List<String> temas = metodosBasicos.lerTodasLinhas("Files/temasHotel.csv");

        String idTema = "t-01";
        String tipoQuarto = "SINGLE";

        for (String q : quartos) {
            String[] d = q.split(";");
            if (d.length >= 3 && d[0].trim().equalsIgnoreCase(numQuarto)) {
                idTema = d[1].trim();
                tipoQuarto = d[2].trim().toUpperCase();
                break;
            }
        }

        double precoBase = 95.0; // SINGLE
        if (tipoQuarto.equals("DOUBLE")) precoBase = 110.0;
        else if (tipoQuarto.equals("SUITE")) precoBase = 250.0;

        double multiplicador = 1.0;
        for (String t : temas) {
            String[] d = t.split(";");
            if (d.length >= 3 && d[0].trim().equalsIgnoreCase(idTema)) {
                try {
                    multiplicador = Double.parseDouble(d[2].trim());
                } catch (Exception ignored) {
                }
                break;
            }
        }

        return precoBase * multiplicador;
    }


    public static void calcularReceitaPorAno() {
        Map<Integer, Double> receitaAno = new TreeMap<>();
        List<String> reservas = metodosBasicos.lerTodasLinhas("Files/reservasHotel.csv");

        for (String res : reservas) {
            String[] d = res.split(";");
            if (d.length >= 5) {
                int noites = calcularNoites(d[1].trim(), d[2].trim());
                int ano = obterAnoReserva(d[1].trim());
                double precoNoite = obterPrecoBaseQuarto(d[4].trim());
                double totalReserva = noites * precoNoite;
                double acumulado;
                if (receitaAno.containsKey(ano)) {
                    acumulado = receitaAno.get(ano); // Vai buscar o valor existente
                } else {
                    acumulado = 0.0; // Começa do zero se for o primeiro
                }
                receitaAno.put(ano, acumulado + totalReserva);

            }
        }

        System.out.println("\n--- Receita total por ano ---");
        for (Map.Entry<Integer, Double> entry : receitaAno.entrySet()) {
            System.out.printf("Ano %d: %.2f€\n", entry.getKey(), entry.getValue());
        }
    }






    //------------------------------------------------------------------
    //galeria
    public static void imprimirFicheiroTexto(String nomeFicheiro) {
        File file = new File(nomeFicheiro);
        if (!file.exists()) {
            System.out.println("Não existe imagem disponivel");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            System.out.println("\n " + nomeFicheiro);
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir ficheiro " + nomeFicheiro);
        }
    }
}

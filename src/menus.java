import java.util.Scanner;

public class menus {

    //Menu principal
    public static void exibirMenuPrincipal() {
        System.out.println("\n================ MENU PRINCIPAL ================");
        System.out.println("1. Gerir Base de Dados");
        System.out.println("2. Consultar Clientes");
        System.out.println("3. Consultar Quartos Disponíveis");
        System.out.println("4. Consultar Reservas");
        System.out.println("5. Registar Nova Reserva");
        System.out.println("6. Dashboard de Análise");
        System.out.println("7. Galeria");
        System.out.println("0. Sair");
        System.out.println("================================================");
    }

    //Submenu 1: gerir base de dados
    public static void menuGerirBaseDados() {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- GERIR BASE DE DADOS ---");
            System.out.println("1. Clientes - Novo Cliente");
            System.out.println("2. Clientes - Atualizar Contactos de Cliente");
            System.out.println("3. Temas - Novo Tema");
            System.out.println("4. Quartos - Novo Quarto");
            System.out.println("5. Autenticação - Novas Credenciais");
            System.out.println("6. Autenticação - Excluir Credenciais");
            System.out.println("7. Produtos - Novo Produto/Serviço");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = metodosBasicos.lerInteiro();

            switch (opcao) {
                case 1:
                    funcoes.criarNovoCliente();
                    break;
                case 2:
                    funcoes.atualizarContactosCliente();
                    break;
                case 3:
                    funcoes.criarNovoTema();
                    break;
                case 4:
                    funcoes.criarNovoQuarto();
                    break;
                case 5:
                    funcoes.criarNovasCredenciais();
                    break;
                case 6:
                    funcoes.excluirCredenciais();
                    break;
                case 7:
                    funcoes.criarNovoProduto();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    //Submenu 2: Consultar clientes
    public static void menuConsultarClientes() {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- CONSULTAR CLIENTES ---");
            System.out.println("1. Pesquisar por idCliente");
            System.out.println("2. Pesquisar por Telemóvel");
            System.out.println("3. Pesquisar por E-mail");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = metodosBasicos.lerInteiro();

            if (opcao >= 1 && opcao <= 3) {
                System.out.print("Introduza o valor de pesquisa: ");
                String termo = input.nextLine().trim();
                funcoes.pesquisarClientePorColuna(opcao - 1, termo);
            }
        } while (opcao != 0);
    }

    //Submeni 3: consultar quartos disponiveis
    public static void menuConsultarQuartosDisponiveis() {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- CONSULTAR QUARTOS DISPONÍVEIS ---");
            System.out.println("1. Todos os quartos disponíveis");
            System.out.println("2. Por Tema");
            System.out.println("3. Por Tipo de Quarto (SINGLE, DOUBLE, SUITE)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = metodosBasicos.lerInteiro(input);

            switch (opcao) {
                case 1:
                    funcoes.listarQuartosDisponiveis();
                    break;
                case 2:
                    System.out.print("Introduza o Nome do Tema: ");
                    String tema = input.nextLine().trim();
                    funcoes.listarQuartosTema(tema);
                    break;
                case 3:
                    System.out.print("Introduza o Tipo de Quarto (SINGLE, DOUBLE, SUITE): ");
                    String tipo = input.nextLine().trim().toUpperCase();
                    funcoes.listarQuartosTipo(tipo);
                    break;
            }
        } while (opcao != 0);
    }

    //submenu 4: consultar reservas
    public static void menuConsultarReservas() {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- CONSULTAR RESERVAS ---");
            System.out.println("1. Histórico de Reservas (Passadas)");
            System.out.println("2. Reservas Ativas");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = metodosBasicos.lerInteiro(input);
            switch (opcao) {
                case 1 -> funcoes.listarReservas(false); // false = Histórico (Passadas)
                case 2 -> funcoes.listarReservas(true);  // true = Apenas Ativas
                case 0 -> System.out.println("A voltar ao menu principal...");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 0);
    }

    //submenu 5:analise
    public static void menuDashboard() {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- Dashboard de analise financeira ---");
            System.out.println("1. Receita Total por Anos");
            System.out.println("2. Despesa Total por Anos");
            System.out.println("3. Lucro por Anos");
            System.out.println("4. Melhor Cliente (Mais gastou)");
            System.out.println("5. Cliente mais Consumista (Serviço de Quarto)");
            System.out.println("6. Cliente mais Presente (Mais noites)");
            System.out.println("7. Tema mais Procurado & Tema mais Lucrativo / menos Lucrativo");
            System.out.println("8. Análise de Produtos/Serviços (Mais/Menos procurado e lucrativo)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = metodosBasicos.lerInteiro(input);

            switch (opcao) {
                case 1:
                    funcoes.calcularReceitaPorAno();
                    break;
                case 2:
                    calcularDespesaPorAno();
                    break;
                case 3:
                    calcularLucroPorAno();
                    break;
                case 4:
                    calcularMelhorCliente();
                    break;
                case 5:
                    calcularClienteMaisConsumista();
                    break;
                case 6:
                    calcularClienteMaisPresente();
                    break;
                case 7:
                    calcularEstatisticasTemas();
                    break;
                case 8:
                    calcularEstatisticasProdutos();
                    break;
            }
        } while (opcao != 0);
    }

    //submenu 6: Galeria

    public static void menuGaleria() {
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- Galeria do Hotel ---");
            System.out.println("1. Visualizar Fachada");
            System.out.println("2. Visualizar Quarto");
            System.out.println("3. Visualizar Spa");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = metodosBasicos.lerInteiro(input);
            String ficheiroArte = null;
            if (opcao == 1) ficheiroArte = "Files/galeriaFachadaHotel.txt";
            else if (opcao == 2) ficheiroArte = "Files/galeriaQuartoHotel.txt";
            else if (opcao == 3) ficheiroArte = "Files/galeriaSpaHotel.txt";

            if (ficheiroArte != null) {
                funcoes.imprimirFicheiroTexto(ficheiroArte);
            }

        } while (opcao != 0);
    }


}

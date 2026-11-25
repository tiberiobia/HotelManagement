import java.util.Scanner;
//problemas
//id comeca com letra corrigir metodo obter id

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // Data de referência do sistema conforme enunciado (08/03/2024)
    public static final String DATA_REFERENCIA="08-03-2024";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("-------------------");
        System.out.println("    Bem vindo ");
        System.out.println("-------------------");

        // Login obrigatorio
        if (funcoes.efetuarLogin()) {
            System.out.println("\n Login efetuado com sucesso!");
            int opcao;
            do {
                menus.exibirMenuPrincipal();
                System.out.print("Escolha uma opção: ");
                opcao = metodosBasicos.lerInteiro();

                switch (opcao) {
                    case 1:
                        menus.menuGerirBaseDados();
                        break;
                    case 2:
                        menus.menuConsultarClientes();
                        break;
                    case 3:
                        menus.menuConsultarQuartosDisponiveis();
                        break;
                    case 4:
                        menus.menuConsultarReservas();
                        break;
                    case 5:
                        funcoes.registarNovaReserva();
                        break;
                    case 6:
                        menus.menuDashboard();
                        break;
                    case 7:
                        menus.menuGaleria();
                        break;
                    case 0:
                        System.out.println("\nA encerrar o sistema. Até breve!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } while (opcao != 0);
        } else {
            System.out.println("Acesso encerrado pelo utilizador.");
        }

        input.close();

    }
}
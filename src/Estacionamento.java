
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.Duration;
import java.util.Scanner;

public class Estacionamento {
    Scanner input = new Scanner(System.in);
    ArrayList<Veiculo> veiculos = new ArrayList<>();

    private boolean[] vagas = new boolean[20];
    private int vagaEscolhida = 0;

    public void controleMenu(char opcao) {
        switch (opcao) {
            case '1':
                estacionar();
                break;
            case '2':
                sairDoEstacionamento();
                break;
            case '3':
                listarVagas();
                break;
            case '4':
                veiculosEstacionados();
                break;
            case '5':
                valores();
                break;
            case '6':
                System.out.println("\nObrigado por usar nosso sistema!");
            default:
                System.out.println("\nEscolha uma opção válida!\n");
        }
    }

    public void menu() {
        System.out.println("\nBem vindo ao Sistema de Estacionamento!");
        System.out.println("[1]. Estacionar Veículo");
        System.out.println("[2]. Sair do Estacionamento");;
        System.out.println("[3]. Listar Vagas");;
        System.out.println("[4]. Listar Veículos Estacionados");;
        System.out.println("[5]. Valores");
        System.out.println("[6]. Sair do Sistema");;
        System.out.print("|Escolha uma opção (1-6): ");;
        char opcao = input.next().charAt(0);

        input.nextLine();

        controleMenu(opcao);
    }

    public void estacionar() {
        boolean vagasDisponiveis = false;

        for (int i = 0; i < vagas.length; i++) {
            if (!vagas[i]) {
                vagasDisponiveis = true;
                break;
            }
        }

        if (!vagasDisponiveis) {
            System.out.println("\nQue pena! Nossas vagas já estão esgotadas! :(\n");
            return;
        }

        System.out.print("\nInforme sua placa: ");
        String placa = input.nextLine();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                System.out.println("\nVocê já tem um veículo estacionado com essa placa!");
                return;
            }
        }

        System.out.print("\nInforme o tipo do veículo (Carro, Moto, Caminhão): ");
        String tipo = input.nextLine();

        while(true){
            if(tipo.equalsIgnoreCase("Carro") || tipo.equalsIgnoreCase("Moto") || tipo.equalsIgnoreCase("Caminhão")){
                break;
            }else{
                System.out.println("Tipo inválido! Por favor, escolha entre Carro, Moto ou Caminhão.");
                return;
            }

        }

        String tipoFormatado = tipo.toUpperCase();

        System.out.print("\nEscolha sua vaga (1-20): ");
        vagaEscolhida = input.nextInt() - 1;

        if (vagaEscolhida < 0 || vagaEscolhida >= vagas.length) {
            System.out.println("\nVaga inválida! Escolha uma vaga entre 1 e 20.\n");
            return;
        }

        if (vagas[vagaEscolhida]) {
            System.out.println("\nEssa vaga já foi escolhida!");
        } else {
            LocalTime horaEntrada = LocalTime.now();
            DateTimeFormatter formatado = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaFormatada = horaEntrada.format(formatado);

            Veiculo v = new Veiculo(placa, tipoFormatado, horaEntrada);
            veiculos.add(v);

            vagas[vagaEscolhida] = true;
            System.out.println("\n" + tipo + " " + placa + " entrou no estacionamento às " + horaFormatada + " na vaga " + (vagaEscolhida + 1));
        }
    }

    public void sairDoEstacionamento(){
        if(vagaEscolhida == 0){
            System.out.println("\nVocê não tem nenhum veículo estacionado!");
            return;
        }

        vagas[vagaEscolhida] = false;

        System.out.println(calcularValor());
        System.out.println("\nObrigado por usar nosso estacionamento!\n");

        for(Veiculo veiculo: veiculos){
            veiculo.setTipo(null);
            veiculo.setHoraEntrada(null);
            veiculo.setPlaca(null);
        }
    }

    public void listarVagas() {
        for (int i = 0; i < vagas.length; i++) {
            if (!vagas[i]) {
                System.out.printf("\nVaga %d: Dispoível", i + 1);
            } else {
                System.out.printf("\nVaga %d: Indisponível", i + 1);
            }
        }
        System.out.print("\n");
    }

    public void veiculosEstacionados(){
        if(vagas.length == 0){
            System.out.println("\nNão há veiculos estacionados!");
        }

        for(int i = 0; i < veiculos.size(); i++){
            System.out.printf("\nVeiculo %d ", i +1);
            System.out.println("\nPlaca: " + veiculos.get(i).getPlaca());
            System.out.println("Tipo: " + veiculos.get(i).getTipo());
            System.out.println("Hora entrada: " + veiculos.get(i).getHoraEntrada());

            for(int j = 0; j < vagas.length; j++ ){
                if(!vagas[j]){
                    System.out.println("Vaga: " + j + 1);
                    break;
                }
            }
        }
    }

    public void valores(){
        System.out.println("\n-----------------------------------------------");
        System.out.println("|Olá, seja bem vindo ao nosso estacionamento! |");
        System.out.println("|---------------------------------------------|");
        System.out.println("|                                             |");
        System.out.println("|*Carro: 1 Minuto = 2 reais                   |");
        System.out.println("|*Moto: 1 Minuto = 1 real                     |");
        System.out.println("|*Caminhão: 1 Minuto = 3 reais                |");
        System.out.println("|---------------------------------------------|\n");
    }

    public String calcularValor() {
        StringBuilder resultado = new StringBuilder();

        for (Veiculo veiculo : veiculos) {
            LocalTime horaSaida = LocalTime.now();
            LocalTime horaEntrada = veiculo.getHoraEntrada();

            Duration duracao = Duration.between(horaEntrada, horaSaida);
            long minutos = duracao.toMinutes();

            float valor;

            switch (veiculo.getTipo()) {
                case "CARRO":
                    valor = minutos * 2;

                    resultado.append("\nMinutos passados: ").append(minutos);
                    resultado.append("\nValor Total: ").append(valor).append("\n");

                    break;
                case "MOTO":
                    valor = minutos;

                    resultado.append("\nMinutos passados: ").append(minutos);
                    resultado.append("\nValor Total: ").append(valor).append("\n");

                    break;
                case "CAMINHAO":
                case "CAMINHÃO":
                    valor = minutos * 3;

                    resultado.append("\nMinutos passados: ").append(minutos);
                    resultado.append("\nValor Total: ").append(valor).append("\n");

                    break;
                default:
                    resultado.append("\nOpção inválida!\n");
            }
        }
        return resultado.toString();
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlataformaReserva {
    private List<Usuario> clientes;
    private List<Voo> voos;
    private List<Hotel> hoteis;

    public PlataformaReserva() {
        clientes = new ArrayList<>();
        voos = new ArrayList<>();
        hoteis = new ArrayList<>();
    }

    public void lerVoos(String arquivoVoos) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoVoos))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String origem = dados[0];
                String destino = dados[1];
                String data = dados[2];
                String hora = dados[3];
                int assentosDisponiveis = Integer.parseInt(dados[4].split(" ")[0]);
                double preco = Double.parseDouble(dados[5].replace("R$", "").trim());
                Voo voo = new Voo(origem, destino, data, hora, assentosDisponiveis, preco);
                voos.add(voo);
            }
        }
    }

    public void lerHoteis(String arquivoHoteis) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoHoteis))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String local = dados[0];
                String nome = dados[1];
                int vagasDisponiveis = Integer.parseInt(dados[2].split(" ")[0]);
                double preco = Double.parseDouble(dados[3].replace("R$", "").trim());
                int estrelas = Integer.parseInt(dados[4].split(" ")[0]);
                Hotel hotel = new Hotel(local, nome, vagasDisponiveis, preco, estrelas);
                hoteis.add(hotel);
            }
        }
    }

    public void lerClientes(String arquivoClientes) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoClientes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String nome = dados[0];
                String origem = dados[1];
                String destino = dados[2];
                int duracao = Integer.parseInt(dados[3].split(" ")[0]);
                int estrelasMinimas = Integer.parseInt(dados[4].split(" ")[0]);
                double saldo = Double.parseDouble(dados[5].replace("R$", "").trim());
                Usuario cliente = new Usuario(nome, saldo, origem, destino, estrelasMinimas, duracao);
                clientes.add(cliente);
            }
        }
    }

    public void adicionarCliente(Usuario cliente) {
        clientes.add(cliente);
    }

    // public void processarReservasSequencial() {
    //     for (Usuario cliente : clientes) {
    //         Orcamento orcamento = gerarOrcamento(cliente);
    //         if (orcamento != null && cliente.getSaldo() >= orcamento.getTotalPreco()) {
    //             // Reserva confirmada
    //             Hotel hotel = orcamento.getHotel();
    //             Voo voo = orcamento.getVoo();
    //             boolean confirmaVoo = voo.reservar();
    //             boolean confirmaHotel = hotel.reservar();
    //             if (confirmaVoo && confirmaHotel) {
    //                 System.out.println("Reserva confirmada para o cliente: " + cliente.getNome());
    //                 System.out.println("Vagas no voo: " + voo.getAssentosDisponiveis());
    //                 System.out.println("Vagas no hotel: " + hotel.getVagasDisponiveis());
    //             } else {
    //                 if (!confirmaHotel && confirmaVoo) {
    //                     voo.liberarReserva();
    //                 }
    //                 if (!confirmaVoo && confirmaHotel) {
    //                     hotel.liberarReserva();
    //                 }
    //             }
    //         } else {
    //             // Reserva não confirmada
    //             System.out.println("Reserva não confirmada para o cliente: " + cliente.getNome());
    //         }
    //     }
    // }

    public void processarReservasSequencial(String arquivoSaida) {
        int totalPedidos = clientes.size();
        Set<String> clientesDistintos = new HashSet<>();
        int pedidosAtendidos = 0;
        double totalGastoClientes = 0;
        double totalGastoHoteis = 0;
        double totalGastoVoos = 0;

        for (Usuario cliente : clientes) {
            Orcamento orcamento = gerarOrcamento(cliente);
            if (orcamento != null && cliente.getSaldo() >= orcamento.getTotalPreco()) {
                Hotel hotel = orcamento.getHotel();
                Voo voo = orcamento.getVoo();
                boolean confirmaVoo = voo.reservar();
                boolean confirmaHotel = hotel.reservar();
                if (confirmaVoo && confirmaHotel) {
                    pedidosAtendidos++;
                    totalGastoClientes += orcamento.getTotalPreco();
                    totalGastoHoteis += hotel.getPreco() * cliente.getDuracao();
                    totalGastoVoos += voo.getPreco();
                    clientesDistintos.add(cliente.getNome());
                    System.out.println("Reserva confirmada para o cliente: " + cliente.getNome());
                    System.out.println("Vagas no voo: " + voo.getAssentosDisponiveis());
                    System.out.println("Vagas no hotel: " + hotel.getVagasDisponiveis());
                } else {
                    if (confirmaVoo) {
                        voo.liberarReserva();
                    }
                    if (confirmaHotel) {
                        hotel.liberarReserva();
                    }
                }
            } else {
                System.out.println("Reserva não confirmada para o cliente: " + cliente.getNome());
            }
        }

        try (FileWriter writer = new FileWriter(arquivoSaida)) {
            writer.append(String.format("%d;%d;%d;%.2f;%.2f;%.2f\n",
                    totalPedidos,
                    clientesDistintos.size(),
                    pedidosAtendidos,
                    totalGastoClientes,
                    totalGastoHoteis,
                    totalGastoVoos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processarReservasParalelo() {
        // Implementação do processamento paralelo de reservas utilizando threads
    }

    private Orcamento gerarOrcamento(Usuario cliente) {
        Voo vooEscolhido = null;
        Hotel hotelEscolhido = null;

        // Escolher voo mais barato
        for (Voo voo : voos) {
            if (voo.getOrigem().equals(cliente.getOrigem()) && voo.getDestino().equals(cliente.getDestino()) && voo.getAssentosDisponiveis() > 0) {
                if (vooEscolhido == null || voo.getPreco() < vooEscolhido.getPreco()) {
                    vooEscolhido = voo;
                }
            }
        }

        // Escolher hotel mais barato
        for (Hotel hotel : hoteis) {
            if (hotel.getLocal().equals(cliente.getDestino()) && hotel.getEstrelas() >= cliente.getEstrelasMinimas() && hotel.getVagasDisponiveis() > 0) {
                if (hotelEscolhido == null || hotel.getPreco() < hotelEscolhido.getPreco()) {
                    hotelEscolhido = hotel;
                }
            }
        }

        if (vooEscolhido != null && hotelEscolhido != null) {
            return new Orcamento(cliente, vooEscolhido, hotelEscolhido);
        } else {
            return null;
        }
    }

    public void medirTempoExecucao() {
        // Implementação da marcação de tempos e experimentação
    }

    public static void main(String[] args) {
        PlataformaReserva plataforma = new PlataformaReserva();

        try {
            plataforma.lerVoos("voos.csv");
            plataforma.lerHoteis("hoteis.csv");
            plataforma.lerClientes("clientes_1000.csv");

            plataforma.processarReservasSequencial("saida-sequencial.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

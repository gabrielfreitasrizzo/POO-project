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

    public void processarReservasSequencial(String arquivoSaida) {
        int totalPedidos = clientes.size();
        Set<String> clientesDistintos = new HashSet<>();
        int pedidosAtendidos = 0;
        double totalGastoClientes = 0;
        double totalGastoHoteis = 0;
        double totalGastoVoos = 0;

        for (Usuario cliente : clientes) {
            Orcamento orcamento = gerarOrcamento(cliente);
            if (orcamento != null) {
                clientesDistintos.add(cliente.getNome());
                if (cliente.getSaldo() >= orcamento.getTotalPreco()) {
                    Hotel hotel = orcamento.getHotel();
                    Voo voo = orcamento.getVoo();
                    boolean confirmaVoo = voo.reservar();
                    boolean confirmaHotel = hotel.reservar();
                    if (confirmaVoo && confirmaHotel) {
                        pedidosAtendidos++;
                        totalGastoClientes += orcamento.getTotalPreco();
                        totalGastoHoteis += hotel.getPreco() * cliente.getDuracao();
                        totalGastoVoos += voo.getPreco();
                        cliente.setSaldo(cliente.getSaldo() - orcamento.getTotalPreco());
                    } else {
                        if (confirmaVoo) {
                            voo.liberarReserva();
                        }
                        if (confirmaHotel) {
                            hotel.liberarReserva();
                        }
                    }
                }
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

    public void processarReservasParalelo(String arquivoSaida) {
        int totalPedidos = clientes.size();
        Set<String> clientesDistintos = new HashSet<>();
        int pedidosAtendidos = 0;
        double totalGastoClientes = 0;
        double totalGastoHoteis = 0;
        double totalGastoVoos = 0;

        // Criar threads para processar os clientes
        ProcessarReservaRunnable runnable1 = new ProcessarReservaRunnable(clientes, voos, hoteis, clientesDistintos);
        ProcessarReservaRunnable runnable2 = new ProcessarReservaRunnable(clientes, voos, hoteis, clientesDistintos);
        ProcessarReservaRunnable runnable3 = new ProcessarReservaRunnable(clientes, voos, hoteis, clientesDistintos);

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        thread1.start();
        thread2.start();
        thread3.start();

        // Aguardar todas as threads terminarem
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Coletar resultados das threads
        pedidosAtendidos += runnable1.getPedidosAtendidosLocal();
        pedidosAtendidos += runnable2.getPedidosAtendidosLocal();
        pedidosAtendidos += runnable3.getPedidosAtendidosLocal();
        totalGastoClientes += runnable1.getTotalGastoClientesLocal();
        totalGastoClientes += runnable2.getTotalGastoClientesLocal();
        totalGastoClientes += runnable3.getTotalGastoClientesLocal();
        totalGastoHoteis += runnable1.getTotalGastoHoteisLocal();
        totalGastoHoteis += runnable2.getTotalGastoHoteisLocal();
        totalGastoHoteis += runnable3.getTotalGastoHoteisLocal();
        totalGastoVoos += runnable1.getTotalGastoVoosLocal();
        totalGastoVoos += runnable2.getTotalGastoVoosLocal();
        totalGastoVoos += runnable3.getTotalGastoVoosLocal();

        // Escrever no arquivo de saída
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
    

    private Orcamento gerarOrcamento(Usuario cliente) {
        Voo vooEscolhido = null;
        Hotel hotelEscolhido = null;

        for (Voo voo : voos) {
            if (voo.getOrigem().equals(cliente.getOrigem()) && voo.getDestino().equals(cliente.getDestino()) && voo.getAssentosDisponiveis() > 0) {
                if (vooEscolhido == null || voo.getPreco() < vooEscolhido.getPreco()) {
                    vooEscolhido = voo;
                }
            }
        }

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

    public static void main(String[] args) {
        PlataformaReserva plataforma = new PlataformaReserva();

        
        try {
            plataforma.lerVoos("voos.csv");
            plataforma.lerHoteis("hoteis.csv");
            plataforma.lerClientes("clientes_10000.csv");
            
            long startTime = System.currentTimeMillis();
            plataforma.processarReservasSequencial("saida-sequencial.csv");
            long endTime = System.currentTimeMillis();
            System.out.println("Tempo de execução total: " + (endTime - startTime) + " ms");

            long startTime2 = System.currentTimeMillis();
            plataforma.processarReservasParalelo("saida-paralelo.csv");
            long endTime2 = System.currentTimeMillis();
            System.out.println("Tempo total de execução: " + (endTime2 - startTime2) + " ms");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

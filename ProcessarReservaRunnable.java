import java.util.List;
import java.util.Set;

public class ProcessarReservaRunnable implements Runnable {
    private List<Usuario> clientes;
    private List<Voo> voos;
    private List<Hotel> hoteis;
    private Set<String> clientesDistintos;

    private int pedidosAtendidosLocal = 0;
    private double totalGastoClientesLocal = 0;
    private double totalGastoHoteisLocal = 0;
    private double totalGastoVoosLocal = 0;

    public ProcessarReservaRunnable(List<Usuario> clientes, List<Voo> voos, List<Hotel> hoteis,
                                    Set<String> clientesDistintos) {
        this.clientes = clientes;
        this.voos = voos;
        this.hoteis = hoteis;
        this.clientesDistintos = clientesDistintos;
    }

    @Override
    public void run() {
        for (Usuario cliente : clientes) {
            Orcamento orcamento = gerarOrcamento(cliente);
            if (orcamento != null) {
                synchronized (clientesDistintos) {
                    clientesDistintos.add(cliente.getNome());
                }
                synchronized (this) {
                    if (cliente.getSaldo() >= orcamento.getTotalPreco()) {
                        Hotel hotel = orcamento.getHotel();
                        Voo voo = orcamento.getVoo();
                        boolean confirmaVoo = voo.reservar();
                        boolean confirmaHotel = hotel.reservar();
                        if (confirmaVoo && confirmaHotel) {
                            pedidosAtendidosLocal++;
                            totalGastoClientesLocal += orcamento.getTotalPreco();
                            totalGastoHoteisLocal += hotel.getPreco() * cliente.getDuracao();
                            totalGastoVoosLocal += voo.getPreco();
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

    public int getPedidosAtendidosLocal() {
        return pedidosAtendidosLocal;
    }

    public double getTotalGastoClientesLocal() {
        return totalGastoClientesLocal;
    }

    public double getTotalGastoHoteisLocal() {
        return totalGastoHoteisLocal;
    }

    public double getTotalGastoVoosLocal() {
        return totalGastoVoosLocal;
    }
}

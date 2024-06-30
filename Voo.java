public class Voo {
    private String origem;
    private String destino;
    private String data;
    private String hora;
    private int assentosDisponiveis;
    private double preco;

    public Voo(String origem, String destino, String data, String hora, int assentosDisponiveis, double preco) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.hora = hora;
        this.assentosDisponiveis = assentosDisponiveis;
        this.preco = preco;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean reservar() {
        if (assentosDisponiveis > 0) {
            assentosDisponiveis--;
            return true;
        }
        return false;
    }

    public void liberarReserva() {
        assentosDisponiveis++;
    }
}

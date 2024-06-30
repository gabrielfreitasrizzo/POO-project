public class Hotel {
    private String local;
    private String nome;
    private int vagasDisponiveis;
    private double preco;
    private int estrelas;

    public Hotel(String local, String nome, int vagasDisponiveis, double preco, int estrelas) {
        this.local = local;
        this.nome = nome;
        this.vagasDisponiveis = vagasDisponiveis;
        this.preco = preco;
        this.estrelas = estrelas;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(int vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(int estrelas) {
        this.estrelas = estrelas;
    }

    public boolean reservar() {
        if (vagasDisponiveis > 0) {
            vagasDisponiveis--;
            return true;
        }
        return false;
    }

    public void liberarReserva() {
        vagasDisponiveis++;
    }
}

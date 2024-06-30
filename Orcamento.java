public class Orcamento {
    private Usuario usuario;
    private Voo voo;
    private Hotel hotel;

    public Orcamento(Usuario usuario, Voo voo, Hotel hotel) {
        this.usuario = usuario;
        this.voo = voo;
        this.hotel = hotel;
    }

    public double getTotalPreco() {
        return voo.getPreco() + (hotel.getPreco() * usuario.getDuracao());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Voo getVoo() {
        return voo;
    }

    public void setVoo(Voo voo) {
        this.voo = voo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}

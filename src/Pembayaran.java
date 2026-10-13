// Interface sebagai kontrak metode pembayaran (menerapkan Polymorphism)
public interface Pembayaran {
    void bayar(double total);
    String getNama();
}

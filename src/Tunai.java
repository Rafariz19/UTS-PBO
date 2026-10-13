// Implementasi metode pembayaran Tunai (Cash)
public class Tunai implements Pembayaran {
    private double uangDiterima;
    private double kembalian;

    public Tunai(double uangDiterima) {
        if (uangDiterima < 0) {
            throw new IllegalArgumentException("Uang diterima tidak boleh negatif");
        }
        this.uangDiterima = uangDiterima;
        this.kembalian = 0.0;
    }

    public double getUangDiterima() {
        return uangDiterima;
    }

    public double getKembalian() {
        return kembalian;
    }

    @Override
    public String getNama() {
        return "Tunai";
    }

    // Validasi kecukupan uang tunai
    @Override
    public void bayar(double total) {
        if (uangDiterima < total) {
            throw new IllegalStateException("Uang tunai tidak mencukupi (Diterima: Rp" + String.format("%.0f", uangDiterima) 
                    + ", Total: Rp" + String.format("%.0f", total) + ")");
        }
        this.kembalian = uangDiterima - total;
    }
}

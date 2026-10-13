// Subclass Minuman menerapkan inheritance dari MenuItem
public class Minuman extends MenuItem {
    private String ukuran;    // S, M, atau L
    private boolean dingin;   // true = Dingin, false = Panas

    public Minuman(String kode, String nama, double harga, int stok, String ukuran, boolean dingin) {
        super(kode, nama, harga, stok);
        if (ukuran == null || (!ukuran.equalsIgnoreCase("S") && !ukuran.equalsIgnoreCase("M") && !ukuran.equalsIgnoreCase("L"))) {
            throw new IllegalArgumentException("Ukuran minuman harus S, M, atau L");
        }
        this.ukuran = ukuran.toUpperCase();
        this.dingin = dingin;
    }

    public String getUkuran() {
        return ukuran;
    }

    public boolean isDingin() {
        return dingin;
    }

    @Override
    public String getKategori() {
        return "Minuman";
    }

    // Overriding: hitungHarga disesuaikan dengan biaya tambahan ukuran
    @Override
    public double hitungHarga() {
        double tambahan = 0.0;
        if (ukuran.equals("M")) {
            tambahan = 1500.0;
        } else if (ukuran.equals("L")) {
            tambahan = 3000.0;
        }
        return getHarga() + tambahan;
    }

    // Overriding: getInfo menambahkan informasi ukuran dan suhu (Dingin/Panas)
    @Override
    public String getInfo() {
        String suhu = dingin ? "Dingin" : "Panas";
        return String.format("[%s] %s (%s, %s) - Rp%.0f (stok %d)", 
                getKategori(), getNama(), ukuran, suhu, hitungHarga(), getStok());
    }
}

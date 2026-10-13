// Subclass Makanan menerapkan inheritance dari MenuItem
public class Makanan extends MenuItem {
    private int levelPedas; // Validasi 0 sampai 5

    public Makanan(String kode, String nama, double harga, int stok, int levelPedas) {
        super(kode, nama, harga, stok);
        if (levelPedas < 0 || levelPedas > 5) {
            throw new IllegalArgumentException("Level pedas harus berada dalam rentang 0-5");
        }
        this.levelPedas = levelPedas;
    }

    public int getLevelPedas() {
        return levelPedas;
    }

    @Override
    public String getKategori() {
        return "Makanan";
    }

    // Overriding: hitungHarga disesuaikan dengan biaya tambahan level pedas
    @Override
    public double hitungHarga() {
        return getHarga() + (levelPedas * 1000.0);
    }

    // Overriding: getInfo menambahkan informasi level pedas
    @Override
    public String getInfo() {
        return String.format("[%s] %s (Level Pedas: %d) - Rp%.0f (stok %d)", 
                getKategori(), getNama(), levelPedas, hitungHarga(), getStok());
    }
}

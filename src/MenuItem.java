// Abstract class sebagai blueprint seluruh item menu dalam sistem
public abstract class MenuItem {
    // Encapsulation: semua atribut bersifat private
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    // Konstruktor dengan validasi business rules
    public MenuItem(String kode, String nama, double harga, int stok) {
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari 0");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getStok() {
        return stok;
    }

    // Akses protected agar subclass dapat mengakses harga dasar untuk perhitungan
    protected double getHarga() {
        return harga;
    }

    // Method untuk mengurangi stok saat pesanan dibayar
    public void kurangiStok(int jumlah) {
        if (jumlah > stok) {
            throw new IllegalStateException("Stok tidak mencukupi untuk dikurangi");
        }
        this.stok -= jumlah;
    }

    // Abstract methods yang wajib diimplementasikan oleh subclass
    public abstract String getKategori();
    public abstract double hitungHarga();

    // Template method representasi informasi menu
    public String getInfo() {
        return String.format("[%s] %s - Rp%.0f (stok %d)", getKategori(), nama, hitungHarga(), stok);
    }
}

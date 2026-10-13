// Class representasi item menu dan kuantitas dalam suatu pesanan
public class ItemPesanan {
    // Relasi asosiasi dengan MenuItem
    private MenuItem menu;
    private int jumlah;

    public ItemPesanan(MenuItem menu, int jumlah) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu tidak boleh null");
        }
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah item harus lebih dari 0");
        }
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public MenuItem getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    // Menghitung subtotal item (harga setelah modifikasi subclass * kuantitas)
    public double getSubtotal() {
        return menu.hitungHarga() * jumlah;
    }
}

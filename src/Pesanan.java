import java.util.ArrayList;
import java.util.List;

// Class representasi transaksi pesanan (menerapkan komposisi dengan ItemPesanan)
public class Pesanan {
    private String idPesanan;
    private Pelanggan pelanggan;
    private List<ItemPesanan> daftarItem; // Komposisi
    private StatusPesanan status;

    public Pesanan(String idPesanan, Pelanggan pelanggan) {
        if (idPesanan == null || idPesanan.trim().isEmpty()) {
            throw new IllegalArgumentException("ID Pesanan tidak boleh kosong");
        }
        if (pelanggan == null) {
            throw new IllegalArgumentException("Pelanggan tidak boleh null");
        }
        this.idPesanan = idPesanan;
        this.pelanggan = pelanggan;
        this.daftarItem = new ArrayList<>();
        this.status = StatusPesanan.DIBUAT;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public List<ItemPesanan> getDaftarItem() {
        return daftarItem;
    }

    public StatusPesanan getStatus() {
        return status;
    }

    // Menambah item ke pesanan dengan validasi status, jumlah, dan stok
    public void tambahItem(MenuItem menu, int jumlah) {
        if (this.status != StatusPesanan.DIBUAT) {
            throw new IllegalStateException("Item hanya dapat ditambahkan saat pesanan berstatus DIBUAT");
        }
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah pesanan harus lebih dari 0");
        }
        if (jumlah > menu.getStok()) {
            throw new IllegalStateException("Stok menu '" + menu.getNama() + "' tidak cukup (Tersedia: " 
                    + menu.getStok() + ", Diminta: " + jumlah + ")");
        }
        daftarItem.add(new ItemPesanan(menu, jumlah));
    }

    // Menghitung subtotal sebelum potongan diskon
    public double getSubtotal() {
        double subtotal = 0.0;
        for (ItemPesanan item : daftarItem) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    // Menghitung nominal diskon berdasarkan tingkat member pelanggan
    public double getDiskon() {
        return getSubtotal() * pelanggan.getTingkat().getPersenDiskon();
    }

    // Menghitung total akhir yang harus dibayar
    public double getTotal() {
        return getSubtotal() - getDiskon();
    }

    // Melakukan pembayaran dengan polymorphism pada interface Pembayaran
    public void bayar(Pembayaran metode) {
        if (daftarItem.isEmpty()) {
            throw new IllegalStateException("Pesanan kosong tidak dapat dibayar");
        }
        if (this.status != StatusPesanan.DIBUAT) {
            throw new IllegalStateException("Pesanan tidak dapat dibayar karena berstatus: " + this.status);
        }

        double total = getTotal();
        // Pemanggilan polymorphic pada objek implementasi Pembayaran (Tunai / EWallet)
        metode.bayar(total);

        // Mengurangi stok setiap menu yang dibeli
        for (ItemPesanan item : daftarItem) {
            item.getMenu().kurangiStok(item.getJumlah());
        }

        // Fitur reward: penambahan poin loyalitas (1 poin per kelipatan Rp 10.000)
        int poinReward = (int) (total / 10000.0);
        pelanggan.tambahPoin(poinReward);

        this.status = StatusPesanan.DIBAYAR;
    }

    // State transition: DIBAYAR -> SELESAI
    public void selesaikan() {
        if (this.status != StatusPesanan.DIBAYAR) {
            throw new IllegalStateException("Pesanan tidak dapat diselesaikan karena belum dibayar (Status saat ini: " + this.status + ")");
        }
        this.status = StatusPesanan.SELESAI;
    }

    // State transition: DIBUAT -> DIBATALKAN
    public void batalkan() {
        if (this.status != StatusPesanan.DIBUAT) {
            throw new IllegalStateException("Pesanan yang sudah dibayar atau diproses tidak dapat dibatalkan (Status saat ini: " + this.status + ")");
        }
        this.status = StatusPesanan.DIBATALKAN;
    }

    // Mencetak struk rincian pesanan
    public void cetakStruk() {
        System.out.println("==================== STRUK PESANAN ====================");
        System.out.println("ID Pesanan : " + idPesanan);
        System.out.println("Pelanggan  : " + pelanggan.getNama() + " (" + pelanggan.getTingkat() + ")");
        System.out.println("-------------------------------------------------------");
        System.out.println("Daftar Item:");
        for (ItemPesanan item : daftarItem) {
            System.out.printf(" - %-28s x%-2d = Rp%,10.0f%n", 
                    item.getMenu().getNama(), 
                    item.getJumlah(), 
                    item.getSubtotal());
        }
        System.out.println("-------------------------------------------------------");
        System.out.printf("Subtotal   : Rp%,10.0f%n", getSubtotal());
        System.out.printf("Diskon     : Rp%,10.0f (%s - %.0f%%)%n", 
                getDiskon(), pelanggan.getTingkat(), pelanggan.getTingkat().getPersenDiskon() * 100);
        System.out.printf("TOTAL      : Rp%,10.0f%n", getTotal());
        System.out.println("Status     : " + status);
        System.out.println("=======================================================");
    }
}

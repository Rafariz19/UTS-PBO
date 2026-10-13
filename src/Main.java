import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("                 SISTEM PEMESANAN MAKANAN BERBASIS OOP JAVA                     ");
        System.out.println("================================================================================\n");

        // Inisialisasi Data Menu
        Makanan nasiGoreng = new Makanan("M01", "Nasi Goreng", 15000, 10, 2);
        Makanan ayamGeprek = new Makanan("M02", "Ayam Geprek", 18000, 2, 4);
        Minuman esTeh = new Minuman("N01", "Es Teh Manis", 4000, 20, "M", true);
        Minuman jusJeruk = new Minuman("N02", "Jus Jeruk", 10000, 10, "L", true);
        Minuman kopiHitam = new Minuman("N03", "Kopi Hitam", 8000, 10, "S", false);

        // Inisialisasi Data Pelanggan
        Pelanggan budi = new Pelanggan("P01", "Budi", 0);
        Pelanggan siti = new Pelanggan("P02", "Siti", 60);
        Pelanggan andi = new Pelanggan("P03", "Andi", 120);

        // ==============================================================================
        // SKENARIO 1: Demo Polymorphism pada Menu
        // ==============================================================================
        System.out.println(">>> SKENARIO 1: Daftar Menu Restoran (Demo Polymorphism getInfo)");
        List<MenuItem> daftarMenu = new ArrayList<>();
        daftarMenu.add(nasiGoreng);
        daftarMenu.add(ayamGeprek);
        daftarMenu.add(esTeh);
        daftarMenu.add(jusJeruk);
        daftarMenu.add(kopiHitam);

        for (MenuItem menu : daftarMenu) {
            // Pemanggilan method getInfo() secara polymorphic (memanggil implementasi subclass masing-masing)
            System.out.println(" - " + menu.getInfo());
        }
        System.out.println();

        // ==============================================================================
        // SKENARIO 2: Transaksi Pelanggan Budi (Pembayaran Tunai)
        // ==============================================================================
        System.out.println(">>> SKENARIO 2: Pemesanan Budi (Pembayaran Tunai)");
        Pesanan pesananBudi = new Pesanan("ORD-001", budi);
        pesananBudi.tambahItem(nasiGoreng, 2);
        pesananBudi.tambahItem(esTeh, 2);

        Tunai tunaiBudi = new Tunai(100000);
        pesananBudi.bayar(tunaiBudi);

        pesananBudi.cetakStruk();
        System.out.println("Metode Bayar : " + tunaiBudi.getNama());
        System.out.printf("Uang Tunai   : Rp%,10.0f%n", tunaiBudi.getUangDiterima());
        System.out.printf("Kembalian    : Rp%,10.0f%n", tunaiBudi.getKembalian());
        System.out.println("Poin Budi    : " + budi.getPoin() + " (Tingkat: " + budi.getTingkat() + ")\n");

        // ==============================================================================
        // SKENARIO 3: Transaksi Pelanggan Siti (Pembayaran E-Wallet GoPay)
        // ==============================================================================
        System.out.println(">>> SKENARIO 3: Pemesanan Siti (Pembayaran E-Wallet GoPay)");
        Pesanan pesananSiti = new Pesanan("ORD-002", siti);
        pesananSiti.tambahItem(ayamGeprek, 1);
        pesananSiti.tambahItem(jusJeruk, 1);

        EWallet gopaySiti = new EWallet("GoPay", 100000);
        pesananSiti.bayar(gopaySiti);

        pesananSiti.cetakStruk();
        System.out.println("Metode Bayar : " + gopaySiti.getNama());
        System.out.printf("Sisa Saldo   : Rp%,10.0f%n", gopaySiti.getSaldo());
        System.out.println("Poin Siti    : " + siti.getPoin() + " (Tingkat: " + siti.getTingkat() + ")\n");

        // ==============================================================================
        // SKENARIO 4: Transaksi Pelanggan Andi (Pembayaran E-Wallet OVO & Selesaikan)
        // ==============================================================================
        System.out.println(">>> SKENARIO 4: Pemesanan Andi (Pembayaran E-Wallet OVO & Selesaikan)");
        Pesanan pesananAndi = new Pesanan("ORD-003", andi);
        pesananAndi.tambahItem(nasiGoreng, 3);
        pesananAndi.tambahItem(kopiHitam, 2);

        EWallet ovoAndi = new EWallet("OVO", 200000);
        pesananAndi.bayar(ovoAndi);
        pesananAndi.selesaikan();

        pesananAndi.cetakStruk();
        System.out.println("Metode Bayar : " + ovoAndi.getNama());
        System.out.printf("Sisa Saldo   : Rp%,10.0f%n", ovoAndi.getSaldo());
        System.out.println("Poin Andi    : " + andi.getPoin() + " (Tingkat: " + andi.getTingkat() + ")");
        System.out.println("Status Akhir : " + pesananAndi.getStatus() + "\n");

        // ==============================================================================
        // SKENARIO 5: Pengujian Validasi Business Rules & Exception Handling
        // ==============================================================================
        System.out.println(">>> SKENARIO 5: Uji Validasi Business Rules (Try-Catch Exception Handling)");

        // 1. Uji Stok tidak cukup (Ayam Geprek awal stok 2, dibeli Siti 1, sisa 1; diminta 5)
        try {
            Pesanan tesStok = new Pesanan("ORD-FAIL-1", budi);
            tesStok.tambahItem(ayamGeprek, 5);
        } catch (Exception e) {
            System.out.println("[DITOLAK] Stok tidak cukup -> " + e.getMessage());
        }

        // 2. Uji Jumlah 0
        try {
            Pesanan tesJumlahNol = new Pesanan("ORD-FAIL-2", budi);
            tesJumlahNol.tambahItem(nasiGoreng, 0);
        } catch (Exception e) {
            System.out.println("[DITOLAK] Jumlah 0 -> " + e.getMessage());
        }

        // 3. Uji Pesanan kosong dibayar
        try {
            Pesanan tesKosong = new Pesanan("ORD-FAIL-3", budi);
            tesKosong.bayar(new Tunai(50000));
        } catch (Exception e) {
            System.out.println("[DITOLAK] Pesanan kosong dibayar -> " + e.getMessage());
        }

        // 4. Uji Saldo E-Wallet tidak cukup untuk 2 Jus Jeruk
        try {
            Pesanan tesSaldoKurang = new Pesanan("ORD-FAIL-4", siti);
            tesSaldoKurang.tambahItem(jusJeruk, 2);
            EWallet danaKurang = new EWallet("DANA", 5000);
            tesSaldoKurang.bayar(danaKurang);
        } catch (Exception e) {
            System.out.println("[DITOLAK] Saldo EWallet(\"DANA\",5000) kurang untuk 2 Jus Jeruk -> " + e.getMessage());
        }

        // 5. Uji Batalkan pesanan Budi yang sudah dibayar
        try {
            pesananBudi.batalkan();
        } catch (Exception e) {
            System.out.println("[DITOLAK] Batalkan pesanan Budi yang sudah dibayar -> " + e.getMessage());
        }

        System.out.println("\n================================================================================");
        System.out.println("                      SEMUA SKENARIO BERHASIL DIJALANKAN                        ");
        System.out.println("================================================================================");
    }
}

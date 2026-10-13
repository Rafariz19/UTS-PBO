// Class untuk menyimpan entitas Pelanggan beserta poin dan tingkat membership
public class Pelanggan {
    private String id;
    private String nama;
    private int poin;

    public Pelanggan(String id, String nama, int poin) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pelanggan tidak boleh kosong");
        }
        if (poin < 0) {
            throw new IllegalArgumentException("Poin awal tidak boleh negatif");
        }
        this.id = id;
        this.nama = nama;
        this.poin = poin;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getPoin() {
        return poin;
    }

    // Business rule penentuan tingkat membership berdasarkan poin reward
    public TingkatMember getTingkat() {
        if (poin >= 100) {
            return TingkatMember.GOLD;
        } else if (poin >= 50) {
            return TingkatMember.SILVER;
        } else {
            return TingkatMember.REGULER;
        }
    }

    // Menambah perolehan poin dari transaksi yang berhasil
    public void tambahPoin(int tambahan) {
        if (tambahan > 0) {
            this.poin += tambahan;
        }
    }
}

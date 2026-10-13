// Enum untuk tingkat keanggotaan pelanggan beserta persentase diskon yang diperoleh
public enum TingkatMember {
    REGULER(0.0),
    SILVER(0.05),
    GOLD(0.10);

    // Persentase diskon yang berhak diterima member
    private final double persenDiskon;

    // Konstruktor enum
    TingkatMember(double persenDiskon) {
        this.persenDiskon = persenDiskon;
    }

    // Getter untuk persentase diskon
    public double getPersenDiskon() {
        return persenDiskon;
    }
}

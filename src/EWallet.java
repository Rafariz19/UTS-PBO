// Implementasi metode pembayaran non-tunai Dompet Digital (E-Wallet)
public class EWallet implements Pembayaran {
    private String provider;
    private double saldo;

    public EWallet(String provider, double saldo) {
        if (provider == null || provider.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider e-wallet tidak boleh kosong");
        }
        if (saldo < 0) {
            throw new IllegalArgumentException("Saldo awal e-wallet tidak boleh negatif");
        }
        this.provider = provider;
        this.saldo = saldo;
    }

    public String getProvider() {
        return provider;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public String getNama() {
        return "E-Wallet " + provider;
    }

    // Validasi kecukupan saldo e-wallet
    @Override
    public void bayar(double total) {
        if (saldo < total) {
            throw new IllegalStateException("Saldo " + getNama() + " tidak mencukupi (Saldo: Rp" 
                    + String.format("%.0f", saldo) + ", Total: Rp" + String.format("%.0f", total) + ")");
        }
        this.saldo -= total;
    }
}

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private int num;
    private int amount;
    private Hash prevHash;
    private long nonce;
    private Hash hash;

    private static byte[] intToBytes(int x) {
        return ByteBuffer.allocate(4).putInt(x).array();
    }

    private static byte[] longToBytes(long x) {
        return ByteBuffer.allocate(8).putLong(x).array();
    }

    private void mine() throws NoSuchAlgorithmException {
        long nonce = 0;
        while (true) {
            Hash candidate = calculateHash(num, amount, prevHash, nonce);
            if (candidate.isValid()) {
                this.nonce = nonce;
                this.hash  = candidate;
                return;
            } else {
                nonce += 1;
            }
        }
    }

    private Hash calculateHash(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(intToBytes(num));
        md.update(intToBytes(amount));
        if (prevHash != null) { md.update(prevHash.getData()); }
        md.update(longToBytes(nonce));
        return new Hash(md.digest());
    }

    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        mine();
    }

    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.hash = calculateHash(num, amount, prevHash, nonce);
    }

    public int getNum()       { return num; }
    public int getAmount()    { return amount; }
    public long getNonce()    { return nonce; }
    public Hash getPrevHash() { return prevHash; }
    public Hash getHash()     { return hash; }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(String.format(
            "Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)",
            num, amount, nonce, prevHash, hash));
        return buf.toString();
    }
}

import java.nio.ByteBuffer;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	private int num;
	private String inchiKey;
	public String prevHash;
	private long nonce;
	public String hash;
	private long timeStamp;
	/*
    private static byte[] intToBytes(int x) {
        return ByteBuffer.allocate(4).putInt(x).array();
    }

    private static byte[] longToBytes(long x) {
        return ByteBuffer.allocate(8).putLong(x).array();
    }

    private void mine() throws NoSuchAlgorithmException {
        long nonce = 0;
        while (true) {
            Hash candidate = calculateHash(num, inchiKey, prevHash, nonce);
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
    }*/

	public Block(int num, String inchiKey, String prevHash) throws NoSuchAlgorithmException {
		this.num = num;
		this.inchiKey = inchiKey;
		this.prevHash = prevHash;
		// mine();
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}

	/*  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.inchiKey = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.hash = calculateHash(num, amount, prevHash, nonce);
    }*/

	public int getNum()       { return num; }
	public String getInchiKey()    { return inchiKey; }
	public long getNonce()    { return nonce; }
	public String getPrevHash() { return prevHash; }
	public String getHash()     { return hash; }

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(String.format(
				"Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)",
				num, inchiKey, nonce, prevHash, hash));
		return buf.toString();
	}
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				this.prevHash +
				Long.toString(this.timeStamp) +
				this.inchiKey
				);
		return calculatedhash;
	}
	
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
}

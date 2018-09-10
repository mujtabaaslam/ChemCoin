import java.nio.ByteBuffer;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	private int num;
	private String owner;
	private String inchiKey;
	public String prevHash;
	private long nonce;
	public String hash;
	private long timeStamp;


	public Block(int num, String owner, String inchiKey, String prevHash) throws NoSuchAlgorithmException {
		this.num = num;
		this.owner = owner;
		this.inchiKey = inchiKey;
		this.prevHash = prevHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}

	public int getNum()       { return num; }
	public String getowner()   { return owner; }
	public String getInchiKey()    { return inchiKey; }
	public long getNonce()    { return nonce; }
	public String getPrevHash() { return prevHash; }
	public String getHash()     { return hash; }

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(String.format(
				"Block %d (InchiKey: %s, Owner: %s, Nonce: %d, prevHash: %s, hash: %s)",
				num, inchiKey, owner, nonce, prevHash, hash));
		return buf.toString();
	}
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				this.prevHash +
				Long.toString(this.timeStamp) +
				this.inchiKey + this.nonce
				);
		return calculatedhash;
	}
	
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!this.hash.substring( 0, difficulty).equals(target)) {
			this.nonce ++;
			this.hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + this.hash);
	}
	
}

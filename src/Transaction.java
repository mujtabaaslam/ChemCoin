import java.security.*;
import java.util.ArrayList;

public class Transaction {
	public String transactionId; // this is also the hash of the transaction
	public PublicKey sender; // senders address/public key
	public PublicKey receiver; // receivers address/public key
	public float value;
	public byte[] signature; // to prevent anyone else from spending funds in our wallet
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int count = 0; // count of how many transactions have gone through
	
	public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.receiver = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	private String calculateHash() {
		this.count++;
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(receiver)+
				Float.toString(value) + count);
	}
	
	// Signs all the data we don't wish to be tampered with
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value);
		this.signature = StringUtil.applyECDSASig(privateKey, data);
	}
	
	// Verifies the data signed hasn't been tampered with
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

}

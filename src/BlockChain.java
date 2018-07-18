import java.security.NoSuchAlgorithmException;

public class BlockChain {
	public static int difficulty = 5;
	
    private static class Node {
        public Block data;
        public Node next;

        public Node (Block data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node first;
    private Node last;

    public BlockChain(String inchi) throws NoSuchAlgorithmException {
        first = new Node(new Block(0, inchi, null));
        first.data.mineBlock(difficulty);
        last  = first;
    }

   /* public Block mine(int amount) throws NoSuchAlgorithmException {
        return new Block(last.data.getNum() + 1, amount, last.data.getHash());
    }
*/
    public int getSize() {
        return last.data.getNum() + 1;
    }

    public void append(Block blk) {
        if (!blk.getPrevHash().equals(last.data.getHash())) {
            throw new IllegalArgumentException();
        } else {
            Node n = new Node(blk);
            blk.mineBlock(difficulty);
            last.next = n;
            last = n;
        }
    }

    public boolean removeLast() {
        if (last.data.getNum() != 0) {
            Node cur = first;
            while (cur.next.next != null) {
                cur = cur.next;
            }
            last = cur;
            last.next = null;
            return true;
        } else {
            return false;
        }
    }

    public String getHash() {
        return last.data.getHash();
    }

    public boolean isValidBlockChain() {
        Node cur = first;
        while (cur.next != null) {
        	if(!cur.data.hash.equals(cur.data.calculateHash()) ){
    			System.out.println("Current Hashes not equal");			
    			return false;
    		} if (cur != first) {
    			String prevBlockHash = cur.data.hash;
    			cur = cur.next; 
    		//compare previous hash and registered previous hash
    		if(!prevBlockHash.equals(cur.data.prevHash) ) {
    			System.out.println("Previous Hashes not equal");
    			return false;
    		}
        } else {
        		cur = cur.next;
        }
        }
        return true;
    }


    public String toString() {
        StringBuffer buf = new StringBuffer();
        Node cur = first;
        buf.append(cur.data);
        while (cur.next != null) {
            cur = cur.next;
            buf.append("\n");
            buf.append(cur.data);
        }
        return buf.toString();
    }
    
    public Boolean search(String inchi) {
    	return searchH(inchi, first);
    }
    
    public Boolean searchH(String inchi, Node root) {
    	System.out.println(root.data.getInchiKey());
    if (root.data.getInchiKey().equals(inchi)){
    	return true;
    }
    else if (root.equals(last)) {
    	return false;
    }
    else {
    	return searchH(inchi, root.next);
    }
    }
}

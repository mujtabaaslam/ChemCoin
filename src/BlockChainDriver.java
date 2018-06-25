import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

//import com.google.gson.GsonBuilder;

public class BlockChainDriver {
    public static void printUsage() {
        System.out.println("Usage: BlockChainDriver <amount>");
    }

    public static void printCommands() {
        System.out.println("Valid commands: ");
        System.out.println("    mine: discovers the nonce for a given transaction");
        System.out.println("    append: appends a new block onto the end of the chain");
        System.out.println("    remove: removes the last block from the end of the chain");
        System.out.println("    check: checks that the block chain is valid");
        System.out.println("    help: prints this list of commands");
        System.out.println("    quit: quits the program");
    }

    public static String promptFor(Scanner in, String msg) {
        System.out.print(msg + " ");
        return in.nextLine();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
    	
    	BlockChain blockchain = new BlockChain("hi im a tidepod");
    	System.out.println(blockchain.toString());
    	blockchain.append(new Block(1, "tidepod", blockchain.getHash()));
    	System.out.println(blockchain.toString());
    	blockchain.append(new Block(2, "tidepod1", blockchain.getHash()));
    	System.out.println(blockchain.toString());
    	blockchain.append(new Block(3, "tidepod2", blockchain.getHash()));
    	System.out.println(blockchain.toString());
    	blockchain.append(new Block(4, "tidepod3", blockchain.getHash()));
    	System.out.println(blockchain.toString());
   
    	
   
		//String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
		//System.out.println(blockchainJson);
    	
        /*if (args.length != 1) {
            printUsage();
        } else {
            BlockChain chain = new BlockChain(Integer.parseInt(args[0]));
            Scanner in = new Scanner(System.in);
            boolean isRunning = true;
            while (isRunning) {
                System.out.println(chain.toString());
                String resp = promptFor(in, "Command?");
                if (resp.equals("mine")) {
                    int amt = Integer.parseInt(promptFor(in, "Amount transferred?"));
                    Block blk = chain.mine(amt);
                    System.out.printf("amount = %d, nonce = %d\n", amt, blk.getNonce());
                } else if (resp.equals("append")) {
                    int amt    = Integer.parseInt(promptFor(in, "Amount transferred?"));
                    long nonce = Long.parseLong(promptFor(in, "Nonce?"));
                    Block blk = new Block(chain.getSize(), amt, chain.getHash());
                    if (blk.getHash().isValid()) {
                        chain.append(blk);
                    } else {
                        System.out.printf("Error: invalid nonce (produces %s)\n", blk.getHash());
                    }
                } else if (resp.equals("remove")) {
                    if (!chain.removeLast()) {
                        System.out.println("Error: blockchain contains no transactions");
                    }
                } else if (resp.equals("check")) {
                    if (chain.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    } else {
                        System.out.println("Chain is invalid!");
                    }
                } else if (resp.equals("report")) {
                    chain.printBalances();
                } else if (resp.equals("help")) {
                    printCommands();
                } else if (resp.equals("quit")) {
                    isRunning = false;
                } else {
                    System.out.printf("Error: \"%s\" is not a valid command\n", resp);
                }
                System.out.println();
            }
        }*/
    }
}

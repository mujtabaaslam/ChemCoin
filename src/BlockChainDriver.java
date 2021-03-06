import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class BlockChainDriver {
    public static void printUsage() {
        System.out.println("Usage: BlockChainDriver <string> <string>");
    }

    public static void printCommands() {
        System.out.println("Valid commands: ");
        System.out.println("    append: appends a new block onto the end of the chain");
        System.out.println("    remove: removes the last block from the end of the chain");
        System.out.println("    check: checks that the block chain is valid");
        System.out.println("    help: prints this list of commands");
        System.out.println("    search: searches the blockchain for an inchi");
        System.out.println("    quit: quits the program");
    }

    public static String promptFor(Scanner in, String msg) {
        System.out.print(msg + " ");
        return in.nextLine();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (args.length != 2) {
            printUsage();
        } else {
            BlockChain chain = new BlockChain(args[0], args[1]);
            Scanner in = new Scanner(System.in);
            boolean isRunning = true;
            while (isRunning) {
                System.out.println(chain.toString());
                String resp = promptFor(in, "Command?");
                  if (resp.equals("append")) {
                    String inchiKey = (promptFor(in, "Please enter the Inchi Key"));
                    String owner = (promptFor(in, "Who is the owner for this chemical structure?"));
                    Block blk = new Block(chain.getSize(), owner, inchiKey, chain.getHash());
                        chain.append(blk);
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
                }  else if (resp.equals("help")) {
                    printCommands();
                } else if (resp.equals("quit")) {
                    isRunning = false;
                } else if (resp.equals("search")) {
                	String inchi = promptFor(in, "Inchi?");
                	boolean exists = chain.search(inchi);
                	if(exists) {
                		System.out.printf("Chemical structure with the Inchi Key %s is already in the blockchain\n", inchi);
                	} else {
                		System.out.printf("A chemical structure with the Inchi Key %s is not in the blockchain\n", inchi);
                	}
                } else {
                    System.out.printf("Error: \"%s\" is not a valid command\n", resp);
                }
                System.out.println();
            }
        }
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class BlockChain implements Serializable{
    // serialVersionUID for serialization
    private static final long serialVersionUID = 7L;    // Serial version to allow object to be serializable

    // Private data members
    private ArrayList<Block> chain;
    private int currBlock;
    private int N;

    // Default Constructor creates single block
    public BlockChain(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number of zeroes \"N\" to be at start of hash: ");
        N = input.nextInt();
        input.nextLine();
        input.close();

        chain = new ArrayList<>();
        Block FirstBlock = new Block(N); // Create the initial block
        chain.add(FirstBlock);  // Add the initial block to the chain
        currBlock = 0;  // set the current block number to 0 (index in list is zero)
        System.out.println("Created initial Block!");   // Print for clarity
        //System.out.println("currblock: " + currBlock);
    }

    // Method to add a block to the blockchain
    public void addBlock(){
        Block newBlock = new Block(N, this.chain.get(currBlock));  //Using constructor, taking in last block added
        chain.add(newBlock);    // Add the newly create block to the chain
        currBlock++;    // increment the current block number
        //System.out.println("currblock: " + currBlock);
    };

    public int getChainSize(){
        return this.chain.size();
    }
    public Block getBlock(int i){
        return this.chain.get(i);
    }

    // If at first block check that hashPrev is 0. (not really necessary, but doesn't hurt to do)
    // Store the hash of the block
    // Move to next block, if hashPrev is not equal to the currently stored hash, return false
    // Repeat until last block
    public boolean validChain(){
        String hash = "";     // the stored hashPrev from previous block
        //String hashPrev; // the stored hashPrev of the current block being looked at
        for(int i = 0; i < this.chain.size(); i++){
            if(i == 0){
                if (chain.get(i).getPrevHash() != "0"){
                    return false;   // Return false if first block hashPrev is not zero
                }
                hash = chain.get(i).getHash(); // Get hash of first block
            }
            else if(chain.get(i).getPrevHash() != hash){
                return false;
            }
            hash = this.chain.get(i).getHash();
        }
        return true;
    }
}//end class
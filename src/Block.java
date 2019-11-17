import java.util.Date;
import java.time.Instant;
import java.util.Random;
import java.nio.charset.Charset;

// Output hashes to file
// Use linux commands to search for duplicates
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

class Block implements Serializable {
    private static final long serialVersionUID = 4L;

    // Private Data Members
    private int id;
    private long timestamp;
    private String hashPrev;
    private String hashOfBlock;
    private long creationTime;

    // Private Helper Function
    // We want to generate a hash with N starting zeroes
    private void generateHash(int N){
        Instant instant1 = Instant.now();   // Get time before block generates
        Random random = new Random();
        byte[] bytes = new byte[256]; // length is bounded by 7
        String randString = "";
        String zeroes = "";
        String hash = "";
        for(int i = 0; i < N; i++){ hash += "x"; }  // need to initialize to string size N, to compare with N zeroes
        int c = 0;
        //For File Out
        try{
            File file = new File("out.txt");
            FileWriter out = new FileWriter(file);
            for(int i = 0; i < N; i++){ zeroes += "0"; }

            while(!hash.substring(0,N).equalsIgnoreCase(zeroes)){
                random.nextBytes(bytes);
                randString = new String(bytes, Charset.forName("UTF-8"));
                hash = StringUtil.applySha256(randString);
                out.write(hash  + "\n");
            }
            out.flush();
            out.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        Instant instant2 = Instant.now();   // get time after block done generating
        this.hashOfBlock = hash;
        long t = instant2.getEpochSecond() - instant1.getEpochSecond(); // t2 - t2 = total time generating
        this.creationTime = t;
    }

    // Default Constructor
    public Block(int N){
        this.id = 1;
        this.timestamp = new Date().getTime();   // Timestamp of milliseconds since January 1st 1970
        this.hashPrev = "0";
        this.generateHash(N);
    }

    // Constructor using previous block
    public Block(int N, Block b){
        this.id = b.id+1;
        this.timestamp = new Date().getTime();
        this.hashPrev = b.getHash();
        this.generateHash(N);
    }

    // Manual Constructor
    public Block(int id, String hashPrev, String hashOfBlock){
        this.id = id;
        this.timestamp = new Date().getTime();
        this.hashPrev = hashPrev;
        this.hashOfBlock = StringUtil.applySha256(this.hashPrev);
    }

    // Getters
    public String getHash(){
        return this.hashOfBlock;
    }

    public String getPrevHash(){
        return this.hashPrev;
    }

    public String getCreationTime(){
        return Long.toString(this.creationTime);
    }

    // toString
    public String toString(){
        String out = "";
        out += "Block\n";
        out += "Id: " + this.id + "\n";
        out += "Timestamp: " + this.timestamp + "\n";
        out += "Hash of Previous Block: " + this.hashPrev + "\n";
        out += "Hash of this Block: " + this.hashOfBlock + "\n";
        out += "Block was generating for " + getCreationTime() + " seconds\n";
        return out;
    }
}//end Block class
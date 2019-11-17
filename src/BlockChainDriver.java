import java.io.*;
import java.util.Scanner;

class BlockChainDriver{
    public static void main(String[] args){
        final String fileName = "BlockChain.ser";
        final int numBlocksToAdd = 5;
        BlockChain bc = null;
        boolean makeObject = false;
        try{
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream inObject = new ObjectInputStream(fileIn);
            bc = (BlockChain) inObject.readObject();
            fileIn.close();
            inObject.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Blockchain has not been created yet. Will now make.");
            makeObject = true;
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }
        catch(ClassNotFoundException e){
            System.out.println("BlockChain not found!");
            e.printStackTrace();
        }
        // Make the object if it has not already been previously made
        if(makeObject == true){
            bc = new BlockChain();  // this will create the initial block as well
        }
        else{
            System.out.println("Blockchain already exists, will add onto it now.");
        }
        for(int i = 0; i < numBlocksToAdd-1; i++){
            bc.addBlock();
        }
        for(int i = 0; i < bc.getChainSize(); i++){
            System.out.println(bc.getBlock(i));
        }
        if(bc.validChain()){
            System.out.println("This is a valid blockchain!");
        }

        // Serialize Object
        try{
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(bc);
            fileOut.close();
            objectOut.close();
            System.out.println("Serialized data is saved in /Desktop/HS/BlockChain.ser");
        } catch(IOException e){
            e.printStackTrace();
        }
        //BlockChain bc = new BlockChain();
        // Serialize object at end
    }//end main
}
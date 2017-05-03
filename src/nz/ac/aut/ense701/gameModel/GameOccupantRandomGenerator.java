package nz.ac.aut.ense701.gameModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Class that represent a background process that modify the game data file
 * @author James
 */
public class GameOccupantRandomGenerator implements Runnable{
    
    public static boolean IS_FILE_BEING_USED = true;

    private String fileName;
    private ArrayList<String> newFileContent;
    private Random rnd;
    
    /**
     * Constructor
     * @param fileName Name of the data file
     */
    public GameOccupantRandomGenerator(String fileName){
        this.fileName = fileName;
        this.newFileContent = new ArrayList<String>();
        this.rnd = new Random();
    }
    
    /**
     * Method that read the file and change position of occupants and modify the file
     */
    private void RandomOccupantPosition(){
        try{
            //Read
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            
            StringBuilder allText = new StringBuilder();
            
            String line;
            int gridRow=0;
            int gridCol=0;
            
            int wellingtonRow=0;
            int wellingtonCol=0;
            
            boolean gridShown = false;
            while((line = br.readLine()) != null){
                String[] lineBreaks = line.split(",");
                boolean change = false;
                
                if(!gridShown){
                    gridShown = true;
                    gridRow = Integer.parseInt(lineBreaks[0]);
                    gridCol = Integer.parseInt(lineBreaks[1]);
                }
                
                if(lineBreaks[0].equals("NewPlayer")){
                    change = true;
                    lineBreaks[1] = getRandPosition(gridRow);
                    lineBreaks[2] = getRandPosition(gridCol);
                }else if(lineBreaks[0].length() == 1){
                    if(lineBreaks[0].equals("C")){
                        change = true;
                        lineBreaks[3] = getRandPosition(gridRow);
                        lineBreaks[4] = getRandPosition(gridCol);
                        if(lineBreaks[1].contains("Wellington")){
                            wellingtonRow=Integer.parseInt(lineBreaks[3]);
                            wellingtonCol=Integer.parseInt(lineBreaks[4]);
                        }
                    }else if(lineBreaks[0].equals("Q")){
                        change = true;
                        lineBreaks[3] = Integer.toString(wellingtonRow);
                        lineBreaks[4] = Integer.toString(wellingtonCol);
                    }else{
                        change = true;
                        lineBreaks[3] = getRandPosition(gridRow);
                        lineBreaks[4] = getRandPosition(gridCol);
                    }
                }
                
                
                if(change){
                    line="";
                    for(int i=0; i<lineBreaks.length; i++ ){
                        line += lineBreaks[i]+",";
                    }
                }
                allText.append(line+ "\r\n");
            }
            br.close();
            
            //Write back
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(allText.toString());
            bw.close();
        }catch(FileNotFoundException e){
            System.err.println("Unable to find data file '" + fileName + "'");
        }catch(IOException e){
            System.err.println("Problem encountered processing file.");
        }catch(Exception e){
            System.err.println("Problem encountered while using operation.\n" + e);
        }
    }
    
    /**
     * String representation of random position integer value
     */
    private String getRandPosition(int rowol){
        return Integer.toString(rnd.nextInt(rowol-1));
    }
    
    /**
     * Override method for Thread Start
     */
    @Override
    public void run() {
        while(IS_FILE_BEING_USED){
            try{
                Thread.sleep(500);
            }catch (InterruptedException ex) {
                System.err.println("Problem encountered while running GameOccupantRandomGenerator thread.\n" + ex);
            }
            
        }
        RandomOccupantPosition();
    }


}

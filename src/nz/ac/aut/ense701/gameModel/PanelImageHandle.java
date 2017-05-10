/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * This class represent as a collection of image icons that we use dynamically in the game.
 * @author James
 */
public class PanelImageHandle {
    
    private ImageIcon AUCKLAND;
    private ImageIcon AUCKLAND_FIXED;
    private ImageIcon CHRISTCHURCH;
    private ImageIcon CHRISTCHURCH_FIXED;
    private ImageIcon OAMARU;
    private ImageIcon OAMARU_FIXED;
    private ImageIcon BLUFF;
    private ImageIcon BLUFF_FIXED;
    private ImageIcon WELLINGTON;
    
    private ImageIcon SAND;
    private ImageIcon SAND_OCCUPANT;
    private ImageIcon FOREST;
    private ImageIcon FOREST_OCCUPANT;
    private ImageIcon WETLAND;
    private ImageIcon WETLAND_OCCUPANT;
    private ImageIcon SCRUB;
    private ImageIcon SCRUB_OCCUPANT;
    private ImageIcon WATER;
    private ImageIcon WATER_OCCUPANT;
    private ImageIcon GROUND;
    private ImageIcon GROUND_OCCUPANT;
    
    private ImageIcon PLAYER;
    private ImageIcon PREDATOR;
    
    /**
     * Constructor that sets the image and adjust its size and store it as variable to reuse them without having to resize every time we need icon
     */
    public PanelImageHandle(){
        String root = "src/images/";
        AUCKLAND = getResizedIcon(root+"auckland.png");
        AUCKLAND_FIXED = getResizedIcon(root+"auckland-fixed.png");
        CHRISTCHURCH = getResizedIcon(root+"christchurch.png");
        CHRISTCHURCH_FIXED = getResizedIcon(root+"christchurch-fixed.png");
        OAMARU = getResizedIcon(root+"oamaru.png");
        OAMARU_FIXED = getResizedIcon(root+"oamaru-fixed.png");
        BLUFF = getResizedIcon(root+"bluff.png");
        BLUFF_FIXED = getResizedIcon(root+"bluff-fixed.png");
        WELLINGTON = getResizedIcon(root+"wellington.png");

        SAND = getResizedIcon(root+"sand.png");
        SAND_OCCUPANT = getResizedIcon(root+"sand-occupant.png");
        FOREST = getResizedIcon(root+"forest.png");
        FOREST_OCCUPANT = getResizedIcon(root+"forest-occupant.png");
        WETLAND = getResizedIcon(root+"wetland.png");
        WETLAND_OCCUPANT = getResizedIcon(root+"wetland-occupant.png");
        SCRUB = getResizedIcon(root+"scrub.png");
        SCRUB_OCCUPANT = getResizedIcon(root+"scrub-occupant.png");
        WATER = getResizedIcon(root+"water.png");
        WATER_OCCUPANT = getResizedIcon(root+"water-occupant.png");
        GROUND = getResizedIcon(root+"ground.png");
        GROUND_OCCUPANT = getResizedIcon(root+"ground-occupant.png");

        PLAYER = getResizedIcon(root+"player.png");
        PREDATOR = getResizedIcon(root+"predator.png");
    }
    
    /**
     * Gets the icon needed
     * @param terrain Type of terrain
     * @param occ Array of occupants
     * @param hasOcc whether has occupant
     * @param hasPredator whether has predator
     * @param hasPlayer whether has player
     * @return return ImageIcon
     */
    public ImageIcon getIcon(Terrain terrain,Occupant[] occ, boolean hasOcc, boolean hasPredator, boolean hasPlayer){
        ImageIcon returnImage;
        switch ( terrain )
        {
            case SAND     : 
                returnImage = hasOcc ? SAND_OCCUPANT : SAND;
                break;
            case FOREST   : 
                returnImage = hasOcc ? FOREST_OCCUPANT : FOREST;
                break;
            case WETLAND : 
                returnImage = hasOcc ? WETLAND_OCCUPANT : WETLAND;
                break;
            case SCRUB : 
                returnImage = hasOcc ? SCRUB_OCCUPANT : SCRUB;
                break;
            case WATER    : 
                returnImage = hasOcc ? WATER_OCCUPANT : WATER;
                break;
            default  : 
                returnImage = hasOcc ? GROUND_OCCUPANT : GROUND;
                break;
        }
        

        for(Occupant o : occ){
            if(o instanceof City){
                City city = (City)o;
                CityType cityType = city.getType();
                switch(cityType){
                    case Auckland : 
                        returnImage = city.isFixed() ? AUCKLAND_FIXED : AUCKLAND;
                        break;
                    case Christchurch : 
                        returnImage = city.isFixed() ? CHRISTCHURCH_FIXED : CHRISTCHURCH;
                        break;
                    case Bluff : 
                        returnImage = city.isFixed() ? BLUFF_FIXED : BLUFF;
                        break;
                    case Oamaru : 
                        returnImage = city.isFixed() ? OAMARU_FIXED : OAMARU_FIXED;
                        break;
                    case Wellington : 
                        returnImage = WELLINGTON;
                        break;
                }
                break;
            }
        }
        
        
        if(hasPredator)
            returnImage = PREDATOR;
        if(hasPlayer)
            returnImage = PLAYER;
                    
        return returnImage;
    }
    
    /**
     * Private method that resize the image to suit the panel
     * @param filelocation file location
     * @return return resized ImageIcon
     */
    private ImageIcon getResizedIcon(String filelocation){
        ImageIcon imageIcon = new ImageIcon(filelocation); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(65, 55,  java.awt.Image.SCALE_FAST); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        return imageIcon;
    }
}

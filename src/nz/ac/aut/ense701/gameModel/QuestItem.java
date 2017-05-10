/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

/**
 * Class represent quest item that can be used to fix city that are broken
 * @author James
 * @version May 2017
 */
public class QuestItem extends Item{
    
    /**
     * Constructor that create instance of quest item.
     * This class extends Item
     * @param pos Position
     * @param name String name of item
     * @param description String description
     * @param weight double weight of item
     * @param size double size of item
     */
    public QuestItem(Position pos, String name, String description, double weight, double size) {
        super(pos, name, description,weight, size);
    }

    @Override
    public String getStringRepresentation() {
        return "Q";
    }
        
}

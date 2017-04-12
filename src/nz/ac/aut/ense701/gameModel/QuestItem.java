/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

/**
 *
 * @author James
 */
public class QuestItem extends Item{
    
    
    public QuestItem(Position pos, String name, String description, double weight, double size) {
        super(pos, name, description,weight, size);
    }

    @Override
    public String getStringRepresentation() {
        return "Q";
    }
        
}

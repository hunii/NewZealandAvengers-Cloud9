/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

/**
 *Class that represent medicine which extends Item class
 * @author James
 * @version May 2017
 */
public class Medicine extends Item{

    private double cureEnergy;
    
    
    /**
     * Cosntructor for creating Medicine instance
     * @param pos position
     * @param name name of the medicine
     * @param description string description
     * @param weight double weight
     * @param size double size
     * @param cure  double cure amount
     */
    public Medicine(Position pos, String name, String description, double weight, double size, double cure){
     super(pos, name, description,weight, size);   
     this.cureEnergy = cure;
    }
    
    /**
     * Get cure amount
     * @return double cure amount
     */
    public double getEnergy(){
        return cureEnergy;
    }
    
    @Override
    public String getStringRepresentation() {
        return "M";
    }
    
}

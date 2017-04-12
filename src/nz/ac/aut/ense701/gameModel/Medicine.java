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
public class Medicine extends Item{

    private double cureEnergy;
    
    public Medicine(Position pos, String name, String description, double weight, double size, double cure){
     super(pos, name, description,weight, size);   
     this.cureEnergy = cure;
    }
    
    public double getEnergy(){
        return cureEnergy;
    }
    
    @Override
    public String getStringRepresentation() {
        return "M";
    }
    
}

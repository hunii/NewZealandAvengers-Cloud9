package nz.ac.aut.ense701.gameModel;

/**
 * Predator represents a predator on the island.
 * If more specific behaviour is required for particular predators, descendants 
 * for this class should be created
 * @author AS
 * @version July 2011
 */
public class Predator extends Fauna
{

    private double attackPower;
    
    /**
     * Constructor for objects of class Predator
     * @param pos the position of the predator object
     * @param name the name of the predator object
     * @param description a longer description of the predator object
     */
    public Predator(Position pos, String name, String description, double attack) 
    {
        super(pos, name, description);
        this.attackPower = attack;
    } 
 
    /**
     * Gets the attack power of this predator to reduce the health of player when player land on the same as the predator
     * @return attack rate
     */
    public double getAttackPower(){
        return attackPower;
    }


    @Override
    public String getStringRepresentation() 
    {
        return "P";
    }    
}

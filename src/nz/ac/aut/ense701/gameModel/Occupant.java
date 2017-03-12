package nz.ac.aut.ense701.gameModel;

/**
 * Abstract base class for occupants that inhabit Kiwi Island.
 * 
 * @author AS
 * @version 1.0 - July 2011
 * @version 2.0 - October 2011 - AS - added toString
 */
public abstract class Occupant 
{
    private Position position;
    private final String   name;
    private final String   description;    

    /**
     * Construct an occupant for a known position & name.
     * @param position the position of the occupant
     * @param name the name of the occupant
     * @param description a longer description
     */
    public Occupant(Position position, String name, String description) 
    {
        this.position    = position;
        this.name        = name;
        this.description = description;        
    }
    
    /**
     * Returns the position of the occupant.
     * 
     * @return the position of the occupant
     */    
    public Position getPosition() 
    {
        return this.position;
    }
    
    /**
     * Changes the position of the occupant.
     * 
     * @param newPosition the new position
     */
    public void setPosition(Position newPosition) 
    {
        this.position = newPosition;
    }
    
    /**
     * Gets the occupant's name.
     * 
     * @return the name of the occupant
     */
    public String getName()
    {
        return this.name;
    } 

   /**
    * Gets the description for the item.
    * 
    * @return the description
    */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Returns the occupant's name for display.
     * 
     * @return the occupant's name
     */
    @Override
    public String toString()
    {
        return getName();
    }
    
    
    /**
     * Gets a string representation of the occupant.
     * Used for interpretation of file content
     * 
     * @return the string representation of the occupant
     */
    public abstract String getStringRepresentation();


}

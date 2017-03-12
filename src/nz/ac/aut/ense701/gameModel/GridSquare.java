package nz.ac.aut.ense701.gameModel;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to represent a grid square on the island.
 * 
 * @author AS
 * @version 1.0 - created
 * @version 2.0 - August 2011 - Extended for Stage 2. AS
 */

public class GridSquare 
{
    private static final int MAX_OCCUPANTS = 3;
    private Terrain terrain;
    private boolean visible;
    private boolean explored;
    private Player  player;
    private Set<Occupant> occupants;
  
    /**
     * Creates a new GridSquare instance.
     * @param terrain the terrain of the grid square
     */
    public GridSquare(Terrain terrain) 
    {
        this.terrain   = terrain;
        this.explored  = false;
        this.visible   = false;
        this.occupants = new HashSet<Occupant>(); 
        this.player    = null;        
    }
    
    /**************************************************************************************************
     * Accessor methods
     **************************************************************************************************/
    /**
     * Gets the terrain of the grid square.
     * 
     * @return the terrain of the grid square
     */
    public Terrain getTerrain()
    {
        return terrain;
    }
    
    /**
     * Check if this grid square has already been explored (ie visited by player).
     * 
     * @return true if square has been explored, false if not.
     */
    public boolean isExplored()
    {
        return this.explored;
    }
    
    /**
     * Check if this grid square is visible to the player.
     * Squares are visible if they:
     *      are visitable and adjacent to the player's current position
     *      already explored by the player
     * @return true is the grid square is visible to the player,
     *         false if not.
     */
    public boolean isVisible()
    {
        return this.visible;
    }

    /**
     * Check if the player is in this square.
     * 
     * @return true if the player is in square, false if not.
     */
    public boolean hasPlayer()
    {
        return (this.player != null);
    }
     
    /**
     * Returns a string representation of the occupants.
     * 
     * @return string that combines strings for all occupants
     */
    public String getOccupantStringRepresentation()
    {
        String result = "";
        for(Occupant occupant : occupants)
        {
            result += occupant.getStringRepresentation();
        }
        return result;
    } 
    
    /**
     * Returns a string representation of the terrain.
     * 
     * @return string that represents the terrains
     */
    public String getTerrainStringRepresentation()
    {
        return terrain.getStringRepresentation();
    }    
    
    /**
     * Checks if this grid square contains a specific occupant.
     * 
     * @param occupant the occupant to check
     * @return true if the square contains the occupant, false if not
     */
    public boolean hasOccupant(Occupant occupant) 
    {
        return occupants.contains(occupant);
    }  
 
    /**
     * Gets the occupants of the grid square as an array.
     * 
     * @return the occupants of the grid square as an array
     */
    public Occupant[] getOccupants()
    {
        return occupants.toArray(new Occupant[occupants.size()]); 
    }
    
    /***************************************************************************************************
     * Mutator methods
     ***************************************************************************************************/
    /**
     * Sets the terrain of the grid square.
     * 
     * @param terrain the new terrain 
     * @throws InvalidParameterException if the terrain is null
     */
    public void setTerrain(Terrain terrain)
    {
        if ( terrain == null )
        {
            throw new InvalidParameterException("Terrain cannot be null");
        }
        this.terrain = terrain;
    }     
    
    /**
     * Marks this grid square as explored.
     */
    public void setExplored()
    {
        this.explored = true;
    }  
    
    /**
     * Marks this grid square as being visible to the player.
     */
    public void setVisible()
    {
        this.visible = true;
    }
    
    /**
     * Sets the player on the grid square.
     * 
     * @param player the player on the square
     *               or null if there is no player
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }  
    
    /**
     * Adds an occupant to a GridSquare if the occupant is not already there 
     * and if the capacity of the grid square is not yet exceeded.
     * 
     * @param occupant the occupant to add
     * @return true if successfully added
     */
    public boolean addOccupant(Occupant occupant) 
    {
        boolean success = false;
        boolean validNewOccupant = occupant != null;
        boolean enoughRoom       = occupants.size() < MAX_OCCUPANTS;
        if ( validNewOccupant && enoughRoom ) 
        {
            success = occupants.add(occupant);
        }
        return success;
    }


    /**
     * Removes an occupant if it is on that GridSquare.
     * 
     * @param occupant the occupant to be removed
     * @return true if occupant was removed,
     *         false if it was not in there (or not valid)
     */
    public boolean removeOccupant(Occupant occupant)
    {
        boolean success = false;
        boolean validOccupant = occupant != null;
        if ( validOccupant )
        {
            success = occupants.remove(occupant);
        }
        return success;
    }
    
}

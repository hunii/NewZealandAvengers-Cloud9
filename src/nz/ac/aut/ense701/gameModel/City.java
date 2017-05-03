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
public class City extends Occupant{
    
    private CityType type;
    private boolean isFixed;
    private boolean counted;
    
    public City(Position pos, String name, String description,boolean fixed){
        super(pos, name, description);
        isFixed = fixed;
        CityType cityType = getCityTypeByString(name);
        if(cityType != null)
            type = cityType;
    }
    
    public CityType getType(){
        return type;
    }
    
    public void fix(){
        isFixed = true;
    }
    
    /**
    * Count this city
    */
    public void count() {
        counted = true;
    }
    
   /**
    * Has this city been counted
    * @return true if counted.
    */
    public boolean counted() {
        return counted;
    }
    
    private CityType getCityTypeByString(String cityName){
        
        if(cityName.contains("Auckland")){
            return CityType.Auckland;
        }else if(cityName.contains("Wellington")){
            return CityType.Wellington;
        }else if(cityName.contains("Christchurch")){
            return CityType.Christchurch;
        }else if(cityName.contains("Oamaru")){
            return CityType.Oamaru;
        }else if(cityName.contains("Bluff")){
            return CityType.Bluff;
        }
        
        return null;
    }
    @Override
    public String getStringRepresentation() {
        return "C";
    }
    
}

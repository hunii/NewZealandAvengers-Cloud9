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
    
    public City(Position pos, String name, String description, boolean fixed){
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
    
    private CityType getCityTypeByString(String cityName){
        
        if(cityName.equals("Auckland")){
            return CityType.Auckland;
        }else if(cityName.equals("Wellington")){
            return CityType.Wellington;
        }else if(cityName.equals("Christchurch")){
            return CityType.Christchurch;
        }else if(cityName.equals("Oamaru")){
            return CityType.Oamaru;
        }else if(cityName.equals("Bluff")){
            return CityType.Bluff;
        }
        
        return null;
    }
    @Override
    public String getStringRepresentation() {
        return "C";
    }
    
}

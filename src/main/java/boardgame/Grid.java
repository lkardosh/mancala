package boardgame;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

public  class Grid{
    private int height;
    private int width;
    private ArrayList<String> data;


    public Grid(final int wide, final int tall){
            setWidth(wide);
            setHeight(tall);
            emptyGrid();
    }

    private void setWidth(final int wide){
        width = wide;
    }
    

    private void setHeight(final int tall){
        height = tall;
    }
    
    /** 
     * accessor method for width of grid.
     * @return int width of grid.
     */
    public int getWidth(){
        return width;
    }
    
    /** 
     * Accessor method for height of grid.
     * @return int  height of grid.
     */
    public int getHeight(){
        return height;
    }

    /** 
     * Empties the grid and sets all positions to a space
     * 
     */

    public  void emptyGrid(){
            data = new ArrayList<>();
            for(int i=0; i<width*height; i++){
                data.add(" "); //empty grid
            }
    }

    
    /** 
     * Sets the value at the specified position.  Grid is expecting positions 1 based.
     * @param across  the position across.
     * @param down   the position down.
     * @param val  String representation of the value.
     */
    public void setValue(final int across, final int down, final String val){
            final int position = (down-1)*width + (across-1);
            data.set(position,val);

    }

    
    /** 
     * Sets the value at the specified position.  Grid is expecting positions 1 based.
     * @param across  the position across.
     * @param down   the position down.
     * @param val  Integer representation of the value.
     */
    public void setValue(final int across, final int down, final int val){
        final int position = (down-1)*width + (across-1);
        data.set(position,String.valueOf(val));

}
    

    protected Iterator<String> iterator(){
        return data.iterator();
    }

    
    /** 
     * Returns the string representation of the grid position indicated
     * by the parameters
     * @param across  position across.
     * @param down  position down.
     * @return String   String value that is at the specified position.
     */
    public String getValue(final int across, final int down){
            final int position = (down-1)*width + (across-1);
            return data.get(position);
    }

    
    /** 
     * Returns a formatted string representation of the grid.
     * The same string is returned by toString()
     * @return String
     */
    public String getStringGrid(){
        String toPrint ="";
 
        int i=0;
        for(final String c: data){
            toPrint = toPrint + " "+ c;
            i++;
            if(i == width){
                toPrint = toPrint + "\n";
                i = 0;
            }

        }
        return toPrint;
    }



    
    /** 
     * wrapper around getStringGrid
     * @return String
     */
    @Override
    public String toString(){
        return getStringGrid();
    }
}





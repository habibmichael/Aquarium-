import java.awt.*;
import java.awt.event.*;

public class Aquarium extends Frame{
    
    //Background and Fish images
    Image aquariumImage;
    Image[] fishImages= new Image[2];
    
    MediaTracker tracker;
    
   Aquarium(){
     
     //Title for Window
     setTitle("The Aquarium");
     
     tracker=new MediaTracker(this);
     
     fishImages[0]=Toolkit.getDefaultToolkit().getImage("fish1.gif");
     tracker.addImage(fishImages[0],0);
     
     fishImages[1]=Toolkit.getDefaultToolkit().getImage("fish2.gif");
     tracker.addImage(fishImages[0],0);
     
     aquariumImage=Toolkit.getDefaultToolkit().getImage("bubbles.gif");
     tracker.addImage(aquariumImage,0);
     
     try{
    	 tracker.waitForID(0);
    	 
     }catch (Exception ex){
    	 System.out.println(ex.getMessage());
     }
     
     //Catch Event of User clicking Exit Button
     this.addWindowListener(new WindowAdapter(){
         public void windowClosing(
            WindowEvent windowEvent){
             System.exit(0);
         }
     });
     
     
}
   
   public static void main(String[] args){
      
      new Aquarium();
      
     }
}

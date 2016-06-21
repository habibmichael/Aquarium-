import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Math;

public class Aquarium extends Frame implements Runnable{
    
    //Background and Fish images
    Image aquariumImage,memoryImage;
    Image[] fishImages= new Image[2];
    int numberFish=12;
    Vector<Fish> fishes= new Vector<Fish>();
    int sleepTime=120;
    boolean runOK=true;
    
    Thread thread;
    
    Graphics memoryGraphics;
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
     
     //Set Window size to background Image, Show Window
     setSize(aquariumImage.getWidth(this),aquariumImage.getHeight(this));
     setResizable(false);
     setVisible(true);
     
     memoryImage=createImage(getSize().width,getSize().height);
     memoryGraphics=memoryImage.getGraphics();
     
     thread=new Thread(this);
     thread.start();
     
     
     //Catch Event of User clicking Exit Button
     this.addWindowListener(new WindowAdapter(){
         public void windowClosing(
            WindowEvent windowEvent){
            runOK=false;//thread clean up
             System.exit(0);
         }
     });
     
     
}
public void update(Graphics graphics){
   memoryGraphics.drawImage(aquariumImage,0,0,this);
   
   for(int i=0;i<numberFish;i++){
     ((Fish)fishes.elementAt(i)).drawFishImage(memoryGraphics);      
     }
      
      graphics.drawImage(memoryImage,0,0,this);
  
}

public void run(){

   //Boundaries that fish swim in
   Rectangle edges = new Rectangle(0 + getInsets().left, 0
   + getInsets().top, getSize().width - (getInsets().left
   + getInsets().right), getSize().height - (getInsets().top
   + getInsets().bottom));
   
   
   for (int i= 0; i < numberFish;i++){
    
            fishes.add(new Fish(fishImages[0], 
                fishImages[1], edges, this));
            try {
                Thread.sleep(20);
            }
            catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }
        
        Fish fish;
        
        while(runOK){
         for(int i=0;i<numberFish;i++){
            fish=(Fish)fishes.elementAt(i);
            fish.swim();
            }
            
            try{
               Thread.sleep(sleepTime);

         }catch(Exception ex){
            System.out.println(ex.getMessage());
         }
         repaint();

}
}
   
   public static void main(String[] args){
      
      new Aquarium();
      
     }
}

class Fish{
   Component tank;
   Image image1;
   Image image2;
   Point location;
   Point velocity;
   Rectangle edges;
   Random ran;
   
   //constructor
   public Fish(Image image1,Image image2,Rectangle edges,
               Component tank){
               
           ran=new Random(System.currentTimeMillis());
           this.tank=tank;
           this.image1=image1;
           this.image2=image2;
           this.edges=edges;
           
           this.location = new Point(100 + (Math.abs(ran.nextInt()) 
            % 300), 100 + (Math.abs(100 + ran.nextInt()) % 100));

        this.velocity = new Point(ran.nextInt() % 8, 
            ran.nextInt() % 8); 
               }
               
       public void swim() 
    {
        if(ran.nextInt() % 7 <= 1){

            velocity.x += ran.nextInt() % 4; 

            velocity.x = Math.min(velocity.x, 8);
            velocity.x = Math.max(velocity.x, -8);

            velocity.y += ran.nextInt() % 4; 

            velocity.y = Math.min(velocity.y, 8);
            velocity.y = Math.max(velocity.y, -8);
        }
    
        location.x += velocity.x;
        location.y += velocity.y;

        if (location.x < edges.x) {
            location.x = edges.x;
            velocity.x = -velocity.x;
        }

        if ((location.x + image1.getWidth(tank))
            > (edges.x + edges.width)){
            location.x = edges.x + edges.width - 
                image1.getWidth(tank);
            velocity.x = -velocity.x;
        }
    
        if (location.y < edges.y){
            location.y = edges.y;
            velocity.y = -velocity.y;
        }

        if ((location.y + image1.getHeight(tank))
            > (edges.y + edges.height)){
            location.y = edges.y + edges.height - 
                image1.getHeight(tank);
            velocity.y = -velocity.y;
        }
    }
  
    public void drawFishImage(Graphics g)
    {
        if(velocity.x < 0) {
            g.drawImage(image1, location.x, 
                location.y, tank);
        }
        else {
            g.drawImage(image2, location.x, 
                location.y, tank);
        }
    }

   
}

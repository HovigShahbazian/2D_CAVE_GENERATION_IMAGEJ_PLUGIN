import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.WindowManager;

public class CellAutomata {
	Random rand = new Random();

	int v1;
	int v2;
	int v3;
	int percent;
	int maxPercent = 99;
	int floorColor;
	int wallColor;
    
   public CellAutomata(int value1,int value2,int value3,int p, int wColor, int fColor){
       v1 = value1;
	   v2 = value2;
	   v3 = value3;
	   percent = p;
	   floorColor = fColor;
	   wallColor = wColor;
   }

	public void createMap(ImagePlus imp,int iter) {
		ImageProcessor ip = imp.getProcessor();
		int w = ip.getWidth();
		int h = ip.getHeight();

		Grid oldGrid = InitializeGrid(w,h);
		Grid newGrid = oldGrid;

		for(int t = 0;t < iter;t++) {
			newGrid = RunAutomation(newGrid);
		}

		//after automation is applied draw the new map
		for (int u = 0; u < w; u++) {
			for (int v = 0; v < h; v++) {
				if (newGrid.getNode(u, v).getWall()) {
					int p = ip.getPixel(u, v);
					ip.putPixel(u, v, wallColor);
				} else {
					int p = ip.getPixel(u, v);
					ip.putPixel(u, v, floorColor);
				}
			}
		}

	}

	public void ApplyAutoToImage(ImagePlus imp){
	    ImageProcessor ip = imp.getProcessor();
		int w = ip.getWidth();
		int h = ip.getHeight();
		Grid newGrid = GetGridFromImage(imp); 
		
		//IJ.log("Ran automation");
		newGrid = RunAutomationThree(newGrid);

		//after automation is applied draw the new map
		for (int u = 0; u < w; u++) {
			for (int v = 0; v < h; v++) {
				if (newGrid.getNode(u, v).getWall()) {
					int p = ip.getPixel(u, v);
					ip.putPixel(u, v, wallColor);
				} else {
					int p = ip.getPixel(u, v);
					ip.putPixel(u, v, floorColor);
				}
			}
		}
	}

	public Grid GetGridFromImage(ImagePlus imp){
		ImageProcessor ip = imp.getProcessor();
		int width = ip.getWidth();
		int height = ip.getHeight();

		Grid newGrid = new Grid(width,height);

		for(int u = 0; u < width; u++){
			for(int v = 0; v < height; v++) {
				int p = ip.getPixel(u, v);
				if(p == wallColor){
					newGrid.getNode(u, v).setWall(true);
				}
				else{
					newGrid.getNode(u, v).setWall(false);
				}
			}
		}
		return newGrid;
	}

	public Grid InitializeGrid(int width,int height){
		Grid randomGrid = new Grid(width,height);
		int rn = 0;

		for(int u = 0; u < width; u++){
			for(int v = 0; v < height; v++){

				//randomGrid.getNode(u, v).setX(u);
				//randomGrid.getNode(u, v).setY(v);

				if(rand.nextInt(maxPercent) < percent){
					randomGrid.getNode(u, v).setWall(true);
				}
				else{
					randomGrid.getNode(u, v).setWall(false);
				}

				if ((v <= 0) || (u <= 0)  || (u >= width-1) || ( v >= height-1)) {
					randomGrid.getNode(u, v).setWall(true);
				}
			}
		}
		return randomGrid;
	}

	
	//Algorithim # 1
	public Grid RunAutomation(Grid grid){

		int height = grid.getHeight();
		int width = grid.getWidth() ;

		//Grid newGrid = new Grid(width,height);

		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				int numofWalls = 0;
				ArrayList<Node> neighbors = grid.getNeighbors(grid.getNode(u, v));

				for (int i = 0; i < neighbors.size(); i++) {
					if (neighbors.get(i).getWall()) {
						numofWalls++;
					}
				}
				if (grid.getNode(u, v).getWall()) {
					if (numofWalls >= v1) {
						grid.getNode(u, v).setWall(true);
					}
					//alters the size of the tunnels
					if ((numofWalls < v2)) {
						grid.getNode(u, v).setWall(false);
					}
				} else {
					if ((numofWalls >= v3)) {
						grid.getNode(u, v).setWall(true);
					}
				}

			}
		}
		return grid;
	}
	
	//run algorithim two
	public Grid RunAutomationTwo(Grid grid){

		int height = grid.getHeight();
		int width = grid.getWidth() ;

		Grid newGrid = new Grid(width,height);

		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				int numofWalls = 0;
				ArrayList<Node> neighbors = grid.getNeighbors(grid.getNode(u, v));

				for (int i = 0; i < neighbors.size(); i++) {
					if (neighbors.get(i).getWall()) {
						numofWalls++;
					}
				}
				if (grid.getNode(u, v).getWall()) {

					if ((numofWalls < v2)) {
						newGrid.getNode(u, v).setWall(false);
					}
					else{
						newGrid.getNode(u, v).setWall(true);
					}
					
				} else {
					
					if ((numofWalls > v3)) {
						newGrid.getNode(u, v).setWall(true);
					}
					else{
						newGrid.getNode(u, v).setWall(false);
					}
				}

			}
		}
		return newGrid;
	}
	
	//run algorithim three
	public Grid RunAutomationThree(Grid grid){

		int height = grid.getHeight();
		int width = grid.getWidth() ;

		Grid newGrid = new Grid(width,height);

		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				int numofWalls = 0;
				ArrayList<Node> neighbors = grid.getNeighbors(grid.getNode(u, v));

				for (int i = 0; i < neighbors.size(); i++) {
					if (neighbors.get(i).getWall()) {
						numofWalls++;
					}
				}				
				if (grid.getNode(u, v).getWall()) {
                     
					if ((numofWalls < v2)) {
						newGrid.getNode(u, v).setWall(false);
					}
					else{
						newGrid.getNode(u, v).setWall(true);
					}
				}		
			}
		}
		return newGrid;
	}
	
	
	
}


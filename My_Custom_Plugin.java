import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.util.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.WindowManager;


public class My_Custom_Plugin implements PlugIn {
	Random rand = new Random();
	ImagePlus imp;

	static String title="Example";
	static int width=200,height=200;
	static int v1 = 4;
	static int v2 = 5;
	static int v3 = 5;

	static int percent = 57;
	static int maxPercent = 99;
	static int floorColor = 0xFFFFFF;;
	static int wallColor = 0x000000;

	public void run(String arg) {

		runDialog();

	}

	public void runDialog(){
		int iterations = 6;
		GenericDialog gd = new GenericDialog("2D Cave Generator");

		//

		gd.addStringField("Title: ", title);
		gd.addNumericField("Width: ", width, 0);
		gd.addNumericField("Height: ", height, 0);
		gd.addNumericField("Iterations: ",iterations,0);
		gd.addNumericField("Value 1",v1,0);
		gd.addNumericField("Value 2:",v2,0);
		gd.addNumericField("Value 3:",v3,0);
		gd.addNumericField("Percent:",percent,0);

		gd.showDialog();

		if (gd.wasCanceled()) return;
		title = gd.getNextString();
		width = (int)gd.getNextNumber();
	height = (int)gd.getNextNumber();
	iterations = (int)gd.getNextNumber();
	v1 = (int)gd.getNextNumber();
	v2 = (int)gd.getNextNumber();
	v3 = (int)gd.getNextNumber();
	percent = (int)gd.getNextNumber();

	//IJ.newImage(title, "8-bit", width, height, 1);
	imp = IJ.createImage("2d Cave ", "RGB white", width,height,1);
	createMap(imp,iterations);
	imp.show();
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

		//after autmation is applied draw the new map
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

	public void ApplyAutoToImage(){

	}

	public Grid ReadImage(){
		ImageProcessor ip = imp.getProcessor();
		int width = ip.getWidth();
		int height = ip.getHeight();

		Grid newGrid = new Grid(width,height);

		for(int u = 0; u < width; u++){
			for(int v = 0; v < height; v++) {

				int p = ip.getPixel(u, v);
				/*
				if(p == wallColor){
					newGrid.getNode(u, v).setWall(true)
				}
				*/
			}
		}
		return newGrid;
	}

	public Grid InitializeGrid(int width,int height){
		Grid randomGrid = new Grid(width,height);
		int rn = 0;

		for(int u = 0; u < width; u++){
			for(int v = 0; v < height; v++){

				randomGrid.getNode(u, v).setX(u);
				randomGrid.getNode(u, v).setY(v);

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

}


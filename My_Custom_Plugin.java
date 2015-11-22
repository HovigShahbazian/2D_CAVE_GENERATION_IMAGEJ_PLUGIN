import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.awt.Button;
import java.util.*;
import java.awt.event.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.WindowManager;


public class My_Custom_Plugin implements PlugIn {

	public void run(String arg) {
		runDialog();
	}

	public void runDialog(){
		
		MenuDialog gd = new MenuDialog();
	    gd.showDialog();
		gd.getButtons()[0].hide();
		gd.getButtons()[1].hide();
		
    }  

}


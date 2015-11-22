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

public class MenuDialog extends GenericDialog implements ActionListener {
	
	int textFieldWidth = 5;
	
    Random rand = new Random();
	
	static ImagePlus imp;

	String title="Example";
	int width = 200;
	int height = 200;
	int v1 = 0;
	int v2 = 4;
	int v3 = 4;
    int iterations = 2;
	int percent = 40;

	int wallColor = 0x993300;
	int floorColor = 0xcc6600;
	
	CellAutomata cellAuto;
	
	Label titleLabel;
    TextField titleText;
	Label heightLabel;
	TextField heightText;
	Label widthLabel;
	TextField widthText;
	Label v1Label;
	TextField v1Text;
	Label v2Label;
	TextField v2Text;
	Label v3Label;
	TextField v3Text;
	Label iterationsLabel;
	TextField iterationsText;
	Label percentLabel;
	TextField percentText;
	
	Button ibutton; 
	Button cbutton; 
	
	public MenuDialog(){
		super("2d Map Generator");	
		setLayout(new GridLayout(12,2));
		
		titleLabel= new Label("Title:");
		titleText = new TextField(title,textFieldWidth);
		heightLabel = new Label("Height:");
		heightText = new TextField(Integer.toString(height),textFieldWidth);
		widthLabel = new Label("Width:");
		widthText = new TextField(Integer.toString(width),textFieldWidth);
		v1Label = new Label("Value 1");
		v1Text = new TextField(Integer.toString(v1),textFieldWidth);
		v2Label = new Label("Value 2");
		v2Text = new TextField(Integer.toString(v2),textFieldWidth);
        v3Label = new Label("Value 3");
        v3Text = new TextField(Integer.toString(v3),textFieldWidth);		
		iterationsLabel = new Label("Iterations");
		iterationsText = new TextField(Integer.toString(iterations),textFieldWidth);
		percentLabel = new Label("Percentage");
		percentText = new TextField(Integer.toString(percent),textFieldWidth);
		
		ibutton = new Button("Iterate");
		cbutton = new Button("Generate");
		cellAuto = new CellAutomata(v1,v2,v3,percent,wallColor,floorColor);
		
		setModal(false);
		
		ibutton.addActionListener(this);
		ibutton.setActionCommand("Iterate");
		cbutton.addActionListener(this);
		cbutton.setActionCommand("Create");
		
		add(titleLabel);
		add(titleText);
		add(heightLabel);
		add(heightText);
		add(widthLabel);
		add(widthText);
		add(v1Label);
		add(v1Text);
		add(v2Label);
		add(v2Text);
		add(v3Label);
		add(v3Text);
		add(percentLabel);
		add(percentText);
		add(iterationsLabel);
		add(iterationsText);
		
		add(ibutton);
		add(cbutton);
		
	    setOKLabel("Enter");
		
	}
	
	public void actionPerformed(ActionEvent e) {

				title = titleText.getText();
				width = Integer.parseInt(widthText.getText());
				height = Integer.parseInt(heightText.getText());
				iterations = Integer.parseInt(iterationsText.getText());
				v1 = Integer.parseInt(v1Text.getText());
				v2 = Integer.parseInt(v2Text.getText());
				v3 = Integer.parseInt(v3Text.getText());
				percent = Integer.parseInt(percentText.getText());
				
	        
			
			if( e.getActionCommand().equals("Create")){
				
				if( imp == null)
					imp = IJ.createImage("2d Cave", "RGB white", width,height,1);
				else
					imp.setImage(IJ.createImage("2d Cave", "RGB white", width,height,1));
				
				
				
                cellAuto = new CellAutomata(v1,v2,v3,percent,wallColor,floorColor);
				cellAuto.createMap(imp,iterations);
				imp.show();
			}
			else if(e.getActionCommand().equals("Iterate")){
			
                cellAuto = new CellAutomata(v1,v2,v3,percent,wallColor,floorColor);
				cellAuto.ApplyAutoToImage(imp);
				imp.updateAndDraw();
				imp.updateImage();
				imp.updateChannelAndDraw();
				imp.show();
			
			}
			else if(e.getActionCommand().equals("Enter")){
				
			    IJ.log("Entered");
			}
			
    }
	
	
}
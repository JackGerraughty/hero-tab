package application;

import java.io.FileInputStream;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jdk.nashorn.internal.ir.Labels;

public class HeroPane extends HBox 
{
	ArrayList<Hero> heroList;

	String selectedHeroType;

	TextArea rightTextArea;
	VBox leftVBox;
	ComboBox<String> heroTypeComboBox;
	ImageView imageView;
	
	GridPane inputPane;
	Label Name, Strength, Charisma, Damage;
	TextField nField, sField, cField, dField;
	Button random;
	
	Button addNewHero;
	Label heroStats;

	public ButtonBase randomButton;
	
	public static final int WINSIZE_X = 950, WINSIZE_Y = 600;

	public HeroPane(ArrayList<Hero> heroList) 
	{
		this.heroList = heroList;

		this.leftVBox = new VBox();
		this.rightTextArea = new TextArea();
		
		String[] heroType = { "Mage", "Fighter", "Unicorn", "Zombie" };
		heroTypeComboBox = new ComboBox<String>();
		heroTypeComboBox.setValue("Hero Type");
		heroTypeComboBox.getItems().addAll(heroType);
		heroTypeComboBox.setOnAction(new HeroTypeComboBoxHandler());
		leftVBox.getChildren().add(heroTypeComboBox);

		Name = new Label("Name");
		Strength = new Label("Strength");
		Charisma = new Label("Charisma");
		Damage = new  Label("Damage");
		nField = new TextField();
		sField = new TextField();
		cField = new TextField();
		dField = new TextField();
		
		addNewHero = new Button("Add New Hero!!!");
		random = new Button("Random");
		heroStats = new Label("");
		heroStats.setTextFill(Color.RED);
		
		inputPane = new GridPane();
		inputPane.add(Name,0,0,1,1);
		inputPane.add(Strength,0,1,1,1);
		inputPane.add(Charisma,0,2,1,1);
		inputPane.add(Damage,0,3,1,1);
		inputPane.add(nField,1,0,1,1);
		inputPane.add(sField,1,1,1,1);
		inputPane.add(cField,1,2,1,1);
		inputPane.add(dField,1,3,1,1);
		inputPane.add(random,3,3,1,1);

		addNewHero.setOnAction(new AddNewHeroButtonHandler());
		random.setOnAction(new RandomButtonHandler());
		
		leftVBox.getChildren().add(inputPane);
		leftVBox.getChildren().add(this.addNewHero);
		leftVBox.getChildren().add(heroStats);
		
		inputPane.setHgap(20);
		leftVBox.setPadding(new Insets(40, 50, 0, 50));
		leftVBox.setSpacing(40);
		leftVBox.setAlignment(Pos.TOP_CENTER);
		leftVBox.setPrefWidth(WINSIZE_X / 2);

		
		imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(100);
		leftVBox.getChildren().add(imageView);
		FileInputStream input;
		try 
		{
			input = new FileInputStream("unicorn.png");
			Image image = new Image(input);
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			imageView.setImage(null);
		}
		this.getChildren().addAll(leftVBox, rightTextArea);
		dField.setEditable(false);
	}
	private class RandomButtonHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent event) 
		{
			int min = 50;
			int max = 100;
			random.setOnMouseClicked(e->
			{
				if(dField.getText().equals(""))
				{
					int dF = (int) (Math.floor(Math.random() * (max - min + 1) + min));
					String dF1 = Integer.toString(dF);
					dField.setText(dF1);
				}
				else 
				{
					heroStats.setText("Damage is already generated");
				}
			});
		}
	}
	private class AddNewHeroButtonHandler implements EventHandler<ActionEvent>
	{
		@SuppressWarnings("unused")
		public void handle(ActionEvent event)
		{
			String nf = nField.getText();
			String sf = sField.getText();
			String cf = cField.getText();
			String df = dField.getText();
			try 
			{
				if (selectedHeroType == null) 
				{
					throw new Exception("Hero type is not yet selected");
				}
				if((nf.equals("")) || (sf.equals("")) || (cf.equals("")) || (df.equals("")))
				{
					throw new Exception("At least one of the text fields is empty");
				}
				for(int i=0; i<heroList.size(); i++) 
				{
					if(heroList.get(i).getName().equalsIgnoreCase(nf))
					{
						
						throw new Exception("Hero existed!");
					}
				}
				int sf1 = Integer.parseInt(sf);
				int cf1 = Integer.parseInt(cf);
				int df1 = Integer.parseInt(df);
				if(sf1<0 || cf1<0)
				{
					throw new Exception("Both Strength and Charisma must be positive numbers");
				}
				if((sf1+cf1)>=100) 
				{
					throw new Exception("The sum of strength and charisma must be less or equal to 100");
				}
				Hero hero = new Hero(nf, selectedHeroType, sf1, cf1, df1);
				heroList.add(hero);
				heroStats.setText("Hero added Successfully");
				nField.setText("");
				sField.setText("");
				cField.setText("");
				dField.setText("");
				updateTextArea();
			} catch (NumberFormatException exception) 
			{
				heroStats.setText("At least one of the text fields is in the incorrect format");

			} catch (Exception exception)
			{ 
				heroStats.setText(exception.getMessage());
			}
		}
	}
	private void updateTextArea() 
	{
		String heroInfo = "";
		for(int i=0;i<heroList.size();i++)
		{
			heroInfo += heroList.get(i).toString();
		}
		rightTextArea.setText(heroInfo);
	}
	private class HeroTypeComboBoxHandler implements EventHandler<ActionEvent> 
	{
		@Override
		public void handle(ActionEvent event) {
			selectedHeroType = heroTypeComboBox.getSelectionModel().getSelectedItem();
			FileInputStream input;
			try 
			{
				input = new FileInputStream(selectedHeroType.toLowerCase() + ".png");
				Image image = new Image(input);
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				imageView.setImage(null);
			}

		}
	}
}

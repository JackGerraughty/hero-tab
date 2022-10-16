package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ArmyPane extends BorderPane 
{
	ArrayList<Hero> heroList;
	
	int totalDamage;
	int totalStrength;
	int totalCharisma;

	Label armyInfo;
	VBox checkBoxes;
	Button loadClear;

	public ArmyPane(ArrayList<Hero> heroList) 
	{
		this.heroList = heroList;

		armyInfo = new Label("Select heroes to add to your army");
		checkBoxes = new VBox();
		loadClear = new Button("Load Heroes/Clear Selectionr");

		loadClear.setOnAction(new LoadHeroesButtonHandler());
		
		this.setTop(armyInfo);
		this.setCenter(checkBoxes);
		this.setBottom(this.loadClear);
	}
	
	private class LoadHeroesButtonHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event) 
		{
			
			checkBoxes.getChildren().clear();
			
			for(int i=0;i<heroList.size();i++)
			{
				String heroInfo = heroList.get(i).toString();
				CheckBox checkBox = new CheckBox(heroInfo);
				checkBox.setOnAction(new CheckBoxHandler(heroList.get(i)));
				checkBoxes.getChildren().add(checkBox);
			}
		}
	}

	private class CheckBoxHandler implements EventHandler<ActionEvent> 
	{

		Hero hero;
		
		public CheckBoxHandler(Hero _hero)
		{
			this.hero = _hero;
		}

		@Override
		public void handle(ActionEvent event) 
		{
			CheckBox source = (CheckBox)event.getSource();
			if(source.isSelected() == true) 
			{
				totalStrength += hero.getStrength();
				totalDamage += hero.getDamage();
				totalCharisma += hero.getCharisma();
			}else
			{
				totalStrength -= hero.getStrength();
				totalDamage -= hero.getDamage();
				totalCharisma -= hero.getCharisma();
			}
			armyInfo.setText("Total Damage: " + totalDamage + "\t\tTotal Strength: " + totalStrength + "\tTotal Charisma: " + totalCharisma);
		}
	}

}

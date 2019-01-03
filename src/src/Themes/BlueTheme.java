package Themes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class BlueTheme implements Theme {
	
	private Image backgroundImage; 
	private Media backgroundMusic;
	private Image startImage;
	private Image goalImage;
	private Image winImage;
	private Image curvedPipe;
	private Image straightPipe;	
	
	public BlueTheme() {
		try {
			this.backgroundImage = new Image(new FileInputStream("./resources/ThemeBlue/BackgroundBlue.jpg"));
			this.backgroundMusic = new Media ("https://www.youtube.com/watch?v=QEjVL3RI14Y");
			this.startImage = new Image(new FileInputStream("./resources/ThemeBlue/StratBlue.png"));
			this.goalImage = new Image(new FileInputStream("./resources/ThemeBlue/FinishBlue.png"));
			this.winImage = new Image(new FileInputStream("./resources/ThemeBlue/WinBlue.png"));
			this.curvedPipe = new Image(new FileInputStream("./resources/ThemeBlue/CurvedBluePipe.png"));
			this.straightPipe = new Image(new FileInputStream("./resources/ThemeBlue/StrightBluePipe.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Image getBackgroundImage() {
		return this.backgroundImage;
	}

	@Override
	public Media getBackgroundMusic() {
		return this.backgroundMusic;
	}

	@Override
	public Image getStartImage() {
		return this.startImage;
	}

	@Override
	public Image getGoalImage() {
		return this.goalImage;
	}

	@Override
	public Image getWinImage() {
		return this.winImage;
	}

	@Override
	public Image getCurvedPipe() {
		return this.curvedPipe;
	}

	@Override
	public Image getStraightPipe() {
		return this.straightPipe;
	}

}

package Themes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class GreyTheme implements Theme {
	
	private Image backgroundImage; 
	private Media backgroundMusic;
	private Image startImage;
	private Image goalImage;
	private Image winImage;
	private Image curvedPipe;
	private Image straightPipe;	
	
	public GreyTheme() {
		try {
			this.backgroundImage = new Image(new FileInputStream("./resources/ThemeGrey/BackgroundGrey.png"));
			this.backgroundMusic = new Media ("http://www.youtube.com/watch?v=dSTsqG6tpKc&t=1m52s");
			this.startImage = new Image(new FileInputStream("./resources/ThemeGrey/StartGrey.png"));
			this.goalImage = new Image(new FileInputStream("./resources/ThemeGrey/FinsihGrey.png"));
			this.winImage = new Image(new FileInputStream("./resources/ThemeGrey/WinGrey.png"));
			this.curvedPipe = new Image(new FileInputStream("./resources/ThemeGrey/CurvedGreyPipe.png"));
			this.straightPipe = new Image(new FileInputStream("./resources/ThemeGrey/StrightGreyPipe.png"));
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

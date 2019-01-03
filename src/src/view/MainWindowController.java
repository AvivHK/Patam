package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    String pipeData = "-g-L\nJ|-7\nJ-Fs\nJL-L\ndone";
    int lvl = 1;
    String QUIT_WORD = "done";
    String currentTheme = "ThemeBlue";
    MediaPlayer songToPlay = null;

    @FXML PipeDisplayer PipeDisplayer;
    @FXML Label numberOfSteps;
    @FXML Label timer;
    private int time;
    private int steps;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.PipeDisplayer.setBoard(pipeData);
        this.switchSong();

        this.PipeDisplayer.setOnMouseClicked(event -> {
            PBView board = this.PipeDisplayer.getPipesBoard();
            double x = event.getSceneX();
            double y = event.getSceneY();

            PipeView pipe = board.getPipeViewByPixels(x, y);

            if (pipe != null){
                pipe.rotate();
                this.PipeDisplayer.setBoard(board);

                this.steps++;
                numberOfSteps.setText(Integer.toString(this.steps));
            }
        });

        this.time = 0;
        this.steps = 0;
        timer.setText(Integer.toString(this.time));
        numberOfSteps.setText(Integer.toString(this.steps));

        Runnable runnable = this::stopWatchLoop;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public ArrayList<String> getStepsToSolve(){
        String[] arrBoard = this.PipeDisplayer.getPipesBoard().toString().split("\n");
        int rows = arrBoard.length; // including the done (for the server)

        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int port = 9583;
        try {
            s = new Socket("127.0.0.1", port);
            try {
                s.setSoTimeout(500000);
            } catch (SocketException e) {
                e.printStackTrace();
            }

            out = new PrintWriter(s.getOutputStream());
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            for (int i = 0 ; i < rows ; i++){
                out.println(arrBoard[i]);
            }

            out.println("done");
            out.flush();

            ArrayList<String> steps = new ArrayList<String>();
            String line;
            while (!(line = in.readLine()).equals(QUIT_WORD)) {
                steps.add(line);
            }

            steps.add(QUIT_WORD);
            return steps;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void switchSong()
    {
        if (this.songToPlay != null) {
            this.songToPlay.stop();
        }
        String musicFileName = "./resources/" + this.currentTheme + "/song.mp3";
        Media song = new Media(new File(musicFileName).toURI().toString());
        this.songToPlay = new MediaPlayer(song);
        this.songToPlay.setOnEndOfMedia(new Runnable() {
            public void run() {
                songToPlay.seek(Duration.ZERO);
            }
        });
        this.songToPlay.play();
    }

    
    
   public void switchThemeFromBlueToGrey() {
	   if(currentTheme.equals("ThemeBlue")){
		   currentTheme= "ThemeGrey";
		   this.PipeDisplayer.setDashPipeFileName("./resources/" + currentTheme + "/-StrightPipe.png");
	        this.PipeDisplayer.setFPipeFileName("./resources/" + currentTheme + "/FCurvedPipe.png");
	        this.PipeDisplayer.setJPipeFileName("./resources/" + currentTheme + "/JCurvedPipe.png");
	        this.PipeDisplayer.setLPipeFileName("./resources/" + currentTheme + "/LCurvedPipe.png");
	        this.PipeDisplayer.setSevenPipeFileName("./resources/" + currentTheme + "/7CurvedPipe.png");
	        this.PipeDisplayer.setPipeLinePipeFileName("./resources/" + currentTheme + "/IStrightPipe.png");
	        this.PipeDisplayer.setStartFileName("./resources/" + currentTheme + "/Start.png");
	        this.PipeDisplayer.setGoalFileName("./resources/" + currentTheme + "/Finish.png");
	        this.PipeDisplayer.setBackgroundFileName("./resources/" + currentTheme + "/Background.png");

	        this.switchSong();
	   }
   }
    
   public void switchThemeFromGreyToBlue() {
	   if(currentTheme.equals("ThemeGrey")){
		   currentTheme= "ThemeBlue";
		   this.PipeDisplayer.setDashPipeFileName("./resources/" + currentTheme + "/-StrightPipe.png");
	        this.PipeDisplayer.setFPipeFileName("./resources/" + currentTheme + "/FCurvedPipe.png");
	        this.PipeDisplayer.setJPipeFileName("./resources/" + currentTheme + "/JCurvedPipe.png");
	        this.PipeDisplayer.setLPipeFileName("./resources/" + currentTheme + "/LCurvedPipe.png");
	        this.PipeDisplayer.setSevenPipeFileName("./resources/" + currentTheme + "/7CurvedPipe.png");
	        this.PipeDisplayer.setPipeLinePipeFileName("./resources/" + currentTheme + "/IStrightPipe.png");
	        this.PipeDisplayer.setStartFileName("./resources/" + currentTheme + "/Start.png");
	        this.PipeDisplayer.setGoalFileName("./resources/" + currentTheme + "/Finish.png");
	        this.PipeDisplayer.setBackgroundFileName("./resources/" + currentTheme + "/Background.png");

	        this.switchSong();
	   }
   }


    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void saveLvl() {
        LvlCurrent curr = new LvlCurrent(this.PipeDisplayer.getPipesBoard().toString(), this.lvl, this.steps, this.time);
        FileHand.saveState(curr);
    }
    public void loadLvl(){
    	LvlCurrent state = FileHand.loadSolution();
        if (state != null) {
            this.setLvl(state.getLvl());
            this.steps = state.getNumOfStep();
            numberOfSteps.setText(Integer.toString(this.steps));
            this.time = state.getTime();
            this.PipeDisplayer.setBoard(state.getGame());
        }
    }


    public void solve()  {
        Runnable runnable =  () -> {
            ArrayList<String> steps = getStepsToSolve();

            for (int i = 0 ; i < steps.size(); i++){
                if (!steps.get(i).equals(QUIT_WORD)){
                    walkthrough(steps.get(i));
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void walkthrough(String step){
        String[] splitted = step.split(",");
        int row = Integer.parseInt(splitted[0]);
        int col = Integer.parseInt(splitted[1]);
        int numOfRotations = Integer.parseInt(splitted[2]);

        for (int i = 0; i < numOfRotations ; i++) {
            this.PipeDisplayer.getPipesBoard().getPipeView(row, col).rotate();
            PipeDisplayer.redraw();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void checkUpdate() {
    	MsgBoxDisplayer.showInfo("No update were found.");
    }
    
    public void aboutUs() {
    	MsgBoxDisplayer.showInfo("The Game was built by: \n Gal Shemesh \n Aviv Hakmon");
    }
    

    public void checkIfFinish() {
        if (this.getStepsToSolve().get(0).equals("done")){
        	MsgBoxDisplayer.showConf("Success!\nYou are the best!");
        } else {
        	MsgBoxDisplayer.showConf("Wrong!\n Please try again");
        }
    }

    private void stopWatchLoop()
    {
        while(true)
        {
            try {
                Thread.sleep(1000);
                this.time++;
                Platform.runLater(
                        () -> {
                            timer.setText(Integer.toString(this.time));
                        }
                );

            } catch (InterruptedException e) { }
        }
    }

    public void quit() {
        Main.active.close();
    }
}
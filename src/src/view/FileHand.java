package view;

import javafx.stage.FileChooser;

import java.io.File;

public class FileHand {

    public static void saveState(LvlCurrent state) {
        String path = getPathWithPicker(true);
        if (path == null) {
            MsgBoxDisplayer.showWarn("The game not saved");
        } else {
            state.toFile(path);
            MsgBoxDisplayer.showConf("The game was saved \n you can continue playing next time");
        }
    }


    public static LvlCurrent loadSolution() {
        String path = getPathWithPicker(false);

        if (path == null) {
            MsgBoxDisplayer.showWarn("Game didn't loaded \n file was not picked");
        } else {
            LvlCurrent ret = LvlCurrent.fromFile(path);
            if (ret == null) {
                MsgBoxDisplayer.showWarn( "The game not loaded, \n please try again");
            }
            else {
                MsgBoxDisplayer.showConf("Load Successes \n Enjoy the game :)");
            }
            return ret;
        }
        return null;
    }

    private static String getPathWithPicker(boolean isSave) {
        FileChooser fileChooser = new FileChooser();

      
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GA files", "*.ga");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialDirectory(new File("./resources/SavedLevels"));

   
        File file;
        if (isSave) {
            file = fileChooser.showSaveDialog(Main.active);

        }
        else {file = fileChooser.showOpenDialog(Main.active); }
        if (file == null) {
            return null;
        }

        return file.getPath();
    }
}
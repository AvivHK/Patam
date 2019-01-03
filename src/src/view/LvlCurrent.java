package view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LvlCurrent {
    String game;
    int lvl;
    int numOfStep;
    int time;


    public LvlCurrent(String game, int lvl, int numOfStep, int time)
    {
        this.game = game;
        this.lvl = lvl;
        this.numOfStep = numOfStep;
        this.time = time;
    }
    
    public void toFile(String path)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeUTF(this.game);
            out.writeInt(this.lvl);
            out.writeInt(this.numOfStep);
            out.writeInt(this.time);
            out.flush();
            out.close();
        }
        catch (Exception e) {
        }
    }

    public static LvlCurrent fromFile(String path)
    {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            String game =in.readUTF();
            int lvl = in.readInt();
            int numOfStep = in.readInt();
            int time = in.readInt();
            in.close();
            return new LvlCurrent(game, lvl, numOfStep, time);
        }
        catch (Exception e) {
            return null;
        }
    }

    public String getGame() {return this.game;}
    public int getLvl() {return this.lvl;}
    public int getNumOfStep() {return this.numOfStep;}
    public int getTime() {return this.time;}
}
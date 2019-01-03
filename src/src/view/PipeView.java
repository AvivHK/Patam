package view;

import test.Node;

import java.util.HashMap;

public class PipeView {

    Character val;
    HashMap<Character, Character> rotateMap;
    Node node;
    HashMap<String,Double> pixels;



    public PipeView(Double height,Double width,Character pipe, Node node) {
        try {
            rotateMapInit();
            setVal(pipe);
            setNode(node);
            setPixels(height,width);
        } catch (Exception e) {
            System.out.println("Pipe.Pipe(Character pipe) :" + e.getMessage());
        }
    }

    public PipeView(Double height,Double width,PipeView pipe) {
        try {
            rotateMapInit();
            setVal(pipe.getVal());
            setNode(pipe.getNode());
            setPixels(height,width);
        } catch (Exception e) {
            System.out.println("Pipe.Pipe(Pipe pipe) :" + e.getMessage());
        }
    }

    private void setPixels(Double height, Double width) {
        this.pixels = new HashMap<String,Double>(){
            {
                put("startX", node.getCol() * width);
                put("startY", node.getRow() * height);
                put("endX", node.getCol() * width + width);
                put("endY", node.getRow() * height + height);
            }};
    }

    public Character getVal() {
        return val;
    }

    public void setVal(Character val) {
        this.val = val;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void rotateMapInit() {
        if (this.rotateMap == null) {
            this.rotateMap = new HashMap<Character, Character>() {{
                put('F', '7');
                put('7', 'J');
                put('J', 'L');
                put('L', 'F');
                put('-', '|');
                put('|', '-');
            }};
        }
    }

    public Character rotate() {
        try {
            if (this.val != 's' && this.val != 'g' && this.val != ' ') {
                try {
                    setVal(this.rotateMap.get(this.val));
                } catch (NullPointerException e) {
                    System.out.println("Null ");
                }
            }
        } catch (Exception e) {
            System.out.println("Pipe.Rotate :" + e.getMessage());
        }
        return getVal();
    }

}
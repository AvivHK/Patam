package test;

public class CheckServer {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Server Side ****");

//        Server s = new MyServer(Integer.parseInt(args[0]));//Take the port from the args
        Server s = new MyServer(6400, 5);//Take the port from the args
        ((MyServer) s).start(new MyClientHandler());
        System.in.read();
        s.stop();
        System.out.println("Closed server");
    }
}

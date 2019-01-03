package test;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class MyClientHandler extends Thread implements ClientHandler{

	private CacheManager cm= new MyCacheManager();
	private Solver solver=new MySolver();
	private BufferedReader in=null;
	private PrintWriter out=null;
	private boolean terminate=true;
	private Socket client=null;
	private boolean solved=false;



	public MyClientHandler(Socket _client){
		this.client=_client;
		try {
			out = new PrintWriter(client.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		}
		catch(IOException e) {
			//System.out.println(e.getMessage());
		}
	}


	public MyClientHandler() {
	}


	@Override
	public void handleClient(InputStream inputStream, OutputStream outputStream) {

			PrintWriter out=new PrintWriter(outputStream);
			//out = new PrintWriter(client.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			//in = new BufferedReader(new InputStreamReader(client.getInputStream()));


		Scanner scanner = new Scanner(inputStream);
		boolean a = true;
		String massage = null;
		String newMap = "";
		String map = "";
		String toSend;
		while(a) {
			massage = scanner.nextLine();
			if (massage != null) {
				if (!massage.equals("done"))
					map = map + massage + "\n";
				else {
					for (int i = 0; i < (map.length() - 1); i++)
						newMap = newMap + map.charAt(i);
					toSend = cm.getSolve(newMap);

					if (!toSend.isEmpty())
						out.println(toSend);
					out.println("done");
					map = "";
					newMap = "";
					a = false;
				}
			}
		}
		scanner.close();

	}




	@Override
	public void handle(PrintWriter outputStream, String board) {

		PrintWriter out = new PrintWriter(outputStream);
		boolean a = true;
		String toSend;
		while (a) {
			toSend = cm.getSolve(board);
			if(toSend == "")
				solved=true;
			if (!toSend.isEmpty())
				out.println(toSend);
			out.println("done");
			out.flush();
			a = false;
		}

		if(out!=null)
			out.close();

	}



	@Override
	public String newGame() {
		return ("S----7" + '\n' + "L|-|j" + '\n' + "j-|-f" + '\n' + "7--|g");

	}

	@Override
	public String loadGame(String path) {
		return cm.loadGame(path);
	}


	@Override
	public void getTimer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getHistory() {
		// TODO Auto-generated method stub
		//HistoryTable hs=hs.getInstance(_playerName, _numMoves, _gameKey, _timer);

	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
	}

	@Override
	public String solve(String game) {
		return cm.getSolve(game);
	}

	@Override
	public void run(){
		String massage=null;
		String newMap="";
		String map= "";
		try {
			while (terminate) {
				massage = in.readLine();
				if(massage!=null) {
					if (!massage.equals("done"))
						map = map + massage+"\n";
					else {
						for(int i=0;i<map.length()-1;i++)
							newMap = newMap + map.charAt(i);
						out.println(cm.getSolve(newMap));
						out.println("done");
						map = "";
						newMap="";
					}
				}
			}
		}
		catch(IOException e){
		}
	}


	@Override
	public void terminate(){
		try{
			in.close();
			out.close();
			client.close();
		}
		catch(IOException e){
			//	System.out.print(e.getMessage());
		}
		terminate=false;
	}
}

package test;

public class MySolver implements Solver {
	
	private String toSolveMySolver;


	
	
	@Override
	public String solve(String name) {
		PipeGameBoard toSolve = new PipeGameBoard(name);
		Searcher<Node> searcher;
		if (name.length()<25)
			searcher = new BFS<>();

		else
			searcher = new HillClimbing(new RandomStateGrader(),3000);

		Solution toSend = searcher.search(toSolve);

		return toSend.toString();



	}

}

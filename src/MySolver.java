public class MySolver implements Solver {
	@SuppressWarnings("unchecked")


	@Override
	public String solve(String name) {
		PipeGameBoard toSolve = new PipeGameBoard(name);
		Searcher<Node> searcher;
		if (name.length()<110)
			searcher=new DFS<>();
		 else if (name.length()<990)
			searcher = new BFS<>();
		else if (name.length()<2750)
			searcher= new BestFirstSearch<>();
		else
			searcher = new HillClimbing(new RandomStateGrader(),3000);

		Solution toSend = searcher.search(toSolve);

		return toSend.toString();



	}

}

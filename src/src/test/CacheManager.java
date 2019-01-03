package test;

@SuppressWarnings("unchecked")

public interface CacheManager {

	void saveGame(String game, String path);
	String loadGame(String path);
	String getSolve(String game);
	boolean isSolutionExist(String game);
	void saveSolution(String game, String sol);
	String loadSolution(String name);

}

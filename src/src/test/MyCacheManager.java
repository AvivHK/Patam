package test;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
@SuppressWarnings("unchecked")

public class MyCacheManager implements CacheManager {
	HashMap<String, String> solution = new HashMap<String, String>();
	Solver toSolve = new MySolver();


	public MyCacheManager() {
		BufferedReader reader = null;
		final File dir = new File("../");
		for (File inFile : dir.listFiles()) {
			if (!inFile.isDirectory())
				if (inFile.getName().contains("Sol")) {
					try {
						reader = new BufferedReader(new FileReader(inFile));
						String s1 = reader.readLine();
						String s2 = reader.readLine();
						solution.put(s1, s2);
					} catch (FileNotFoundException e) {
						//System.out.println(e.getMessage());
					} catch (IOException e) {
						//System.out.println(e.getMessage());
					} finally {
						try {
							reader.close();
						} catch (IOException e) {
							//System.out.println(e.getMessage());
						}

					}
				}
		}
	}

	@Override
	public String getSolve(String game) {
		String sol;
		if (isSolutionExist(game))
			return (solution.get(game));
		else {
			toSolve = new MySolver();
			sol = toSolve.solve(game);
			saveSolution(game, sol);
			return sol;
		}
	}

	@Override
	public void saveGame(String game, String path) {
		FileOutputStream fos = null;
		PrintWriter out;

		try {
			fos = new FileOutputStream(path);
			out = new PrintWriter(fos);
			out.print(game);
		} catch (FileNotFoundException e) {
			//System.out.print(e.getMessage());
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.getMessage();

				}
			}
		}
	}

	@Override
	public boolean isSolutionExist(String game) {
		return (solution.containsKey(game));
	}

	@Override
	public void saveSolution(String game, String sol) {
		BufferedReader reader = null;
		solution.put(game, sol);
		Random r = new Random();
		PrintWriter writer = null;
		String sol2 = "Sol.txt";
		try {
			writer = new PrintWriter(sol2, "UTF-8");

			writer.println(game);

			writer.println(sol);
		} catch (FileNotFoundException e) {
			//System.out.println(e.getMessage());
		} catch (IOException e) {
			//System.out.println(e.getMessage());
		} finally {
			writer.close();
		}
	}

	@Override
	public String loadSolution(String game) {
		return solution.get(game);
	}

	public String loadGame(String path) {
		FileInputStream fis = null;
		Scanner in;
		String game= null;  
		try {
			fis=new FileInputStream(path);
			in=new Scanner(fis);
			while (in.hasNextLine()) {
				game = game + in.nextLine();
			}
		}
		catch(FileNotFoundException e)
		{
			//System.out.print(e.getMessage());
		}
		finally {
			if(fis != null) {
				try {
				fis.close();
				}catch (IOException e) {
					e.getMessage();
				
				}
			}
		}
		return game;
	}
}
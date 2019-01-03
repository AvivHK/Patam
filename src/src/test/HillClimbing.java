package test;

import java.util.List;
import java.util.Random;
@SuppressWarnings("unchecked")


class HillClimbing<T> implements Searcher<T> {

    //private static final Logger LOGGER = LoggerFactory.getLogger(HillClimbing.class);

    private long timeToRun;
    private StateGrader<T> grader;


    HillClimbing(StateGrader<T> grader, long timeToRun) {
        this.grader = grader;
        this.timeToRun = timeToRun;
    }

    @Override
    public Solution search(Searchable<T> searchable) {
        Solution s=null;

        while(s==null)
            s=RandomRestart(searchable);
           return s;
    }

    private Solution RandomRestart(Searchable<T> searchable){
        //Define the current state as an initial state
        State<T> next = searchable.getInitialState();
        Solution result = new Solution();

        long time0 = System.currentTimeMillis();
        //Loop until the goal state is achieved or no more operators can be applied on the current state:
        while (System.currentTimeMillis() - time0 < timeToRun) {
            if (searchable.isGoalState(next)) break;
            List<State<T>> neighbors = searchable.getPossibleStates(next);
            if(neighbors.isEmpty()) return null;
            if (Math.random() < 0.7) { // with a high probability
                // find the best one
                int grade = grader.grade(neighbors.get(0));
                for(int i=1;i<neighbors.size();i++) {
                    State<T>step=neighbors.get(i);
                    int g = grader.grade(step);
                    if (g <= grade) {
                        grade = g;
                        next = step;//add this step to the solution
                    }
                }
                next = neighbors.get(new Random().nextInt(neighbors.size()));
                result.addSolution(next.getState().toString());
            }
        }
        return result;

    }
}


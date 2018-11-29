import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
@SuppressWarnings("unchecked")

public class BFS<T> implements Searcher<T> {

    @Override
    public Solution search(Searchable<T> s) {
        Queue<State> open = new LinkedBlockingQueue<>();
        Queue<State> closed = new LinkedBlockingQueue<State>();
        open.add(s.getInitialState());

        while (!open.isEmpty()) {
            State n = open.remove();
            closed.add(n);

            if (s.isGoalState(n))
                return backTrace(n);
            List<State> successors = s.getPossibleStates(n);
            for (State state : successors) {
                if (!isExist(closed,state) && !isExist(open,state)) {
                    state.setCameFrom(n);
                    open.add(state);
                }
            }
        }
        return null;
    }

    public Solution backTrace(State n){
        Solution result = new Solution();

        while(n.getCameFrom()!=null)
        {
            result.addSolution(n.getState().toString());
            n=n.getCameFrom();
        }
        return result.Reverse();
    }
    private boolean isExist(Queue<State> list,State s){
        for(State st:list){
            if(st.getState().hashCode()==s.getState().hashCode()) return true;
        }
        return false;
    }
}

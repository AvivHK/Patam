package test;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
@SuppressWarnings("unchecked")

public class DFS<T> implements Searcher<T> {

    @Override
    public Solution search(Searchable<T> s) {
        Solution result = new Solution();
        ArrayList<State> visited = new ArrayList<>();
        Stack t = new Stack();
        t.push(s.getInitialState());
        while (!t.isEmpty()) {
            State currentState = (State) t.pop();
            if (!visited.contains(currentState)) {
                visited.add(currentState);
                result.add(currentState.getState().toString());
                if (s.isGoalState(currentState)) {
                    return result;
                }
                for (Object object : s.getPossibleStates(currentState)) {
                    State state = (State) object;
                    if (!visited.contains(state))
                        t.push(state);
                }
            }
        }
        return null;
    }
}
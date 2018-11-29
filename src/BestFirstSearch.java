import java.util.*;
@SuppressWarnings("unchecked")

public class BestFirstSearch<T> implements Searcher<T> {

    @Override
    public Solution search(Searchable<T> s) {
        boolean goal=false;
        Solution result = new Solution();
        List<State> open = new ArrayList<>();
        List<State> closed = new ArrayList<>();
        open.add(s.getInitialState());
        State current= null;

        while (!open.isEmpty()&&(!goal)) {
            current = null;
            while (current == null && open.size() != 0) {
                current = open.remove(0);
                for (State<T> t : closed)
                    if (t.getState().hashCode() == current.getState().hashCode()) {
                        current = null;
                        break;
                    }
            }
            List<State> possibleStates = s.getPossibleStates(current);
            int size = possibleStates.size();
            for (int i = 0; i < size; i++) {
                open.add(possibleStates.get(0));
                possibleStates.remove(0);
            }
            closed.add(current);
            sort(open);
            if (s.isGoalState(current)) {
                goal= true;

                while (!(s.getInitialState().getState().hashCode() == current.getState().hashCode())) {
                        result.addSolution(current.getState().toString());
                        current = current.getCameFrom();
                    }
                    result.addSolution(s.getInitialState().getState().toString());
                }
            }

            result.Reverse();
            result=result.toStrin(result);
            return result;
    }

    public List<State> sort(List<State> open) {
        int n= open.size();
        for (int i=0;i<n;i++)
            for (int j=1; j<n-i;j++) {
                if (open.get(j-1).getCost() > open.get(j).getCost()) {
                    State temp = open.remove(j);
                    open.add(j-1 , temp);
                }
            }
            return open;
    }

}


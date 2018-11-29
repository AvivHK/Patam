import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked")


public interface Searchable<T> {
    //SearchTree<State<Node>> getAllPossibleState ();
    State<T> getInitialState();
    List<State<T>> getPossibleStates(State<T> s);
    boolean isGoalState(State<T> s);
}

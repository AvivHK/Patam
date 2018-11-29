@SuppressWarnings("unchecked")

public class State<T> {
    private T state;    // the state represented by a string
    protected double cost=0;     // cost to reach this state
    private State<T> cameFrom;  // the state we came from to this state



    public State(T state,State<T> cameFrom,double cost){
        this.state=state;
        this.cameFrom=cameFrom;
        this.cost=cost;
    }



    public void setCameFrom(State<T> cameFrom) {
        this.cameFrom = cameFrom;
    }

    public T getState() {
        return state;
    }


    public double getCost() {
        return cost;
    }

    public State<T> getCameFrom() {
        return cameFrom;
    }

    @Override
    public boolean equals(Object obj) { // we override Object's equals method
        return state.equals(((State) obj).state);
    }
}
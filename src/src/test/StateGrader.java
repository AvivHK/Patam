package test;

@SuppressWarnings("unchecked")

interface StateGrader<T>{
		int grade(State<T> s); // give a grade to a certain state - how close it is to the solution
}
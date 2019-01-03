package test;

@SuppressWarnings("unchecked")


public interface Searcher<T>{

    Solution search(Searchable<T> s);
}

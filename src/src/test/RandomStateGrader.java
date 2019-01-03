package test;

import java.util.Random;
@SuppressWarnings("unchecked")


public class RandomStateGrader implements StateGrader<char[][]> {

    private Random random = new Random();

    @Override
    public int grade(State s) {
        return (int) (s.cost *random.nextInt(Integer.MAX_VALUE));

    }
}

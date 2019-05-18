import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionalOperations {

    interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        String name();
    }

    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<>() {

        @Override
        public String name() {
            return "add";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble + aDouble2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<>() {

        @Override
        public String name() {
            return "diff";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble - aDouble2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<>() {

        @Override
        public String name() {
            return "mult";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble * aDouble2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<>() {

        @Override
        public String name() {
            return "div";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            try {
                return aDouble / aDouble2;
            } catch(ArithmeticException e) {
                System.out.println("DIVIDE BY ZERO ERROR.");
                return null;
            }
        }

    };

    /**
     * Applies a given list of bifunctions -- functions that take two arguments of a
     * certain type, and produce a single instance of that type -- to a list of
     * arguments of that type. The functions are applied in an iterative manner, and
     * the result of each function is stored in the list in an iterative manner as
     * well, to be used by the next bifunction in the next iteration.
     * For example, given
     * List<Integer> args = [1,1,3,0,4], and
     * List<BiFunction<Double, Double, Double>> bfs = [add, multiply, add, divide],
     * <code>zip(args, bfs)</code> will proceed iteratively as follows:
     * - index 0: the result of add(1,1) is stored in args[1] to yield args = [1,2,3,0,4]
     * - index 1: the result of multiply(2,3) is stored in args[2] to yield args = [1,2,6,0,4]
     * - index 2: the result of add(6,0) is stored in args[3] to yield args = [1,2,6,6,4]
     * - index 3: the result of divide(6,4) is stored in args[4] to yield args = [1,2,6,6,1]
     *
     * @param args: the arguments over which <code>bifunctions</code>
     * will be iteratively applied.
     * @param bifunctions: the given list of bifunctions that will iteratively be
     * applied on the <code>args</code>.
     * @param <T>: the type parameter of the arguments (e.g., Integer, Double)
     * @return the last element in <code>args</code>, the final result of
     * all the bifunctions being applied in sequence.
     */
    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions) throws IllegalArgumentException{
        if(args.size() != bifunctions.size()+1) {
            System.out.println("Arguments do not line up with bifunctions. Zip has failed.");
            throw new IllegalArgumentException("Incorrect Number of Args");
        }
        for(int i = 0; i < args.size()-1; i++) {
            args.set(i+1, bifunctions.get(i).apply(args.get(i), args.get(i+1)));
        }
        System.out.println(args);
        return args.get(args.size()-1);

    }

    static class FunctionComposition <T, U, R> {
        BiFunction<Function<T, U>, Function<U, R>, Function<T, R>> composition = Function::andThen;
    }
}
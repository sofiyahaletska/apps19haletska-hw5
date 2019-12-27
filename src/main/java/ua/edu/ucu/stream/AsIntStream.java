package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Iterator;

public class AsIntStream implements IntStream {

    private final Iterator<Integer> listIterator;
    private final int amount;
    private ArrayList<Integer> list;

    private AsIntStream(ArrayList<Integer> values, int amount) {
        this.list = values;
        this.listIterator = list.iterator();
        this.amount = amount;

    }

    public static IntStream of(int... values) {
        ArrayList<Integer> listOfValues = new ArrayList<Integer>();
        for(int i = 0; i < values.length; i++){
            listOfValues.add(i, values[i]);
        }
        return new AsIntStream(listOfValues, values.length);
    }
    private void cheakEmptyness(){
        if(amount == 0) {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public Double average() {
        cheakEmptyness();
        return  (double) sum() /amount;
    }
    private Integer cmp(String operation){
        Iterator<Integer> Iter = listIterator;
        int value = Iter.next();
        while (Iter.hasNext()){
            int currentEl = Iter.next();
            if (operation.equals("greater")) {
                if (currentEl > value) {
                    value = currentEl;
                }
            }
            else if (operation.equals("lesser")){
                if (currentEl < value) {
                    value = currentEl;
                }
            }
        }
        return value;
    }
    @Override
    public Integer max() {
        cheakEmptyness();
        return cmp("greater");
    }

    @Override
    public Integer min() {
        cheakEmptyness();
        return cmp("lesser");
    }

    @Override
    public long count() {
        return amount;
    }

    @Override
    public Integer sum() {
        cheakEmptyness();
        Iterator<Integer> sumIter = listIterator;
        int sum = 0;
        while (sumIter.hasNext()){
            sum += sumIter.next();
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        Iterator<Integer> filterIter = listIterator;
        ArrayList<Integer> values = new ArrayList<>();
        while (filterIter.hasNext()){
            int currentEl =  filterIter.next();
            if(predicate.test(currentEl)){
                values.add(currentEl);
            }
        }
        return new AsIntStream(values, values.size());
    }

    @Override
    public void forEach(IntConsumer action) {
        Iterator<Integer> forEachIter = listIterator;
        while (forEachIter.hasNext()){
            action.accept(forEachIter.next());
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        Iterator<Integer> mapIter = listIterator;
        ArrayList<Integer> values = new ArrayList<>();
        while (mapIter.hasNext()){
            int currentEl =  mapIter.next();
            values.add(mapper.apply(currentEl));
        }
        return new AsIntStream(values, values.size());
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
//        Iterator<Integer>flatMapIter = this.listIterator;
//        ArrayList<Integer> values = new ArrayList<>();
//        while (flatMapIter.hasNext()){
//            values.add(func.applyAsIntStream(flatMapIter.next()));
//        }
//        return new AsIntStream(values, values.size());
        return new AsIntStream(list, list.size());
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        Iterator<Integer> reduceIter = listIterator;
        boolean flag = false;
        int reduced = 0;
        if (reduceIter.hasNext()){
            flag = true;
            reduced = op.apply(identity, reduceIter.next());
        }
        while (reduceIter.hasNext()){
            reduced = op.apply(reduced, reduceIter.next());
        }
        if(flag){
            return reduced;
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        int[] array = new int[amount];
        Iterator<Integer> arrIter = listIterator;
        for (int i = 0; arrIter.hasNext(); i++){
            array[i] = arrIter.next();
        }
        return array;
    }

}

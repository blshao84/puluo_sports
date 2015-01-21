/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puluo.util;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.puluo.util.Pair;

/**
 *
 * @author hd2210
 */
public class KeyValueHeap {

    PriorityQueue<Pair> heap;
    PriorityQueue<Pair> au_heap;
    int size;

    class PositiveKeyValueComparator implements Comparator{

        @Override
        public int compare(Object item1, Object item2){

            double value1 = (Double) ((Pair)item1).Second();
            double value2 = (Double) ((Pair)item2).Second();

            if(value1 > value2)
                return -1;
            else if(value1 < value2)
                return 1;
            else
		return 0;
	}
    }

    class NegativeKeyValueComparator implements Comparator{

        @Override
        public int compare(Object item1, Object item2){

            double value1 = (Double) ((Pair)item1).Second();
            double value2 = (Double) ((Pair)item2).Second();

            if(value1 > value2)
                return 1;
            else if(value1 < value2)
                return -1;
            else
		return 0;
	}
    }

    public KeyValueHeap(int size){
        this.size = size;
        heap = new PriorityQueue(size,new PositiveKeyValueComparator());
        au_heap = new PriorityQueue(size, new NegativeKeyValueComparator());
    }

    public void add(Pair item){
        if(heap.size() < size){
            heap.add(item);
            au_heap.add(item);
        }else{
            double current_value = (Double) item.Second();
            double min_value = (Double) au_heap.peek().Second();
            if(current_value > min_value){
                Pair<String,Double> remove = au_heap.poll();
                heap.remove(remove);
                heap.add(item);
                au_heap.add(item);
            }
        }
    }

    public Pair poll(){
        Pair<String,Double> item = heap.poll();
        au_heap.remove(item);
        return item;
    }

    public Pair peek(){
        return heap.peek();
    }

    public void clear(){
        heap.clear();
        au_heap.clear();
    }

    public int size(){
        return heap.size();
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    public static void main(String[] args){
        KeyValueHeap heap = new KeyValueHeap(5);
        heap.add(new Pair<String,Double>("one",new Double(1)));
        heap.add(new Pair<String,Double>("two",new Double(2)));
        heap.add(new Pair<String,Double>("three",new Double(3)));
        heap.add(new Pair<String,Double>("four",new Double(4)));
        heap.add(new Pair<String,Double>("five",new Double(5)));
        heap.add(new Pair<String,Double>("six",new Double(6)));
        heap.add(new Pair<String,Double>("seven",new Double(7)));
        heap.add(new Pair<String,Double>("eight",new Double(8)));
        heap.add(new Pair<String,Double>("nine",new Double(9)));
        heap.add(new Pair<String,Double>("ten",new Double(10)));

        while(!heap.isEmpty()){
            Pair<String,Double> pair = heap.poll();
            System.out.println(pair.First());
        }
    }
}

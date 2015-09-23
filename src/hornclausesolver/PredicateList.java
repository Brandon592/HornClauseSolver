/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hornclausesolver;

import java.util.ArrayList;

/**
 *
 * @author Brandon
 */
public class PredicateList {
    
    public PredicateList(ArrayList<Predicate> predicates){
        this.predicates = predicates;
    }
    
    public PredicateList(PredicateList other){
        predicates = new ArrayList();
        for (int i = 0; i < other.size(); i++){
            predicates.add(new Predicate(other.get(i)));
        }
    }

    public ArrayList<Predicate> getPredicates() {
        return predicates;
    }

    public int size() {
        return predicates.size();
    }
    
    public boolean isEmpty(){
        return predicates.isEmpty();
    }
    
    public Predicate get(int index){
        return predicates.get(index);
    }
    
    public Predicate remove(int index){
        return predicates.remove(index);
    }
    
    public boolean remove(Predicate p){
        return predicates.remove(p);
    }
    
    public void addAll(PredicateList list){
        predicates.addAll(list.predicates);
    }
    
    private ArrayList<Predicate> predicates;
}

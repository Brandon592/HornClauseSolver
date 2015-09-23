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
public class TermList {
    
    public TermList(ArrayList<Term> terms){
        this.terms = terms;
    }
    
    public TermList(TermList other){
        this.terms = new ArrayList(other.getTerms());
    }

    public ArrayList<Term> getTerms() {
        return terms;
    }
    
    public Term get(int i){
        return terms.get(i);
    }

    public void set(int i, Term newTerm) {
        this.terms.set(i, newTerm);
    }
    
    public int size(){
        return terms.size();
    }
    
    public int indexOf(Term t){
        return terms.indexOf(t);
    }
    
    public boolean resolveable(TermList other){
        for(int i=0; i<terms.size(); i++){
            if(!terms.get(i).resolveable(other.getTerms().get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TermList other = (TermList) obj;
        for (int i = 0; i < terms.size(); i++){
            if (!terms.get(i).equals(other.get(i))){
                return false;
            }
        }
        return true;
    }

 
    private ArrayList<Term> terms;

}

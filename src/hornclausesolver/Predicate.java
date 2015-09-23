/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hornclausesolver;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Brandon
 */
public class Predicate {
    
    public Predicate(){
        this.name = "";
        this.terms = new TermList(new ArrayList());
        this.arity = 0;
    }
    
    public Predicate(String name, TermList terms){
        this.name = name;
        this.terms = terms;
        this.arity = terms.size();
    }
    
    public Predicate(Predicate other){
        this.name = other.name;
        this.terms = new TermList(other.terms);
        arity = terms.size();
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getArity(){
        return terms.size();
    }
    
    public Term getTerm(int index){
        return terms.get(index);
    }
    
    public TermList getTerms(){
        return terms;
    }
    
    @Override
    public boolean equals(Object other){
        if (other == null){
            return false;
        }
        if (getClass() != other.getClass()){
            return false;
        }
        Predicate otherPredicate = (Predicate) other;
        if (otherPredicate.getName().equals(name) && otherPredicate.getArity() == terms.size()){
            return true;
        }
        return false;
    }
    
    public boolean deepEquals(Predicate other){
        if (!this.equals(other)){
            return false;
        }
        return this.terms.equals(other.terms);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.terms);
        return hash;
    }
    
    public boolean hasVars(){
        for (Term p: terms.getTerms()){
            if (p instanceof Variable){
                return true;
            }
        }
        return false;
    }
    
    public boolean containsVar(Variable other){
        for (Term p: terms.getTerms()){
            if (p.equals(other)){
                return true;
            }
        }
        return false;
    }
    
    public void swapVar(Variable original, Term newParam){
        terms.set(terms.indexOf(original), newParam);
    }
    
    public boolean resolveable(Predicate p){
        if(!p.getName().equals(this.getName())){
            return false;
        }
        if(p.getArity()!=this.getArity()){
            return false;
        }
        if(!p.getTerms().resolveable(this.getTerms())){
            return false;
        }
        return true;
    }
    
    public ArrayList<HornClause> getCompatibleRules(ArrayList<HornClause> rules){
        ArrayList<HornClause> compRules = new ArrayList();
        for(HornClause hc: rules){
            if(resolveable(hc.getConsequent())){
                compRules.add(new HornClause(hc));
            }
        }
        return compRules;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("(");
        sb.append(terms.get(0).toString());
        for (int i = 1; i < terms.size(); i++){
            sb.append(", ");
            sb.append(terms.get(i).toString());
        }
        sb.append(")");
        return sb.toString();
    }
    
    private final String name;
    private final TermList terms;
    private final int arity;
}

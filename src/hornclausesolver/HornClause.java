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
public class HornClause {

    public HornClause(Predicate consequent) {
        this.consequent = consequent;
        this.antecedents = new PredicateList(new ArrayList());
    }
    
    public HornClause(PredicateList antecedents){
        this.antecedents = antecedents;
        this.consequent = new Predicate();
    }

    public HornClause(Predicate consequent, PredicateList antecedents) {
        this.antecedents = antecedents;
        this.consequent = consequent;
    }

    public HornClause(HornClause other) {
        this.consequent = new Predicate(other.consequent);
        this.antecedents = new PredicateList(other.antecedents);
    }

    public boolean isAssertion() {
        return antecedents.isEmpty();
    }
    
    public PredicateList getAntecedents(){
        return this.antecedents;
    }
    
    public Predicate getConsequent(){
        return consequent;
    }
    
    public void swapParam(Variable original, Term newParam){
        if(consequent.containsVar(original)){
            consequent.swapVar(original, newParam);
        }
        for(Predicate p: antecedents.getPredicates()){
            if(p.containsVar(original)){
                p.swapVar(original, newParam);
            }
        }
    }
    
    public void resolve(HornClause other, int index){
        Predicate toReplace = antecedents.get(index);
        Predicate replacement = other.consequent;
        for (int i = 0; i < toReplace.getTerms().size(); i++){
            if (toReplace.getTerm(i) instanceof Variable){
                swapParam((Variable) toReplace.getTerm(i), replacement.getTerm(i));
            }else if (replacement.getTerm(i) instanceof Variable){
                other.swapParam((Variable) replacement.getTerm(i), toReplace.getTerm(i));
            }
        }
        antecedents.remove(index);
        antecedents.addAll(other.antecedents);
    }
    
    public void resolve(BindingList bindings){
        for(Binding b : bindings.getBindings()){
            if(b.prev instanceof Variable){
                swapParam((Variable)b.prev, b.post);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!consequent.getName().isEmpty()){
            sb.append(consequent);
        }
        if (!antecedents.isEmpty()) {
            sb.append(":-");
            sb.append(antecedents.get(0).toString());
            for (int i = 1; i < antecedents.size(); i++) {
                sb.append(", ");
                sb.append(antecedents.get(i).toString());
            }
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final HornClause other = (HornClause) obj;
        if (!this.consequent.deepEquals(other.consequent)) {
            return false;
        }
        for (int i = 0; i < antecedents.size(); i++){
            if (!antecedents.get(i).deepEquals(other.antecedents.get(i)))
                return false;
        }
        return true;
    }

    private Predicate consequent;
    private PredicateList antecedents;
}

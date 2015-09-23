/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hornclausesolver;

import java.util.Objects;

/**
 *
 * @author Brandon
 */
public abstract class Term {
    
    public Term(String name){
        this.name = name;
    }
    
    public static Term copyTerm(Term t){
        if(t instanceof Variable){
            return new Variable((Variable) t);
        } else{
            return new Constant((Constant) t);
        }
    }
    
    public String getName(){
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Term other = (Term) obj;
        return this.name.equals(other.name);
    }
    
    public abstract boolean resolveable(Term t);
    
    protected final String name;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hornclausesolver;

/**
 *
 * @author Brandon
 */
public class Variable extends Term{
    
    public Variable(String name, int subscript){
        super(name);
        this.subscript = subscript;
    }
    
    public Variable(Variable v){
        super(v.name);
        this.subscript = v.subscript;
    }
    
    public int getSubscript(){
        return subscript;
    }
    
    public void incSubscript(){
        subscript++;
    }
    
    public boolean equals(Variable other){
        return other.getName().equals(this.getName()) && this.subscript == other.subscript;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.subscript;
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
        final Variable other = (Variable) obj;
        return other.getName().equals(this.getName()) && this.subscript == other.subscript;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(subscript);
        return sb.toString();
    }
    
    private int subscript;

    @Override
    public boolean resolveable(Term t) {
        return true;
    }
}

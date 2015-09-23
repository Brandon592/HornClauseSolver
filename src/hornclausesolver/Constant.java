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
public class Constant extends Term {
    
    public Constant(String name){
        super(name);
    }
    
    public Constant (Constant c){
        super(c.name);
    }
    
    @Override
    public String toString(){
        return name;
    }

    @Override
    public boolean resolveable(Term t) {
        if(t instanceof Variable){
            return true;
        }
        if(t.getName().equals(this.getName())){
            return true;
        }
        return false;
    }
    
}

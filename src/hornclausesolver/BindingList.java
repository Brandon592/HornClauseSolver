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
public class BindingList {
    
    public BindingList(){
        
    }
    
    public BindingList(BindingList b){
        for(Binding temp : b.bindings){
            this.bindings.add(new Binding(Term.copyTerm(temp.prev), Term.copyTerm(temp.post)));
        }
    }

    public ArrayList<Binding> getBindings() {
        return bindings;
    }
    
    public boolean contains(Term prev){
        for(Binding b: bindings){
            if(b.getPrev().equals(prev)){
                return true;
            }  
        }
        return false;
    }
    
    public Term getBinding(Term prev){
        for(Binding b: bindings){
            if(b.getPrev().equals(prev)){
                return b.post;
            }  
        }
        return null;
    }
    
    public boolean isCompatible(BindingList other){
        for(Binding b: bindings){
            if(other.contains(b.prev)){
                if(b.post instanceof Constant && !other.getBinding(b.prev).equals(b.post)){
                    return false;
                }
            }
        }
        return true;
    }
    
    public void resolve(BindingList other){
        for(Binding b: bindings){
            if(other.contains(b.prev) && b.post instanceof Variable){
                b.post = other.getBinding(b.prev);
            }
        }
    }
    
    public void add(Binding b){
        bindings.add(b);
    }
    
    private ArrayList<Binding> bindings = new ArrayList();
}

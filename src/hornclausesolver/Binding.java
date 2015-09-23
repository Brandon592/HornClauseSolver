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
public class Binding {
    
    public Binding(Term prev, Term post){
        this.prev = prev;
        this.post = post;
    }

    public Term getPrev() {
        return prev;
    }

    public void setPrev(Term prev) {
        this.prev = prev;
    }

    public Term getPost() {
        return post;
    }

    public void setPost(Term post) {
        this.post = post;
    }
    
    
    
    Term prev;
    Term post;
}

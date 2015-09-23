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
public class StringTokenizer {
    
    public StringTokenizer(String str){
        this(str, null);
    }
    
    public StringTokenizer (String str, String delims){
        this(str, delims, false);
    }
    
    public StringTokenizer (String str, String delims, boolean retDelims){
        this(str, delims, retDelims, false);
    }
    
    public StringTokenizer (String str, String delims, boolean retDelims, boolean ignoreWhiteSpace){
        this.str = str;
        this.dels = delims;
        this.rDels = retDelims;
        this.igWS = ignoreWhiteSpace;
        this.pos = 0;
        if (igWS){
            this.str = this.str.trim();
        }
        if (!rDels){
            skipDelims();
        }
    }
    
    public StringTokenizer(StringTokenizer st){
        this (st.str, st.dels, st.rDels, st.igWS);
        this.pos = st.pos;
    }
    
    public boolean hasNextToken(){
        return (pos < str.length() || peeking);
    }
    
    public String nextToken(){
        String retString;
        if (peeking){
            retString = peekstr;
            peeking = false;
            return retString;
        }
        if (pos >= str.length()){
            return null;
        }
        while (rDels && pos < str.length() && isDelim(pos)){
            if (!igWS || !isWhiteSpace(pos)){
                retString = str.substring(pos, pos+1);
                ++pos;
                return retString;
            }else{
                ++pos;
            }
        }
        int endPos;
        if (str.charAt(pos) == '\''){
            endPos = pos + 1;
            while (endPos < str.length() &&
                    (str.charAt(endPos) != '\'' || str.charAt(endPos-1)=='\\')){
                ++endPos;
        }
        if (endPos < str.length()){
            ++endPos;
        }
        }else if (str.charAt(pos) == '"'){
            endPos = pos + 1;
            while (endPos < str.length() && 
                    (str.charAt(endPos) != '"' || str.charAt(endPos-1)=='\\')) {
                ++endPos;
            }
            ++endPos;
        }else {
            endPos = pos+1;
            while(endPos < str.length() && !isDelim(endPos)){
                ++endPos;
            }
        }
        
        retString = str.substring(pos, endPos);
        pos = endPos;
        if (!rDels) {
            skipDelims();
        }else if (igWS) {
            skipWhiteSpace();
        }
        return retString;
    }
    
    public String peek(){
        this.peekstr = this.nextToken();
        this.peeking = true;
        return peekstr;
    }
    
    private void skipDelims(){
        while (pos < str.length() && (isDelim(pos) || (!igWS && isWhiteSpace(pos)))){
            ++pos;
        }
    }
    
    private boolean isDelim(int pos){
        if(dels == null){
            return isWhiteSpace(pos);
        }else{
            return dels.indexOf(str.charAt(pos)) >= 0;
        }
    }
    
    private boolean isWhiteSpace(int pos){
        return Character.isWhitespace(str.charAt(pos));
    }
    
    private void skipWhiteSpace(){
        while(pos < str.length() && isWhiteSpace(pos)){
            ++pos;
        }
    }
    
    private String str;
    private String peekstr;
    private String dels;
    private boolean rDels;
    private boolean igWS;
    private boolean peeking;
    private int pos;
}

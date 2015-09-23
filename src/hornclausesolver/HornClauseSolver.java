/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hornclausesolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Brandon
 */
public class HornClauseSolver {

    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the file path: ");
        String filename = scanner.nextLine();
        try {
            Scanner reader = new Scanner(new File(filename));
            while (reader.hasNext()) {
                ArrayList<Predicate> antecedents = new ArrayList();
                StringTokenizer st = new StringTokenizer(reader.nextLine(), " :-,()", true, true);
                Predicate consequent = parsePredicate(st);
                if (st.hasNextToken()) {
                    st.nextToken();
                    st.nextToken();
                    antecedents = parseAntecedents(st);
                }
                if (antecedents.isEmpty()) {
                    rules.add(new HornClause(consequent));
                } else {
                    rules.add(new HornClause(consequent, new PredicateList(antecedents)));
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        System.out.println();
        for (HornClause rule : rules) {
            System.out.println(rule);
        }
        while (true) {
            System.out.println();
            System.out.print("Please enter your query in the form of one or more predicates separated by commas"
                    + "or type 'quit' to exit\n>");
            String input = scanner.nextLine();
            if (input.equals("quit")) {
                System.exit(0);
            }
            StringTokenizer st = new StringTokenizer(input, " :-,()", true, true);
            HornClause toResolve = new HornClause(new PredicateList(parseAntecedents(st)));
            resolve(toResolve);
        }
    }

    public ArrayList<Predicate> parseAntecedents(StringTokenizer st) {
        ArrayList<Predicate> antecedents = new ArrayList();
        while (st.hasNextToken()) {
            antecedents.add(parsePredicate(st));
            st.nextToken();
        }
        return antecedents;
    }

    public Predicate parsePredicate(StringTokenizer st) {
        String name = st.nextToken();
        st.nextToken();
        ArrayList<Term> params = new ArrayList();
        while (!st.peek().equals(")")) {
            if (st.peek().startsWith("?")) {
                params.add(new Variable(st.nextToken(), rules.size()));
            } else {
                params.add(new Constant(st.nextToken()));
            }
            if (st.peek().equals(",")) {
                st.nextToken();
            }
        }
        st.nextToken();
        return new Predicate(name, new TermList(params));
    }

    public void resolve(HornClause hc) {
        ArrayList<ArrayList<HornClause>> possibleResolutions = new ArrayList();
        ArrayList<ArrayList<HornClause>> solutions = new ArrayList();
        for (Predicate p : hc.getAntecedents().getPredicates()) {
            ArrayList<HornClause> pResolutions = (p.getCompatibleRules(rules));
            for (HornClause res : pResolutions) {
                for (int i = 0; i < res.getConsequent().getTerms().size(); i++) {
                    if (res.getConsequent().getTerm(i) instanceof Variable) {
                        res.swapParam((Variable) res.getConsequent().getTerm(i), p.getTerm(i));
                    }
                }
            }
            possibleResolutions.add(pResolutions);
        }
        for (int index = 0; index < possibleResolutions.size(); index++) {
            ArrayList<HornClause> tempResolutions = new ArrayList();
            ArrayList<HornClause> list = possibleResolutions.get(index);
            while (!list.isEmpty()) {
                ArrayList<HornClause> toKeep = new ArrayList();
                for (HornClause clause : list) {
                    if (clause.isAssertion() && !tempResolutions.contains(clause)) {
                        tempResolutions.add(clause);
                    }
                    for (int j = 0; j < clause.getAntecedents().size(); j++) {
                        Predicate p = clause.getAntecedents().get(j);
                        ArrayList<HornClause> tempRules = p.getCompatibleRules(rules);
                        if (tempRules.isEmpty()) {
                            break;
                        } else {
                            for (int i = 0; i < tempRules.size(); i++) {
                                HornClause newClause = new HornClause(clause);
                                newClause.resolve(tempRules.get(i), j);
                                toKeep.add(newClause);
                            }
                        }
                    }
                }
                list.clear();
                list.addAll(toKeep);
            }
            solutions.add(tempResolutions);
        }
        ArrayList<ArrayList<BindingList>> bindings = new ArrayList();
        for (int i = 0; i < hc.getAntecedents().size(); i++) {
            ArrayList<BindingList> tempBindings = new ArrayList();
            ArrayList<HornClause> temp = solutions.get(i);
            Predicate prev = hc.getAntecedents().get(i);
            for (HornClause clause : temp) {
                BindingList bList = new BindingList();
                for (int j = 0; j < clause.getConsequent().getArity(); j++) {
                    Binding b = new Binding(prev.getTerm(j), clause.getConsequent().getTerm(j));
                    bList.add(b);
                }
                tempBindings.add(bList);
            }
            bindings.add(tempBindings);
        }
        BindingList origBindings = new BindingList();
        for (Predicate p : hc.getAntecedents().getPredicates()) {
            for (Term t : p.getTerms().getTerms()) {
                if (!origBindings.contains(t)) {
                    origBindings.add(new Binding(t, t));
                }
            }
        }
        ArrayList<BindingList> compatibleBindings = resolveSolutions(bindings, origBindings);
        ArrayList<HornClause> resolutions = new ArrayList();
        for(BindingList b : compatibleBindings){
            HornClause temp = new HornClause(hc);
            temp.resolve(b);
            resolutions.add(temp);
        }
        if (resolutions.size() == 0){System.out.println("No rules meet the criteria");}
        for (HornClause resolution : resolutions) {
            System.out.println(resolution);
        }
    }

    private ArrayList<BindingList> resolveSolutions(ArrayList<ArrayList<BindingList>> solutions, BindingList origBindings) {
        ArrayList<BindingList> resolutions = new ArrayList();
        for (BindingList list : solutions.get(0)) {
            BindingList temp = new BindingList(origBindings);
            if (temp.isCompatible(list)) {
                temp.resolve(list);
                if (solutions.size() > 1){
                    resolutions.addAll(this.resolveSolutionsHelper(solutions, 1, temp));
                }else{
                    resolutions.add(temp);
                }
            }
        }
        return resolutions;
    }

    private ArrayList<BindingList> resolveSolutionsHelper(ArrayList<ArrayList<BindingList>> solutions, int index, BindingList current) {
        ArrayList<BindingList> compatibleSolutions = new ArrayList();
        for (BindingList list : solutions.get(index)) {
            BindingList temp = new BindingList(current);
            if (temp.isCompatible(list)) {
                temp.resolve(list);
                if(index<solutions.size()-1){
                    compatibleSolutions.addAll(this.resolveSolutionsHelper(solutions, index + 1, temp));
                } else{
                    compatibleSolutions.add(temp);
                }
            }
        }
        return compatibleSolutions;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HornClauseSolver solver = new HornClauseSolver();
        solver.init();
    }

    private final ArrayList<HornClause> rules = new ArrayList();
}

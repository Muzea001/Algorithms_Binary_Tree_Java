package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Kan ikke ha nullverdier");

        //p starter i roten. Lager et hjelpevariabel
        Node<T> p = rot,
                q = null;
        int midlertidig = 0;

        while (p != null) {
            q = p;
            midlertidig = comp.compare(verdi, p.verdi);
            p = midlertidig < 0 ? p.venstre : p.høyre;
        }

        p = new Node<>(verdi, null);

        if (q == null) rot = p;
        else if (midlertidig < 0) q.venstre = p;
        else q.høyre = p;

        if (q != null) {
            p.forelder = q;
        } else {
            p.forelder = null;
        }
        antall++;
        endringer++;
        return true;
    }

    public boolean fjern(T verdi) {
        if (verdi==null){
            return false;
        }

        Node<T> p = rot, q = null;
        while (p!=null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp > 0) {
                q = p;
                p = p.venstre;
            } else if (p == null) {
                return false;
            }
        }

            if (p.venstre==null|| p.høyre==null){
                Node<T> barn = p.venstre!=null ? p.venstre:p.høyre;
                if (p==rot){
                    rot = barn;
                }
                else if (q==q.venstre){
                    q.venstre=barn;
                }
                else {
                    q.høyre=barn;
                }
            }
            else {
                Node<T>s = p, r = p.høyre;
                while (r.venstre!=null){
                    s=r;
                    r=r.venstre;
                }
                p.verdi=r.verdi;

                if (s!=p){
                    s.venstre=r.høyre;
                }
                else {
                    s.høyre = r.høyre;
                }
                if (r.høyre!=null){
                    r.høyre.forelder=s;
                }
            }
            antall--;
            return true;


    }

    public int fjernAlle(T verdi) {
        if(tom()){
            return 0;
        }

        int verdiAntall = 0;
        while (fjern(verdi)){
            verdiAntall++;
        }
        return verdiAntall;
    }

    public int antall(T verdi) {
        if (verdi == null) {
            return 0;
        }
        int antall = 0;
        Node<T> p = rot;
        while (p!= null) {
            int midlertidig = comp.compare(verdi, p.verdi);
            if (midlertidig < 0){
                p = p.venstre;
            }
            else {
                if (midlertidig == 0) {
                    antall++;
                }
                    p = p.høyre;

            }

        }

        return antall;

    }

    public void nullstill() {
       if (antall==0){
           return;
       }

       Node<T> p = rot;
       int avslutning = antall;
       p = førstePostorden(p);

       while (avslutning!=0){
           if (p!=null){
               fjern(p.verdi);
           }

           if (p!=null){
               p.verdi=null;
           }
           if (p!=null){
               p = nestePostorden(p);
           }
           avslutning--;
       }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p);
        while (true) {
            if (p.venstre != null) {
                p = p.venstre;
            } else if (p.høyre != null) {
                p = p.høyre;
            } else {
                return p;
            }
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> f = p.forelder;
        if (f == null) {
            return null;
        }

        if (f.høyre == p || f.høyre == null) {
            return f;
        } else return førstePostorden(f.høyre);
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot;

        Node<T> første = førstePostorden(p);
        oppgave.utførOppgave(første.verdi);
        while (første.forelder != null) {
            første = nestePostorden(første);
            oppgave.utførOppgave(Objects.requireNonNull(første.verdi));
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        if (!tom()){
        postordenRecursive(rot,oppgave);
        }

    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p==null){
            return;
        }
        postordenRecursive(p.venstre,oppgave);
        postordenRecursive(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        ArrayList<T>treeArray = new ArrayList<>();
        Queue<Node<T>> queue = new LinkedList<>();

        queue.add(rot);
        while (!queue.isEmpty()){
            Node<T> p = queue.remove();
            treeArray.add(p.verdi);
            if (p.venstre!=null){
                queue.add(p.venstre);
            }
            if (p.høyre!=null){
                queue.add(p.høyre);
            }
        }
        return treeArray;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tree = new SBinTre<>(c);
        for (K verdi : data){
            tree.leggInn(verdi);
        }
        return tree;
    }


} // ObligSBinTre

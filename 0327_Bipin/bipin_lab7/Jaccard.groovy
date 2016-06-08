package bipin_lab7;

import java.util.HashSet;

/**
 * Created by bips on 6/6/16.
 */
public class Jaccard {

    public static float jaccardSimilarity(String d1, String d2){
        def document1List=d1.tokenize(" ").unique()
        def document2List=d2.tokenize(" ").unique()

        def intersectionWords=intersection(document1List,document2List)//contains words after intersection
        def unionWords= union(document1List,document2List)// contains words after union of two documents

        //calculation of jaccard coefficient based on intersection and union of two documents.
        def jaccardCoefficient=intersectionWords.size()/unionWords.size()
        return jaccardCoefficient
    }

    public static void main(String[] args) {
        String d1="I am Sam" //test document
        String d2="Sam I am"//another test document
        String d3="I do not like green eggs and ham"
        String d4="I do not like them Sam I am"

        println "Jaccard similarity of d1 and d2: " + jaccardSimilarity(d1,d2)
        println "Jaccard similarity of d1 and d3: " + jaccardSimilarity(d1,d3)

    }


    public static def union(def document1,document2){
        return (document1+document2).unique()

    }

    public static def intersection(def document1,def document2){
        HashSet intersection=new HashSet()
        for(int i=0;i<document1.size();i++){
            for(int j=0;j<document2.size();j++){
                if(document1[i]==document2[j]){
                    intersection.add(document1[i])
                }
            }
        }
        return intersection
    }
}

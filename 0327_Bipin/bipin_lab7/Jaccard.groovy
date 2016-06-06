package bipin_lab7;

import java.util.HashSet;

/**
 * Created by bips on 6/6/16.
 */
public class Jaccard {
    public static void main(String[] args) {
        String d1="I am Bipin am Saroj" //test document
        String d2="I am Saroj"//another test document

        def document1=d1.tokenize(" ").unique()
        def document2=d2.tokenize(" ").unique()

        def intersectionWords=intersection(document1,document2)//contains words after intersection

        def unionWords=union(document1,document2) // contains words after union of two documents
        println(unionWords)

        //calculation of jaccard coefficient based on intersection and union of two documents.
        def jaccardCoefficient=intersectionWords.size()/unionWords.size()
        println(jaccardCoefficient)
    }


    public static def union(def document1,document2){
        return (document1+document2).unique()

    }

    public static def intersection(def document1,def document2){
        // println(document1)
        HashSet returnDocument=new HashSet()
        for(int i=0;i<document1.size();i++){
            for(int j=0;j<document2.size();j++){
                if(document1[i]==document2[j]){
                    returnDocument.add(document1[i])
                }
            }
        }
        return returnDocument
    }
}

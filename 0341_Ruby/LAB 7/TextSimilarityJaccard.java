import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Ruby on 6/7/2016.
 */
public class TextSimilarityJaccard {
    public static void main(String[] args) {
        String [] documents=new String[4];
        documents[0]="I am Ruby.";
        documents[1]="Ruby I am.";
        documents[2]="I do not like green eggs and ham.";
        documents[3]="I do not like them, Sam I am.";

        for (int i=0; i<documents.length; i++){
            documents[i]=documents[i].replaceAll("[.,!?;:]", "");
        }

        double[][] similarity=new double[4][4];
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        System.out.println("========Similairty by Jaccard Method=======");
        System.out.println("X\tD1\t\tD2\t\tD3\t\tD4");
        for (int i=0; i<4; i++){
            System.out.print("D"+ (i+1) + "\t");
            for (int j=0; j<4; j++){
                similarity[i][j]=(float)getIntersectionCount(documents[i], documents[j])/getUnionCount(documents[i], documents[j]);
                System.out.print(numberFormat.format(similarity[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    static int getUnionCount(String doc1, String doc2){
        String doc=doc1.toUpperCase()+" " + doc2.toUpperCase();
        Set<String> uniqueWords=new HashSet<String>(Arrays.asList(doc.split(" ")));
        return uniqueWords.size();
    }

    static int getIntersectionCount(String doc1, String doc2){
        Set<String> first;
        Set<String> second;
        int count=0;
        if (doc1.length()<=doc2.length()){
            first=new HashSet<String>(Arrays.asList(doc1.toUpperCase().split(" ")));
            second=new HashSet<String>(Arrays.asList(doc2.toUpperCase().split(" ")));
        }else {
            first=new HashSet<String>(Arrays.asList(doc2.toUpperCase().split(" ")));
            second=new HashSet<String>(Arrays.asList(doc1.toUpperCase().split(" ")));
        }
        Iterator iterator=first.iterator();
        while (iterator.hasNext()){
            if (second.contains(iterator.next())){
                count++;
            }
        }
        return count;
    }
}



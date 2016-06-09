import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Shreha on 6/8/2016.
 */
public class JaccardTextSimilarity {
    public static void main(String[] args) {
        String [] document=new String[4];
        document[0]="I am Sam.";
        document[1]="Sam I am.";
        document[2]="I do not like green eggs and ham.";
        document[3]="I do not like them, Sam I am.";

        for (int i=0; i<document.length; i++){
            document[i]=document[i].replaceAll("[.,!?;:]", "");
        }

        double[][] similarity=new double[4][4];
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        System.out.println("Using Jacard Method, we get");
        System.out.println("X\t\tDoc1\tDoc2\tDoc3\tDoc4");
        for (int i=0; i<4; i++){
            System.out.print("Doc"+ (i+1) + "\t");
            for (int j=0; j<4; j++){
                similarity[i][j]=(float) totalIntersectiCount(document[i], document[j])/ totalCountofUnion(document[i], document[j]);
                System.out.print(numberFormat.format(similarity[i][j]) + "\t");
            }
            System.out.println();
        }
    }


    static int totalCountofUnion(String doc1, String doc2){
        String doc=doc1.toUpperCase()+" " + doc2.toUpperCase();
        Set<String> uniqueWords=new HashSet<String>(Arrays.asList(doc.split(" ")));
        return uniqueWords.size();
    }

    static int totalIntersectiCount(String doc1, String doc2){
        Set<String> first_string;
        Set<String> second_string;
        int count=0;
        if (doc1.length()<=doc2.length()){
            first_string=new HashSet<String>(Arrays.asList(doc1.toUpperCase().split(" ")));
            second_string=new HashSet<String>(Arrays.asList(doc2.toUpperCase().split(" ")));
        }else {
            first_string=new HashSet<String>(Arrays.asList(doc2.toUpperCase().split(" ")));
            second_string=new HashSet<String>(Arrays.asList(doc1.toUpperCase().split(" ")));
        }
        Iterator iterator=first_string.iterator();
        while (iterator.hasNext()){
            if (second_string.contains(iterator.next())){
                count++;
            }
        }
        return count;
    }
}


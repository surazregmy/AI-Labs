package bipin_lab7
/**
 * Created by bips on 6/1/16.
 */
class TextSimilarity {
    public static void main(String[] args) {
        String d1="I am Bipin am Saroj"
        String d2="I am Saroj"

//        def document1=d1.tokenize(" ").unique()
//        def document2=d2.tokenize(" ").unique()
//
//        def intersectionWords=intersection(document1,document2)//contains words after intersection
//
//        def unionWords=union(document1,document2) // contains words after union of two documents
//        println(unionWords)
//
//        //calculation of jaccard coefficient based on intersection and union of two documents.
//        def jaccardCoefficient=intersectionWords.size()/unionWords.size()
//        println(jaccardCoefficient)

        def documents=[["I","am","Bipin","am","Saroj"],["I", "am", "Saroj"]]
        def unionWords=union(documents[1],documents[2])
        def documentMap=[]
        def documentVector=[]
        documents.each {
            def temp=[:]
            it.each {
                for (int i=0;i<documentMap.size();i++){
                    temp[documentMap[i].tolowerCase()]=0
                }
                if (temp.containsKey(it.toLowerCase())){
                    temp[it.toLowerCase()]+=1
                }
                else{
                    temp[it.toLowerCase()]=1
                }
            }
            documentMap.add(temp)
        }
        println(documentMap)
        def termFrequencyVector=[]



    }


}

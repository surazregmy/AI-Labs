package bipin_lab7
/**
 * Created by bips on 6/1/16.
 */
class TextSimilarity {


    public static void main(String[] args) {
        String d1="i do not like green eggs and ham"
        String d2="i do not like them sam i am"
        String query="sam i am"

        def document1List=d1.tokenize(" ")
        def document2List=d2.tokenize(" ")
        def queryList=query.tokenize(" ")
        //union of all the list of words are done and stored
        def union= document1List.plus(document2List).plus(queryList).unique{a,b->a<=>b}

        ConfigObject termMapMatrix=new ConfigObject()//matrix with word and the frequency in the document
        ConfigObject termFrequencyMatrix= new ConfigObject() // matrix with words with their term frequency

        union.each {
            termMapMatrix['document1List'][it]=document1List.count(it)
            termMapMatrix['document2List'][it]=document2List.count(it)
            termMapMatrix['queryList'][it]=queryList.count(it)
        }

        println("Term map matrix with word frequency " +termMapMatrix)

        //maximum value of term frequency in each document
        def maxFromDoc1 = termMapMatrix['document1List'].max{it.value}.value
        def maxFromDoc2=termMapMatrix['document2List'].max{it.value}.value
        def maxFromQuery=termMapMatrix['queryList'].max{it.value}.value

        //term frequency matrix
        union.each {
            termFrequencyMatrix['document1List'][it]=termMapMatrix['document1List'][it]/maxFromDoc1
            termFrequencyMatrix['document2List'][it]=termMapMatrix['document2List'][it]/maxFromDoc2
            termFrequencyMatrix['queryList'][it]=termMapMatrix['queryList'][it]/maxFromQuery
        }

        println("Term frequency matrix " +termFrequencyMatrix)


        def df=[:]  //document frequency map

        def idf=[:] //inverse document frequency map

        //calculation of document frequency for each term
        union.each{
            df[it] = (termMapMatrix['document1List'][it] >=1 ? 1 :0 ) +
                    (termMapMatrix['document2List'][it] >= 1 ? 1 : 0)+
                    (termMapMatrix['queryList'][it] >= 1 ? 1:0)
        }

        println("df is "+ df)
        def N=3 // it denotes the total number of documents
        //calculation of inverse document frequency is done here
        union.each {
            idf[it]= Math.log(N/df[it])
        }
        //matrix that contains tf-idf weighing
        ConfigObject weightMatrix=new ConfigObject()
        union.each{
            weightMatrix['document1List'][it]=termFrequencyMatrix['document1List'][it] * idf[it]
            weightMatrix['document2List'][it]=termFrequencyMatrix['document2List'][it]*idf[it]
            weightMatrix['queryList'][it]=termFrequencyMatrix['queryList'][it]*idf[it]
        }

        println("Weighted matrix " + weightMatrix)

        println("\n\n\n")
        println('Document 1 is :  ' + d1)
        println('Document 2 is : ' + d2)
        println('Our query is:  ' + query)
        println("----------------------------------------")
        println("Similarity of query with document 1 is :"
                +cosine(weightMatrix['document1List'],weightMatrix['queryList'],union) + "\n\n")
        println("Similarity of query with document 2 is :"
                +cosine(weightMatrix['document2List'],weightMatrix['queryList'],union))


    }

    //calculate cosine frequency
    def static float cosine(d1,query,union){
        def numerator=0
        def denominator
        def temp1=0
        def temp2=0
        union.each{
            numerator+=d1[it]*query[it]
            temp1 += d1[it]*d1[it]
            temp2 += query[it]*query[it]
        }
        denominator=Math.sqrt(temp1*temp2)
        def similarity = numerator/denominator

        return similarity

    }

}

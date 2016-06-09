import java.lang.*;

ConfigObject term_F_matrix = new ConfigObject()
ConfigObject term_C_matrix = new ConfigObject()
ConfigObject tf = new ConfigObject() //term frequency
ConfigObject w = new ConfigObject() //weight

def doc_freq = [:] //document frequency
def inv_freq = [:] //inverse document frequency


def doc1 = ['i','do','not','like','them','sam','i','am'] /*data of DOC 1*/
def doc2 = ['i','do','not','like','green','eggs','and','ham'] /*data of DOC 2*/
def query  =['sam','i','am'] /*data of query*/

def j = i = 0
def union  = doc1.plus(doc2).plus(query).unique { a, b -> a <=> b } // terms in doc1 + doc2 + query
def N = 3 //total count of doc

//creating binary matrix
union.each{
    if(doc1.contains(it))
        term_F_matrix['doc1'][it] = 1
    else
        term_F_matrix['doc1'][it] = 0
    if(doc2.contains(it))
        term_F_matrix['doc2'][it] = 1
    else
        term_F_matrix['doc2'][it] = 0
    if(query.contains(it))
        term_F_matrix['query'][it] = 1
    else
        term_F_matrix['query'][it] = 0
}

//creates term count matrix
union.each{
    term_C_matrix['doc1'][it] = doc1.count(it)
    term_C_matrix['doc2'][it] = doc2.count(it)
    term_C_matrix['query'][it] = query.count(it)
}


maxDoc1 = term_C_matrix['doc1'].max{it.value}.value //maximum num of count in doc1
maxDoc2 = term_C_matrix['doc2'].max{it.value}.value //maximum num of count in doc2
maxQuery = term_C_matrix['query'].max{it.value}.value //maximum num of count in query  

//creates term frequency matrix
union.each{
    tf['doc1'][it] =  term_C_matrix['doc1'][it]/maxDoc1
    tf['doc2'][it] =  term_C_matrix['doc2'][it]/maxDoc2
    tf['query'][it] =  term_C_matrix['query'][it]/maxQuery
}

//no of document where there is presence of the given term
union.each{
    doc_freq[it] = term_F_matrix['doc1'][it] + term_F_matrix['doc2'][it] + term_F_matrix['query'][it]
}

//inverse of document frequency
union.each{
    inv_freq[it] = Math.log(N/doc_freq[it])
}

//tf-inv_freq weighting
union.each{
    w['doc1'][it] = tf['doc1'][it] * inv_freq[it]
    w['doc2'][it] = tf['doc2'][it] * inv_freq[it]
    w['query'][it] = tf['query'][it] * inv_freq[it]
}

//function that calculates the cosine similarity of w2 text with w1
def cosine(w1,w2,union){

    def temp1 = 0
    def temp2 = 0.0
    def temp3 = 0.0

    union.each{
        temp1 = w1[it]*w2[it]+temp1
    }

    w1.each{
        temp2 = temp2 + (it.value * it.value)
    }

    w2.each{
        temp3 = temp3 + (it.value * it.value)
    }
    return temp1/(Math.sqrt(temp2)*Math.sqrt(temp3))
}


def cosine1 = cosine(w['doc1'],w['query'],union)
def cosine2 = cosine(w['doc2'],w['query'],union)

println "Cosine of document1 : "+cosine1+" Cosine of document2 : "+cosine2;

if(cosine1>cosine2){
    println "Query is more similar with Document 1"
}else{
    println "Query is more similar with Document 2"
}

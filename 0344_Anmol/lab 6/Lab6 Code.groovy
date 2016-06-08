//Reading the file shakespeare.txt 
def fileContents = new File('/home/asperoph/shakespeare.txt').getText();

//Tokenize is used to divide the text according to defined parameters and insert it in the list
def lists = fileContents.tokenize(' !.&,;?\n:');

def count = 0;

//the number of words imported from the file.
def size = lists.size()

//the list that contains distinct words (non-repeated) derived from lists[].
def words = [];

def probablemap = [:];

//the list that stores the frequency of words appeared distinctly in words[].
def map = [:];

//the list that stores the frequency of adjascent words appeared in lists[].
def maps = [:];


def temp = "";
def neglet =['the','and','i','a','or','in','that','to','you','of','my','for','is','will','as','me'];

 
for(int i=0; i<size-1; i++){
    //counts the frequency of each word and store in map
    lists[i] = lists[i].toLowerCase()
    
    //the following code tests for the occurence
    //if the text has not appeared yet, it is pushed in words list
    //and the map list is updated with an incremented count

      if(!words.contains(lists[i])){
          words.push(lists[i])
          map.put(lists[i],1)
        }else{
        map.put(lists[i],map[lists[i]]+1)
      }

         
    //the following code selects two adjascent words and makes them a pair
    //if the pair has not appeared yet, it is pushed into maps
    //if the pair has already appeared, the frequency is incremented.
    temp = lists[i]+":"+lists[i+1]
    if(maps[temp]==null){
        maps.put(temp,1)  
    }else{
        maps.put(temp,maps[temp]+1)
       
    }
   
}

count = lists.size()

//calculate the probability of given word 
def probabiliti(word,map,count){
    word = "$word"
    return map[word]/count
}

//calculates the probability of one word after another
def probability(first,second,maps, map,count){
    temp = "$first:$second"
    first = "$first"
    maps[temp]/map[first]

}

//println "Words      :Frequency"
//println "THE        :" +map['the']
//println "BECOME     :" +map['become']
//println "BRAVE      :" +map['brave']
//println "TREASON    :" +map['treason']
//
//println "Probability(Word1/Word2 : Probability"
//println "P(Court/The)            : "+ probability('the','court',maps,map,count)
//println "P(Word/His)             : "+ probability('his','word',maps,map,count)
//println "P(Qualities/Rare)        : "+ probability('rare','qualities',maps,map,count)
//println "P(Men/Young)        : "+ probability('young','men',maps,map,count)


//probability of second word after first word
def prob(first,second,maps,map,count){
    probabiliti(first,map,count)*probability(first,second,maps,map,count)
}

//probability of third word after first and second
def prob(first,second,third,maps,map,count){
        probabiliti(first,map,count)*probability(first,second,maps,map,count)*probability(second,third,maps,map,count)
}

//probability of fourth word after first second and third
def prob(first,second,third,fourth,maps,map,count){
        probabiliti(first,map,count)*probability(first,second,maps,map,count)*probability(second,third,maps,map,count)*probability(third,fourth,maps,map,count)
}
//
//println "p(have, sent)="+prob('have','sent',maps,map,count)
//println "p(will, look, upon)="+prob('will','look','upon',maps,map,count)
//println "p(i, am, no, baby)="+prob('i','am','no','baby',maps,map,count)
//println "p(wherefore, art, thou, Romeo)="+prob('wherefore','art','thou','Romeo',maps,map,count)
//
//println "p(have, sent) indepently="+probabiliti('have',map,count)*probabiliti('sent',map,count)
//println "p(will, look, upon) indepently="+probabiliti('will',map,count)*probabiliti('look',map,count)*probabiliti('upon',map,count)
//println "p(i, am, no, baby) indepently="+probabiliti('i',map,count)*probabiliti('am',map,count)*probabiliti('no',map,count)*probabiliti('baby',map,count)
//println "p(wherefore, art, thou, Romeo) indepently="+probabiliti('wherefore',map,count)*probabiliti('art',map,count)*probabiliti('thou',map,count)*probabiliti('romeo',map,count)

def sort = map.sort { b, a -> a.value <=> b.value }
def sorts = maps.sort { b, a -> a.value <=> b.value }

//Ranks words by its count
//println "Rank  Word    Frequency"
//i=0
//sort.each{
//    if(!neglet.contains(it.key)) {
//        i++
//        println  i+"     "+it.key+"      "+it.value
//    }
//}
//UNCOMMENT THIS REGION LATER
//Ranks wordpairs by its count
//i=0
//println "Rank  Wordpair    Frequency"
//sorts.each {
//    println i + "     " + it.key + "      " + it.value
//    i++
//}
//
//END UNCOMMENT
//
def prediction(word1, word2, word3,lists) {
    def probablemap = [:];
    count = lists.size()
    for(int i=0; i<count-3; i++){
        if(lists[i]==word1 && lists[i+1]==word2 && lists[i+2]==word3){
            if(probablemap[lists[i+3]]==null)
                probablemap.put(lists[i+3],1)
            else
                probablemap.put(lists[i+3],probablemap[lists[i+3]]+1)

        }
    }
    return probablemap
}

probablemap = prediction("i","am","no",lists)
def probablemap1 = prediction("wherefore","art","thou",lists)
probablemap = probablemap.sort { b, a -> a.value <=> b.value }
probablemap1 = probablemap1.sort { b, a -> a.value <=> b.value }

print "The Most Probable word after I am no are: "
probablemap.each{
    println it.key
}

print "The Most Probable word after wherefore art thou is: "
probablemap1.each{
    print it.key+" "
}
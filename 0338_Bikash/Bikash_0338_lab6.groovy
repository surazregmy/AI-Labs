//this lab is complete only upto task 3. It will be complete by 1st july 2016 i.e after final exam
def fileContents = new File('/home/bikash/Desktop/shakespeare.txt').getText();
def lists = fileContents.tokenize(' !.&,;?\n:');
def size = lists.size()
def count = 0;
def words = [];
def map = [:];
def maps = [:];
def temp = "";
def neglet =['the','and','i','a','or','in','that','to','you','of','my','for','is','will','as','me'];

 
for(int i=0; i<size-1; i++){
    //counts the frequency of each word and store in map
    lists[i] = lists[i].toLowerCase()

      if(!words.contains(lists[i])){
          words.push(lists[i])
          map.put(lists[i],1)
        }else{
        map.put(lists[i],map[lists[i]]+1)
      }
    //counts the frequency of two words comming together and store in a map          
    temp = lists[i]+":"+lists[i+1]
    if(maps[temp]==null){
        maps.put(temp,1)  
    }else{
        maps.put(temp,maps[temp]+1)
    }
}
count = words.size()
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

println "The frequency of THE is " +map['the']
println "The frequency of BECOME is " +map['become']
println "The frequency of BRAVE is " +map['brave']
println "The frequency of TREASON is " +map['treason']

println "p(court/the)="+ probability('the','court',maps,map,count)
println "p(word/his)="+ probability('his','word',maps,map,count)
println "p(qualities/rare)="+ probability('rare','qualities',maps,map,count)
println "p(men/young)="+ probability('young','men',maps,map,count)


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

println "p(have, sent)="+prob('have','sent',maps,map,count)
println "p(will, look, upon)="+prob('will','look','upon',maps,map,count)
println "p(i, am, no, baby)="+prob('i','am','no','baby',maps,map,count)
println "p(wherefore, art, thou, Romeo)="+prob('wherefore','art','thou','Romeo',maps,map,count)


def sort = map.sort { b, a -> a.value <=> b.value }
def sorts = maps.sort { b, a -> a.value <=> b.value }

//Ranks words by its count
println "Rank  Word    Frequency"
i=0
sort.each{
    if(!neglet.contains(it.key)) {
        i++
        println  i+"     "+it.key+"      "+it.value
    }
}

//Ranks wordpairs by its count
i=0
println "Rank  Wordpair    Frequency"
sorts.each {
    println i + "     " + it.key + "      " + it.value
    i++
}

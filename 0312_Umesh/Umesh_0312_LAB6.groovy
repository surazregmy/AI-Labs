/**
 * Created by UMESH on 6/5/2016.
 */
def file = new File('D:\\DWIT\\V\\LAB\\AI\\lab_6_prob\\shakespeare.txt').getText();

def lists = file.tokenize(' !.&,;?\n:'); //word separated by these character are stored
def size = lists.size()    //gives the total size of lists
def count = 0;
def words = [];
def map = [:];          //to store the number of each word
def maps = [:];         //to store the number of two words in sequence
def probablemap = [:];  //to store the number of probable word after certain words
def temp = "";

def neglect =['the','and','i','a','or','in','that','to','you','of','my','for','is','will','as','me'];


for(int i=0; i<size-1; i++){
    //counts the frequency of each word and store in map
    lists[i] = lists[i].toLowerCase()

    if(!words.contains(lists[i])){
        words.push(lists[i])
        map.put(lists[i],1)
    }else{
        map.put(lists[i],map[lists[i]]+1)
    }
    //counts the frequency of two words in sequence and store in a maps
    temp = lists[i]+":"+lists[i+1]
    if(maps[temp]==null){
        maps.put(temp,1)
    }else{
        maps.put(temp,maps[temp]+1)

    }

}
count = lists.size()
//calculate the probability of given word
def probability1(word,map,count){
    word = "$word"
    return map[word]/count
}

//calculates the probability of words in sequence
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
def probability2(first,second,maps,map,count){
    probability1(first,map,count)*probability(first,second,maps,map,count)
}

//probability of third word after first and second
def probability2(first,second,third,maps,map,count){
    probability1(first,map,count)*probability(first,second,maps,map,count)*probability(second,third,maps,map,count)
}

//probability of fourth word after first second and third
def probability2(first,second,third,fourth,maps,map,count){
    probability1(first,map,count)*probability(first,second,maps,map,count)*probability(second,third,maps,map,count)*probability(third,fourth,maps,map,count)
}

println "p(have, sent)="+probability2('have','sent',maps,map,count)
println "p(will, look, upon)="+probability2('will','look','upon',maps,map,count)
println "p(i, am, no, baby)="+probability2('i','am','no','baby',maps,map,count)
println "p(wherefore, art, thou, Romeo)="+probability2('wherefore','art','thou','Romeo',maps,map,count)

println "p(have, sent) indepently="+probability1('have',map,count)*probability1('sent',map,count)
println "p(will, look, upon) indepently="+probability1('will',map,count)*probability1('look',map,count)*probability1('upon',map,count)
println "p(i, am, no, baby) indepently="+probability1('i',map,count)*probability1('am',map,count)*probability1('no',map,count)*probability1('baby',map,count)
println "p(wherefore, art, thou, Romeo) indepently="+probability1('wherefore',map,count)*probability1('art',map,count)*probability1('thou',map,count)*probability1('romeo',map,count)

def sort = map.sort { b, a -> a.value <=> b.value }//sorts map in descending order
def sorts = maps.sort { b, a -> a.value <=> b.value }//sorts maps in descending order

//Ranks words by its count
println "Rank  Word    Frequency"
i=0
sort.each{
    if(!neglect.contains(it.key)) {
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

def probablemap1 = prediction("i","am","no",lists)
def probablemap2 = prediction("wherefore","art","thou",lists)
probablemap1 = probablemap1.sort { b, a -> a.value <=> b.value }
probablemap2 = probablemap2.sort { b, a -> a.value <=> b.value }

print "The most probable word after I am no are: "
probablemap1.each{
    println it.key+" "
}

print "The most probable word after wherefore art thou is: "
probablemap2.each{
    print it.key+" "
}
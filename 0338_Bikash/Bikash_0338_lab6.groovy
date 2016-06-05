//this lab is complete only upto task 3. It will be complete by 1st july 2016 i.e after final exam
def fileContents = new File('/home/bikash/Desktop/shakespeare.txt').getText();
def lists = fileContents.tokenize(' !.&,;?\n');
def size = lists.size()
def count = 0;
def words = [];
def map = [:];
def maps = [:];
def temp = ""

 
for(int i=0; i<size-1; i++){
    //counts the frequency of each word and store in map
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

//calculate the probability of given word 
def probabiliti(word,map,count){
    map[word]/count
}

//calculates the probability of one word after another
def probability(first,second,maps, map,count){
    temp = "$first:$second"
    maps[temp]/map[first]

}

print "the probability of 'is' after 'love' is "+ probability('love','is',maps,map,count)
print "the probability of 'word' after 'his' is "+probability('his','word',maps,map,count)
print "the probability of 'qualities' after 'rare' is "+probability('rare','qualities',maps,map,count)
print "the probability of 'men' after 'young' is "+probability('young','men',maps,map,count)
print "the probability of 'sent' after 'have' is "+probability('have','sent',maps,map,count)

//probability of second word after first word
def prob(first,second,maps,map,count){
    probabiliti(first,map,count)*probability(second,first,maps,map,count)
}

//probability of third word after first and second
def prob(first,second,third,maps,map,count){
        probabiliti(first,map,count)*probability(second,first,maps,map,count)*probability(third,second,maps,map,count)
}

//probability of fourth word after first second and third
def prob(first,second,third,fourth,maps,map,count){
        probabiliti(first,map,count)*probability(second,first,maps,map,count)*probability(third,second,maps,map,count)*probability(fourth,third,maps,map,count)
}

prob('I','am','no','baby',maps,map,count)
prob('have','sent',maps,map,count)
prob('will','look','upon',maps,map,count)

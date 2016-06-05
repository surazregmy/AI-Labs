package bipin_lab6
/**
 * Created by bips on 4/8/16.
 */
class LanguageModel {
    private static def wordList=[]
    private static def wordMap=[:]
//this function calculates the probability for the bigram model
    public static takeData(wordForProb) {
        def probability=0
        switch(wordForProb.size()){
            case 2: // if it is bi-gram model
                probability=calculateProbability(wordForProb)
                break
            case 3: // if it is tri-gram model
                probability=  wordMap.get(wordForProb[0])/wordList.size()  * calculateProbability([wordForProb[0],wordForProb[1]])*
                        calculateProbability([wordForProb[1],wordForProb[2]])
                break
            case 4: // if it is 4-gram model
                probability=wordMap.get(wordForProb[0])/wordList.size()  * calculateProbability([wordForProb[0],wordForProb[1]])*
                        calculateProbability([wordForProb[1],wordForProb[2]],) *
                        calculateProbability([wordForProb[2],wordForProb[3]])
                break

        }
        println(probability)


    }

    public static float calculateProbability(wordForProb){//takes two words in a list and find the probability
        def counter = 0
        def probability
        for (int i = 0; i < wordList.size - 2; i++) {
            def firstWord = wordList[i]
            def secondWord = wordList[i + 1]
            if (firstWord == wordForProb[0] && secondWord == wordForProb[1]) {
                counter++
            }
        }
        probability = counter / (wordMap.get(wordForProb[0]))
       return probability
    }

    //


    public static void main(String[] args) {
        String fileContents;
        try {
            //this line reads file from the directory
            fileContents = new File('/media/bips/Data/study/sem 5/AI/lab_6_prob/shakespeare.txt').text
        }
        catch (FileNotFoundException e) {
            println("File not found . Check the location")
        }
        // separate the content of the file according to the given parameters
        wordList = fileContents.tokenize(' \n\n,.:;!?')//separate words from text and put it in a list
        wordMap = [:] //map of words with their frequency
        wordList.each {
            if (wordMap.containsKey(it.toLowerCase())) {
                wordMap[it.toLowerCase()] += 1
            } else {
                wordMap[it.toLowerCase()] = 1
            }
        }
        //sample word for which we want to calculate the probability of
        //this is a bigram model
        def wordForProb = ["the", "court"] //this is the list of words we want to calculate probability of
        takeData(wordForProb)

        takeData(["love", "is"])
        takeData(["is","my"])
        println("3 trigram")
        takeData(["he","is","my"])
        println("4 gram")
        takeData(["the","court","is","good"])
    }
}

package bipin_lab6
/**
 * Created by bips on 4/8/16.
 */
class LanguageModel {
    private static def wordList = []
    private static def wordMap = [:]

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
        wordMap = wordMap.sort { -it.value }
        def frequent = []
        def counter = 1
        wordMap.each {
            if (counter == 21) {
                return true
            }
            def temp = []
            temp.add(counter)
            temp.add(it.getKey())
            temp.add(it.getValue())
            frequent.add(temp)
            counter++
            return false
        }
        println("Top 20 frequent words with rank, word and frequency respectively: ")
        println(frequent)



        println("Probability of words")

        println("Probability of \"the\" " + takeData(["the"]))
        println("Probability of \"become\" " + takeData(["become"]))
        println("Probability of \"brave \" " + takeData(["brave"]))
        println("Probability of \"treason\" " + takeData(["treason"]))

        println("--------------------\n")
        println("2 ")
        println("P(court|the) " + takeData(["the", "court"]))
        println("P(word|his) " + takeData(["his", "word"]))
        println("P(qualities|rare) " + takeData(["rare", "qualities"]))
        println("P(men|young) " + takeData(["young", "men"]))

        println("------------------\n")
        println("Chain rule- dependent case")
        println("Probability of P(have, sent) " + takeData(["have", "sent"]))
        println("Probability of P(will, look, upon)  " + takeData(["will", "look", "upon"]))
        println("Probability of P(i, am, no, baby) " + takeData(["i", "am", "no", "baby"]))
        println("Probability of P(wherefore, art, thou, romeo) " + takeData(["wherefore", "art", "thou", "romeo"]))

        println("------------------------\n")
        println("Chain rule- independent case")
        println("Probability of P(have, sent) " + independentProbability(["have", "sent"]))
        println("Probability of P(will, look, upon)  " + independentProbability(["will", "look", "upon"]))
        println("Probability of P(i, am, no, baby) " + independentProbability(["i", "am", "no", "baby"]))
        println("Probability of P(wherefore, art, thou, romeo) " + independentProbability(["wherefore", "art", "thou", "romeo"]))
    }

//this function calculates the probability for the bigram model
    public static float takeData(wordForProb) {
        def probability = 0
        switch (wordForProb.size()) {
            case 1:
                probability = wordMap.get(wordForProb[0]) / wordList.size()
                break
            case 2: // if it is bi-gram model
                probability = calculateProbability(wordForProb)
                break
            case 3: // if it is tri-gram model
                probability = wordMap.get(wordForProb[0]) / wordList.size() * calculateProbability([wordForProb[0], wordForProb[1]]) *
                        calculateProbability([wordForProb[1], wordForProb[2]])
                break
            case 4: // if it is 4-gram model
                probability = wordMap.get(wordForProb[0]) / wordList.size() * calculateProbability([wordForProb[0], wordForProb[1]]) *
                        calculateProbability([wordForProb[1], wordForProb[2]],) *
                        calculateProbability([wordForProb[2], wordForProb[3]])
                break
        }
        return probability
    }

    public static float calculateProbability(wordForProb) {//takes two words in a list and find the probability
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


    public static float independentProbability(wordList) {
        def answer = 1
        wordList.each {
            answer *= takeData([it])
        }
        return answer
    }
}

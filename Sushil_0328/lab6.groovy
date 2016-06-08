import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by Sushil on 6/6/2016.
 */
class DocumentProcessing {
    public static void main(String[] args) {
        ProcessingWork processingWork=new ProcessingWork();
        List words=processingWork.getWords("D:\\AI_LABS\\shakespeare.txt);
        Map wordFreq=processingWork.getWordFrequency(words)
        processingWork.getWordRankFreq(wordFreq)
        processingWork.displayProbabilities(wordFreq, processingWork.getTotalFrequencies())

        /*conditional probabilities*/
        println "=============Conditional probabilities==========="
        println "P(court/the): " + processingWork.getConditionalProbability("COURT", "THE", wordFreq,processingWork.getText())
        println "P(word/his): " +processingWork.getConditionalProbability("WORD", "HIS", wordFreq,processingWork.getText())
        println "P(qualities/rare): " +processingWork.getConditionalProbability("QUALITIES","RARE", wordFreq,processingWork.getText())
        println "P(men/young): " +processingWork.getConditionalProbability("MEN", "YOUNG", wordFreq,processingWork.getText())

        /*chain rule probability -dependent case*/
        println "=========Chain rule probability - dependent case======="
        println "Probability of P(have, sent) : " + processingWork.chainRuleDependentProbability("HAVE SENT", wordFreq, processingWork.getText())
        println "Probability of P(will, look, upon) : " + processingWork.chainRuleDependentProbability("WILL LOOK UPON", wordFreq, processingWork.getText())
        println "Probability of P(i, am, no, baby) : " + processingWork.chainRuleDependentProbability("I AM NO BABY", wordFreq, processingWork.getText())
        println "Probability of P(wherefore, art, thou, romeo) : " + processingWork.chainRuleDependentProbability("WHEREFORE ART THOU ROMEO", wordFreq, processingWork.getText())

        /*chain rule probability -independent case*/
        println "=========Chain rule probability - independent case======="
        println "Probability of P(have, sent) : " + processingWork.independentProbability("HAVE SENT", wordFreq)
        println "Probability of P(will, look, upon) : " + processingWork.independentProbability("WILL LOOK UPON", wordFreq)
        println "Probability of P(i, am, no, baby) : " + processingWork.independentProbability("I AM NO BABY", wordFreq)
        println "Probability of P(wherefore, art, thou, romeo) : " + processingWork.independentProbability("WHEREFORE ART THOU ROMEO", wordFreq)

    }
}

class ProcessingWork{
    private int totalFrequencies=0
    private String text

    int getTotalFrequencies(){
        return totalFrequencies
    }

    String getText(){
        return text
    }

    List getWords( String URL) throws FileNotFoundException, IOException{
        List words=[]
        text = new String(Files.readAllBytes(Paths.get(URL)), StandardCharsets.UTF_8);
        text=text.toUpperCase()
        text=text.replaceAll("[:.,!?;]", "").replaceAll(" +", " ")
        words=text.tokenize(" -\n")
        return words
    }

    Map getWordFrequency(List words){
        def wordFreq=[:]
        words.groupBy {it}.each {key, value->
            wordFreq.put(key, value.size())
            totalFrequencies+=value.size()
        }
        wordFreq=wordFreq.sort{-it.value}
        return wordFreq
    }

    void displayProbabilities(Map wordFreq, int total){
        println "========= Probability of words ========="
        println "Probability of THE : " + getProbability(wordFreq,total, "THE")
        println "Probability of BECOME : " + getProbability(wordFreq,total, "BECOME")
        println "Probability of BRAVE : " + getProbability(wordFreq,total, "BRAVE")
        println "Probability of TREASON : " + getProbability(wordFreq,total, "TREASON")
    }

    void getWordRankFreq(Map wordFreq){
        def wrfArr=[]
        def bottomFreq=[]
        def v=0
        for (Map.Entry entry: wordFreq){
            if (v<20){
                def table1=[]
                table1[0]=entry.key
                table1[1]=v+1
                table1[2]=entry.value
                wrfArr.add(table1)
                v++
            }else{
                break
            }
        }
        println "The table of 20 most frequent words and their rank is as follows:"
        println wrfArr

        v=0
        for (Map.Entry entry1: wordFreq.groupBy {it.value}.sort{it.key}) {
            if (v < 10) {
                def ind = 0
                def table2 = []
                Map val = entry1.value
                Set valList = val.keySet()
                table2[0] = entry1.key
                table2[1] = val.size()
                table2[2] = ""
                for (Object obj : valList) {
                    table2[2] += (String) obj + ";"
                    if (++ind == 3) {
                        break
                    }
                }
                bottomFreq.add(table2)
                v++
            }else{
                break
            }
        }
        println "A table, containing list of bottom frequencies. The table contains three columns: frequency,\n" +
                "word count and example words."
        println bottomFreq
    }

    float getProbability(Map wordFreq, int total, String word){
        int frequency=wordFreq.get(word)
        return frequency/total
    }

    float getConditionalProbability(String B, String givenA, Map wordFreq, String text){
        List wordP=text.findAll(givenA + " " + B)
        int countAB=wordP.size()
        int countA=(int)wordFreq.get(givenA)
        return countAB/countA
    }

    float chainRuleDependentProbability(String msg, Map wordFreq, String text){
        String [] wordsInMsg=msg.split(" ")
        float initialPVal=getProbability(wordFreq, getTotalFrequencies(), wordsInMsg[0])
        for(int i=1; i<wordsInMsg.length; i++){
            initialPVal*=getConditionalProbability(wordsInMsg[i], wordsInMsg[i-1], wordFreq, text)
        }
        return initialPVal
    }

    float independentProbability(String msg, Map wordFreq){
        String [] wordsInMsg=msg.split(" ")
        float probability=1
        for(int i=0; i<wordsInMsg.length; i++){
            probability*=getProbability(wordFreq, getTotalFrequencies(), wordsInMsg[i])
        }
        return probability
    }


}

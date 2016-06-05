/**
 * Created by Shreha on 6/5/2016.
 */
class AILab6 {

    public static void main(String[] args) {

        String fileContents = new File('D:\\DEERWALK\\Fifth Semester\\Artificial Intelligence\\lab_6_prob\\shakespeare.txt').getText('UTF-8')
        def list1 = fileContents.tokenize(' \n-\n,.:;!?')
//        list1 = fileContents.tokenize(" ,")
        println(list1)
        def val = 0
        def map1=[:]
        int total = 0


        list1.groupBy {it}.each {key, value ->
            map1.put(key, value.size())
            total+=value.size()
        }
        map1=map1.sort{-it.value}
        println(map1)

        def toparr=[]
        def lowarr=[]
        for (Map.Entry entry: map1){
            if (val<20){
                def table1=[]
                table1[0]=entry.key
                table1[1]=val+1
                table1[2]=entry.value
                toparr.add(table1)
                val++
            }else{
                break
            }
        }
        println "The table of 20 most frequent words and their rank is as follows:"
        println toparr

        println "Probability of THE : " + getProbability(map1,total, "THE")
        println "Probability of BECOME : " + getProbability(map1,total, "BECOME")
        println "Probability of BRAVE : " + getProbability(map1,total, "BRAVE")
        println "Probability of TREASON : " + getProbability(map1,total, "TREASON")

    }

    static float getProbability(Map map1, int total, String word){
        int frequency=map1.get(word)
        return frequency/total
    }
}


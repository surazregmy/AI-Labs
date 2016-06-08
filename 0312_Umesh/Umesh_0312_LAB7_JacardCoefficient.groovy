/**
 * Created by UMESH on 6/8/2016.
 */

def t1 = "I am Sam "
def t2 = "Sam am I"

text1 = t1.tokenize() //stores words of t1 in an array
text2 = t2.tokenize() //stores words of t2 in an array

text1 = text1.unique { a, b -> a <=> b }//removes repeated datas
text2 = text2.unique { a, b -> a <=> b }//removes repeated datas

def common = text1.intersect(text2)//get intersection of text1 and text2
def collection = text1.plus(text2)//get union of text1 and text2

lCommon = common.size()//length of intersection
lCollection = collection.size()//length of union

def jacardCoeff = lCommon/lCollection
print "The similirity of two text using Jacard Coefficient is $jacardCoeff"
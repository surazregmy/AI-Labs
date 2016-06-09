def main():
        #input sentences
	text1 = input("Enter text 1:")
	text2 = input("Enter text 2:")

	#sentences to lists of words
	text1 = text1.lower().split()
	text2 = text2.lower().split()

	#sentences to sets
	text1 = set(text1)
	text2 = set(text2)

	#sentences union and intersection
	nint = len(text1 & text2)
	nuni = len(text1 | text2)

        #calculation of jacard coefficient
	jacard_coeff = nint/nuni
	print("The jacard coefficient of input strings are :"+ str(jacard_coeff))


if __name__ == '__main__':
    main()
	
	
	

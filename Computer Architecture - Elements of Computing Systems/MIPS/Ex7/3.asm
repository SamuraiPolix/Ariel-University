addi $s0, $0, 15	# $s0 = (n for loop) Num of values to add
addi $s1, $0, 1		# $s1 = value to place in memory
addi $t1, $0, 0		# $t1 = index for loop

lui $s2, 0x1001
ori $s2, $s2, 0x1020	# $s2 = curr address in memory

FOR:
	beq $t1, $s0, DONE		# jump to DONE when i==15
	sw $s1, 0($s2)			# store value in place
	addi $s1, $s1, 2		# add 2 for the next value we want to add
	addi $s2, $s2, 4		# move to next register in memory
	addi $t1, $t1, 1		# i++
	j FOR
DONE:

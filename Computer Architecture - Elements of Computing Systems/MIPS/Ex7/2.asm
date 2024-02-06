# $t0 = i, starts at 0
addi $t0, $0, 0
addi $s0, $0, 10
addi $s1, $0, 1		# A helper
addi $s2, $0, -1	# B helper
addi $s3, $0, 1		# C helper
addi $s5, $0, 0

# $s3 <- 2^9
sll $s3, $s3, 9

FOR:
	beq $t0, $s0, DONE
	mult $s1, $s2
	mflo $t2
	mult $t2, $s3
	mflo $t3
	add $s5, $s5, $t3
	
	# A
	sll $s1, $s1, 2
	
	# B - flips between -1 and 1
	xori $s2, $s2, -1
	addi $s2, $s2, 1
	
	# C - move 1 right at a time, 2^9 -> 2^0
	srl $s3, $s3, 1
	
	addi $t0, $t0, 1		# i++
	j FOR
DONE:

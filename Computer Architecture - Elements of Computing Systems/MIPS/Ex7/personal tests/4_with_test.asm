addi $s0, $0, 0			# i = Index
lui $s1, 0x1001			# TESTT
add $t1, $t1, $s1
addi $t0, $0, 0			# Number of "charigot"

TEST:
	lui $s1, 0x1001
	add $t2, $t2, $s1
	lui $t3, 0x9999
	sw $t3, 0($t2)
	addi $t3, $0, 5
	sw $t3, 4($t2)
	lui $t3, 0x1111
	sw $t3, 8($t2)
	lui $t3, 0x3213
	sw $t3, 12($t2)
	addi $t3, $0, 0x5403
	sw $t3, 16($t2)
	addi $s2, $0, 5
	
FOR:
	beq $s0, $s2, DONE
	lw $t2, 0($t1)
	mult $t2, $t2			# arr[i]^2
	mflo $t3
	mfhi $t4
	add $t2, $0, $t3
	or $t2, $t2, $t4
	sw $t2, 0($t1)
	beq $t4, $0, NEXT		# skips next line if high 16 bits are 0 (no Chariga to 32bits)
	addi $t0, $t0, 1		# add chariga
NEXT:
	addi $t1, $t1, 4		# next address
	addi $s0, $s0, 1		# i++
	j FOR
DONE:

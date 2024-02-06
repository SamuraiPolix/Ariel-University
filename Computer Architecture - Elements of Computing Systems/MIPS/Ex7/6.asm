StrangeSum:			# StrangeSum(i, addr)
	# $a0 is i
	# $a1 is addr
	li $s0, 0		# int sum=0
	li $t0, 0		# int j=0
	# we don't need to save $s's in stack because our main doesn't use them, we can overwrite them
	FOR:
		sle $t4, $t0, $a0		# 1 if $t0 <= $a0, 0 if $t0 > $a0
		beq $t4, $0, ENDFOR		# if $t4 is 1, continue, else break
		li $t1, 3
		lw $t2, 0($a1)
		mult $t2, $t1
		mflo $t3
		sw $t3,0($a1)
		addi $a1, $a1, 8
		add $s0, $s0, $t3
		subi $t0, $t0, 1	# j--
		addi $t0, $t0, 3	# j+=3
		j FOR
	ENDFOR:
		addi $sp, $sp, -8	# make space for 2 registers in stack
		sw $ra, 0($sp)		# store $ra
		sw $s0, 4($sp)		# store $s0
		add $a0, $0, $s0	# prepare $a0 for func
		jal MULTI
		lw $ra, 0($sp)		# restore $ra
		lw $v0, 4($sp)		# restore $s0 into $v0, to return it
		addi $sp, $sp, 8	# deallocate stack space
		jr $ra
MULTI:
	li $t5, 12
	mult $a0, $t5
	mflo $v0
	jr $ra
		
		

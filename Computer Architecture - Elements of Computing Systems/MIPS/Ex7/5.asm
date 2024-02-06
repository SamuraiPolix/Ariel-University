fib:
	addi $t0, $0, 0		# fib(0) / fib(i-2)
	addi $t1, $0, 1		# fib(1) / fib(i-1)
	addi $t2, $0, 2		# index for loop - starts from 2
	
	# IF $a1 is 0/1, we return fib(0) or fib(1)
	addi $v0, $t0, 0
	beq $a1, $t0, END
	addi $v0, $t1, 0
	beq $a1, $t1, END
	
	addi $a1, $a1, 1		# add 1 to include $t2 == $a1
	FOR:
		beq $t2, $a1, END
		add $v0, $t0, $t1	# set returned value
		
		add $t0, $0, $t1	# update fib(i-2)
		add $t1, $0, $v0	# update fib(i-1)
		
		addi $t2, $t2, 1	# i++
		j FOR
	END:
		jr $ra		# return $v0
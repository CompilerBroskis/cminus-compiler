.text
	.align 4
.globl  test
test:
test_bb2:
	pushl	%EBX
	pushl	%EBP
	pushl	%EDI
test_bb3:
	pushl	$0
	pushl	$1
	pushl	$0
	movl	%EDX, %EBX
	movl	$0, %EBP
	cmpl	%EDX, %EBX
	jle	test_bb21
test_bb20:
	movl	$1, %EBP
test_bb21:
	pushl	%EBP
	cmpl	$0, %EBP
	je	test_bb5
test_bb4:
	pushl	$1
	subl	%EBP, %EDX
test_bb6:
	movl	$0, %EBP
	cmpl	%EDX, %EBX
	jne	test_bb23
test_bb22:
	movl	$1, %EBP
test_bb23:
	pushl	%EBP
	cmpl	$0, %EDX
	je	test_bb15
test_bb7:
	pushl	$2
	pushl	$2
	movl	$0, %EBP
	cmpl	%EDI, %EDX
	jne	test_bb25
test_bb24:
	movl	$1, %EBP
test_bb25:
	pushl	%EBP
	cmpl	$0, %EBP
	je	test_bb10
test_bb9:
	pushl	$1
	addl	%EBP, %EDX
test_bb11:
	pushl	%ECX
	cmpl	$0, %EBP
	jne	test_bb7
test_bb12:
	pushl	$2
	addl	%EBP, %EDX
	pushl	%EAX
	cmpl	$0, %EBP
	jne	test_bb12
test_bb13:
	jmp	test_bb11
test_bb16:
	movl	$0, %EAX
	cmpl	%EDX, %EBX
	jne	test_bb27
test_bb26:
	movl	$1, %EAX
test_bb27:
	pushl	%EAX
	cmpl	$0, %EBP
	je	test_bb19
test_bb18:
	pushl	$1
	movl	%EDX, %EBX
	subl	%EBP, %EBX
	pushl	%EAX
	cmpl	$0, %EBP
	jne	test_bb18
test_bb19:
	pushl	%ECX
	cmpl	$0, %EAX
	jne	test_bb16
test_bb15:
	pushl	$3
test_bb1:
	popl	%EDI
	popl	%EBP
	popl	%EBX
	ret
test_bb10:
	pushl	$1
	movl	%EAX, %EDX
	pushl	$1
	movl	$0, %EAX
	cmpl	%EBP, %EDX
	jne	test_bb29
test_bb28:
	movl	$1, %EAX
test_bb29:
	pushl	%EAX
	cmpl	$0, %EBP
	je	test_bb11
test_bb5:
	pushl	$2
	movl	%ECX, %EDX
	pushl	$2
	movl	$0, %ECX
	cmpl	%EBP, %EDX
	jne	test_bb31
test_bb30:
	movl	$1, %ECX
test_bb31:
	pushl	%ECX
	cmpl	$0, %EBP
	je	test_bb6

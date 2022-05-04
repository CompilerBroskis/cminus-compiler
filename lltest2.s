.data
.comm	b,4,4

.comm	c,4,4

.text
	.align 4
.globl  main
main:
main_bb2:
main_bb3:
	movl	$99, %EDI
	movl	%EDI, %ESI
	movl	$100, %EDI
	movl	%EAX, %EDI
	movl	%EDI, %EAX
	addl	%ESI, %EAX
	movl	%EDI, c(%RIP)
main_bb1:
	ret

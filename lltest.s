.data
.comm	b,4,4

.comm	c,4,4

.text
	.align 4
.globl  main
main:
main_bb2:
main_bb3:
	pushl	$99
	movl	%EAX, %EDX
	pushl	$100
	movl	%EAX, %ECX
	movl	%ECX, %EAX
	addl	%EDX, %EAX
	movl	c, %EAX
	pushl	%EAX
main_bb1:
	ret

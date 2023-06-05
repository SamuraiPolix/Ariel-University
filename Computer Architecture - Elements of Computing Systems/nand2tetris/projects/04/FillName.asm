// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/FillName.asm

// BONUS ASSIGNMENT EX4

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program displays the initials 'L', 'S',
// i.e. writes "black" in the specified order;
// the initials should remain displayed as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here:
// _ L
(BLACK)
@SCREEN
D=A
@6915
D=D+A
@address
M=D
@8
D=D+A
@finish
M=D
(LOOPBLACK_L1)
@address
A=M
M=-1
@address
M=M+1
D=M
@finish
D=M-D
@LOOPBLACK_L1
D;JGT

// _ Bottom S
@SCREEN
D=A
@6960
D=D+A
@address
M=D
@5
D=D+A
@finish
M=D
(LOOPBLACK_S1)
@address
A=M
M=-1
@address
M=M+1
D=M
@finish
D=M-D
@LOOPBLACK_S1
D;JGT

// _ Middle S
@SCREEN
D=A
@4400
D=D+A
@address
M=D
@5
D=D+A
@finish
M=D
(LOOPBLACK_S2)
@address
A=M
M=-1
@address
M=M+1
D=M
@finish
D=M-D
@LOOPBLACK_S2
D;JGT

// _ Top S
@SCREEN
D=A
@1840
D=D+A
@address
M=D
@5
D=D+A
@finish
M=D
(LOOPBLACK_S3)
@address
A=M
M=-1
@address
M=M+1
D=M
@finish
D=M-D
@LOOPBLACK_S3
D;JGT

// | Top S
@SCREEN
D=A
@1840
D=D+A
@address
M=D
@2560
D=D+A
@finish
M=D
(LOOPBLACK_S4)
@address
A=M
M=-1
@64
D=A
@address
M=M+D
D=M
@finish
D=M-D
@LOOPBLACK_S4
D;JGT

// | Bottom S
@SCREEN
D=A
@4405
D=D+A
@address
M=D
@2560
D=D+A
@finish
M=D
(LOOPBLACK_S5)
@address
A=M
M=-1
@64
D=A
@address
M=M+D
D=M
@finish
D=M-D
@LOOPBLACK_S5
D;JGT

// | Vertical L
@SCREEN
D=A
@1795
D=D+A
@address
M=D
@5120
D=D+A
@finish
M=D
(LOOPBLACK_L2)
@address
A=M
M=-1
@64
D=A
@address
M=M+D
D=M
@finish
D=M-D
@LOOPBLACK_L2
D;JGT

@LOOPKBD
0;JMP
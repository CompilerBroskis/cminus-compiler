(FUNCTION  putDigit  [(int s)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Pass [(r 1)]  [(i 48)])
    (OPER 5 Add_I [(r 2)]  [(r 1)(r -1)])
    (OPER 6 Pass [(r 3)]  [(r 2)])
    (OPER 7 JSR []  [(s putchar)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  printInt  [(int x)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Pass [(r 3)]  [(r -1)])
    (OPER 5 BEQ [(bb 5)]  [(i 0)])
  )
  (BB 4
    (OPER 6 Pass [(r 4)]  [(i 45)])
    (OPER 7 Pass [(r 5)]  [(r 4)])
    (OPER 8 JSR []  [(s putchar)])
    (OPER 9 Pass [(r 6)]  [(i 0)])
    (OPER 10 Sub_I [(r 7)]  [(r 6)(r -1)])
    (OPER 11 Mov [(r -1)]  [(r 7)])
  )
  (BB 5
    (OPER 12 Mov [(r 2)]  [(r -1)])
    (OPER 13 Pass [(r 8)]  [(i 10)])
    (OPER 14 Div_I [(r 9)]  [(r -1)(r 8)])
    (OPER 15 Mov [(r -1)]  [(r 9)])
    (OPER 16 Pass [(r 10)]  [(i 10)])
    (OPER 17 Mul_I [(r 11)]  [(r -1)(r 10)])
    (OPER 18 Sub_I [(r 12)]  [(r 2)(r 11)])
    (OPER 19 Mov [(r 1)]  [(r 12)])
    (OPER 20 Pass [(r 13)]  [(r -1)])
    (OPER 21 BEQ [(bb 7)]  [(i 0)])
  )
  (BB 6
    (OPER 22 Pass [(r 14)]  [(r -1)])
    (OPER 23 JSR []  [(s printInt)])
  )
  (BB 7
    (OPER 24 Pass [(r 15)]  [(r 1)])
    (OPER 25 JSR []  [(s putDigit)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  fact  [(int x)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Pass [(r 1)]  [(r -1)])
    (OPER 5 BEQ [(bb 5)]  [(i 0)])
  )
  (BB 4
    (OPER 6 Pass [(r 2)]  [(i 1)])
    (OPER 7 Sub_I [(r 3)]  [(r -1)(r 2)])
    (OPER 8 Pass [(r 4)]  [(r 3)])
    (OPER 9 JSR []  [(s fact)])
    (OPER 10 Mul_I [(r 5)]  [(r -1)(r -1)])
    (OPER 11 Pass [(m RetReg)]  [(r 5)])
  )
  (BB 6
  )
  (BB 7
    (OPER 15 Pass [(r 7)]  [(i 66)])
    (OPER 16 Pass [(r 8)]  [(r 7)])
    (OPER 17 JSR []  [(s putchar)])
    (OPER 18 Pass [(r 9)]  [(i 65)])
    (OPER 19 Pass [(r 10)]  [(r 9)])
    (OPER 20 JSR []  [(s putchar)])
    (OPER 21 Pass [(r 11)]  [(i 68)])
    (OPER 22 Pass [(r 12)]  [(r 11)])
    (OPER 23 JSR []  [(s putchar)])
    (OPER 24 Pass [(r 13)]  [(i 0)])
    (OPER 25 Pass [(r 14)]  [(i 1)])
    (OPER 26 Sub_I [(r 15)]  [(r 13)(r 14)])
    (OPER 27 Pass [(m RetReg)]  [(r 15)])
  )
  (BB 9
    (OPER 33 Jmp [(bb lowlevel.BasicBlock@72ea2f77)]  [])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 8
    (OPER 29 Pass [(r 16)]  [(i 1)])
    (OPER 30 Pass [(m RetReg)]  [(r 16)])
    (OPER 32 Jmp [(bb lowlevel.BasicBlock@33c7353a)]  [])
  )
  (BB 5
    (OPER 13 Pass [(r 6)]  [(r -1)])
    (OPER 14 BEQ [(bb 8)]  [(i 0)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Pass [(r 2)]  [(i 5)])
    (OPER 5 Mov [(r 1)]  [(r 2)])
    (OPER 6 Pass [(r 3)]  [(r 1)])
    (OPER 7 JSR []  [(s fact)])
    (OPER 8 Pass [(r 4)]  [(r -1)])
    (OPER 9 JSR []  [(s printInt)])
    (OPER 10 Pass [(r 5)]  [(i 10)])
    (OPER 11 Pass [(r 6)]  [(r 5)])
    (OPER 12 JSR []  [(s putchar)])
    (OPER 13 Pass [(r 7)]  [(i 0)])
    (OPER 14 Pass [(m RetReg)]  [(r 7)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)

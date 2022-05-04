(FUNCTION  test  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Pass [(r 3)]  [(i 0)] [(PARAM_NUM 0)])
    (OPER 5 Mov [(r 1)]  [(r 3)])
    (OPER 6 Pass [(r 4)]  [(i 1)] [(PARAM_NUM 0)])
    (OPER 7 Mov [(r 2)]  [(r 4)])
    (OPER 8 Pass [(r 5)]  [(i 0)] [(PARAM_NUM 0)])
    (OPER 9 Mov [(r 2)]  [(r 5)])
    (OPER 10 Mov [(r 1)]  [(r 2)])
    (OPER 11 GT [(r 6)]  [(r 1)(r 2)])
    (OPER 12 Pass [(r 7)]  [(r 6)] [(PARAM_NUM 0)])
    (OPER 13 BEQ []  [(r 7)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 14 Pass [(r 8)]  [(i 1)] [(PARAM_NUM 0)])
    (OPER 15 Sub_I [(r 9)]  [(r 2)(r 8)])
    (OPER 16 Mov [(r 2)]  [(r 9)])
  )
  (BB 6
    (OPER 47 EQ [(r 28)]  [(r 1)(r 2)])
    (OPER 48 Pass [(r 29)]  [(r 28)] [(PARAM_NUM 0)])
    (OPER 49 BEQ []  [(r 29)(i 0)(bb 15)])
  )
  (BB 7
    (OPER 23 Pass [(r 14)]  [(i 2)] [(PARAM_NUM 0)])
    (OPER 24 Mov [(r 2)]  [(r 14)])
    (OPER 25 Pass [(r 15)]  [(i 2)] [(PARAM_NUM 0)])
    (OPER 26 EQ [(r 16)]  [(r 2)(r 15)])
    (OPER 27 Pass [(r 17)]  [(r 16)] [(PARAM_NUM 0)])
    (OPER 28 BEQ []  [(r 17)(i 0)(bb 10)])
  )
  (BB 9
    (OPER 29 Pass [(r 18)]  [(i 1)] [(PARAM_NUM 0)])
    (OPER 30 Add_I [(r 19)]  [(r 2)(r 18)])
    (OPER 31 Mov [(r 2)]  [(r 19)])
  )
  (BB 11
    (OPER 44 Pass [(r 27)]  [(r 12)] [(PARAM_NUM 0)])
    (OPER 45 BNE []  [(r 27)(i 0)(bb 7)])
  )
  (BB 12
    (OPER 38 Pass [(r 24)]  [(i 2)] [(PARAM_NUM 0)])
    (OPER 39 Add_I [(r 25)]  [(r 2)(r 24)])
    (OPER 40 Mov [(r 2)]  [(r 25)])
    (OPER 41 Pass [(r 26)]  [(r 22)] [(PARAM_NUM 0)])
    (OPER 42 BNE []  [(r 26)(i 0)(bb 12)])
  )
  (BB 13
    (OPER 43 Jmp [(bb 11)]  [(bb 11)])
  )
  (BB 8
    (OPER 46 Jmp [(bb 6)]  [(bb 6)])
  )
  (BB 14
    (OPER 50 EQ [(r 30)]  [(r 1)(r 2)])
    (OPER 51 Pass [(r 31)]  [(r 30)] [(PARAM_NUM 0)])
    (OPER 52 BEQ []  [(r 31)(i 0)(bb 17)])
  )
  (BB 16
    (OPER 53 EQ [(r 32)]  [(r 1)(r 2)])
    (OPER 54 Pass [(r 33)]  [(r 32)] [(PARAM_NUM 0)])
    (OPER 55 BEQ []  [(r 33)(i 0)(bb 19)])
  )
  (BB 18
    (OPER 56 Pass [(r 34)]  [(i 1)] [(PARAM_NUM 0)])
    (OPER 57 Sub_I [(r 35)]  [(r 2)(r 34)])
    (OPER 58 Mov [(r 1)]  [(r 35)])
    (OPER 59 Pass [(r 36)]  [(r 32)] [(PARAM_NUM 0)])
    (OPER 60 BNE []  [(r 36)(i 0)(bb 18)])
  )
  (BB 19
    (OPER 61 Pass [(r 37)]  [(r 30)] [(PARAM_NUM 0)])
    (OPER 62 BNE []  [(r 37)(i 0)(bb 16)])
  )
  (BB 17
  )
  (BB 15
    (OPER 63 Pass [(r 38)]  [(i 3)] [(PARAM_NUM 0)])
    (OPER 64 Mov [(r 2)]  [(r 38)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 10
    (OPER 32 Pass [(r 20)]  [(i 1)] [(PARAM_NUM 0)])
    (OPER 33 Mov [(r 2)]  [(r 20)])
    (OPER 34 Pass [(r 21)]  [(i 1)] [(PARAM_NUM 0)])
    (OPER 35 EQ [(r 22)]  [(r 2)(r 21)])
    (OPER 36 Pass [(r 23)]  [(r 22)] [(PARAM_NUM 0)])
    (OPER 37 BEQ []  [(r 23)(i 0)(bb 13)])
  )
  (BB 5
    (OPER 17 Pass [(r 10)]  [(i 2)] [(PARAM_NUM 0)])
    (OPER 18 Mov [(r 2)]  [(r 10)])
    (OPER 19 Pass [(r 11)]  [(i 2)] [(PARAM_NUM 0)])
    (OPER 20 EQ [(r 12)]  [(r 2)(r 11)])
    (OPER 21 Pass [(r 13)]  [(r 12)] [(PARAM_NUM 0)])
    (OPER 22 BEQ []  [(r 13)(i 0)(bb 8)])
  )
)

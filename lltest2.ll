(DATA  b)
(DATA  c)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Pass [(r 4)]  [(i 99)] [(PARAM_NUM 0)])
    (OPER 5 Mov [(r 1)]  [(r 4)])
    (OPER 6 Pass [(r 5)]  [(i 100)] [(PARAM_NUM 0)])
    (OPER 7 Mov [(r 2)]  [(r 5)])
    (OPER 8 Add_I [(r 6)]  [(r 2)(r 1)])
    (OPER 9 Mov [(r 3)]  [(r 6)])
    (OPER 10 Store []  [(r 2)(s c)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)

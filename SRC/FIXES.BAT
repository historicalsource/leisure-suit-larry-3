@echo off
copy d:\sierra\lsl3\input.log+d:\sierra\lsl3\note.log+script\input.log+script\note.log+fixes tmp > NUL
del fixes                     > NUL
sortf tmp fixes /C /Q /+1,37  > NUL
del tmp                       > NUL
del script\input.log          > NUL
del script\note.log           > NUL
del d:\sierra\lsl3\input.log  > NUL
del d:\sierra\lsl3\note.log   > NUL
exit

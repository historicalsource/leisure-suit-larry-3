@echo off
xced pushb
if "%1" == "" goto notNothing
:doitDemo
if not "%1" == "demo" goto notDemo
c:\brief\b 000.sc 120.sc 130.sc 200.sc 260.sc 266.sc 395.sc 450.sc 416.sc 530.sc -m%game%keys
xced popb
quit

:notDemo
if not "%1" == "t" goto notTrivia
c:\brief\b 14?.sc 15?.sc 16?.sc -m%game%keys
xced popb
quit

:notTrivia
if not "%1" == "c" goto notC
c:\brief\b 200.sc 21?.sc 220.sc 25?.sc -m%game%keys
xced popb
quit

:notC
if not "%1" == "l" goto notL
c:\brief\b ?99.s? -m%game%keys
xced popb
quit

:notL
if not "%1" == "bf" goto notBf
c:\brief\b 07?.sc 455.sc 325.sc 265.sc 435.sc 395.sc 335.sc -m%game%keys
xced popb
quit

:notBf
if not "%1" == "1" goto not1
c:\brief\b 1??.s? view.sh -m%game%keys
xced popb
quit

:not1
if not "%1" == "2" goto not2
c:\brief\b 2??.s? view.sh -m%game%keys
xced popb
quit

:not2
if not "%1" == "3" goto not3
c:\brief\b 3??.s? view.sh -m%game%keys
xced popb
quit

:not3
if not "%1" == "4" goto not4
c:\brief\b 4??.s? view.sh -m%game%keys
xced popb
quit

:not4
if not "%1" == "5" goto not5
c:\brief\b 5??.s? view.sh -m%game%keys
xced popb
quit

:not5
if not "%1" == "6" goto not6
c:\brief\b 6??.s? view.sh -m%game%keys
xced popb
quit

:not6
if not "%1" == "f" goto notF
c:\brief\b 235.sc 245.sc 530.sc 52?.sc 041.sc -m%game%keys
xced popb
quit

:notF
:notNothing
xced popb
echo Your choices are: 1 2 3 4 5 6 c l f bf OR demo
echo     editall    1 loads disk 1
echo     editall    2 loads disk 2
echo     editall    3 loads disk 3
echo     editall    4 loads disk 4
echo     editall    5 loads disk 5
echo     editall    6 loads disk 6
echo     editall    c loads credits
echo     editall    l loads locales
echo     editall    f loads falling scenes
echo     editall    t loads trivia
echo     editall   bf loads big faces
echo     editall demo loads demo scenes

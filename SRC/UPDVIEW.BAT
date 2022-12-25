@echo off
echo	Put VIEW disk in drive B.
pause
c:
cd\%game%
 ds e view
 md b:\view
upd b:\view from v: -f
updsrc

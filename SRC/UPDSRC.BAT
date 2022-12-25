@echo off
echo	Put SOURCE CODE disks in drive A.
pause
xced clean
c:
cd\%game%
 ds en
 md a:\src
upd a:\src -f
echo	All done!

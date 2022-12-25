@echo off
echo Put PICTURE, SOUND and INC disk in drive A.
pause
c:
cd\%game%
 ds en/s
 md a:\pic
 md a:\sound
 md a:\inc
upd a:\pic   from pic   -f
upd a:\inc   from inc   -f
upd a:\sound from sound -f
updview

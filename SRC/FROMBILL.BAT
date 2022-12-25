@echo off
xced clean 
d:
cd \%game%
xced clean
cd\
l:
ds en a:/s
dir a:\pic   > tmp.tmp
dir a:\view >> tmp.tmp
dir a:\test >> tmp.tmp
type tmp.tmp | more
upd d:\%game%                -f
upd d:\%game%\pic  from pic  -f
upd d:\%game%\view from view -f
copy a:test
copy a:pic pic
copy a:view view
slash /d
echo Send tmp file to printer?  (y/n):
slash /d
query
if not errorlevel 1 quit
copy tmp.tmp lpt1
cat file.* -n > lpt1
copy c:\epson\formfeed lpt1

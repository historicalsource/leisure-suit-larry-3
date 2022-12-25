@echo off
s:exe\vcpp vocab.vc vocab.tmp
copy %vb%+vocab.tmp vocab.txt
s:exe\vc > vc.err
del vocab.tmp
copy vocab.000 script
type vc.err

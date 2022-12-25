;**
;**	Face.sh -- common header for all the Big Face scenes
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 8, 1989
;**


(extern
	React					71 1
	PrintL				71	2
	PrintP				71	3
)


;(define	vTawniFace					265)	;**	Each view equals "curRoomNum"
;(define	vSuziFace					325)
;(define	vDaleFace					335)
;(define	vBambiFace					395)
;(define	vCherriFace					435)
;(define	vPattiFace					455)
	(define	lEyeWest				0)
	(define	lEyeEast				1)
		(define	cLookLeft	0)
		(define	cLookAtUser	2)
		(define	cLookRight	6)

	(define	lLidWest				2)
	(define	lLidEast				3)
		(define	cLidOpen		0)
		(define	cLidSquint	1)
		(define	cLidClosed	2)

	(define	lNose					4)
		(define	cInhale		0)
		(define	cExhale		2)

	(define	lMouthOh				5)
	(define	lMouthSmile			6)
	(define	lMouthFrown			7)
	(define	lMouthLick			8)
		(define	cTongueOut	5)


(enum												;**	React procedure uses these values
	rNormal
	rLook
	rSmile
	rSexy
	rFrown
	rLove
	rOh
	rSilly
)


(enum 1											;**	Notify uses these values
	EyeWest
	EyeEast
	EyelidWest
	EyelidEast
	Nose
	Mouth
)


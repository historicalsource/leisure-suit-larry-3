;**
;**	Header for scripts 420 & 421 -- outside the showroom
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 20, 1989
;**


(define	vRoom							420)
	(define	lDoor					0)
	(define	lPoster				1)
		(define	cPoster1	 	0)
		(define	cPoster2	 	1)
	(define	lPhone				1)
		(define	cPhone 	 	2)
	(define	lPodium				2)
	(define	lRope					3)

(define	vStageManager				421)
	(define lTalking				0)
	(define lStompingFoot		1)
	(define lLouch					2)
	(define lLpunchingBag		3)
	(define lBigPunch				4)
	(define lLflying				5)

(define	vMaitreD						422)
	(define 	lMovingRight		0)
	(define 	lMovingLeft			1)
	(define	lMDtalking			2)
	(define	lFaceIcon			3)
		(define	cSmile		6)
		(define	cBigSmile	9)
	(define	lHandOut				4)

(define	vLdeadStageManager		423)

;(define	vCherri						436)
	(define	lTalkingPhone		4)


;(define	sNearDanger					006)	
;(define	sDoorClosing				011)
;(define	s1Boing						003)
;(define	sHitBottom					012)


(define	nearPhone				(& (ego onControl) cMagenta))
(define	nearStageDoor			(& (ego onControl) cCyan))
(define	nearMaitreD				(& (ego onControl) cRed))


(define	MaitreDX						172)
(define	MaitreDY						105)
(define	cherriY						124)
(define	onPhoneX						 82)

(define	outsideStageDoorPosn	45 140)
(define	insideStageDoorPosn	13 140)


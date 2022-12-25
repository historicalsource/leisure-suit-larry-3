;**
;**	Header for room 323 & 324 -- Suzi Cheatum's office
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	August 28, 1989
;**


;**	lawyerState						135	;**		change lawyerState to this
;**		(enum									;**	value in this room at this time:
;**			LSbusy							;**	000 start of game
;**			LSfree							;**	435 when Cherri hints for land
;**			LSwaiting4deed					;**	323 at end of Suzi's free chat
;**			LSdeedReady						;**	320 as L leaves 320 for 220
;**			LSneeds500						;**	320 L gets the deed from Roger
;**			LSdivorce						;**	320 L gives his $500 to Roger
;**			LSwaiting4divorce				;**	323 during $500 chat (or 324)
;**			LSdivorceReady					;**	320 as L leaves 320 for 220
;**			LSdone							;**	320 get divorce from Roger
;**		)


(define	nearDoor					(& (ego onControl) cBlue))
(define	nearSofa					(& (ego onControl) cGreen))

(define	humpPosn					123 118)
(define	headPosn					133 117)
(define	hatX							115)
(define	hatY							138)
(define	phoneX						146)	;**	end of moveTo
(define	phoneY						  6)
(define	TrapdoorX					152)	;**	end of moveTo
(define	TrapdoorY					  6)
(define	SuziX							242)
(define	SuziY							120)
(define	chairX						247)
(define	chairY						133)
(define	sSitX							233)
(define	sSitY							119)
(define	deskUR_X						232)	;**	corners of Suzi's desk
(define	deskUR_Y						125)
(define	deskUL_X						190)
(define	deskUL_Y						125)
(define	deskLL_X						199)
(define	deskLL_Y						169)
(define	faxX							204)	;**	Suzi's destination
(define	faxY							204)
(define	stripX						148)	;**	where Suzi takes off her clothes
(define	stripY						133)
(define	doorY							156)	;**	center of doorway


(define	vLziegfieldSitting		322)
	(define	lSitting				0)

(define	vSuziOffice					323)
	(define	lLowerDoor			0)
	(define	lUpperDoor			1)
	(define	lFaxMachine			2)
	(define	lPhoneRinging		3)
	(define	lTrapdoor			4)
	(define	lAirPlane			5)
	(define	lPhone				6)
		(define	cBoth			0)
		(define	cPad			1)
		(define	cPhone		2)
	(define	lSuziClothes		7)

(define	vSuziSitting				324)
	(define	lTuggingSkirt		0)
	(define	lGetPhone			1)
	(define	lTalkPhone			2)
	(define	lTalking				3)
		(define	cNotTalking	0)
	(define	lWriting				4)
	(define	lFaceIcon			5)

;(define	vSuziFace					325)	is not used in this file.
(define	vLsitting323				326)
	(define	lSittingCouch		0)
	(define	lYawning				1)
	(define	lBlinking			2)
	(define	lLookingCeiling	3)
	(define	lStretching			4)

(define	vSuzi							327)	;**	walk-around view
(define	vL&SdoingIt					328)
	(define	lSstripping			0)
	(define	lHat					1)
	(define	lRaiseTail			2)
	(define	lL&Sscrewing		3)
	(define	lSarmToPhone		4)
	(define	lPhoneToHead		5)
	(define	lStalking			6)
		(define	cMouthShut	1)
	(define	lSgettingPad		7)
	(define	lSwriting			8)
	(define	lSthrowingPlane 	9)

(define	vSuziChair					329)
;	(define	lTuggingSkirt		0)
	(define	lCrossLegs			1)
	(define	lWiggleLegs			2)
	(define	lChair				3)
	(define	lDesktopStuff		4)

;(define	vLziegfield					708)

;(define	sPhone						020)
;(define	sForeplay					008)	
;(define	sInterrupted				009)	

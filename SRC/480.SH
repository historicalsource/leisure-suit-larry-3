;**
;**	Header for region 480 -- Patti's Penthouse Apartment 
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	September 29, 1989
;**



(define	vRoom							480)
	(define	lDoor					0)
   (define	lClothes 			1)
		(define cPanties		0)
		(define cBra			1)
		(define cPantyhose	2)
	(define	lDress				2)			;**	do endloop of this
	(define	lTelescope			3)
	(define 	lWine					4)
		(define	cTrayOnly	0)
		(define	c0glasses	1)
		(define	c1glass		2)
		(define	c2glasses	3)
		(define	cAll			4)

(define vLroom							481)
	(define	lGettingOnBed		0)
	(define	lGettingBottle		1)
		(define	cEraseBottle 7)
	(define 	lPourGlass1			2)
	(define 	lPourGlass2			3)
	(define 	lGiveWineToPatti	4)
		(define	cDrawBottle	5)
		(define	cPhasGlass	9)				;**	endLoop
	(define 	lGettingLwine		5)
		(define	cEraseGlass2 0)			;**	beg of loop

(define	vPinSheet					482)
	;loops 0-3 walking views

(define	vP&LDoingIt					483)
	(define 	lLdrinkingWine		0)
	(define 	lPdrinkingWine		1)
	(define 	lBothKissing		2)
	(define 	lBothRollingOntoP	3)
	(define 	lPtakingWine		4)

(define	vLafterSex					484)
(define	vPafterSex					485)
	(define	lSleeping			0)			;**		These two views share 
	(define	lThoughtBubbles	1)			;**	these same loop names.
	(define	lLawakening			2)			;**		But these two loops 
	(define	lPfeelingBed		2)			;**	are different.
	(define	lSittingUp			3)			;**		These two are once again
	(define	lSlidingAcrossBed	4)			;**	the same.
	(define	lPstanding			5)			;**	<-- BUT, only P has this loop

(define 	vBalcony						490)
	(define	lSparkle				2)

(define	vLsmall						491)
(define	vPbalcony					492)

;(define	vLundressingHunk			721)


;(define	vThermometer				054)
	(define	lRising				0)
	(define	lPulsating			1)


(define	sLontoBed					480)
(define	sFirstKiss					481)
;(define	THIS NUMBER LEFT BLANK	482)
(define	sFadeToBlack1				483)
(define	sThermometers				484)
;(define	sFadeToBlack2				485)	;**		These two sounds are also used
;(define	sAfterGlow					486)	;**	again in 485/490, cannibals.
(define	sArnoldsStinger			487)
(define	sPfeelsBed	  				488)
(define	sPonBalcony	 				489)
(define	sItsHim						490)
(define	sPlearnsHerQuest			491)
(define	sPmissesL					492)


(define	nearBottle				(ego inRect 126 126 196 141))
(define	onBalcony				(& (ego onControl) cGreen))
(define	nearClothes				(& (ego onControl) cRed))

(define	NWofBed					(& (ego onControl origin) cLtBlue))
(define	NEofBed					(& (ego onControl origin) cLtGreen))
(define	SWofBed					(& (ego onControl origin) cLtCyan))
(define	SEofBed					(& (ego onControl origin) cLtRed))

(define	bedCenterX				161)		;**	These refer to walking around bed 
(define	bedCenterY				120)
(define	bedWestX					 95)
(define	bedEastX					231)
(define	wineX						158)		;**	Not where wine sits, but where
(define	wineY						140)		;**	ego walks to when placing it.


;**	
;**	Running	------Segment------
;**	 Time		Time	Number   Name				Action
;**	==============================================================================
;**	
;**	
;**	 0:00		1:00	480	sLontoBed			after placing wine, 
;**															L climbs on bed
;**	
;**	 1:00		0:42	481	sFirstKiss	
;**	
;**	 1:40												L rolls on top of P
;**	
;**	 1:42				483	sFadeToBlack1
;**	
;**	 2:45				484	sThermometers	 
;**	
;**	 3:42				485	sFadeToBlack2
;**	
;**	 3:55				486	sAfterGlow
;**	
;**	 5:00				487	sArnoldsStinger
;**	
;**	 5:45												L leaves bed
;**	
;**	 6:10				488	sPfeelsBed			 
;**	
;**	 6:45				489	sPonBalcony			
;**	
;**	 7:15												P sees L
;**	
;**	 7:33				490	sItsHim
;**	
;**	 8:00				491	sPlearnsHerQuest
;**	
;**	 8:15												end scene
;**	
;**						492	sPlonely				when P returns to room from 
;**															balcony; just a simple
;**															looped walkin' around tune


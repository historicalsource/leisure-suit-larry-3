;**
;**	Logics for room 290 -- speed check upon restart
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 25, 1989
;**


(script#				290)

(public
	rm290				0
)

(local
	doneTime
)


(define	vRoom						290)		;**	a black on black with black ego


(instance rm290 of Room
	(properties
		picture		blackPicture
		style			IRISIN
	)

	(method (init &tmp filthFile [str 9])
		(HandsOff)
		(= demo FALSE)							;**	Do housekeeping first.
		(StatusLine	disable)
		(TheMenuBar	hide)

		(super init)

		;**	restore old filthLevel:
		(= filthFile (FOpen {RESOURCE.LL3} fRead))
		(if (!= filthFile ERROR)			;**	else playing at Clean.
			(= filthLevel (ReadNumber (FGets @str 8 filthFile)))
		)
		(FClose filthFile)

		(ego
			view				vRoom,
			posn				20 100,
			setStep			1 1,
			setMotion		MoveTo 3000 100,
			setCycle			Walk,
			init,
		)

		(theGame setSpeed 0)
	)

	(method (doit)
		(super doit)

		(++ machineSpeed)
		(if (== machineSpeed 1)
			(= doneTime (+ 60 (GetTime)))
		)
		(if ( < doneTime (GetTime))		
		
			;**	60 ticks have elapsed; set up the game



;**				(if (TstFlg inQA)
;**					(Printf "Attention QA: 
;**						this machine has an Al Lowe speed rating of %d. 
;**						Please note this number and the type of machine you're 
;**						playing on and tell Al." 
;**						machineSpeed)
;**				)


			(if debugging
				(theGame setSpeed 2)
			else
				(theGame setSpeed 6)
			)

			;**					NOTE:	expletiveBuffer has a 40 character limit!!
			(= expletive 
				(Format @expletiveBuffer "%s" 
					(switch filthLevel
						(RAUNCHY		{Son of a bitch!})
						(DIRTY		{Damn it to hell!})
						(RISQUE		{Damn!})
						(CLEAN		{Gol dang it!})
						(else			{Golly gee!})
					)
				)
			)
			(Format @filthLevelBuffer "%s" 
				(switch filthLevel
					(RAUNCHY			{Totally Raunchiest})
					(DIRTY			{Really Filthy})
					(RISQUE			{Pretty Dirty})
					(CLEAN			{Rather Risque})
					(else				{Mother Goose})
				)
			)
			(if (> (DoSound NumVoices) 3)	;**	don't prolong bad sound!
				(= bgMusicLoops	FOREVER)
			else
				(= bgMusicLoops	1)
			)
 			(= creditsFinished	0)			;**	Yes. Credits, please.
			(= autoSaveMinutes	5)			;**	Do a little housekeeping.
			(= gameSeconds 		0)
			(= gameMinutes			0)
			(= gameHours			0)
			(= stringDelay			20)
			(= normalEgoView		vLtropic)
			(= egoState				TROPIC)
			(= egoName				(Format @egoNameBuffer "Larry"))
			(ClrFlg preventAutoSave)
			(ClrFlg cantSave)
			(TheMenuBar	draw)
			(StatusLine	enable)
			(curRoom newRoom 200)			;**	first real room
		)
	)
)

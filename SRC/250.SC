;**
;**	Logics for room 250 -- Casino exterior
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 28, 1989
;**


(script#				250)

(public
	rm250				0
)


(procedure
	PrintPlot
)


(define	vRoom							250)
	(define	lWaterfall			0)

(define	vLroom				(+ 1 250))
	(define	lLsharpeningKnife	0)

(define	vSCIby						252)
	(define	lSCIby				0)
	(define	lJeff					1)
	(define	lBob					2)
	(define	lPablo				3)
	(define	lStuart				4)

;(define	vLstooping					709)
;	(define	stoopOffset			19)


(define	sKnifeSharpening			250)


(define	hitTree					(& (ego onControl) cGreen))
(define	enteringCasino			(& (ego onControl) cRed))
(define	enteringNativesInc	(& (ego onControl) cBlue))
(define	climbingStairs			(& (ego onControl) cLtRed))

(define	sharpenX						278)
(define	sharpenY						134)



(enum												;**	CreditsScript state values
	Credit0
	Credit1
	Credit2
	Credit3
	Credit4
	Credit5
	Credit6
	Credit7
	Credit8
	Credit9
	clearCredits
)


(enum												;**	RoomScript state values
	intro
	learnThePlot
	readMsg2
	readMsg3
	readMsg4
	plotDone
	startSharpening
	atIt
	DrBenDover
	rubIt
	standUpBoy
	getDone
	backFromFiring
	ohShit
)


(local
	onSteps
	seenMsg
	seenCasinoMsg
	seenNativesMsg
	sharpenCount
	[string 200]
)


(instance rm250 of Room
	(properties
		picture		250
		south			260
	)

	(method (init)
		(if (and	(ego has iKnife)
					(== ((Inventory at iKnife) view) iKnife))
			(Load VIEW vLroom)
			(Load VIEW vLstooping)
			(Load VIEW iKnife)
			(Load SOUND sKnifeSharpening)
		)

		(if (== egoState GETTING_FIRED)
			(self style IRISOUT)
		)

		(super init)

		(self
			setScript 		RoomScript
		)

		(if (not (TstFlg beenIn250))
			(Load VIEW vPointer)
		)

		(if (and (TstFlg beenIn250)
					(not (TstFlg sawCredits250)))
			(Load VIEW vSCIby)
			(aCredit1			init)
			(aCredit2			init)
		)

		(if (> machineSpeed pc8088)
			(aFountain			init)
		)

		(cond
			((== prevRoomNum 305)
				(ego
					posn			2 186,
				)
				(= seenNativesMsg TRUE)
			)
			((== prevRoomNum 220)		
				(ego posn	2 122)
			)
			((== prevRoomNum 253)		
				(ego posn 317 125)
			)
			((== prevRoomNum 260)		
				(ego 
					posn 			154 187, 
					loop 			facingNorth,
				)
			)
			(else 
				(= onSteps TRUE)		
				(ego 
					posn 			317 118, 
					loop 			facingWest,
				)
			)
		)

		(NormalEgo)

		(if onSteps
			(ego
				setPri 			11,
				observeControl cYellow,
				init
			)
		else
			(ego
				observeControl cLtMagenta,
				init
			)
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (ego edgeHit)
			(cond
				((& (ego onControl) cBlue)		(curRoom newRoom 305))
				((& (ego onControl) cCyan)		(curRoom newRoom 220))
				((& (ego onControl) cRed)
					(if onSteps						(curRoom newRoom 400)
					else		 						(curRoom newRoom 253))
				)
			)
		)

		(cond										;**	stair handler
	 		((== onSteps ERROR)				;**	do nothing while sharpening knife
			)
			((& (ego onControl) cLtRed)	;**	on steps 
				(= onSteps TRUE)
			)
			((& (ego onControl) cLtCyan)	;**	off steps
				(= onSteps FALSE)
			)
		)

		(if (== onSteps TRUE)				;**	ignore error handler
			(ego
				setPri 			11,
				observeControl cYellow,
				ignoreControl	cLtMagenta
			)
		)

		(if (== onSteps FALSE)
			(ego
				setPri 			RELEASE,
				ignoreControl	cYellow cRed,
				observeControl cLtMagenta
			)
		)

		(if (and enteringNativesInc
					(or	egoIsPatti
							(!= egoState NORMAL)))
			(if (not seenNativesMsg)
				(= seenNativesMsg TRUE)
				(ego
					posn 				(ego xLast) (ego yLast),
					setMotion		FALSE,
					observeControl cBlue,
				)
				(if egoIsPatti
					(Print "You have no business in \"Natives, Inc.,\" Patti.
						There's nothing in there to help you find Larry!")
				else
					(Print "You can't enter \"Natives, Inc.\" dressed like that!")
				)
				(RedrawCast)
			)
		else
			(= seenNativesMsg FALSE)
		)

		(if (and enteringCasino
					onSteps
					(!= egoState WEARING_ZIEGFIELD)
					(!= egoState NORMAL))
			(if (not seenCasinoMsg)
				(= seenCasinoMsg TRUE)
				(ego
					posn 				(ego xLast) (ego yLast),
					setMotion		FALSE,
					observeControl cRed
				)
				(Print "You can't enter the casino dressed like that!")
				(RedrawCast)
			)
		else
			(= seenCasinoMsg FALSE)
		)

		(if hitTree
			(if (not seenMsg)
				(= seenMsg TRUE)
				(ShakeScreen 1 SHAKE_LEFT_RIGHT)
				(Print "Ow!")
				(Print "(There must not be a path that way!)" LOW)
			)
		else
			(= seenMsg FALSE)
		)
	)


	(method (handleEvent event)
		(if (and (not (super handleEvent: event))
					(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if (Said 'rub, sharpen/knife>')
			(cond
				((not	(ego has iKnife))
					(DontHave)
				)
				((== ((Inventory at iKnife) view) ivSharpKnife)
					(ItIs)
				)
				((Said '/[/noword]')
					(Print "On what?")
				)
				((Said '//fountain, seat, bench')
					(Print "The fountain's rough surface would only dull your
						knife.")
				)
				((not (Said '//step, sidewalk'))
					(Print "That would never sharpen a fine ginsu knife.")
				)
				((not onSteps)
					(Print "Move closer to the steps.")
				)
				((!= egoState NORMAL)
					(NotNow)
				)
				(else
					(self changeState startSharpening)
				)
			)
			(event claimed TRUE)
		)

		(if (Said 'look>')
			(cond
				((Said '/tree, bush, ground')
					(Print "The landscaping of the casino blends nicely
						into the jungle beyond.")
				)

				((Said '/native, office')
					(if egoIsPatti
						(Print "You have no need to go that way, %s." egoName)
					else
						(Print "Down the pathway to the lower left is the
							headquarters of \"Natives, Inc.\"")
					)
				)

				((Said '/water, waterfall, fountain')
					(Print "Isn't it beautiful?")
				)

				((Said '/step, walk, concrete')
					(Print "The steps leading into the casino are made
						of smooth concrete...
						as smooth as a sharpening stone.")
				)

				((Said '[/room]')
					(Print "You are outside the island's casino.
						Steps lead up into the lobby,
						while water splashes merrily down the fountain.")
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(if (not (TstFlg beenIn250))
					(= cycles 25)
				)
			)

			(learnThePlot
				(Format @string 
					"Down this path lies your office.
					Say, what about that cute little redhead in accounting?
					You've always wanted a chance to balance her figures!"
				)	
				(= seconds (PrintPlot))
				(aCredit1
					view			vPointer,
					posn			0 156,
					setCycle		Forward,
					init,
				)
				(delay (+ 2 11))
			)

			(readMsg2
				(Format @string 
					"It IS nearly time to start work.
					You'd better hurry, Larry!
					You're going to be late again
					and you know how much Chairman Kenneth hates tardiness."
				)	
				(= seconds (PrintPlot))
			)

			(readMsg3
				(Format @string 
					"\"I hope this trouble with Kalalau doesn't influence
					my excellent working relationship with Chairman Kenneth,\"
					you think."
				)	
				(= seconds (PrintPlot))
			)

			(readMsg4
				(SetFlg beenIn250)
				(Format @string 
					"\"Surely, it won't make any difference to Ken.
					My meteoric rise through the ranks of `Natives, Inc.'
					is because of my superior managerial abilities,
					not just because I married the boss' daughter!\""
				)	
				(= seconds (PrintPlot))
			)

			(plotDone
				(aCredit1 dispose)
				(delay 0)
			)


			;**	handle sharpening the knife here:

			(startSharpening
				(Ok)
				(HandsOff)
				(= onSteps ERROR)				;**	turn off auto shit
				(ego
					illegalBits		0,
					setMotion		MoveTo sharpenX sharpenY self
				)
			)

			(atIt
				(ego
					view				vLstooping,
					loop				0,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(DrBenDover
				(Print "You rub your dull ginsu knife back and forth on the
					smooth concrete steps of the casino."
					#icon iKnife 0 0
				)
				(= sharpenCount 0)
				(delay 2)
				(= oldGameSpeed (theGame setSpeed 6))
			)

			(rubIt
				(soundFX
	 				number			sKnifeSharpening,
					loop				1,
					play,
				)
				(ego
					view				vLroom,
					cel				0,
					setCycle			EndLoop,
				)
				(= cycles 7)
				(if ( < (++ sharpenCount) 11)
					(-- state)
				)
			)

			(standUpBoy
				((Inventory at iKnife) view ivSharpKnife)
				(Format ((Inventory at iKnife) name) "Sharp Knife")
				(soundFX 			stop)
				(theGame 
					setSpeed 		oldGameSpeed,
					changeScore 	50,
				)
				(ego
					view				vLstooping,
					loop				0,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(getDone
				(NormalEgo)
				(= onSteps FALSE)				;**	turn auto shit back on.
				(Print "Much to your surprise, it becomes razor sharp!
					Without thinking, you hastily shove it back in your pocket.")
				(Print "OW!")
			)
		)
	)
)

(instance aFountain of Prop
	(properties
		view					vRoom
		loop					lWaterfall
		x						194
		y						148
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			isExtra			TRUE,
			setCycle			Forward,
			setPri			11,
		)
	)
)

(instance aCredit1 of Prop
	(properties
		view					vSCIby
;		loop					lSCIby
;		cel					0
		x						288
		y						131
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
		)
	)
)
(instance aCredit2 of Prop
	(properties
		view					vSCIby
		loop					lJeff
;		cel					0
		x						288
		y						154
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
			setScript		CreditsScript,
		)
	)
)

(instance CreditsScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(Credit0
				(delay 3)
			)

			(Credit1
				(aCredit1 setCycle EndLoop)
				(= cycles 13)
			)

			(Credit2
				(aCredit2 setCycle EndLoop)
				(= cycles 16)
			)

			(Credit3
				(aCredit2 setCycle BegLoop self)
			)

			(Credit4
				(aCredit2
					loop			lBob,
					setCycle		EndLoop
				)
				(= cycles 16)
			)

			(Credit5
				(aCredit2 setCycle BegLoop self)
			)

			(Credit6
				(aCredit2
					loop			lPablo,
					setCycle		EndLoop
				)
				(= cycles 16)
			)

			(Credit7
				(aCredit2 setCycle BegLoop self)
			)

			(Credit8
				(aCredit2
					loop			lStuart,
					setCycle		EndLoop
				)
				(= cycles 16)
			)

			(Credit9
				(SetFlg sawCredits250)
				(aCredit1 setCycle BegLoop)
				(aCredit2 setCycle BegLoop self)
			)

			(clearCredits
				(aCredit1 dispose)
				(aCredit2 dispose)
			)
		)
	)
)
(procedure (PrintPlot &tmp t )
	(Print @string
		#at 			10 5 
		#width 		290
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)


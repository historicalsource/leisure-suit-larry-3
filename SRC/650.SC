;**
;**	Logics for room 650 -- THE END
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 22, 1990
;**


(script#				650)

(public
	rm650				0
)


(procedure
	Print650
)


(define	vRoom							650)
	(define	lATPs					0)
		(define	cKeyboard	0)
		(define	cGlass		1)
		(define	cBigGlass	2)
	(define	lRightHand			1)
	(define	lLeftHand			2)
	(define	lBrief				3)
	(define	lLeg					4)
	(define	lLeftys				5)
	(define	lLittlestLarry		6)
	(define	lDoor					7)

(define	vTheEnd						651)
	(define	lTheEnd				0)
	(define	lReflections		1)


;(define	sTitle						120)



(enum												;**	RoomScript state values
	intro
	itAllBegins
	outsideAbar
	leftys
	anythingUsay
	compileIt
	littleLarry
	showGraphics
	startWalking
	atDoor
	enterBar
	closeTheDoor
	delaySome
	blankScreen
	backToBrief
	theEnd1
	theEnd2
	gamesOver
	recommendations
	whileYoureAtIt
	didYouTry7
	didYouTry6
	didYouTry5
	didYouTry4
	didYouTry3
	didYouTry2
	didYouTry1
	goAgain
)


(local
	theCounter
	seenMsg
	[string 222]
)


(instance rm650 of Room
	(properties
		picture		650
	)


	(method (init)
		(HandsOff)

		(Load SCRIPT REVERSE)

		(aLeg					init)
		(aMonitor			init)
		(aLeftHand			init)
		(aRightHand			init)
		(aDoor				init)
		(aTheEnd				init)

		(super init)

		(addToPics
			add 				atpKeyboard,
			add 				atpGlass,
			add 				atpBigGlass,
			doit,
		)

		(self
			setScript 		RoomScript
		)

		(= oldGameSpeed (theGame setSpeed 6))

		(NormalEgo lLittlestLarry vRoom)
		(HandsOff)
		(ego 
			posn 				999 999,
			setPri			15,
			setStep			1 1,
			setLoop			lLittlestLarry,
			ignoreActors,
			moveSpeed		2,
			init,
		)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (and (not (super handleEvent: event))
					(not (event claimed))
					modelessDialog
					seconds
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(= seconds 0)
			(cls)
			(self cue)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(intro
				(delay 3)
			)

			(itAllBegins
				(Format @string "Let's see, now: it all begins in Lost Wages...")
				(= seconds (Print650))
			)

			(outsideAbar
				(Format @string "...outside a bar named, \"Louie's.\"
					No, \"Lois'.\" No, that's not right either.
					Hey, Patti! Gimme a name for a bar.\"")
				(= seconds (Print650))
			)

			(leftys
				(Format @string "\"How about `Lefty's?'\"")
				(delay (Print650 122))
			)

			(anythingUsay
				(Format @string "\"That's good, babe!\"")
				(= seconds (Print650 111))
			)

			(compileIt
				(Format @string "Hmmm. Maybe I should compile and test this.")
				(= seconds (Print650))
			)

			(littleLarry
				(Format @string "Let's see if my little Larry can go inside...")
				(aMonitor			hide)
				(= seconds (Print650))
			)

			(showGraphics
				(aMonitor
					show,
					setLoop			lLeftys,
					setCycle			Forward,
				)
				(aDoor				
					posn				64 99,
				)
				(ego
					posn				64 103,
				)
				(delay 6)
			)

			(startWalking
				(ego
					setMotion		MoveTo 64 100 self,
				)
			)

			(atDoor
				(aDoor
					setCycle			EndLoop self,
				)
			)

			(enterBar
				(ego
					posn				999 999,
				)
				(= seconds 3)
			)

			(closeTheDoor
				(aDoor
					setCycle			BegLoop self,
				)
			)

			(delaySome
				(delay 6)
			)

			(blankScreen
				(aMonitor			hide)
				(aDoor  
					posn				234 345,
				)
				(ego
					posn				999 999,
				)
				(delay 3)
			)

			(backToBrief 
				(aMonitor
					show,
					loop				lBrief,
					setCycle			Forward,
				)
				(delay 3)
			)

			(theEnd1
				(if seenMsg
					(= seconds 7)
					(= state (- showGraphics 1))
				else
					(aTheEnd 
						init,
						setCycle 	EndLoop self,
					)
				)
			)

			(theEnd2
				(aTheEnd 
					setLoop			lReflections,
					cycleSpeed		3,
					setCycle			Forward,
				)
				(delay 3)
			)

			(gamesOver
				(= seenMsg TRUE)
				(Format @string "Congratulations! You did it!
					You've won!!")
				(= seconds (Print650))
			)

			(recommendations
				(Format @string "We hope you've enjoyed \"Leisure Suit Larry 3:
						Passionate Patti in Pursuit of the Pulsating Pectorals!\"
						You, Larry and Patti have certainly been 
						through a lot together!")
				(= seconds (Print650))
			)

			(whileYoureAtIt
				(if (and (TstFlg scoredTan)
							(TstFlg seenDale)
							(TstFlg hadBambi)
							(TstFlg scoredDuckPoints)
							(not (TstFlg skippedLogRide))
							(not (TstFlg missedKeycardPoints))
							(TstFlg scoredSuzi))
					(Format @string "You did an excellect job! What a score!!")
				else
					(Format @string "You did a great job, 
						but you did miss a little something.
						For instance...")
				)
				(= seconds (Print650))
			)

			(didYouTry7
				(if (not (TstFlg skippedLogRide))
					(self cue)
				else
					(Format @string "You shouldn't have short-changed yourself
						while straddling that log!")
					(= seconds (Print650))
				)
			)

			(didYouTry6
				(if (TstFlg scoredDuckPoints)
					(self cue)
				else
					(Format @string "You know, you could find some of your
						missing points back at \"The Comedy Hut.\"")
					(= seconds (Print650))
				)
			)

			(didYouTry5
				(if (TstFlg missedKeycardPoints)
					(Format @string "What a shame you didn't \"sleuth\" more
						when you had your divorce.")
					(= seconds (Print650))
				else
					(self cue)
				)
			)

			(didYouTry4
				(if (TstFlg scoredTan)
					(self cue)
				else
					(Format @string "Isn't it surprising that you spent so much
						time at a tropical resort and never took time out to
						improve your tan?")
					(= seconds (Print650))
				)
			)

			(didYouTry3
				(if (not (TstFlg seenDale))
					(Format @string "You wonder now
						why you ignored Chip 'n' Dale's
						on your way to the bamboo forest.")
					(= seconds (Print650))
				else
					(self cue)
				)
			)

			(didYouTry2
				(if (TstFlg hadBambi)
					(self cue)
				else
					(Format @string "Do you remember when you were at Fat City?
						Someone was waiting for you, but you missed her!")
					(= seconds (Print650))
				)
			)

			(didYouTry1
				(if (TstFlg scoredSuzi)
					(self cue)
				else
					(Format @string "You probably don't know it, 
						but a lot of the points that are missing above got lost
						when you didn't completely
						explore the island in your showgirl costume.")
					(= seconds (Print650))
				)
			)

			(goAgain
				(= state (- showGraphics 1))
			)
		)
	)
)

(instance atpKeyboard of PicView
	(properties
		view					vRoom
		loop					lATPs
		cel					cKeyboard
		x						60 
		y						189
		priority				14
	)
)								 
(instance atpGlass of PicView
	(properties
		view					vRoom
		loop					lATPs
		cel					cGlass
		x						221 
		y						158
		priority 			15
		signal				ignrAct
	)
)
(instance atpBigGlass of PicView
	(properties
		view					vRoom
		loop					lATPs
		cel					cBigGlass
		x						192
		y						173
		priority 			15
		signal				ignrAct
	)
)

(instance aRightHand of Prop
	(properties
		view				vRoom
		loop				lRightHand
;		cel				cRightHand
		x					103
		y					173
		cycleSpeed		1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			setCycle			Forward ,
			ignoreActors,
		)
	)

	(method (doit &tmp i)
		(super doit)

		(= i (Random 0 33))
		(cond
			((== i 1)	(self posn 100 178))
			((== i 2)	(self posn 101 184))
			((== i 3)	(self posn  34 180))
			((== i 4)	(self posn  36 184))
			(( < i 17)	(self cel 0))
			(else			(self cel 1))
		)
	)
)

(instance aLeftHand of Prop
	(properties
		view				vRoom
		loop				lLeftHand
;		cel				cLeftHand
		x					17
		y					173
		cycleSpeed		1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			setCycle			Forward ,
			ignoreActors,
		)
	)

	(method (doit &tmp i)
		(super doit)

		(= i (Random 0 33))
		(cond
			((== i 1)	(self posn  11 173))
			((== i 2)	(self posn  18 180))
			((== i 3)	(self posn   8 185))
			(( < i 17)	(self cel 0))
			(else			(self cel 1))
		)
	)
)

(instance aMonitor of Prop
	(properties
		view				vRoom
		loop				lBrief
;		cel				cMonitor
		x					61
		y					104
		cycleSpeed		0
	)

	(method (init)
		(super init)
		(self
			setPri			4,
			setCycle			Forward,
			ignoreActors,
		)
	)

	(method (doit)
		(super doit)
		(if (== loop lBrief)
			(switch (Random 0 7)
				(1	(self setCycle	Forward))
				(2	(self setCycle	Reverse))
				(3 (self cycleSpeed (Random 0 1)))
			)
		)
	)
)
(instance aLeg of Prop
	(properties
		view				vRoom
		loop				lLeg
;		cel				cLeg
		x					306
		y					189
		cycleSpeed		1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward ,
		)
	)

	(method (doit)
		(super doit)
		(switch (Random 0 7)
			(0
				(self
					setCycle		Forward,
				)
			)
			(7
				(self
					setCel,
				)
			)
		)
	)
)
(instance aDoor of Prop
	(properties
		view				vRoom
		loop				lDoor
;		cel				0
		x					640
		y					990
		cycleSpeed		1
	)

	(method (init)
		(super init)
		(self
			setPri			7,
			ignoreActors,
		)
	)
)

(procedure (Print650 w &tmp t )
	(if (< argc 1)
		(= w 200)
	)
	(Print @string
		#at			-1 10
		#width 		w
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 2 t))
)


(instance aTheEnd of Prop
	(properties
		view					vTheEnd
		loop					lTheEnd
;		cel					0
		x						96
		y						47
	)
)
;**				(didYouTry
;**					(if (TstFlg )
;**						(self cue)
;**					else
;**						(Format @string "")
;**						(= seconds (Print650))
;**					)
;**				)


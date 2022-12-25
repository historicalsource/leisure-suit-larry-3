;**
;**	Logics for room 482 -- Patti's Penthouse Apartment (Larry leaves Patti)
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 17, 1989
;**


(script#				482)

(include	"480.sh")
(include	"480.inc")

(public
	rm482				0
)


(define	LarryPosn			178 105)		;**		These are when on the bed,
(define	PattiPosn			155  98)		;**	but only in this view.


(enum												;**	RoomScript state values
	intro
	Lthought1
	clear1
	Pthought2
	clear2
	bothThought3
	clear3
	Lthought4
	clear4
	Pthought5
	clear5
	alSays1
	clearAl1
	Pthought6
	clear6
	Pthought7
	clear7
	Lthought8
	clear8
	Lthought9
	clear9
	Lthought10
	clear10
	LsitsUp
	Lthought11
	clear11
	Lscoots
	LatEdge
	Ldresses
	Ldressed
	Lleaves
	Lleaving1
	Lleaving2
	Lleaving3
	PfeelsBed
	PfeelsUp
	PfeelsDown
	PfeelsUpFaster
	PfeelsDownFaster
	PfeelsUpFastest
	PfeelsDownFastest
	PsitsUp
	Pthought21
	clear21
	Pthought22
	clear22
	Pthought23
	clear23
	Pthought24
	Pscoots
	somethingIsDone
	Pstands
	Pstanding
	PatDoor
	doorHasOpened
	changeRooms
)


(enum												;**	PrintPatti & PrintLarry values
	nothing
	startBubbles
	printIt
	printDone
)


(local
	[string 222]
)


(instance rm482 of Room
	(properties
		picture		480
	)


	(method (init)
		(HandsOff)

		(Load SCRIPT REVERSE)
		(Load VIEW vLundressingHunk)

		(Load SOUND sAfterGlow)
		(Load SOUND sArnoldsStinger)
		(Load SOUND sPfeelsBed)

		(super	 	init)

		(music
			number			sAfterGlow,
			loop				2,
			play,
		)

		(aWine
			init,
			setCel			c0glasses,
			stopUpd,
		)
		(aDoor				init)
		(aPanties  			init)
		(aBra					init)
		(aPantyhose  		init)
		(aDress  			init)
		(aBubbleLarry		
			setScript		PrintLarry,
			init,
		)
		(aBubblePatti		
			setScript		PrintPatti,
			init,
		)

		(addToPics
			add 				atpTelescope,
			doit
		)

		(self
			setScript 		RoomScript,
		)

		(aPatti				init)

		(ego
			ignoreActors,
			illegalBits		0,
			setPri			ltBlack,
			view				vLafterSex,
			loop				lSleeping,
			cycleSpeed		4,
			cel				1,
			setCycle			Forward,
			posn 				LarryPosn,
			init,
		)

		(systemWindow
			color 			textColor,
			back				backgroundColor,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
		(theGame setSpeed 6)
	)


	(method (handleEvent event)
		(if (and	(== (event type) keyDown)
					(== (event claimed) FALSE)
					(== (event message) `#8))
			(Print "Skipping ahead...")
			(SetFlg tookShortcut)
			(curRoom newRoom 484)
		)
	)

	(method (changeState newState &tmp t)
		(ShowState self newState 1 red)
		(switch (= state newState)

			(intro
				(= cycles 33)
			)

			(Lthought1
				(Format @string "\"The woman of my dreams...\"")
				(PrintLarry cue)
			)

			(clear1
				(= cycles 22)
			)

			(Pthought2
				(Format @string "\"The man of my dreams...\"")
				(PrintPatti cue)
			)

			(clear2
				(= cycles 22)
			)

			(bothThought3
				(Format @string "\"I'm in love!\"")
				(PrintLarry cue)
				(aBubblePatti
					cel				0,
					setCycle			EndLoop ,
				)
			)

			(clear3
				(aBubblePatti
					setCycle			BegLoop ,
				)
				(= cycles 22)
			)

			(Lthought4
				(Format @string "\"There'll never be another woman for me!\"")
				(PrintLarry cue)
			)

			(clear4
				(= cycles 22)
			)

			(Pthought5
				(Format @string "\"How I wish I still smoked!\"")
				(PrintPatti cue)
			)

			(clear5
				(= cycles 33)
			)

			(alSays1
				(Format @string "The two of you drift off to sleep 
					bathed in the glow of your experience together...")
				(delay (= t (+ 3 (/ (StrLen @string) stringDelay))))
				(Print @string
					#time				t
					#dispose
				)
			)

			(clearAl1
				(= cycles 33)
			)

			(Pthought6
				(Format @string "\"That settles it; 
					from now on it's Larry Laffer forever!
					Tomorrow I must call my boy friend and tell him
					I'm breaking off our relationship for good.
					Sorry, Arnold.\"")
				(PrintPatti cue)
			)

			(clear6
				(= cycles 33)
			)

			(Pthought7
				(Format @string "As she falls asleep, 
					thinking about how she'll handle the end of her other
					relationship,
					Patti murmurs softly a single, devastating word:
					\"...Arnold.\"")
				(delay (= t (+ 3 (/ (StrLen @string) stringDelay))))
				(Print @string
					#time				t
					#dispose
				)
			)

			(clear7
				(= cycles 3)
			)

			(Lthought8
				(music
					number			sArnoldsStinger,
					loop				2,
					play,
				)
				(aBubbleLarry
					cycleSpeed		0,
				)				
				(Format @string "\"What! What did she say?!
					Arnold? Arnold! Oh, no!\"")
				(PrintLarry cue)
				(ego
					loop				lLawakening,
					cel				0,
					setCycle			CycleTo 3 ctForward,
				)
			)

			(clear8
				(= cycles 5)
			)

			(Lthought9
				(Format @string "\"I thought she felt something...
					I know I felt it...
					During the best sexual experience of my life 
					she was thinking of another man?!
					I am so stupid!
					By now, I should have learned:
					some men have a way with women and some men don't!\"")
				(PrintLarry cue)
			)

			(clear9
				(= cycles 5)
			)

			(Lthought10
				(Format @string "\"I was a fool to believe my new body 
					would make a difference.
					Nothing makes any difference!
					Once you're a loser, you're always a loser!\"")
				(PrintLarry cue)
				(ego
					setCycle			EndLoop ,
				)
			)

			(clear10
				(= cycles 22)
			)

			(LsitsUp
				(ego
					loop				lSittingUp,
					setCycle			EndLoop ,
				)
				(= cycles 44)
			)

			(Lthought11
				(Format @string "\"I give up.
					I've had it with women!
					It's just not worth it!
					I'm going where no woman will ever frustrate me again!\"")
				(PrintLarry cue)
			)

			(clear11
				(= cycles 22)
			)

			(Lscoots
				(ego
					posn				188 95,
					setLoop			lSlidingAcrossBed,
					setCycle			Forward,
					setStep			1 1,
					cycleSpeed		0,
					setMotion		MoveTo 193 90 self,
				)
			)

			(LatEdge
				(ego
					posn				196 103,
					view				vLundressingHunk,
					setLoop			facingEast,
					setCel			LastCel,
					setPri			RELEASE,
					cycleSpeed		1,
				)
				(= cycles 6)
			)

			(Ldresses
				(ego
					setCycle			BegLoop self,
				)
			)

			(Ldressed
				(= cycles 11)
			)

			(Lleaves
				(NormalEgo facingEast vLhunk)
				(HandsOff)
				(ego
					setMotion		MoveTo bedEastX (ego y) self,
				)
			)

			(Lleaving1
				(ego
					setMotion		MoveTo bedEastX bedCenterY self,
				)
			)

			(Lleaving2
				(ego
					setMotion		MoveTo 160 188 self,
				)
			)

			(Lleaving3
				(ego
					setMotion		MoveTo 160 444 self,
				)
			)

			(PfeelsBed
				(aPatti
					cycleSpeed		1,
					loop				lPfeelingBed,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 33)
			)

			(PfeelsUp
				(aPatti
					setCycle			CycleTo 7 ctReverse,
				)
				(= cycles 22)
			)

			(PfeelsDown
				(aPatti
					setCycle			EndLoop ,
				)
				(= cycles 22)
			)

			(PfeelsUpFaster
				(aPatti
					setCycle			CycleTo 7 ctReverse self,
				)
			)

			(PfeelsDownFaster
				(aPatti
					setCycle			EndLoop self,
				)
			)

			(PfeelsUpFastest
				(aPatti
					cycleSpeed		0,
					setCycle			CycleTo 7 ctReverse self,
				)
			)

			(PfeelsDownFastest
				(aPatti
					setCycle			EndLoop self,
				)
			)

			(PsitsUp
				(music
					number			sPfeelsBed,
					loop				2,
					play,
				)
				(aPatti
					posn				155 98,
					setLoop			lSittingUp,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 22)
			)

			(Pthought21
				(aBubblePatti 
					cycleSpeed		0,
				)				
				(Format @string "\"HE'S GONE! What happened? Where could he be?\"")
				(PrintPatti cue)
			)

			(clear21
				(= cycles 11)
			)

			(Pthought22
				(Format @string "\"Oh, no!
					I finally meet the man of my dreams,
					and now he vanishes into thin air!\"")
				(PrintPatti cue)
			)

			(clear22
				(= cycles 33)
			)

			(Pthought23
				(Format @string "\"Now what will I do?\"")
				(PrintPatti cue)
			)

			(clear23
				(= cycles 33)
			)

			(Pthought24
				(Format @string "\"Where is he?\"")
				(PrintPatti cue)
			)

			(Pscoots
				(aPatti
					posn				154 97,
					cycleSpeed		0,
					setLoop			lSlidingAcrossBed,
					cel				0,
					setCycle			EndLoop self,
					setMotion		MoveTo 148 89 self,
				)
			)

			(somethingIsDone
			)

			(Pstands
				(aPatti
					posn				143 84,
					setPri			RELEASE,
					setLoop			lPstanding,
					cel				0,		  
					setCycle			EndLoop self,
				)
			)

			(Pstanding
				(aPatti
					posn				143 84,
					view				vPinSheet,
					setLoop			RELEASE,
					loop				facingNorth,
					setCycle			Walk,
					setMotion		MoveTo 159 65 self,
				)
			)

			(PatDoor
				(aDoor
					setCycle			EndLoop self,
				)
			)

			(doorHasOpened
				(aDoor				stopUpd)
				(aPatti
					setMotion		MoveTo 159 55 self,
				)
			)

			(changeRooms
				(curRoom newRoom 483)
			)
		)
	)
)

(instance PrintLarry of Script
	(method (changeState newState)
		(ShowState self newState 2 red)

		(switch (= state newState)

			(nothing
			)

			(startBubbles
				(aBubbleLarry
					cel			0,
					setCycle		EndLoop self,
				)
			)

			(printIt
				(Print @string
					#at			10 5 
					#width 		290
					#mode			teJustCenter
					#time			(= seconds (PrintDelay @string))
					#dispose
				)
			)

			(printDone
				(aBubbleLarry
					setCycle		BegLoop ,
				)
				(= state nothing)
				(RoomScript cue)
			)
		)
	)
)

(instance PrintPatti of Script
	(method (changeState newState)
		(ShowState self newState 3 red)

		(switch (= state newState)

			(nothing
			)

			(startBubbles
				(aBubblePatti
					cel			0,
					setCycle		EndLoop self,
				)
			)

			(printIt
				(Print @string
					#at			10 5 
					#width 		290
					#mode			teJustCenter
					#time			(= seconds (PrintDelay @string))
					#dispose
				)
			)

			(printDone
				(aBubblePatti
					setCycle		BegLoop ,
				)
				(= state nothing)
				(RoomScript cue)
			)
		)
	)
)

(instance aBubblePatti of Prop
	(properties
		view					vPafterSex
		loop					lThoughtBubbles
		cycleSpeed			1
;		cel 					0
		x						141 
		y						82
	)
)
(instance aBubbleLarry of Prop
	(properties
		view					vLafterSex
		loop					lThoughtBubbles
		cycleSpeed			1
;		cel 					0
		x						179 
		y						82
	)
)
(instance aPatti of Actor
	(properties
		view					vPafterSex
;		loop					lSleeping
		cycleSpeed			4
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			posn				PattiPosn,
			setPri			ltBlack,
			setCycle			Forward,
		)
	)
)


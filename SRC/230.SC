;**
;**	Logics for room 230 -- Path with Grass
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 12, 1989
;**


(script#				230)

(public
	rm230				0
)


(procedure
	PrintMD
)


(define	vRoom							230)
	(define	lDoor					0)
	(define	lSign					1)
	(define	lSignATP				2)

(define	vLcuttingGrass				231)
	(define	lLcuttingRight		0)
	(define	lLcuttingLeft		1)

(define	vMaitreD						422)
	(define 	lMovingRight		0)
	(define 	lMovingLeft			1)
	(define	lMDtalking			2)
	(define	lFaceIcon			3)
		(define	cSmile		6)
		(define	cBigSmile	9)
	(define	lHandOut				4)


(define	nearGrass				(& (ego onControl) cRed))
(define	nearDoorman				(& (ego onControl) cMagenta))

(define	DoormanX						179)
(define	DoormanY						 53)


(enum												;**	DoormanScript state values
	MDintro
	startMDtalk
	stopMDtalk
	giveMoney
	decisionTime
	enterShowroom
	moveLarry
	centeredOnOpening
	closeTheFuckingDoorYouAsshole
	goto430
	changeRooms
	giveBJ
	huh
	ohhh
	MDtoRock
	PfollowsToRock
	centeredOnZipper
	goingDown
	lookAtThatSmile
	comingUp
	howWasThat
	walkThisWay
	ifIcould
	backFromShow
	egoInPlace
	doorIsClosed
	MDinPlace
	MDspeaks
)


(enum												;**	RoomScript state values
	intro
	getGrass
	atGrass
	cutIT
	getIT
	getIT2
	he'sUp
)


(local
	seenMsg
	beenWarned
	[string 200]
)


(synonyms
	(man maitred doorman bouncer manager guard )
)


(instance rm230 of Room
	(properties
		picture		230
		south			220
		east			240
	)


	(method (init)
		(if (and	(InRoom iGrass)
					(ego has iKnife)
					(== ivSharpKnife ((Inventory at iKnife) view)))
			(Load VIEW vLcuttingGrass)
			(Load VIEW ivSharpKnife)
		)

		(super init)

		(self
			setScript 		RoomScript
		)

 		(addToPics
			add 				atpSign,
			doit
		)

		(aDoor				init)

		(if egoIsPatti
			(aSign			init)
			(aDoorman		init)
			(Load SOUND sDoorClosing)
			(if (ego has iMoney)
				(Load VIEW ivTips)
			)
		)

		(NormalEgo)
		(ego					init)
		(cond
			((== prevRoomNum 220)
				(ego loop facingNorth)
				(switch (Random 1 3)
					(1			(ego posn  52 188))
					(2 		(ego posn 135 188))
					(3 		(ego posn 212 188))
				)
			)

			((== prevRoomNum 240)
				(ego
					posn 			317 135,
					loop 			facingWest
				)
			)

			((== prevRoomNum 235)
				(ego
					posn 			100 128,
					loop 			facingEast
				)
			)

			((== prevRoomNum 330)
				(= egoState NORMAL)
				(HandsOff)
				(aDoorman
				 	posn				149 53,
					setCycle			Walk,
				)
				(ego
					ignoreActors,
					illegalBits		0,
					posn				179 48,
					loop 				facingSouth,
				)
				(aDoor
					setCel			LastCel,
				)
				(DoormanScript changeState backFromShow)
			)

			(else
				(ego
					posn 			212 188,
				)
			)
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (& (ego onControl) cBlue)
			(curRoom newRoom 235)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'pull, tear, get, rip/grass')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					(egoIsPatti
						(Print "The tough tropical island grass slices your
							hands to ribbons, but refuses to budge.")
					)
					((not (InRoom iGrass))	;**	default is curRoomNum
						(AlreadyTook)
					)
					((not nearGrass)
						(NotClose)
					)
					(else
						(Print "The tough tropical island grass slices your
							hands to ribbons, but refuses to budge.")
					)
				)
			)

			((Said 'cut/grass>')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					(egoIsPatti
						(Print "The tough tropical island grass slices your
							hands to ribbons, but refuses to budge.")
					)
					((not (InRoom iGrass))	;**	default is curRoomNum
						(AlreadyTook)
					)
					((not nearGrass)
						(NotClose)
					)
					((or	(not (Said '//knife'))
							(not (ego has iKnife)))
						(Print "The tough tropical island grass slices your
							hands to ribbons, but refuses to budge.
							If only you had some way to cut it.")
					)
					((!= ((Inventory at iKnife) view) ivSharpKnife)
						(Print "Good idea!
							You try and try to cut the grass with your dull knife,
							but it is just not sharp enough!")
					)
					(else
						(self changeState getGrass)
					)
				)
				(event claimed TRUE)
			)

			((Said 'use/knife>')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (ego has iKnife))
						(DontHave)
					)
					((not (InRoom iGrass))	;**	default is curRoomNum
						(AlreadyTook)
					)
					((not nearGrass)
						(NotClose)
					)
					((not (Said '//grass<cut'))
						(Print "How would you use the knife?")
					)
					((!= ((Inventory at iKnife) view) ivSharpKnife)
						(Print "Good idea!
							You try and try to cut the grass with your dull knife,
							but it is just not sharp enough!")
					)
					(else
						(self changeState getGrass)
					)
				)
				(event claimed TRUE)
			)

			((Said 'look>')
				(cond
					((Said '/door, sign, club')
						(Print "Chip 'N' Dale's\n\n
								  Nontoonyt's finest\n
								  Adult Entertainment"
							#mode			teJustCenter
						)

						(if (not egoIsPatti)
							(Print "CLOSED")
						else
							(Print  "OPEN\n\n
										Admission: $25.00"
								#mode			teJustCenter
							)
						)
					)

					((Said '/rock, mountain')
						(Print "There appears to be an opening
							in the side of the concrete gigantic rock
							just outside the door to the nightclub.")
					)

					((and (InRoom iGrass)
							(Said '/grass'))
						(Print "Long, slender and beautiful, 
							the island's indigenous grass 
							was once worn by the natives as ritual costumes.")
						(Print "(Now they mostly just order from\nL. L. Bean!)" LOW)
					)

					((Said '/flower')
						(Print "These flowers are attractive,
							but nothing out of the ordinary for a beautiful,
							tropical island like Nontoonyt.")
					)

					((Said '/tree')
						(Print "Trees are everywhere.")
						(Print "(This is also a dog's paradise!)" LOW)
					)

					((Said '[/room]')
						(if (cast contains aDoorman)
							(Print "\"Chip 'N' Dale's\" is open at last!
								A familiar face waits near the door.")
						else
							(Print "You are just outside Nontoonyt's famous striptease
								joint, \"Chip 'N' Dale's.\"
								A large cliff mostly prevents your passage to the west.
								In the center of this area is a large clump of 
								island grass.")
							(Print "There is a sign on the door.")
						)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)

			(getGrass
				(HandsOff)
				(Ok)
				(ego
					setMotion		MoveTo 201 123 self,
				)
			)

			(atGrass
				(ego
					view				vLcuttingGrass,
					setLoop			(if (ego loop) lLcuttingLeft
										else				lLcuttingRight),
					setCel			0,
					cycleSpeed		2,
					setCycle			EndLoop self,
				)
			)

			(cutIT
				(ego
					setLoop			(+ (ego loop) 2),
					setCycle			Forward,
				)
				(= cycles 20)
			)

			(getIT
				(Print "Your razor sharp ginsu knife slices through the
					blades of grass like a hot samurai's sword
					through a tub of cheap margarine."
					HIGH
					#icon ivSharpKnife 0 0
				)
				(= cycles 20)
			)

			(getIT2
				(ego
					get 				iGrass,
					setLoop			(+ (ego loop) 2),
					setCycle			EndLoop,
				)
				(= cycles 10)
			)

			(he'sUp
				(NormalEgo (- (ego loop) 4))
				(theGame changeScore 20)
				(Print "Congratulations.
					You are the proud new owner of some long blades of grass."
					HIGH)
			)
		)
	)
)

(instance aDoor of Prop
	(properties
		view				vRoom
		loop				lDoor
		x					179
		y					50
	)

	(method (init)
		(super init)
		(self
			setPri		1,
			stopUpd,
		)
	)

	(method (handleEvent event)
		(if (or	(!= (event type?) saidEvent) (event claimed?)) (return))

		(if (Said 'look/door')
			(if egoIsPatti
				(Print "Say! It looks like this place is finally open!")
			else
				(Print "Perhaps it will be open later!")
			)
		)
	)
)

(instance aSign of Prop
	(properties
		view					vRoom
		loop					lSign
		x						179
		y						0
		cycleSpeed			3
	)

	(method (init)
		(super init)
		(self
			setPri			5,
			setCycle			Forward ,
		)
	)
)
(instance atpSign of PicView
	(properties
		view					vRoom
		loop					lSignATP
;		cel					cSign
		x						179
		y						0
		priority				4
		signal				ignrAct
	)
)

(instance aDoorman of Actor
	(properties
		view				vMaitreD
		x					DoormanX
		y					DoormanY
		loop				lMDtalking
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			setScript		DoormanScript,
			setPri			1,
			stopUpd,
		)
	)
)

(instance DoormanScript of Script
	(method (handleEvent event &tmp theObj)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'give/job<blow')
					(Said 'give/blow<job')
					(Said 'give/head')
					(Said 'yes')
					(Said 'give/head<job')
					(Said 'eat, blow, lick, suck/man, ball, nut, cock'))
				(cond
					((not nearDoorman)
						(NotClose)
					)
					((TstFlg beenIn330)
						(Print "You really don't want to go back in there.")
					)
					((not beenWarned)
						(= beenWarned TRUE)
						(Print "Are you sure you want to do that?")
					)
					((< filthLevel DIRTY)
						(Print "Sorry, but you're too young and inexperienced
							to do that.")
						(Print "(Or, at least your current \"Filth Level\" is!)" 
							LOW)
					)
					(else
						(self changeState giveBJ)
					)
				)
			)

			((or	(Said 'enter/club, room, showroom')
					(Said 'ask/dale, chip, show')
					(Said 'see/show'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearDoorman)
						(Print "He'll never hear you from here.")
					)
					(else
						(Printf "\"Hello, sir\" you say to the maitre d'.
							\"Could you give me some information
							about the show inside?\"")
						(Format @string "The doorman responds, 
							\"Tonight's show is offered at the special low rate
							of only $25.00.							
							Of course, with tax, that'll come to $43.00 exactly.\"")
						(self changeState startMDtalk)
					)
				)
			)

			((or	(Said 'hand, give, show/ticket/man')
					(Said 'hand, give, show/man/ticket'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearDoorman)
						(Print "He'll never see it from here.")
					)
					(else
						(Print "\"How about if I show you my pass from the 
							showroom.\"")
						(Format @string "\"No way; this place costs money!\"")
						(self changeState startMDtalk)
					)
				)
			)

			((or	(Said 'hand, give, show/money, ones, bill/man')
					(Said 'pay, tip/man')
					(Said 'hand, give, show/man/money, ones, bill'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((TstFlg beenIn330)
						(Print "You really don't want to go back in there.")
					)
					((not (ego has iMoney))
						(Print "You're broke.")
					)	
					((not nearDoorman)
						(Print "He'll never see it from here.")
					)
					(else
						(self changeState giveMoney)
					)
				)
			)

			((Said 'talk/man')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearDoorman)
						(Print "No one is near you.")
					)
					((TstFlg beenIn330)
						(Print "\"Just couldn't get enough of Dale 
							your first time, eh?\"")
					)	
					(else
						(Print "\"Hello, sir\" you say to the doorman.
							\"Could you give me some information
							about the show inside?\"")
						(Format @string "The maitre 'd responds, 
							\"Tonight's show is offered at the special low rate
							of only $25.00.							
							Of course, with tax, that'll come to $42.95 exactly.\"")
						(self changeState startMDtalk)
					)
				)
			)

			((Said 'look/man')
				(Print "The maitre d' looks like he would
					be the life of the party...")
				(Print "(...if the party was an undertakers' convention!)" LOW)
			)


			((Said 'give>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((not nearDoorman)
						(NotClose)
					)
					((Said '[/noword]')
						(Print "What did you want to give him?")
					)
					((not theObj)
						(Print "It's not yours to give.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					((== theObj (inventory at iKey))
						(Print "\"Want to go to my place later?\"")
						(Format @string "\"Do I look like a fool?
							You can't make me leave my post that easily.\"")
						(self changeState startMDtalk)
					)
					((or	(== theObj (inventory at iPanties))
							(== theObj (inventory at iPantyhose))
							(== theObj (inventory at iBra))
							(== theObj (inventory at iDress)))
						(Format @string "\"Do I look like that kind of man?
							Pay your way in, just like everyone else!\"")
						(self changeState startMDtalk)
					)
					((== theObj (inventory at iMarker))
						(Print "\"Wow! I don't want that!!\"")
					)
					((not (== theObj (inventory at iMoney)))
						(Print "The maitre d' replies, 
							\"And just exactly what am I supposed to do with 
							that?\"")
					)
					(else
						(self changeState giveMoney)
					)
				)
				(event claimed TRUE)
			)
		)
	)


	(method (changeState newState &tmp theLine)
		(ShowState self newState 2 green)

		(switch (= state newState)

			(MDintro
			)


			;**	Talk a little

			(startMDtalk
				(HandsOff)
				(aDoorman
					setLoop			lMDtalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(stopMDtalk
				(aDoorman
					setCel			0,
					stopUpd,
				)
				(if (not string)
					(Format @string "\"Yes? May I help you madam?\"")
				)
				(PrintMD vMaitreD lFaceIcon 0)
				(HandsOn)
			)


			;**	Give the maitre d' his $20 tip

			(giveMoney
				(HandsOff)
				(theGame changeScore 43)
				(Print "\"Here's all the money I have in the world,\"
					you say to the doorman,
					\"Boy, what luck!
					I just happen to have $43.00 in tips!\""
					#icon		ivTips 0 0
				)
				(= dollars 0)
				(PutInRoom iMoney)
				(aDoorman
					setLoop			lMDtalking,
					setCycle			Forward,
				)
				(= cycles 0)
				(delay 3)
			)

			(decisionTime
				(aDoorman
					setCel			0,
					stopUpd,
				)
				(Format @string
					"\"Why, thank you very much, ma'am.
					I know you'll enjoy the show!\"")
				(PrintMD vMaitreD lFaceIcon 1)
				(delay 2)
			)

			(enterShowroom
				(User canControl TRUE)
				(aDoorman
					illegalBits		0,
					setLoop			lMovingLeft,
					setCycle			Forward,
					setMotion		MoveTo 149 53 self,
				)
				(aDoor
					setCycle			EndLoop 
				)
			)

			(moveLarry
				(HandsOff)
				(aDoorman
					setLoop			lMDtalking,
					setCel			0,
				)
				(ego
					ignoreActors,
					illegalBits		0,
					setMotion		MoveTo 179 57 self,
				)
			)

			(centeredOnOpening
				(ego
					setMotion		MoveTo 179 48 self,
					setPri			0,
				)
				(if (TstFlg gaveHead)
					(Print "The doorman mumbles something as you enter the club.
						You, of course, respond, \"Come again?\"")
				)
			)

			(closeTheFuckingDoorYouAsshole
				(aDoor
					setCycle			BegLoop self,
				)
			)

			(goto430
				(soundFX
					number			sDoorClosing,
					loop				1,
					play,
				)
				(= cycles 5)
			)

			(changeRooms
				(curRoom newRoom 330)
			)


			;**	Give the maitre d' more than his $20 tip!!

			(giveBJ
				(HandsOff)
				(Print "\"You know, I'm a little short of cash right now,\"
					you say to the doorman,
					\"But I do have an idea! 
					Step over behind that rock over there and
					I'll explain it to you!")
				(aDoorman
					setLoop			lMDtalking,
					setCel			0,
				)
				(= cycles 0)
				(delay 3)
			)

			(huh
				(Format @string "\"Huh?\"")
				(PrintMD vMaitreD lFaceIcon 1)
				(delay 3)
			)

			(ohhh
				(Format @string "\"OH!\"")
				(PrintMD vMaitreD lFaceIcon cSmile)
				(delay 3)
			)

			(MDtoRock
				(User canControl TRUE)
				(aDoorman
					illegalBits		0,
					setLoop			lMovingLeft,
					setCycle			Forward,
					setMotion		MoveTo 144 53 self,
				)
			)

			(PfollowsToRock
				(HandsOff)
				(aDoorman
					setLoop			lMDtalking,
					setCel			0,
					stopUpd,
				)
				(ego
					ignoreActors,
					illegalBits		0,
					setMotion		MoveTo 144 57 self,
				)
			)

			(centeredOnZipper
				(ego
					setLoop			facingNorth,
					setCel			0,
					setPri			,
					setMotion		MoveTo 144 77 self,
				)
			)

			(goingDown
				(delay 3)
			)

			(lookAtThatSmile
				(BJicon
					view			vMaitreD ,
					loop			lFaceIcon,
				)
				(Print " "
					HIGH
					#title		{the Doorman says}
					#mode			teJustCenter
					#icon			BJicon
				)
				(delay 3)
			)

			(comingUp
				(ego
					setMotion		MoveTo 144 57 self,
				)
			)

			(howWasThat
				(Print "\"What did you think of that?\" you ask him.")
				(delay 3)
			)

			(walkThisWay
				(Format @string "\"Walk this way!\"")
				(PrintMD vMaitreD lFaceIcon cBigSmile)
				(aDoorman
					setLoop			RELEASE,
					setCel			RELEASE,
					setCycle			Walk,
					setMotion		MoveTo DoormanX DoormanY self,
				)
			)

			(ifIcould
				(SetFlg gaveHead)
				(Print "You think, 
					\"If I walked like that...\"")
				(ego
					setLoop			RELEASE,
					setCel			RELEASE,
					setPri			RELEASE,
					setCycle			Walk,
				)
				(= cycles 12)
				(= state (- enterShowroom 1))
			)


			;**	Patti is leaving the club.

			(backFromShow
				(ego
					setMotion		MoveTo 186 60 self,
				)
			)

			(egoInPlace
				(aDoor				
					setCycle			BegLoop self,
				)
			)

			(doorIsClosed
				(soundFX
					number			sDoorClosing,
					loop				1,
					play,
				)
				(aDoor				stopUpd)
				(aDoorman
					setMotion		MoveTo DoormanX DoormanY self,
				)
			)

			(MDinPlace
				(aDoorman
					loop				lMDtalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(MDspeaks
				(aDoorman
					setCel			0,
					stopUpd,
				)
				(NormalEgo)
				(Print "\"What a show, eh, honey?\" slurps the maitre d'.
					\"And how about that Dale?\"")
			)
		)
	)
)

(procedure (PrintMD v l c)
	(Print @string
		HIGH
		#title		{the Doorman says}
		#mode			teJustCenter
		#icon			v l c
	)
	(= string 0)
)
(instance BJicon of DCIcon
	(method (init)
		(super init)
		(if cycler	
			(cycler	dispose)
		)
		((= cycler 	(EndLoop new)) init self)
	)
)


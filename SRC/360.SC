;**
;**	Logics for room 360 -- Fat City Lobby
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**


(script#				360)

(public
	rm360				0
)


(define	vRoom 						360)
	(define	lLockerDoor			0)
	(define	lTanBoothDoor		1)
	(define	lStudioDoor			2)
;		(define 	cOpen			1)

	(define	lCardSlots			3)
		(define	cRight		0)
		(define	cLeft			1)
		(define	cMiddle		2)

	(define	lAddtopix			4)
		(define	cFatCity		0)
		(define	cBboard		1)
		(define	cShelves		2)
		(define	cSumtin		3)
		(define	cBlender		4)

(define	vRobin						362)
	(define	lTalking				2)
	(define	lOnElbow				3)

;(define	vBambi						393)
	(define	lEastLight			0)
	(define	lEastDark			1)
	(define  lDownLight			2)
	(define	lUsingCard 			3)


;(define	sDoorClosing				011)


(define nearMan				(& (ego onControl) cLtGray))
(define nearLockerDoor		(& (ego onControl) (aLockerDoor doorCtrl)))
(define nearStudioDoor		(& (ego onControl) (aStudioDoor doorCtrl)))
(define nearTanBoothDoor	(& (ego onControl) cMagenta))
(define TanBoothBlock		(& (ego onControl) cLtRed))

(define manXleft						101)
(define manXright						155)
(define manY							105)

(define LockerDoorX					11)
(define LockerDoorY					172)

(define StudioDoorX					199)
(define StudioDoorY					117)

(define TanBoothDoorX				296)
(define TanBoothDoorY				163)

(define RightCardHoleX				314)
(define RightCardHoleY				165)

(define LeftCardHoleX				 43)
(define LeftCardHoleY				143)

(define RearCardHoleX				224)
(define RearCardHoleY				103)


(enum												;**	RoomScript state values
	intro
	gotoLockerKeypad
	useLockerKeypad
	centerOnLockerDoor
	enterLocker
	closeLockerDoor
	changeToLockerRoom
	gotoStudioKeypad
	useStudioKeypad
	centerOnStudioDoor
	enterStudioDoor
	humping2please
	startBambi
	startLarry
	BambiAtMiddle
	LarryAtMiddle
	unlockingDoor
	slightDelay
	openDoor
	doorIsOpen
	comeOn
	moveBambi
	moveLarry
)


(enum												;**	ManScript state values
	manInit
	manStart
	smallDelay
	manMiddle
	handOnChin
	manDone
	talkAlittle
	saySomething
	showedKeycard
)


(synonyms
	(man robin attendant receptionist clerk )
)


(local
	messageCount
)


(instance rm360 of Room
	(properties
		picture		360
		horizon		111
		north			390
		south			300
		east			365
	)

	(method (init)
		(if (ego has iKeycard)
			(Load VIEW vLusingKey)
			(Load VIEW iKeycard)
			(Load SOUND sDoorClosing)
		)

		(super init)

		(addToPics
			add 				atpRightCardHole,
			add 				atpLeftCardHole,
			add 				atpRearCardHole,
			add 				atpFatCity ,
			add 				atpBboard ,
			add 				atpShelves ,
			add 				atpSumtin ,
			add 				atpBlender ,
			doit
		)

		(aTanBoothDoor			init)
		(aStudioDoor 			init)
		(aLockerDoor			init)

		(if (not egoIsPatti)
			(aRobin					init)
		)

		(self
			setLocales		locClerkL,
			setScript 		RoomScript,
		)

		(NormalEgo)
		(cond
			((== prevRoomNum 390)			;**	coming back from studio
				(ego
					posn 					198 122,
					loop 					facingSouth,
				)
				(aStudioDoor
					close,
					locked				TRUE,
				)
				(if (== egoState DOING_IT)	;**	entering with bambi
					(HandsOff)
					(aBambi 	 			init)
					(ego
						view				vLhunk,
						posn 				191 122,
						illegalBits		0,
						ignoreActors,
					)
					(music
						number			sFatCityRegion,
						loop				bgMusicLoops,
						play ,			
					)
					(RoomScript changeState humping2please)
				)
			)
			((== prevRoomNum 370) 			;**	coming in from locker room
				(ego
					posn 				27 173
				)
				(aLockerDoor
					close,
					locked			TRUE,
				)
			)
			((== prevRoomNum 365)
				(TheMenuBar	
					draw,
					state 			TRUE,
				)
				(StatusLine enable)
				(ego
					loop				facingWest,
					posn 				294 177,
				)
			)
			(else
				(ego
					posn 				159 186,
					loop 				facingNorth,
				)
			)
		)
		(ego			init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (!= egoState DOING_IT)
			(ego
				observeControl			TanBoothBlock,
				ignoreControl			nearTanBoothDoor,
			)
		)
	)


	(method (handleEvent event)
		(if (or (!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'use, insert/keycard, card')
					(Said 'stick, insert/keycard, card/door, slot')
					(Said 'stick, insert//keycard, card')
					(Said 'unlock, open/door'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (ego has iKeycard))
						(Print "The doors here are all controlled by a keycard
							given to members only. Since you're not a member,
							you can't get in.")
					)
					((and (not nearLockerDoor)
							(not nearStudioDoor)
							(not nearTanBoothDoor))
						(NotClose)
					)
					(nearTanBoothDoor
						(Print "\"Hey!\" shouts the man behind the counter,
							\"You can't go in there!
							The tanning booth is out of order.\"")
						(Print "\"We'll get it fixed Real Soon Now.\"")
					)
					(nearLockerDoor
						(RoomScript changeState gotoLockerKeypad)
					)
					(nearStudioDoor
						(RoomScript changeState gotoStudioKeypad)
					)
				)
			)

			((Said '/spa, exercise, weight, booth, aerobic')
				(Print "Fat City's facilities are available to members only.")
			)

			((Said '/shelf, counter')
				(Print "There are many items behind the counter that are
					totally uninteresting to you!")
			)

			((Said '/blender')
				(Print "You don't have time for a drink now!")
			)

			((Said '/shirt')
				(Print "You'd rather order a \"Leisure Suit Larry 3\" T-shirt
					from Sierra.
					Just call 1 (800) 326-6654 (or 1 (209) 683-4468)
					to buy yours today!!")
			)

			((Said 'get/soap')
				(Print "There is no soap available here. You must bring your own!")
			)

			((Said 'look>')
				(cond
					((Said '/door')
						(cond
							(nearLockerDoor
								(Print "A small sign on the door says,
									\"Locker Room.\"")
							)
							(nearStudioDoor
								(Print "A small sign on the door says,
									\"Aerobics Studio.\"")
							)
							(nearTanBoothDoor
								(Print "A small sign on the door says,
									\"Tanning Booth.\"")
							)
							(else
								(Print "There are four doors here,
									one on each wall.")
							)
						)
					)

					((Said '/sign, board, note, poster')
						(Print "The bulletin board is filled with advertisements
							for products you find totally uninspiring.")
					)

					((Said '/man')
						(if (cast contains aRobin)
							(Print "Behind the counter you see a man.")
						else
							(Print "You don't see anyone.")
						)
					)

					((Said '/desk, counter<behind, back')
						(if (cast contains aRobin)
							(Print "There's a handsome young man behind the counter.")
						else
							(Print "Looking behind the counter you see...")
							(Print "...nothing.")
						)
					)

					((Said '/desk, counter')
						(if egoIsPatti
							(Print "You see a counter.")
						else
							(Print "Robin stands behind the counter, totally bored,
								patiently counting the minutes until his shift ends.")
						)
					)

					((Said '/skylight')
						(Print "You wouldn't think they'd need those lights
							turned on with a skylight that large!")
					)

					((Said '/board, bulletin, note, sign')
						(Print "There is a bulletin board here filled with
							totally boring announcements.")
					)

					((Said '/announcement')
						(Print "blah, blah, blah")
					)

					((Said '[/spa, club, room]')
						(Print "You are inside the lobby of \"Fat City,\"
							the island's newest and brightest health spa.")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(delay 0)
			)


			;**	Open door to locker room

			(gotoLockerKeypad
				(HandsOff)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 60 160 self
				)
			)

			(useLockerKeypad
				(ego
				 	view 				vLusingKey,
					setLoop			facingLeft,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(centerOnLockerDoor
				(NormalEgo)
				(HandsOff)
				(if (not (TstFlg beenIn360))
					(SetFlg beenIn360)
					(theGame changeScore 3)
					(Print "You insert Suzi's keycard into the slot.
						The door opens automatically."
						#icon iKeycard 0 0
					)
				)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 12 171 self,
				)
				(aLockerDoor
					locked			FALSE,
					force				TRUE,
					open,
				)
			)

			(enterLocker
				(ego
					setMotion		MoveTo 0 171 self,
				)
			)

			(closeLockerDoor
				(aLockerDoor
					force				TRUE,
					close,
				)
				(soundFX
					number			sDoorClosing,
					loop				1,
					play,
				)
				(ego
					setMotion		MoveTo -20 171	self,
				)
			)

			(changeToLockerRoom
				(curRoom newRoom 370)
			)


			;**	Middle door stuff

			(gotoStudioKeypad
				(HandsOff)
			 	(Ok)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 217 124 self
				)
			)

			(useStudioKeypad
				(ego
				 	view 				vLusingKey,
					setCel			0,
					setLoop			2,
					setCycle			EndLoop self,
				)
			)

			(centerOnStudioDoor
				(if (not (TstFlg beenIn395))
					(SetFlg beenIn395)
					(theGame changeScore 3)
					(Print "You insert Suzi's keycard into the slot.
						The door opens automatically."
						#icon iKeycard 0 0
					)
				)
				(NormalEgo)
				(HandsOff)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 197 118 self,
				)
				(aStudioDoor
					locked			FALSE,
					force				TRUE,
					open,
				)
			)

			(enterStudioDoor
				(ego
					setMotion		MoveTo 197 0	;**	Let horizon change rooms
				)
			)


			;**	Larry offered to "help" with the video; here's his reward...

			(humping2please
				(= cycles 10)
			)

			(startBambi
				(aBambi
					setMotion		MoveTo 225 165 self
				)
				(= cycles 5)
			)

			(startLarry
				(ego
					setMotion		MoveTo 225 165 self
				)
			)

			(BambiAtMiddle
				(Print "\"Oh, Larry, I'm so excited about this...\"")
				(Print "\"...new video idea of yours!\"")
				(aBambi
					setMotion		MoveTo 294 176
				)
			)

			(LarryAtMiddle
				(ego
					setMotion		MoveTo 280 179	self
				)
			)

			(unlockingDoor
				(aBambi
					cycleSpeed		3,
					setLoop			lUsingCard,
					cel				0,
					setCycle			EndLoop self
				)
			)

			(slightDelay
				(theGame changeScore 3)
				(= cycles 10)
			)

			(openDoor
				(aBambi
					setCycle			BegLoop
				)
				(aTanBoothDoor
					setCycle			EndLoop self
				)
			)

			(doorIsOpen
				(aTanBoothDoor
					stopUpd
				)
				(= cycles 20)
			)

			(comeOn
				(Print "\"Come on, Larry,\" says Bambi,
					\"Let's do a little `blocking!'\"")
				(= cycles 10)
			)

			(moveBambi
				(aBambi
					cycleSpeed		0,
					setCycle			Walk,
					setLoop			lEastLight,
					setMotion		MoveTo 316 175 self
				)
			)

			(moveLarry
				(ego
					setMotion		MoveTo 333 174	;**	Let edgeHit change rooms.
				)
			)
		)
	)
)


(instance aRobin of Actor
	(properties
		view					vRobin
;		loop					0
		x						133
		y						105
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			setPri			10,
			setCycle			Walk,
			setScript		ManScript,
		)
	)
)

(instance ManScript of Script
	(method (handleEvent event)
		(if (or (!= (event type) saidEvent) (event claimed)) (return))

	 	(cond
			((Said 'talk/man')
				(cond
					(egoIsPatti
						(Print "Where?")
					)
					((not nearMan)
						(NotClose)
					)
					(else
						(ManScript
							changeState 	talkAlittle,
							register 		(++ messageCount),
						)
					)
				)
			)

			((Said 'ask>')
				(cond
					(egoIsPatti
						(Print "Where?")
					)
					((not nearMan)
						(NotClose)
					)
					((or	(Said '/door')
							(Said '//door'))
						(Print "\"What's the story on these locked doors, buddy?\"
							you ask the clerk")
						(ManScript
							changeState 	talkAlittle,
							register			102,
						)
					)
					((or	(Said '/keycard, city, key, membership, card')
							(Said 'join')
							(Said '//keycard, key, city, membership, card'))
						(Print "\"How can I get a membership in this place?\"
							you ask.")
						(ManScript
							changeState 	talkAlittle,
							register			104,
						)
					)
					((or	(Said '/bambi, video')
							(Said '//video, bambi'))
						(Print "\"Is this where I buy a copy of Bambi's video?\"
							you ask the clerk.")
						(ManScript
							changeState 	talkAlittle,
							register			106,
						)
					)

					((or	(Said '/locker')
							(Said '//locker'))
						(Print "\"Hey, Robin! Where's my locker?\"")
						(ManScript
							changeState 	talkAlittle,
							register			108,
						)
					)
					((or	(Said '/combination')
							(Said '//combination'))
						(Print "\"Hey, Robin! What's the combination to my locker?\"")
						(ManScript
							changeState 	talkAlittle,
							register			107,
						)
					)
					(else
						(ManScript
							changeState 	talkAlittle,
							register			103,
						)
					)
				)
				(event claimed TRUE)
			)

			((Said 'show/keycard')
				(cond
					((not (ego has iKeycard))
						(DontHave)
						(event claimed TRUE)
					)
					((not nearMan)
						(NotClose)
					)
					(else
						(Print "\"Here's my membership card,\"
							you tell the attendant.
							\"May I please have all the rights and privileges
							pertaining thereto?\""
							#icon	iKeycard 0 0
						)
						(ManScript
							changeState 	talkAlittle,
							register			101,
						)
					)
				)
			)

			((Said 'give')
				(if nearMan
					(ManScript
						changeState 	talkAlittle,
						register			105,
					)
				else
					(NotClose)
				)
			)

			((Said '/man')
				(Print "He seems pleasant enough, but none too bright.")
			)
		)
	)


	(method (changeState newState)		;**	 &tmp ManX ManY [str 100]
		(switch (= state newState)

			(manInit
				(delay (Random 4 8))
			)

			(manStart
				(aRobin
					illegalBits		0,
					cycleSpeed		0,
					setLoop			RELEASE,
					setCycle			Walk,
					setMotion		MoveTo (Random manXleft manXright) manY self,
				)
			)

			(smallDelay
				(delay (Random 1 3))
			)

			(handOnChin
				(aRobin
					setLoop			lOnElbow,
				)
				(delay (Random 4 8))
				(= state readyToStartOver)
			)


			;**	Larry said something to the clerk.

			(talkAlittle
				(HandsOff)
				(delay 0)
				(aRobin
					setMotion		FALSE,
					setLoop			lTalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(saySomething
				(aRobin
					setCel			0,
				)
				(switch register
					;**	These are cycled through once per visit:
					(1
						(Print "\"Hello,\" says Robin \"New in town?\"")
					)
					(2
						(Print "\"You certainly have an interesting way
							with clothes,\" insults the man behind the counter.")
					)
					(3
						(Print "Robin looks bored.
							\"Do you have nothing better to do than
							stand here talking to me?\"")
					)
					(4
						(cond
							((< newspaperState NSpHere)
								(Print "\"Have you heard the news?\"
									says the receptionist.
									\"Passionate Patti is coming to the
									casino lounge.\"")
							)
							((== newspaperState NSpHere)
								(Print "\"By the way,\"	says Robin.
									\"Have you had Passionate Patti too?\"")
							)
							(else
								(Print "\"I'm sorry, but I haven't seen Larry Laffer.\"")
							)
						)
					)


					;**	These are specific answers to specific questions:

					(101
						(Print "Robin looks at you and says,
							\"Excuse me. I didn't recognize you, Ms. Cheatem!
							Just insert your card in the slot beside the door you
							wish to enter. And, `Have a Nice Day,' ok?\"")
					)
					(102
						(Print "\"The doors are locked and only open by keycards
							given to each of our members,\" says the attendant
							behind the counter.")
					)
					(103
						(Print "\"Excuse me, sir,\" you say,
							\"may I ask you a question?\"")
						(Print "\"Certainly,\" Robin replies.
							\"What is it?\"")
					)
					(104
						(Print "\"Our membership roster is full right now,
							but we'll be starting a big campaign next fall,\"
							Robin replies. \"I'll call you, ok?\"")
					)
					(105
						(Print "\"Thank you anyway, but I'm just here for
							atmosphere!\"")
					)
					(106
						(Print "\"Not yet,\" replies Robin.	
							\"She's having a hell of a time getting it made.\"")
					)
					(107
						(Print "Robin replies, \"I'm sorry, but due to security
							reasons, I'm not allowed to give combinations.
							You should have made a note of it when you received
							your membership card.\"")
					)
					(108
						(Print "\"People expect me to do everything around here!\"
							Robin whines.
							\"How do you expect me to remember if you can't?\"")
					)
					(else
						(Print "\"How may I help you?\" asks the clerk.")
						(= messageCount 4)
					)
				)
				(= register 0)
				(HandsOn)
				(self changeState manInit)
			)
		)
	)
)

(instance aBambi of Actor
	(properties
		view					vBambi
		loop					facingSouth
		x						200
		y						126
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			illegalBits		0,
			setCycle			Walk,
		)
	)
)

(instance aTanBoothDoor of Prop
	(properties
		view				vRoom
		loop				lTanBoothDoor
		x	 				TanBoothDoorX
		y					TanBoothDoorY
		cycleSpeed		2
	)
)
(instance aStudioDoor of Door
	(properties
		view				vRoom
		loop				lStudioDoor
		x	 				StudioDoorX
		y					StudioDoorY
		entranceTo 		390
		doorCtrl 		cCyan
		roomCtrl			cRed
		doorBlock		cLtMagenta
		locked			TRUE
	)
)
(instance aLockerDoor of Door
	(properties
		view				vRoom
		x	 				LockerDoorX
		y					LockerDoorY
		loop				lLockerDoor
		entranceTo 		370
		roomCtrl			FALSE
		locked			TRUE
	)
)

(instance atpRightCardHole of PicView
	(properties
		view			vRoom
		loop			lCardSlots
		cel			cRight
		x	 			RightCardHoleX
		y				RightCardHoleY
		priority		14
	)
)
(instance atpLeftCardHole of PicView
	(properties
		view			vRoom
		loop			lCardSlots
		cel			cLeft
		x	 			LeftCardHoleX
		y				LeftCardHoleY
		priority		11
	)
)
(instance atpRearCardHole of PicView
	(properties
		view			vRoom
		x	 			RearCardHoleX
		y				RearCardHoleY
		loop			lCardSlots
		cel			cMiddle
		priority		8
	)
)
(instance atpFatCity of PicView
	(properties
		view 			vRoom
		loop 			lAddtopix
		cel			cFatCity
		x				129 
		y				40
		priority		0
	)
)
(instance atpBboard of PicView
	(properties
		view 			vRoom
		loop 			lAddtopix
		cel			cBboard	
		x				277 
		y				134
		priority		9
	)
)
(instance atpShelves of PicView
	(properties
		view 			vRoom
		loop 			lAddtopix
		cel			cShelves
		x				131 
		y				104
		priority		6
	)
)
(instance atpSumtin of PicView
	(properties
		view 			vRoom
		loop 			lAddtopix
		cel			cSumtin	
		x				170 
		y				93
		priority		8
	)
)
(instance atpBlender of PicView
	(properties
		view 			vRoom
		loop 			lAddtopix
		cel			cBlender
		x				91 
		y				109
		priority		9
	)
)

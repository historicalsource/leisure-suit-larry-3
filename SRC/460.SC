;**
;**	Logics for room 460 -- Hotel Lobby
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 23, 1989
;**


(script#				460)

(public
	rm460				0
)


(define	vRoom							460)
	(define	lElevator			0)
	(define	lButtons				1)
		(define cOff			0)
		(define cUp				1)
		(define cDown			2)
	(define	lLights				2)

(define  vClerk						462)
	(define	lTalking				2)

;(define	vLusingKey					715)
	(define  lFacingNorth		2)

;(define	sElevatorDoorOpening		460)
;(define	sElevatorDoorClosing		461)


(define manXleft						230)
(define manXright						273)

(define manYleft						140)
(define manYright						155)

(define LDoorX							 69)
(define LDoorY							132)
(define RDoorX							135)
(define RDoorY							132)
(define ButtonX						102)
(define ButtonY						117)
(define LightRightX					135)
(define LightLeftX 					 69)
(define LightY							 60)


(define nearDoor				(& (ego onControl) cBlue))
(define nearMan				(& (ego onControl) cGreen))
(define nearCounter			(& (ego onControl) cCyan))


(enum												;**	RoomScript state values
	intro
	gotoButton
	pressButton
	donePressing
	moveElevator
	waiting
	openDoor
	tinyDelay
	moveToDoor
	enterElevator
	wait4Door
	closeDoor
	changeRooms
	backFrom470
	closeTheDoor
	allDone
)


(enum												;**	LightScript state values
	ElevatorIntro
	ElevatorInit
	ElevatorDown
	PickUpLarry
	GetLarry
	waitingToOpenDoor
	ElevatorDone
)

(enum												;**	ManScript state values
	manInit
	manStart
	waitALittle
	moveUmBack
	manDone
	moveToCenter
	talkAlittle
	shutUp
	saySomething
)

(local
	seenMsg
	msgVar
)


(synonyms
	(man arnold clerk )
)


(instance rm460 of Room
	(properties
		picture		460
		west			400
		north 		470
	)

	(method (init)
		(Load VIEW (+ vLusingKey (* 100 egoIsPatti)))
		(Load SCRIPT REVERSE)

		(Load SOUND sElevatorDoorOpening)
		(Load SOUND sElevatorDoorClosing)

		(super init)

		(addToPics
			add				atpDoor,
			doit,
		)

		(if (not egoIsPatti)
			(aMan				init)
		)

		(aButton
			setPri			9,
			init,
			stopUpd,
		)

		(aLightLeft
			setScript		(LightScript new),
			init,
		)

		(aLightRight
			setScript		(LightScript new),
			init,
		)

		(aDoor
			setCel			0,
			ignoreActors,
			init,
			stopUpd,
		)

		(self
			setLocales		(+ locClerkL egoIsPatti),
			setScript 		RoomScript,
		)

		(NormalEgo)
		(if (== prevRoomNum 470)
			(HandsOff)
			(ego
				illegalBits	0,
				posn			70 133,
				loop			facingDown,
				init,
			)
			(aDoor
				setCel		LastCel,
			)
			(aLightLeft
				setCel		0,
			)
			(RoomScript changeState backFrom470)
		else
			(ego
				posn			1 164,
				init,
			)
		)
	)
)

(instance RoomScript of Script
	(method (handleEvent event)
		(if (or (!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'press')
					(Said 'take, use/elevator')
					(Said 'enter, open/elevator, door'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearDoor)
						(Print "You couldn't reach the elevator's pushbutton 
							from here.")
					)
					(else
						(RoomScript changeState gotoButton)
					)
		  		)
			)

			((Said 'look>')
				(cond
					((Said '/tree')
						(Print "You always appreciate a fine ficus benjamina.")
					)

					((Said '/floor, carpet')
						(Print "Isn't it wonderful?
							The linoleum industry has made such advances recently!")
					)

					((Said '/elevator, door, light')
						(Printf "The left elevator is currently on floor %d and the
							right elevator is on floor %d."
							(+ 1 (aLightLeft cel)) (+ 1 (aLightRight cel)))
					)

					((and egoIsPatti
							(or	(Said '/counter, arnold')
									(Said '//counter, arnold')))
						(Print "It's too late at night!
							Arnold has gone to bed until tomorrow morning's shift.
							Deal with him later, Patti.
							What he doesn't know, won't hurt him tonight!")
					)

					((Said '[/room, lobby, hotel]')
						(Printf "You are in the lobby of the casino hotel. %s
							There are two elevators here."
							(if egoIsPatti {} 
							else				{A clerk stands bored behind the counter.}
							)
						)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
			)


			;**	handle the elevator

			(gotoButton
				(HandsOff)
				(= egoState IN_ELEVATOR)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo 94 139 self
				)
			)

			(pressButton
				(ego
					cycleSpeed		1,
					view				(+ vLusingKey (* 100 egoIsPatti)),
					setLoop			lFacingNorth,
					setCycle			EndLoop self,
				)
			)

			(donePressing
				(aButton
					setCel			cUp,
					stopUpd,
				)
				(ego
					setCycle			BegLoop self,
				)
			)

			(moveElevator
				(NormalEgo facingNorth)
				(HandsOff)
				((aLightLeft script) changeState PickUpLarry)
			)


			;**		Wait here until the elevator gets to the ground floor,
			;**	indicated by the LightScript reaching cel 0.

			(waiting
				(delay 0)
			)

			(openDoor
				(aLightLeft
					setScript		FALSE,
				)
				(soundFX
					number			sElevatorDoorOpening,
					loop				1 ,
					play ,			
				)
				(aDoor
					setCycle			EndLoop self,
				)
			)

			(tinyDelay
				(aDoor			stopUpd)
				(= cycles 10)
			)

			(moveToDoor
				(ego
					illegalBits		0,
					setMotion		MoveTo 70 135 self
				)
			)

			(enterElevator
				(ego
					setMotion		MoveTo 72 128 self
				)
			)

			(wait4Door
				(ego
					setLoop			facingDown,
				)
				(= cycles 14)
			)

			(closeDoor
				(soundFX
					number			sElevatorDoorClosing,
					loop				1 ,
					play ,			
				)
				(aDoor
					setCycle			BegLoop ,
				)
				(delay 3)
			)

			(changeRooms
				(music				fade)
				(curRoom newRoom 470)
			)


			;**	coming back from 470

			(backFrom470
				(ego
					setMotion		MoveTo 70 154 self,
				)
			)

			(closeTheDoor
				(soundFX
					number			sElevatorDoorClosing,
					loop				1 ,
					play ,			
				)
				(aDoor
					setCycle			BegLoop self,
				)
			)

			(allDone
				(aDoor			stopUpd,)
				(NormalEgo)
				(= egoState NORMAL)
			)
		)
	)
)


(instance ManScript of Script
	(method (handleEvent event)
		(if (or (!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said '/casino, gamble, gambling')
					(Said '//casino, gamble, gambling'))
				(Print "\"Hey, buddy! How do you get into that casino?\"")
				(ManScript
					changeState		moveToCenter,
					register			102,
				)
			)

			((Said 'talk')
				(cond
					(nearCounter
						(Print "He can't hear you from way back here.")
					)
					(nearMan
						(Print "You attempt to catch the clerk's attention,
							\"Excuse me.\"")
						(ManScript changeState moveToCenter)
					)
					(else
						(NotClose)
					)
				)
			)

			((and (not (Said 'look>'))
					(or	(Said '/key, penthouse, room')
							(Said 'register')
							(Said '//key, penthouse, room')))
				(Print "\"Excuse me, clerk. May I rent a room here?\" you say.")
				(ManScript
					changeState		moveToCenter,
					register			101,
				)
			)

			((Said 'look/man, counter, clerk')
				(Print "The desk clerk eagerly awaits your every request.")
				(Print "(If you are a registered guest!)" LOW)
			)
		)
	)


	(method (changeState newState)		;**	 &tmp ManX ManY [str 100]
		(switch (= state newState)

			(manInit
				(delay (Random 4 8))
			)

			(manStart
				(aMan
					illegalBits		0,
					cycleSpeed		0,
					setLoop			RELEASE,
					setCycle			Walk,
					setMotion		MoveTo 257 162 self,
				)
			)

			(waitALittle
				(delay (Random 4 8))
			)

			(moveUmBack
				(aMan
					setMotion		MoveTo 233 140 self,
				)
			)

			(manDone
				(self changeState manInit)
			)


			;**	handle talking to larry

			(moveToCenter
				(aMan
					setMotion		MoveTo 249 155 self,
				)
			)

			(talkAlittle
				(aMan
					cycleSpeed		4,
					setLoop			lTalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(shutUp
				(aMan
					setCel			0,
				)
				(= cycles 2)
			)

			(saySomething
				(cond
					(register
						(switch register
							(101 
								(Print "\"Say, you're that Laffer guy that used
									to work around here,\" he replies.
									\"Your money's no good here. Literally!\"")
							)	
							(102 
								(Print "\"There's no need for you to go to the
									casino.\"")
							)	
						)
					)

					(egoIsPatti
						(switch msgVar
							(0
								(Print "\"Hello, Patti.\"")
							)
							(1
								(Print "\"May I help you, Patti?\"")
							)
							(2
								(Print "\"Hello, miss.\"")
							)
							(3
								(Print "\"Good evening, Patti. How may I help you?\"")
							)
						)
						(if ( > (++ msgVar) 3)
							(= msgVar 0)
						)
					)

					(else
						(switch msgVar
							(0
								(Print "\"I'm sorry sir, but this is a private hotel!\"")
							)
							(1
								(Print "\"Yes? May I help you?\"")
							)
							(2
								(Print "\"Would you like me to call security, bub?\"")
							)
							(3
								(Print "\"How may I help you?\"")
							)
						)
						(if ( > (++ msgVar) 3)
							(= msgVar 0)
						)
					)
				)

				(self changeState manInit)
			)
		)
	)
)


(instance aButton of Prop
	(properties
		view				vRoom
		x	 				ButtonX
		y					ButtonY
		loop				1
	)
)

(instance aLightLeft of Prop
	(properties
		x	 				LightLeftX
	)
)
(instance aLightRight of Prop
	(properties
		x	 				LightRightX
	)
)

(instance aDoor of Prop
	(properties
		view				vRoom
		x	 				LDoorX
		y					LDoorY
		priority			9
	)
)
(instance atpDoor of PicView
	(properties
		view				vRoom
		x	 				RDoorX
		y					RDoorY
		priority			9
	)
)

(instance aMan of Actor
	(properties
		view				vClerk
		loop				facingLeft
		x					233
		y					140
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			4,
			setCycle			Walk,
			setScript		ManScript,
		)
	)
)

(instance LightScript of Script
	(method (changeState newState &tmp destCel)
		(switch (= state newState)

			(ElevatorIntro
				(client
					view				vRoom,
					setCel			(Random 2 9),
					setLoop			lLights,
					cycleSpeed		(/ machineSpeed 2),	;**	was 40
					setPri			9,
					y					LightY,
				)
				(delay 3)		;**	give L/P a chance to get out of elevator
			)

			(ElevatorInit
				(delay (Random 4 10))
			)

			(ElevatorDown
				(= destCel (client cel))
				(while (== (client cel) destCel)
					(= destCel (Random 1 8))
				)
				(client
					setCycle			CycleTo destCel (if (> destCel (client cel))
											ctForward else ctReverse) self,
				)
				(= state (- ElevatorInit 1))
			)


			;**	We only get here when someone pushes the button

			(PickUpLarry
				(if (== (client cel) 0)		;**	it's here and waiting
					(client
						setCel			0 ,
					)
					(= state waitingToOpenDoor)
				)
				(delay 3)
			)

			(GetLarry
				(client
					cycleSpeed		(/ machineSpeed 2),	;**	was 40
					setCycle			CycleTo 0 ctReverse self,
				)
			)

			(waitingToOpenDoor
				(delay 3)
			)

			(ElevatorDone
				(RoomScript changeState openDoor)
			)
		)
	)
)


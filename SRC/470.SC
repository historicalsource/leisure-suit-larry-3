;**
;**	Logics for room 470 -- Inside Elevator
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 27, 1990
;**


(script#				470)

(public
	rm470				0
)


(define	vElevatorInterior			470)
	(define	lFloorOfRoom480	0)
	(define	lPanel				1)
	(define	lBeamRear			2)
	(define	lBeamFront			3)
	(define	lFloorOfRoom460	4)
	(define	lBlankCel			5)

(define	vElevator					460)
	(define	lDoor					0)
	(define	lButton				1)
	(define	lLight				2)

;(define	vLusingKey					715)
;(define	vPusingKey					815)


;(define	sMuzak						032)	
;(define	sElevatorDoorOpening		460)


(define 	doorX					160)
(define 	doorY					125)

(define	PanelX				184)
(define	PanelY				110)
(define	LightX				160)
(define	LightY				 62)


(enum												;**	RoomScript state values
	intro
	evidently
	gotoButton
	pressButton
	lowerArm
	buttonDone
	incFloor
	posnAboveScreen
	repoRear
	decFloor
	repoBeamRear
	posnBelowScreen
	beamDone
	openDoors
	leaveElevator
	walkOut
	changeRooms
)


(local
	destination 
	lightCel		= 0
)


(instance rm470 of Room
	(properties
		picture		470
	)


	(method (init)
		(if (ego has iKey)
			(Load VIEW iKey)
		)

		(Load VIEW (+ vLusingKey (* 100 egoIsPatti)))
		(Load SOUND sElevatorDoorOpening)

		(super init)

		(aBeamFront			init)
		(aBeamRear			init)
		(aDoor				init)
		(aFloor				init)

		(addToPics
			add 				atpPanel,
			doit
		)

		(self
			setScript 		RoomScript
		)

		(NormalEgo facingUp)
		(ego
			posn				161 137,
			init
		)

		(if (> prevRoomNum curRoomNum)	;**	upstairs
			(= lightCel 8)
		)

		(aLight
			setPri			4,
			setCel			lightCel,
			init,
			stopUpd,
		)

		(music
			number			sMuzak,
		 	loop				bgMusicLoops,
			play,
		)
	)
)


(instance RoomScript of Script
	(method (handleEvent event &tmp [string 50])
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'use/key')
					(Said 'press/button, floor'))
				(Print "You must pick a floor. Which floor?")
			)

			((or	(Said '/penthouse, 9, nine')
					(Said '//penthouse, 9, nine')
					(Said 'go, press/penthouse, 9, nine'))
				(cond
					((not (ego has iKey))
						(Print "You attempt to pick the lock with your fingernail,
							but are unsuccessful!")
						(Print (Format @string
							"(You must have a room key to operate this
							elevator, %s.)" egoName) LOW)
					)
					(destination
						(Print "The elevator is already underway!")
					)
					(( > lightCel 7)
						(Print "That's where you are!")
					)
					(else
						(if egoIsPatti
							(= destination 484)
						else
							(= destination 480)
						)
						(self changeState gotoButton)
					)
				)
			)

			((or	(Said '/ground, 1, lobby')
					(Said '//ground, 1, (floor<first), lobby')
					(Said 'go, press/ground, 1, lobby'))
				(cond
					((not (ego has iKey))
						(Print "You attempt to pick the lock with your fingernail,
							but are unsuccessful!")
						(Print (Format @string
							"(You must have a room key to operate this
							elevator, %s.)" egoName) LOW)
					)
					(destination
						(Print "The elevator is already underway!")
					)
					((== lightCel 0)
						(Print "That's where you are!")
					)
					(else
						(= destination 460)
						(self changeState gotoButton)
					)
				)
			)

			((Said 'up, down, open, close/door, floor, elevator')
				(Print "Press the floor number, please.")
			)

			((Said 'press, go/button, door, floor')
				(Print "You have no business travelling to any floor
					except one and nine.")
			)

			((Said 'listen')
				(if egoIsPatti
					(Print "Gawd, do you hate musical tripe!")
				else
					(Print "Your favorite style of music is playing.")
				)
			)

			((Said 'leave, go, exit, quit, stop')
				(Ok)
				(Print "Wait until the door opens.")
			)

			((and (ego has iKeycard)
					(Said '/keycard'))
				(Print "The keycard would work better if you were in Fat City!")
			)

			((Said 'pick, hit, smash/lock')
				(Print "The locks here will only respond to an official
					hotel room key.")
			)

			((Said 'look>')
				(cond
					((Said '/light')
						(Printf "The row of lights above the door indicate you
							are currently at floor %d." (+ 1 (aLight cel)))
					)

					((Said '/switch, button')
						(Print "On the wall to the right of the doors
							is a panel with a keyhole beside a vertical column of 
							buttons numbered from one to nine.
							\"Could those indicate potential floor destinations?\"
							you wonder.")
					)

					((Said '/door')
						(Printf "The elevator's door is presently %s."
							(if (aDoor cel) {open} else {closed}))
					)

					((Said '/(hole<key), keyhole')
						(Print "The keyhole is a hole into which you insert a key.")
					)

					((Said '[/room, elevator]')
						(Print "It's an elevator.")
						(Print "(What did you expect?)" LOW)
					)
				)
			)
		)
	)

	(method (changeState newState &tmp destY)
		(switch (= state newState)

			(intro
				(delay 12)
			)


			;**	If we get here, nobody pressed a button.

			(evidently
				(Print "Evidently, you don't want to change floors!")
				(self changeState openDoors)
			)


			;**	Ok, he's awake.  Press button, close doors and take off.

			(gotoButton
				(Ok)
				(HandsOff)
				(ego
					illegalBits		0,
					setMotion		MoveTo 179 130 self,
				)
			)

			(pressButton
				(ego
					cycleSpeed		1,
					view				(+ vLusingKey (* 100 egoIsPatti)),
					loop				2,			;**	OK, even tho P only has loop 0
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(lowerArm
				(if (not (TstFlg usedElevator))
					(SetFlg usedElevator)
					(theGame changeScore 4)
					(Print "You insert Patti's penthouse key into the
						ninth floor penthouse access lock and turn it one-quarter
						turn to the right.
						Both you and the elevator begin to rise."
						#icon iKey 0 0
					)
				)
				(ego
					setCycle			BegLoop self,
				)
			)

			(buttonDone
				(NormalEgo facingNorth)
				(= cycles 10)
				(if (== destination 460)
					(= state (- decFloor 1))
				)
			)


			;**	This block is only if ego is going up.  Move beams down.

			(incFloor
				(aLight
					setCel			(++ lightCel),
				)
				(if (>= lightCel 9)
					(self changeState beamDone)
				else
					(aBeamRear
						setMotion		MoveTo 159 260,
					)
					(aBeamFront
						setMotion		MoveTo 160 260 self,
					)
				)
			)

			(posnAboveScreen
				(aBeamRear
					posn				159 -16,
					setMotion		MoveTo 159 130,
				)
				(aBeamFront
					posn				160 -32,
					setMotion		MoveTo 160 80 self,
				)
			)

			(repoRear
				(aBeamRear
					posn				159 48,
					setMotion		MoveTo 159 128,
				)
				(aBeamFront
					setMotion		MoveTo 160 160 self,
				)
				(= state (- incFloor 1))
			)


			;**	This block is only if going down,

			(decFloor
				(aLight
					setCel				(-- lightCel),
				)
				(if (== lightCel 0)
					(self changeState beamDone)
				else
					(aBeamRear
						setMotion		MoveTo 159 80
					)
					(aBeamFront
						setMotion		MoveTo 160 80 self
					)
				)
			)

			(repoBeamRear
				(aBeamRear
					posn				159 104,
					setMotion		MoveTo 159 -32
				)
				(aBeamFront
					setMotion		MoveTo 160 -32 self,
				)
			)

			(posnBelowScreen
				(aBeamRear
					posn			 	159 232,
					setMotion		MoveTo 159 128,
				)
				(aBeamFront
					posn				160 260,
					setMotion		MoveTo 160 160 self,
				)
				(= state (- decFloor 1))
			)


			;**	Now we're back to code that both directions execute.

			(beamDone
				(aFloor
					setLoop			(if (== destination 460)	lFloorOfRoom460 
										else 								lFloorOfRoom480),
					stopUpd,
				)
				(delay 3)
			)


			;**	This code also is invoked if he press no buttons.

			(openDoors
				(HandsOff)
				(soundFX
					number			sElevatorDoorOpening,
					loop				1 ,
					play ,			
				)
				(aDoor
					setCycle			EndLoop self,
				)
			)

			(leaveElevator
				(aDoor				stopUpd)
				(ego
					illegalBits		0,
					setMotion		MoveTo 160 128 self,
				)
			)

			(walkOut
				(ego
					setMotion		MoveTo 160 124 self,
				)
			)

			(changeRooms
				(music fade)
				(if (not destination)
					(= destination prevRoomNum)
				)
				(curRoom newRoom destination)
			)
		)
	)
)

(instance atpPanel of PicView
	(properties
		view			vElevatorInterior
		x	 			PanelX
		y				PanelY
		cel			0
		loop			1
		priority		5
	)
)

(instance aLight of Prop
	(properties
		view			 	vElevator
		loop				lLight
		x	 				LightX
		y					LightY
		cel				0
	)
)

(instance aBeamFront of Actor
	(properties
		view					vElevatorInterior
		x						160
		y						156
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			illegalBits		0,
			setPri			15,
			setLoop			(if (> machineSpeed pc8088) lBeamFront else lBlankCel),
			setStep			0 8,
		)
	)
)

(instance aBeamRear of Actor
	(properties
		view					vElevatorInterior
		x						doorX
		y						doorY
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			illegalBits		0,
			setPri			2,
			setLoop			(if (> machineSpeed pcAT) lBeamRear else lBlankCel),
			setStep			0 8,
		)
	)
)

(instance aDoor of Prop
	(properties
		view					vElevator
		loop					lDoor
		x						doorX
		y						doorY
		illegalBits			0
		signal				ignrAct
	)

	(method (init)
		(super init)
		(self
			setCel			0
			setPri			6,
			stopUpd,
			ignoreActors,
		)
	)
)

(instance aFloor of Prop
	(properties
		view					vElevatorInterior
		x						doorX
		y						doorY
		signal 				ignrAct
		illegalBits			0
	)
	(method (init)
		(super init)
		(self
			setPri			5,
			setLoop			(if (== prevRoomNum 460)	lFloorOfRoom460 
								else 								lFloorOfRoom480)
			ignoreActors,
			stopUpd,
		)
	)
)


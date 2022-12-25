;**
;**	Logics for room 510 -- near a stream after the bamboo maze
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 23, 1989
;**


(script#				510)

(public
	rm510				0
)


;(define	vStream						520)
	(define	lRock1				0)
	(define	lRock2				1)
	(define	lRock3				2)

;(define	vPdrinkingStream			511)
	(define	lGoingDown			0)
	(define	lDrinking			1)

;(define	vPdrowning					812)


;(define	sNearDanger					006)


(define onWater			(& (ego onControl origin)	cLtBlue))
(define nearWater			(& (ego onControl )			cLtBlue))


(enum												;**	RoomScript state values
	intro
	youDidIt
	startDrowning
	drowning2
	drowning3
	drowning5
	startDrinking
	drinkWater
	allDone
	tinyDelay
	doneDrinking
)


(local
	drownCount
)


(instance rm510 of Room
	(properties
		picture		510
		horizon		65
		north			520
		south			500
	)


	(method (init)
		(SetFlg beenIn510)
		(Load VIEW vPdrinkingStream)
		(Load VIEW vPdrowning)
		(Load SOUND sNearDanger)

		(super init)

		(self
			setScript 		RoomScript
		)

		(aRock1				init)
		(aRock2				init)
		(aRock3				init)
		(aRock4				init)
		(aRock5				init)

		(cond
			((== prevRoomNum 520)
				(ego
					posn 		275 79,
					loop		facingDown,
				)
			)
			(else
				(ego
					posn		284 188,
				)
			)
		)

		(NormalEgo)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(cond
			((and onWater
					(== egoState NORMAL))
				(self changeState startDrowning)
			)

			((and	(== egoState DROWNING_IN_RIVER)
					( < 8 (++ drownCount)))
				(= drownCount 0)
				(ego
					;**	Pick loop 0 or 1 if big Patti, or Loop 2 or 3 if far away.
					setLoop			(+ (Random 0 1) (* 2 (< (ego y) 87))),
				)
			)
		)
	)

	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'get/drink, water')
					(Said 'drink')
					(Said 'drink/water'))
				(cond
					((TstFlg drankRiverWater)
						(Print "Your thirst is sufficiently quenched now, Patti.")
					)
					((not nearWater)
						(NotClose)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
					  	(self changeState startDrinking)
					)
				)
			)

			((Said 'make/rope')
				(Print "Bamboo will not make a rope.")
			)

			((or	(Said 'make/boat')
					(Said 'climb, get, use/bamboo'))
				(Print "The bamboo plants will do you no good.")
			)

			((or	(Said 'go<swim')
					(Said 'swim'))
				(Print "Just walk into the water, Patti.")
			)

			((Said 'look>')
				(cond
					((Said '/tree')
						(Print "These trees bring back fond memories of your youth
							spent back on the plains of Mora, Minnesota.")
					)

					((Said '/rock, tit')
						(Print "This island is just filled with unusual rock
							formations, isn't it?")
					)

					((Said '/bamboo')
						(Print "To the south lies the final remnants of the
							bamboo forest.
							You're thankful you have no need to reenter that
							place!")
					)

					((Said '/rapid, river')
						(Print "The river here sweeps down from the mountains,
							swiftly disappearing into the jungle ahead.
							Vicious rapids roil its surface.")
						(Print "(At least the water is clean!)" LOW)
					)

					((Said '[/room]')
						(Print "You find yourself clear of the bamboo maze at last!
							You are standing beside a swift, cold, mountain stream,
							filled with beautiful clear water.")
					)
				)
			)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	She hit the water.

			(startDrowning
				(= egoState DROWNING_IN_RIVER)
				(HandsOff)
				(rm510 horizon 1)
				(ego
					view				vPdrowning,
					setLoop			0,
					setStep			1 3,
					setCycle			Forward,
					illegalBits		0,
					posn				(- (ego x) 20) (ego y),
					setMotion		MoveTo 160 104 self,
				)
				(music
					number			sNearDanger,
					loop				FOREVER,
					play,
				)
			)

			(drowning2
				(ego
					setMotion		MoveTo 179 87 self,
				)
			)

			(drowning3
				(ego
					setMotion		MoveTo 200 54 self,
				)
			)

			(drowning5
				(curRoom newRoom 520)
			)


			;**	Patti drinks water

			(startDrinking
				(HandsOff)
				(= egoState DRINKING)
				(ego
					view				vPdrinkingStream,
					cycleSpeed		2,
					setLoop			lGoingDown,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(drinkWater
				(ego
					setLoop			lDrinking,
					setCycle			Forward,
				)
				;**	Cycle through her drinking loop 4 times:
				(= cycles (* (* 2 (ego cycleSpeed)) (* 4 (- (NumCels ego) 1))))
			)

			(allDone
				(ego
					setLoop			lGoingDown,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(tinyDelay
				(delay 2)
			)

			(doneDrinking
				(theGame changeScore 42)
				(SetFlg drankRiverWater)
				(NormalEgo facingLeft)
				(= egoState NORMAL)
				(Print "You find the stream's water remarkably refreshing,
					without a trace of caffeine.")
			)
		)
	)
)


(instance aRock1 of Prop
	(properties
		view					vStream
		loop					lRock1
		x						49 
		y						177
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock2 of Prop
	(properties
		view					vStream
		loop					lRock2
		cel					1
		x						169 
		y						170
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock3 of Prop
	(properties
		view					vStream
		loop					lRock2
		x						157 
		y						189
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock4 of Prop
	(properties
		view					vStream
		loop					lRock3
		x						52 
		y						143
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock5 of Prop
	(properties
		view					vStream
		loop					lRock3
		cel					1
		x						111 
		y						104
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)


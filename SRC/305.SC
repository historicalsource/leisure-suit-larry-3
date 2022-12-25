;**
;**	Logics for room 305 -- Natives, Inc. exterior
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				305)

(public
	rm305				0
)


(define	vRoom							305)
	(define	lDoor					0)
	(define	lPalmTrees			1)
		(define	cFar			0)
		(define	cNear			1)
	(define	lSign					2)

(enum												;**	RoomScript state values
	intro
	backFromFiring
	ohShit
)


(local
	seenMsg
	theCounter
	[string 305]
)


(instance rm305 of Room
	(properties
		picture		305
		east			250
		west			350
	)


	(method (init)
		(super init)

		(addToPics
			add 				atpPalmTree1,
			add 				atpPalmTree2,
			add 				atpPalmTree3,
			add 				atpPalmTree3a,
			add 				atpPalmTree4,
			add 				atpPalmTree5,
			add 				atpPalmTree6,
			add 				atpPalmTree7,
			add 				atpPalmTree8,
			add 				atpPalmTree9,
			add 				atpPalmTree10,
			doit
		)

		(if (> machineSpeed pc8088)
			(aSign				
				setPri			12,
				setCycle			Forward,
				isExtra			TRUE,
				init,
			)
		)

		(aDoor				init)

		(self
			setScript 		RoomScript
		)

		(cond
			((== prevRoomNum 350)		 (ego posn  78 144, loop facingEast,))
			(else								 (ego posn 313 161))
		)

		(NormalEgo)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look<in/window')
				(Print "There are so few windows for such a large building_--
					and none of them open!")
			)

			((Said 'look>')
				(cond
					((Said '/sign')
						(Print "It's blindingly tasteless.")
					)

					((Said '/tree, palm')
						(Print "What?!
							Can it be true?
							Could \"Natives, Inc.\" really use cardboard palm trees?")
					)

					((Said '[/building, room]')
						(Print "You are outside \"Natives, Inc.,\" home of
							the island's major (and only) corporation.")
						(if (not egoIsPatti)
							(Print "(You've always been a big fan of steel buildings!)"
								LOW)
						)
					)	
				)
			)	
		)
	)

	(method (changeState newState)		;**	 &tmp xxX xxY)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
				(if (== egoState GETTING_FIRED)
					(SetFlg beenIn250)
					(= egoState NORMAL)
					(= state backFromFiring)
					(= cycles 20)
				)
			)


			;**	He's just been fired... too bad Larry!

			(backFromFiring
			)

			(ohShit
				(Print "That was humiliating, Larry.
					You vow never to work for HIM again!")
			)
		)
	)
)


(instance atpPalmTree1 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					0
		x						143 
		y						128
		priority				 9
	)
)
(instance atpPalmTree2 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					0
		x						183 
		y						131
		priority				 9
	)
)
(instance atpPalmTree3 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					0
		x						225 
		y						135
		priority				 9
	)
)
(instance atpPalmTree3a of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					0
		x						266
		y						139
		priority				 9
	)
)
(instance atpPalmTree4 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					0
		x						311 
		y						144
		priority				10
	)
)
(instance atpPalmTree5 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					1
		x						42 
		y						161
		priority				13
		signal				ignrAct
	)
)
(instance atpPalmTree6 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					1
		x						90 
		y						169
		priority				14
		signal				ignrAct
	)
)
(instance atpPalmTree7 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					1
		x						140 
		y						175
		priority				15
		signal				ignrAct
	)
)
(instance atpPalmTree8 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					1
		x						190 
		y						180
		priority				15
		signal				ignrAct
	)
)
(instance atpPalmTree9 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					1
		x						238 
		y						185
		priority				15
		signal				ignrAct
	)
)
(instance atpPalmTree10 of PicView
	(properties
		view					vRoom
		loop					lPalmTrees
		cel					1
		x						287 
		y						192
		priority				15
		signal				ignrAct
	)
)

(instance aSign of Prop
	(properties
		view				vRoom
		loop				lSign
		x					149
		y					60
	)
)

(instance aDoor of AutoDoor 
	(properties
		view				vRoom
		loop				lDoor
		x					93
		y					86
		cycleSpeed		1
		entranceTo 		350
		doorCtrl 		cBlue
		roomCtrl			cGreen
		doorBlock		cYellow
	)							

	(method (init)
		(super init)
		(self
			setPri			5,
		)
	)
)


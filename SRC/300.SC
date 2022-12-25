;**
;**	Logics for room 300 -- Fat City exterior
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				300)

(public
	rm300				0
)


(define	vRoom							300)
	(define	lSign					0)
	(define	lSpout				1)


(enum												;**	RoomScript state values
	intro
	from360
	atLipOfTongue
	clearMouth
	enterMouth
	changeRooms
)


(local
	seenMsg
	theCounter
	[string 300]
)


(enum 300										;**	local egoState values
	ENTERING_WHALE
	LEAVING_WHALE
)


(instance rm300 of Room
	(properties
		picture		300
		east			220
	)


	(method (init)
		(super init)

		(addToPics
			add 				atpSign,
			doit
		)
		(aSpout				init)

		(self
			setScript 		RoomScript
		)

		(NormalEgo)

		(cond
			((== prevRoomNum 360)		 
				(ego 
					posn 		155 190,
					setPri	4,
					setLoop	facingSouth,
				)
				(RoomScript changeState from360)
				(if (and	egoIsHunk
							(not (TstFlg needsSoap))
							(not (TstFlg needsShower)))
					(SetFlg missedBambi)		;**	Sorry, Charlie, no beaver!
				)
			)
			(else								 
				(ego 
					posn 		318 184,
					loop		facingWest,
				)
			)
		)

		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (& (ego onControl) cYellow)
			(cond
				((== egoState NORMAL)
					(= egoState ENTERING_WHALE)
					(self changeState enterMouth)
				)

				((== egoState LEAVING_WHALE)
				)

				((not seenMsg)
					(= seenMsg TRUE)
					(Print "You can't enter \"Fat City\" dressed like that!")
				)
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look>')
				(cond
					((Said '/whale')
						(Print "Fat City has an entrance ramp disguised as a 
							whale.
							Doesn't it remind you of \"The Louvre?\"")
					)

					((Said '/sign')
						(Print "It says \"Fat City!\"")
					)

					((Said '/tail')
						(Print "This is no fish tale!")
					)

					((Said '/eye')
						(Print "Are those cleverly disguised television cameras?")
					)

					((Said '/mouth')
						(Print "What a clever doorway!
							Fat City's entrance ramp leads down to its lower-level
							lobby.")
					)

					((Said '/tongue')
						(Print "Perhaps you should get closer...")
					)

					((Said '/spout')
						(Print "That's some spout!")
					)

					((Said '[/room]')
						(Print "Ahead of you lies \"Fat City,\"
							the island's famous health spa.
							Is that symbolism, or what?")
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


			;**	entering from inside the whale

			(from360
				(HandsOff)
				(= egoState LEAVING_WHALE)
				(ego
					illegalBits		0,
					setMotion		MoveTo 155 160 self,
				)
			)

			(atLipOfTongue						;**	Get it?
				(ego
					setMotion		MoveTo 155 164 self,
				)
			)

			(clearMouth
				(= egoState NORMAL)
				(NormalEgo)
			)


			;**	He tripped over Yellow wire;

			(enterMouth
				(HandsOff)
				(ego
					illegalBits		0,
					setPri			4,
					setLoop			facingNorth,
					setMotion		MoveTo (ego x) 200 self,
				)
			)

			(changeRooms
				(= egoState NORMAL)
				(curRoom newRoom 360)
			)
		)
	)
)

(instance atpSign of PicView
	(properties
		view					vRoom
;		loop					lSign
;		cel					cSign
		x						62
		y						110
		priority				7
		signal				ignrAct
	)
)
(instance aSpout of Prop
	(properties
		view				vRoom
		loop				lSpout
;		cel				cSpout
		x					157
		y					56
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			setCycle			Forward ,
		)
	)
)


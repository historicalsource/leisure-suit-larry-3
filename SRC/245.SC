;**
;**	Logics for room 245 -- on the way to the Bamboo Maze
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	August 23, 1989
;**


(script#				245)

(public
	rm245				0
)


;(define	vLfalling					713)
;(define	vPfalling					813)


(enum												;**	RoomScript state values
	intro
)


(local
	seenMsg
)


(instance rm245 of Room
	(properties
		picture		245
		west			240
	)


	(method (init)

		(super init)

		(self
			setRegions		regFalling,
			setScript 		RoomScript
		)

		(if (== prevRoomNum 500)
			(ego
				posn 			126 73,
				loop			facingSouth,
			)
		else
			(ego
				posn 			5 172,
				loop			facingEast,
			)
		)
		(NormalEgo)
		(ego					init)
		(if egoState
			(ego observeControls cGreen cCyan cRed)	;**	No falls if weird.
		)
	)

	(method (newRoom n)
		(cls)
		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (doit)

		(if (not egoIsPatti)
			(ego observeControl cYellow)

			(if (& (ego onControl) cMagenta)
				(if (not seenMsg)
					(= seenMsg TRUE)
					(Print "There is nothing before you but limitless jungle.
						There's no need for a guy like you to lose himself
						in that forsaken wilderness.
						You'd better head back to the bright lights of the city,
						Larry!")
				)
			else
				(= seenMsg FALSE)
			)
		)

		(if (== egoState NORMAL)
			(cond
				((& (ego onControl) cBlue)
					(curRoom newRoom 500)
				)

				((& (ego onControl) cRed)
					(Notify regFalling ltBlue 300)
				)

				((& (ego onControl) cGreen)
					(Notify regFalling black 300)
				)

				((or	(== EAST (ego edgeHit))
				 		(& (ego onControl) cCyan))
					(ego
						x				(+ (ego x) 8),
					)
					(Notify regFalling gray 300)
				)

			)
		)
		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if (Said 'look>')
			(cond
				((Said '/dick, rock, tit')
					(Print "What unusual rock formations!")
				)

				((Said '/bamboo')
					(Print "The thicket of bamboo is so thick
						it is impossible to see very far into it.")
				)

				((Said '[/room]')
					(Print "A dangerous cliff lies beside this ledge.
						A bamboo thicket is in the distance.")
				)
			)
		)
	)
)


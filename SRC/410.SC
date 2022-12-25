;**
;**	Logics for room 410 -- Resort staircase
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				410)

(public
	rm410				0
)

(local
	seenMsg
)


(instance rm410 of Room
	(properties
		picture		410
		south			400
	)


	(method (init)
		(super init)

		(self
			setScript 		RoomScript
		)

		(NormalEgo)
		(cond
			((== prevRoomNum 415)		(ego posn  59  45))
			((== prevRoomNum 416)		(ego posn 263  45))
			(else								(ego posn 159 188))
		)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(cond
			((& (ego onControl origin)  cBlue)	(curRoom newRoom 415))
			((& (ego onControl origin) cGreen)	(curRoom newRoom 416))
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look>')
				(cond
					((Said '/picture, body, nude, woman, painting')
						(Print "From this angle you can see her entire naked body.")
					)

					((Said '/pillar')
						(Print "The pillars reach from the floor to the ceiling.")
					)

					((Said '/railing')
						(Print "It's about three feet tall.")
					)

					((Said '/stairway')
						(Print "The staircase here is perfectly suited to walking.")
					)

					((Said '/curtain')
						(Print "The curtains prevent you from seeing behind them.")
					)

					((Said '[/room]')
						(Print "A grand staircase rises from the resort's lobby.")
					)
				)
			)
		)
	)
)


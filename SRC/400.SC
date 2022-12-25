;**
;**	Logics for room 400 -- Resort Entrance
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	August 9, 1989
;**


(script#				400)

(public
	rm400				0
)


(instance rm400 of Room
	(properties
		picture		400
		horizon		83
		north			410
		east			460
		west			250
		south			250
	)


	(method (init)
		(super init)

		(self
			setScript 		RoomScript
		)

		(NormalEgo)
		(switch prevRoomNum
			(460		(ego posn	318 162))
			(410		(if (< (ego x) 111) (ego x 111))
						(if (> (ego x) 210) (ego x 210)))
			(else		(ego posn	159 188))
		)
		(ego init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look>')
				(cond
					((Said '/light')
						(Printf "Nice pair of sconces, eh, %s." egoName)
					)

					((Said '[/room]')
						(Print "You are inside Nontoonyt Resort.
							To the north stretches the casino,
							to the east you see the hotel lobby,
							and to the south lies the exit to the outside.")
					)
				)
			)
		)
	)
)


;**
;**	Logics for room 240 -- Comedy Club exterior
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	August 10, 1989
;**


(script#				240)

(public
	rm240				0
)


(define	vComedyHutExterior	240)
	(define	lDoor				0)


(define	enteringComedyHut			(& (ego onControl) cBlue))


(local
	seenMsg
)


(instance rm240 of Room
	(properties
		picture		240
		horizon		66
		north			245
		east			245
		west			230
	)


	(method (init)
		(super init)

		(self
			setScript 		RoomScript
		)

		(aDoor
			locked			(or 	(!= egoState NORMAL)
										egoIsPatti),
			init,
		)

		(cond
			((== prevRoomNum 230)		(ego x 1))
			((== prevRoomNum 340)
				(ego
					posn  					53 77,
					loop						facingSouth,
				)
			)
			(else								(ego posn 292  73))
		)

		(NormalEgo)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (and enteringComedyHut
					(or	egoIsPatti
							(!= egoState NORMAL)))
			(if (not seenMsg)
				(= seenMsg TRUE)
				(ego
					posn 				(ego xLast) (ego yLast),
					setMotion		FALSE,
					observeControl cBlue,
				)
				(if egoIsPatti
					(Print "The world-famous \"Comedy Hut\" is presently closed.")
				else
					(Print "The world-famous \"Comedy Hut's\" strict dress code
						policy prohibits you from enjoying them at this time.")
				)
				(RedrawCast)
			)
		else
			(= seenMsg FALSE)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if (Said 'look>')
			(cond
				((Said '/club, comedy, store')
					(Print "You might try walking inside.")
				)
				((Said '/door')
					(if egoIsPatti
						(Print "The door is locked.")
					else
						(Print "It's unlocked; why not walk inside.")
					)
				)
				((Said '[/room]')
					(Print "You are outside the world-famous \"Comedy Hut,\"
						the fast-food of comedy clubs!")
					(if egoIsPatti
						(Print "They are presently closed.")
					else
						(Print "They appear to be open.")
					)
				)
			)
		)
	)
)

(instance aDoor of AutoDoor
	(properties
		view				vComedyHutExterior
;		loop				lDoor
		x					53
		y					66
		entranceTo 		340
	)
)


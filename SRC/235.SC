;**
;**	Logics for room 235 -- Orchid cliff
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 9, 1989
;**


(script#				235)

(public
	rm235				0
)


(define	vLroom				(+ 1 235))
	(define	lLpickingOrchids	0)

;(define	vLfalling					713)
;(define	vPfalling					813)


(define	nearWestOrchids 	(& (ego onControl) cCyan))
(define	nearEastOrchids 	(& (ego onControl) cGreen))


(enum												;**	RoomScript state values
	intro
	getOrchids
	pluckOrchids
	gottum
)


(instance rm235 of Room
	(properties
		picture		235
		west			230
	)

	(method (init)

		(if (InRoom iOrchids)
			(Load VIEW vLroom)
		)

		(super init)

		(self
			setRegions		regFalling,
			setScript 		RoomScript
		)

		(ego
			posn 				43 124,
			loop				facingEast,
			init
		)
		(NormalEgo)
		(if egoState
			(ego observeControls cBlue)	;**	No falls if weird.
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(if (and	(& (ego onControl) cBlue)
					(== egoState NORMAL))
			(Notify regFalling green 300)
		)

		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look<up')
				(Print "From in here, you can barely see the sky.")
			)

			((Said 'look<down')
				(Print "Just step closer to the edge of the cliff.")
				(Print "(Heh, heh, heh.)" LOW)
			)

			((Said 'look>')
				(cond
					((Said '[/room]')
						(Print "You are in a small opening in the side of the volcano,
							far above the trail you left.
							In the distance, Nontoonyt Lagoon glistens greasily
							in the sunlight.")
					)

					((Said '/ground, floor')
						(Print "A few hardy weeds attempt to grow in solid rock.")
					)

					((Said '/ceiling, sky')
						(Print "From in here, you can barely see the sky.")
					)

					((Said '/city, beach, ocean, water')
						(Print "From here you have a spectacular view of the beach,
							but you are too far from the edge to see the city.")
						(Print "(Heh, heh, heh.)" LOW)
					)

					((Said '/plant')
						(Print "There are a few weeds growing in the floor,
							but a beautiful display of orchids climbing the righthand
							wall.")
					)

					((Said '/flower, rock, wall')
						(Print "Garlands of wild orchids festoon the otherwise
							bare rock walls.
							You bet your F.T.D. man wishes he were here!")
					)
				)
			)

			((Said 'get/plant')
				(Print "The only plants here that catch your eye are the orchids
					growing on the walls.")
			)

			((Said 'get, pick/orchid')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					(egoIsPatti
						(Print "You have no use for any other orchids, Patti;
							you'll always cherish those Larry gave you.")
					)
					((and	(not nearEastOrchids)
							(not nearWestOrchids))
						(Print "Move closer to the flowers near the cave opening.
							They are more beautiful since they catch more light.")
					)
					((and (== curTimer ORCHIDS)
							(== rgTimer 0))	;**	Picked 'em too long ago.
						(self changeState getOrchids)
					)
					((not (InRoom iOrchids))
						(Print "You need no more orchids.")
					)
					(else
						(self changeState getOrchids)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	Handle Larry plucking orchids:

			(getOrchids
				(HandsOff)
				(ego
					illegalBits 	0,
					setMotion		MoveTo (if nearWestOrchids 99 else 235) 96 self,
				)
			)

			(pluckOrchids
				(ego
					get				iOrchids,
					view				vLroom,
					loop				(if (< (ego x) 160) facingWest else facingEast),
					cel				0,
					cycleSpeed		1,
					setCycle			Forward,
				)
				(= cycles 44)
			)

			(gottum
				(cond
					((and (== curTimer ORCHIDS)
							(== rgTimer 0))	;**	Picked 'em too long ago.
						(Print "Yeah, good idea. 
							Those ones you picked before were getting pretty 
							droopy.")
					)
					((TstFlg scoredOrchids)
						(Print "Fortunately for you,
							there are plenty of orchids available.")
					)
					(else
						(SetFlg scoredOrchids)
						(theGame changeScore 25)
						(Print "You pluck a few fresh orchids from the walls
							of the crevice.")
					)
				)
				(NormalEgo)
				;**	Give him only 10 minutes to deliver the orchids to Patti.
				(SetRgTimer ORCHIDS 10 0)
				((Inventory at iOrchids)
					view		iOrchids,
				)
			)
		)
	)
)


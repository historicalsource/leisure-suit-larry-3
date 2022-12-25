;**
;**	Logics for room 523 -- east of the Waterfall
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				523)

(public
	rm523				0
)


(define walkingOnAir		(& (ego onControl )			cBlue))


(instance rm523 of Room
	(properties
		picture		523
		west			520
	)

	(method (init)
		(super init)

		(self
			setRegions		regFalling,
			setScript 		RoomScript
		)

		(NormalEgo)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)

		(if (and walkingOnAir
					(== egoState NORMAL))
			(Notify regFalling green 188)
		)
		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'use, tie/bra')
				(cond
					((not (ego has iBra))
						(DontHave)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(Print "You briefly consider using your bra to lower
							yourself over the cliff,
							but decide it's just too small to do any good.
							Perhaps it will be more useful later.")
					)
				)
			)

			((Said 'use, tie/hose')
				(cond
					((not (ego has iPantyhose))
						(Print "A good idea, but you forgot to wear your pantyhose
							when you got dressed back at the casino.")
					)
					(else
						(Print "A good idea, but there are no rocks here
							with a shape suitable for an anchor.")
					)
				)
			)

			((Said 'look>')
				(cond
					((Said '/rock')
						(Print "What unusual rock formations the lava flows
							have left around here.")
					)
					((Said '[/room]')
						(Print "A dangerous cliff may be directly north of you.")
					)
				)
			)

			((Said '(up<climb), climb/rock, arch')
				(Print "There is no way you could climb up that arch!
					It will never help you, Patti.")
			)

			((or	(Said '(climb, go)<(down, over)')
					(Said 'lower/me'))
				(Print "Yes, but how?")
			)

			((Said 'climb')
				(Print "You MIGHT be able to climb down this cliff.
					Try moving closer to the edge.")
				(Print "(Will you really \"fall\" for this old joke?)" LOW)
			)

			((Said 'pull, hold, get/vine, rope')
				(Print "The flowering vines are not sturdy enough to support you.")
			)

			((Said 'get, use/tree')
				(Print "The trees here are not close enough to the edge of the
					cliff to aid your descent.")
			)

			((Said '/plant')
				(Print "The plants here are of no use to you.")
			)

			((Said '/arch')
				(Print "The arch is impossible to climb.")
			)

			((Said '/flower')
				(Print "The flowers are pretty, but unnecessary.")
			)

			((Said 'jump')
				(Print "It is unnecessary to jump off this cliff.
					Just walk a little closer to the edge.")
			)
		)
	)
)


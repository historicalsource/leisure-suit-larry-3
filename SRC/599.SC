;**
;**	Logics for Locale 599 -- Patti's Post-Bamboo Quest
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 20, 1989
;**


(script#				599)

(public
	rm599				0
)


;(define	sJungleRegion				599)


(instance rm599 of Locale
	(method (init)
		(if (< normalEgoView 800)
			(= normalEgoView 800)
		)

		(super init)

		(if (not (OneOf prevRoomNum JUNGLE_ROOMS))
			(music
				number		sJungleRegion,
				loop			bgMusicLoops,
				play,
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'talk/man, person, woman')
				(Print "There is no one within miles of you, Patti.")
			)

			((Said 'use/nut')
				(Print "O.K. But how?")
			)

			((Said '/bra')
				(Print "That won't work here.")
			)

			((Said 'climb')
				(Print "That will not help, Patti!")
			)

			((Said '/vine')
				(Print "This entire area is filled with unusually shaped
					rocks and plants.")
				(Print "(None of which will help you!)" LOW)
			)

			((Said '/ground')
				(Print "Dirt, dirt everywhere.")
			)

			((and (ego has iCoconuts)
					(or	(Said 'drink/nut')
							(Said 'drink/milk')
							(Said 'drink/nut/milk')))
				(Print "Clever idea.")
				(Print "(But wrong!)" LOW)
			)

			((Said 'look>')
				(cond
					((Said '/up, sky')
						(Print "The air is clean and fresh.")
					)

					((Said '/rock')
						(Print "The centuries of lava flows have left unusual
							rock formations throughout this jungle.")
					)

					((Said '/ground')
						(Print "The bare dirt feels good under your feet.")
					)

					((Said '/tree, foliage')
						(Print "This warm tropical weather makes everything grow.")
					)
				)
			)
		)
	)
)



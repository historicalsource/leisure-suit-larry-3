;**
;**	Logics for Locale 299 -- Island Exterior
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 21, 1989
;**


(script#				299)

(public
	rm299				0
)


;(define	sVillageRegion				299)


(instance rm299 of Locale
	(method (init)
		(super init)

		(if (not (OneOf prevRoomNum VILLAGE_ROOMS))
			(music
				stop,
				number		sVillageRegion,
				loop			bgMusicLoops,
				play,
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'talk/man, person, woman')
				(Print "No one here is interested in talking to you.")
			)

			((Said 'cut, get/grass')
				(Print "This grass is unsuitable for your needs.")
			)

			((Said '/taxi')
				(Print "There are no taxis in this \"Leisure Suit Larry.\"")
			)

			((Said '/car, street')
				(Print "At least \"Natives, Inc.\05\" did something right:
					to \"maintain that native island feeling\" they
					banned vehicular traffic from the island;
					everyone walks everywhere.")
				(Print "(Including you!)" LOW)
			)

			((Said 'climb/tree')
				(Print "These ARE wonderful trees,
					but that's not something you should do right now.")
			)

			((Said 'remove, (take<off)/shoe')
				(Printf "You'd love to, but you'd %s!"
					(if egoIsPatti
						{rip your nylons}
					else
						{get your white socks dirty}
					)
				)
			)

			((Said 'look>')
				(cond
					((Said '/up, sky')
						(Print "The air blows in off the lagoon crisp and clean,
							bringing a slight taste of salt and
							the aroma of diesel slicks.")
					)

					((Said '/traffic, city')
						(Print "How nice it is that automobiles were never allowed
							here on the island!
							Everyone does aerobic walking all the time!")
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

